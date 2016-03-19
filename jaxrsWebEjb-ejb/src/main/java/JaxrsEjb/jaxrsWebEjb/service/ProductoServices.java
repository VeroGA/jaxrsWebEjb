/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package JaxrsEjb.jaxrsWebEjb.service;

import JaxrsEjb.jaxrsWebEjb.model.Producto;
import JaxrsEjb.jaxrsWebEjb.model.Venta;
import JaxrsEjb.jaxrsWebEjb.model.VentaDetalle;
import JaxrsEjb.jaxrsWebEjb.data.ProductoRepository;
import JaxrsEjb.jaxrsWebEjb.model.Compra;
import JaxrsEjb.jaxrsWebEjb.model.CompraDetalle;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
@LocalBean
public class ProductoServices {

	@Inject
	private Logger log;

	@PersistenceContext
	private EntityManager em;

	@Inject
	private ProductoRepository productoRepository;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void register(Producto producto) throws Exception {
		log.info("Registering " + producto.getNombre());
		if (!productoRepository.isExist(producto.getId())) {
			em.persist(producto);
		}else{
			//agregar a la tabla producto duplicado
		}
		log.info("Transaccion exitosa!!.");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteProducto(Producto producto) throws Exception {
		log.info("Sera eliminado el cliente con nombre: " + producto.getNombre());
		if (productoRepository.isExist(producto.getId())) {
			em.remove(producto);
		}else{
			log.info("El producto no existe!!.");
		}
		log.info("Transaccion exitosa!!.");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void realizarVenta(Venta venta) throws Exception {
		log.info("Se realizo Venta en el ProductoServices de parte del cliente " + venta.getDescripcion());
		ventaMasiva(venta);
		log.info("Transaccion exitosa!!.");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void realizarCompra(Compra compra) throws Exception {
		log.info("Se realizo la compra en el ProductoServices de parte del cliente " + compra.getDescripcion());
		compraMasiva(compra);
		log.info("Transaccion exitosa!!.");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void compraMasiva(Compra compra) throws Exception {
		try {
			log.info("Se realizo compra en el ProductoServices de parte del cliente " + compra.getDescripcion());
			List<CompraDetalle> detalles = compra.getCompra_detalle();
			for (int i = 0; i < detalles.size(); i++) {
				comprarProducto(detalles.get(i).getProducto(), detalles.get(i).getCantidad());
			}
			em.persist(compra);
		} catch (Exception e) {
			// posible producto null
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void comprarProducto(Producto producto, Long cantidad) {
		if (producto != null) {
			if (productoRepository.isExist(producto.getId())) {
				producto.setStock(producto.getStock() + cantidad);
				em.persist(producto);
			} else {
				producto.setStock(cantidad);
				em.persist(producto);
			}
		} else {
			// exception de null
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void ventaMasiva(Venta venta) throws Exception {
		try {
			log.info("Se realizo compra en el ProductoServices de parte del cliente " + venta.getDescripcion());
			List<VentaDetalle> detalles = venta.getVenta_detalle();
			for (int i = 0; i < detalles.size(); i++) {
				venderProducto(detalles.get(i).getProducto(), detalles.get(i).getCantidad());
			}
			em.persist(venta);
		} catch (Exception e) {
			// posible inexistencia de producto o stock insuficiente
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void venderProducto(Producto producto, Long cantidad) {
		if (producto != null) {
			if (productoRepository.isExist(producto.getId())) {
				if (producto.getStock() >= cantidad) {
					producto.setStock(producto.getStock() - cantidad);
					em.persist(producto);
				} else {
					// throws Excep; de cantidad superada
				}
			} else {
				// exception de no existe producto
			}
		} else {
			// exception de null
		}
	}
}
