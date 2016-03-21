package JaxrsEjb.jaxrsWebEjb.service;

import JaxrsEjb.jaxrsWebEjb.model.Producto;
import JaxrsEjb.jaxrsWebEjb.model.Venta;
import JaxrsEjb.jaxrsWebEjb.model.VentaDetalle;
import JaxrsEjb.jaxrsWebEjb.dummies.VentaDummy;
import JaxrsEjb.jaxrsWebEjb.dummies.VentaDetalleDummy;
import JaxrsEjb.jaxrsWebEjb.model.Cliente;
import JaxrsEjb.jaxrsWebEjb.data.ProductoRepository;
import JaxrsEjb.jaxrsWebEjb.data.ClienteRepository;
import JaxrsEjb.jaxrsWebEjb.model.Compra;
import JaxrsEjb.jaxrsWebEjb.model.CompraDetalle;
import JaxrsEjb.jaxrsWebEjb.model.ProductoDuplicado;
import JaxrsEjb.jaxrsWebEjb.exceptions.ProductoDuplicadoException;
import JaxrsEjb.jaxrsWebEjb.exceptions.VentaDetalleCantidadException;
import JaxrsEjb.jaxrsWebEjb.exceptions.InsuficienciaStockVentaDetalleException;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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

	@Resource
	private EJBContext context;

	@PersistenceContext
	private EntityManager em;

	@Inject
	private ProductoRepository productoRepository;

	@Inject
	private ClienteRepository clienteRepository;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void register(Producto producto) throws ProductoDuplicadoException, Exception {
		log.info("Registering " + producto.getNombre());
		if (productoRepository.findByName(producto.getNombre()) != null) {
			em.persist(producto);
		} else {
			throw new ProductoDuplicadoException("Violacion de unique nombre");
		}
		log.info("Transaccion exitosa!!.");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteProducto(Producto producto) throws Exception {
		log.info("Sera eliminado el cliente con nombre: " + producto.getNombre());
		if (productoRepository.isExist(producto.getId())) {
			em.remove(producto);
		} else {
			log.info("El producto no existe!!.");
		}
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
			// List<CompraDetalle> detalles = compra.getCompra_detalle();
			// for (int i = 0; i < detalles.size(); i++) {
			// comprarProducto(detalles.get(i).getProducto(),
			// detalles.get(i).getCantidad());
			// }
			em.persist(compra);
		} catch (Exception e) {
			// posible producto null
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void comprarProducto(Producto producto, Integer cantidad) {
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
	public void realizarVenta(VentaDummy ventad) throws Exception {
		log.info("Se realizo Venta en el ProductoServices de la venta: " + ventad.getDescripcion());

		try {
			ventaMasiva(ventad);
		} catch (Exception e) {
			log.info(e.getMessage());
			//context.setRollbackOnly();
		}

		log.info("Venta realizada exitosamente!!.");
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void ventaMasiva(VentaDummy ventad) throws VentaDetalleCantidadException, InsuficienciaStockVentaDetalleException, Exception {
		log.info("Se realizara la venta Masiva en el ProductoServices de la venta: " + ventad.getDescripcion());

		List<VentaDetalleDummy> detalles = ventad.getVenta_detalle();

		Venta venta = guardarVenta(ventad);// le paso el ventadummy para
											// crear la venta
		for (int i = 0; i < detalles.size(); i++) {
			
			detalles.get(i).setVenta_id(venta.getId());
			Producto producto = productoRepository.findById(detalles.get(i).getProducto_id());
			venderProducto(venta, producto, detalles.get(i).getCantidad());

		}

	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void venderProducto(Venta venta, Producto producto, Integer cantidad)
			throws VentaDetalleCantidadException, InsuficienciaStockVentaDetalleException, Exception {
		System.out.println("Vendiendo producto: " + producto.getNombre());
		if (producto != null) {
			if (cantidad > 0) {
				if (producto.getStock() >= cantidad) {
					
					producto.setStock(producto.getStock() - cantidad);
					em.merge(producto);
					//em.flush();
					
					VentaDetalle ventaDetalle = new VentaDetalle(cantidad, venta, producto);
					guardarVentaDetalle(ventaDetalle);
				} else {
					log.info("Se supero la cantidad en stock..");
					throw new InsuficienciaStockVentaDetalleException(
							"Se supero la cantidad en Stock!. STOCK INVALIDO.");
				}
			} else {
				log.info("Cantidad menor a cero!.");
				throw new VentaDetalleCantidadException("Cantidad ivalida para la venta!.");
			}
		} else {
			log.info("Producto no encontrado durante la venta");
			throw new Exception("Producto Nulo!.");
		}
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Venta guardarVenta(VentaDummy ventad) throws Exception {
		log.info("Guardando la venta " + ventad.getDescripcion() + "...");

		Cliente cliente = clienteRepository.findById(ventad.getCliente_id());
		Venta venta = new Venta(ventad.getDescripcion(), cliente, 0);
		venta = em.merge(venta);
		//em.flush();
		log.info("Transaccion exitosa!!.");
		return venta;
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void guardarVentaDetalle(VentaDetalle ventadetalle) throws Exception {
		log.info("Guardando la venta detalle del producto: " + ventadetalle.getProducto().getNombre()
				+ "... con cantidad: " + ventadetalle.getCantidad() + "...");

		em.persist(ventadetalle);
		em.flush();
		
		log.info("Transaccion exitosa!!.");
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void guardarProductoDuplicado(Producto producto) throws Exception {
		// log.info("Guardando producto duplicado: " + producto.getNombre() +
		// "... con cantidad: " + cantidad + "...");
		// pDuplicado.setProducto(producto);
		// pDuplicado.setCantidad(cantidad);
		// em.persist(pDuplicado);
		log.info("Guardado!! producto duplicado por id: " + producto.getId());
	}
}
