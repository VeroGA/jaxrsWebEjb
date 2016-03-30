package JaxrsEjb.jaxrsWebEjb.service;

import JaxrsEjb.jaxrsWebEjb.model.Producto;
import JaxrsEjb.jaxrsWebEjb.model.Venta;
import JaxrsEjb.jaxrsWebEjb.model.VentaDetalle;
import JaxrsEjb.jaxrsWebEjb.dummies.VentaDummy;
import JaxrsEjb.jaxrsWebEjb.dummies.CompraDetalleDummy;
import JaxrsEjb.jaxrsWebEjb.dummies.CompraDummy;
import JaxrsEjb.jaxrsWebEjb.dummies.ProductoDummy;
import JaxrsEjb.jaxrsWebEjb.dummies.VentaDetalleDummy;
import JaxrsEjb.jaxrsWebEjb.model.Cliente;
import JaxrsEjb.jaxrsWebEjb.data.ProductoRepository;
import JaxrsEjb.jaxrsWebEjb.data.ProveedorRepository;
import JaxrsEjb.jaxrsWebEjb.data.ClienteRepository;
import JaxrsEjb.jaxrsWebEjb.data.ProductoDuplicadoRepository;
import JaxrsEjb.jaxrsWebEjb.model.Compra;
import JaxrsEjb.jaxrsWebEjb.model.CompraDetalle;
import JaxrsEjb.jaxrsWebEjb.model.ProductoDuplicado;
import JaxrsEjb.jaxrsWebEjb.model.Proveedor;
import JaxrsEjb.jaxrsWebEjb.exceptions.ProductoDuplicadoException;
import JaxrsEjb.jaxrsWebEjb.exceptions.VentaDetalleCantidadException;
import JaxrsEjb.jaxrsWebEjb.exceptions.InsuficienciaStockVentaDetalleException;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

//import org.hibernate.Criteria;
//import org.hibernate.Session;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

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

	@Inject
	private ProductoDuplicadoRepository productoDuplicadoRepository;

	@Inject
	private ProveedorRepository proveedorRepository;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void register(Producto producto) throws ProductoDuplicadoException, Exception {
		log.info("Registering " + producto.getNombre());

		try {

			if (!productoRepository.findByName(producto.getNombre())) {
				persistir(producto);
			} else {
				log.info("Lanzando la excepcion!.");
				throw new ProductoDuplicadoException("Ocurrio una violacion de constraint unique!.");
			}

		} catch(ProductoDuplicadoException e) {
			log.info("Lanzando la excepcion!.");
			guardarProductoDuplicado(producto);
		}

		log.info("Transaccion exitosa!!.");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void persistir(Producto producto) throws Exception {
		log.info("Guardando... " + producto.getNombre());

		em.persist(producto);
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
	public void realizarCompra(CompraDummy comprad) throws Exception {
		log.info("Se realizo Compra en el ProductoServices de la compra: " + comprad.getDescripcion());

		try {
			compraMasiva(comprad);
		} catch (Exception e) {
			log.info("Ocurrio un error: " + e.getMessage());
			context.setRollbackOnly();
		}
		log.info("Compra realizada exitosamente!!.");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void compraMasiva(CompraDummy comprad) throws Exception {
		log.info("Se realizara la compra Masiva en el ProductoServices de la compra: " + comprad.getDescripcion());

		List<CompraDetalleDummy> detalles = comprad.getCompra_detalle();

		Compra compra = guardarCompra(comprad);

		for (int i = 0; i < detalles.size(); i++) {

			Producto producto = productoRepository.findById(detalles.get(i).getProducto_id());
			comprarProducto(compra, producto, detalles.get(i).getCantidad());

		}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void comprarProducto(Compra compra, Producto producto, Integer cantidad) throws Exception {
		System.out.println("Comprando producto: " + producto.getNombre());
		if (producto != null) {
			if (cantidad > 0) {

				producto.setStock(producto.getStock() + cantidad);
				em.merge(producto);

				CompraDetalle compraDetalle = new CompraDetalle(cantidad, compra, producto);
				guardarCompraDetalle(compraDetalle, compra);
			} else {
				log.info("Cantidad menor a cero!.");
				throw new Exception("Cantidad invalida!.");
			}
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Compra guardarCompra(CompraDummy comprad) throws Exception {
		log.info("Guardando la compra " + comprad.getDescripcion() + "...");

		Proveedor proveedor = proveedorRepository.findById(comprad.getProveedor_id());
		Compra compra = new Compra(proveedor, comprad.getDescripcion(), 0);
		compra = em.merge(compra);

		log.info("Transaccion exitosa!!.");
		return compra;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void guardarCompraDetalle(CompraDetalle compradetalle, Compra compra) throws Exception {
		log.info("Guardando la compra detalle del producto: " + compradetalle.getProducto().getNombre()
				+ "... con cantidad: " + compradetalle.getCantidad() + "...");

		em.persist(compradetalle);
		compra.setTotal(compra.getTotal() + compradetalle.getCantidad() * compradetalle.getProducto().getPrecio());
		em.merge(compra);

		log.info("Transaccion exitosa!!.");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void realizarVenta(VentaDummy ventad) throws Exception {
		log.info("Se realizo Venta en el ProductoServices de la venta: " + ventad.getDescripcion());

		try {
			ventaMasiva(ventad);
		} catch (Exception e) {
			log.info("Ocurrio un error: " + e.getMessage());
			context.setRollbackOnly();
		}

		log.info("Venta realizada exitosamente!!.");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void ventaMasiva(VentaDummy ventad)
			throws VentaDetalleCantidadException, InsuficienciaStockVentaDetalleException, Exception {
		log.info("Se realizara la venta Masiva en el ProductoServices de la venta: " + ventad.getDescripcion());

		List<VentaDetalleDummy> detalles = ventad.getVenta_detalle();

		Venta venta = guardarVenta(ventad);

		for (int i = 0; i < detalles.size(); i++) {

			Producto producto = productoRepository.findById(detalles.get(i).getProducto_id());
			venderProducto(venta, producto, detalles.get(i).getCantidad());

		}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void venderProducto(Venta venta, Producto producto, Integer cantidad)
			throws VentaDetalleCantidadException, InsuficienciaStockVentaDetalleException, Exception {
		System.out.println("Vendiendo producto: " + producto.getNombre());
		if (producto != null) {
			if (cantidad > 0) {
				if (producto.getStock() >= cantidad) {

					producto.setStock(producto.getStock() - cantidad);
					em.merge(producto);

					VentaDetalle ventaDetalle = new VentaDetalle(cantidad, venta, producto);
					guardarVentaDetalle(ventaDetalle, venta);
				} else {
					log.info("Se supero la cantidad en stock..");
					throw new InsuficienciaStockVentaDetalleException(
							"Se supero la cantidad en Stock!. STOCK INVALIDO.");
				}
			} else {
				log.info("Cantidad menor a cero!.");
				throw new VentaDetalleCantidadException("Cantidad ivalida para la venta!.");
			}
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Venta guardarVenta(VentaDummy ventad) throws Exception {
		log.info("Guardando la venta " + ventad.getDescripcion() + "...");

		Cliente cliente = clienteRepository.findById(ventad.getCliente_id());
		Venta venta = new Venta(ventad.getDescripcion(), cliente, 0);
		venta = em.merge(venta);

		log.info("Transaccion exitosa!!.");
		return venta;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void guardarVentaDetalle(VentaDetalle ventadetalle, Venta venta) throws Exception {
		log.info("Guardando la venta detalle del producto: " + ventadetalle.getProducto().getNombre()
				+ "... con cantidad: " + ventadetalle.getCantidad() + "...");

		em.persist(ventadetalle);
		venta.setTotal(venta.getTotal() + ventadetalle.getCantidad() * ventadetalle.getProducto().getPrecio());
		em.merge(venta);

		log.info("Transaccion exitosa!!.");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void guardarProductoDuplicado(Producto producto) throws Exception {
		log.info("Guardando producto duplicado para producto: " + producto.getNombre());

		ProductoDuplicado productoDuplicado = productoDuplicadoRepository.findByProducto(producto);
		Producto productoOriginal = productoDuplicadoRepository.findProductoByName(producto.getNombre());
		if (productoDuplicado == null) {
			productoDuplicado = new ProductoDuplicado(productoOriginal, 1);
			em.persist(productoDuplicado);
		} else {
			productoDuplicado.setCantidad(productoDuplicado.getCantidad() + 1);
			em.merge(productoDuplicado);
		}

		log.info("Guardado!! producto duplicado por id: " + productoDuplicado.getProducto().getId());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void cargaProductos(String direccion) throws IllegalStateException, IOException {
		FileReader fr;
		BufferedReader br;
		File archivo;
		String linea;
		Integer cantidadTotal = 0;

		direccion = direccion.replaceAll("\"", "");
		archivo = new File(direccion);
		String errores = new String();
		fr = new FileReader(archivo);
		br = new BufferedReader(fr);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

		int cantErrores = 0;

		while (br.ready()) {
			linea = br.readLine();
			cantidadTotal++;
			System.out.println(linea);

			try {
				ProductoDummy productod = gson.fromJson(linea, ProductoDummy.class);
				Proveedor proveedor = proveedorRepository.findById(productod.getProveedor_id());
				System.out.println(productod.getNombre().trim());
				Producto producto = new Producto(productod.getNombre().trim(), productod.getPrecio(),
						productod.getStock(), proveedor, productod.getDescripcion());

				try {

					register(producto);

				} catch (ProductoDuplicadoException e) {
					errores = errores + "Error producto duplicado: " + cantidadTotal
							+ " agregado a tabla de productos duplicados \n";

					// guardarProductoDuplicado(producto);

					cantErrores++;
				} catch (Exception e) {
					log.info("Lanzando la excepcion!.");
					guardarProductoDuplicado(producto);
					log.info("Error: " + e.getLocalizedMessage());
				}

			} catch (JsonSyntaxException e) {
				errores += "Error de sintaxis";
				cantErrores++;
			} catch (Exception e) {
				// context.setRollbackOnly();
				log.info("Se presentaron problemas a la hora de cargar los productos: " + e.getMessage());
			}

		}

		log.info("TOTAL: " + cantidadTotal);
		log.info("TOTAL EROORES: " + cantErrores);
		log.info("Errores: " + errores);

		fr.close();

		if (cantErrores > 0) {
			context.setRollbackOnly();
			log.info(errores);
		}
		log.info("Carga Exitosa," + cantidadTotal + " productos cargados");
	}
}
