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

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import JaxrsEjb.jaxrsWebEjb.model.Cliente;
import JaxrsEjb.jaxrsWebEjb.model.Pago;
import JaxrsEjb.jaxrsWebEjb.model.Venta;

import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
@LocalBean
public class ClienteServices {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private Logger log;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void register(Cliente cliente) throws Exception {
		log.info("Registering " + cliente.getName());
		em.persist(cliente);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteCliente(Cliente cliente) throws Exception {
		log.info("Sera eliminado el cliente con nombre: " + cliente.getName());
		em.remove(cliente);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void realizarPago(Pago pago, Venta venta, Cliente cliente) throws Exception {
		System.out.println("Llego a entrar en la transaccion?..");
		try {
			pago.setCliente(cliente);
			pago.setVenta(venta);
			//pago.setMonto(venta.getTotal());
			
			log.info("Se realizo el pago en el ClienteServices de parte del cliente " + pago.getCliente().getName());
			em.persist(pago);
			log.info("Transaccion Exitosa!");
		} catch (Exception e) {
			log.info("Ocurrio un error durante el Pago: " + e.getMessage());
		}
	}

}
