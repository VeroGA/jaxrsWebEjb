package JaxrsEjb.jaxrsWebEjb.service;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Producto;
import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Venta;
import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Compra;
import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Proveedor;

import JaxrsEjb.jaxrsWebEjb.dummies.VentaDummy;
import JaxrsEjb.jaxrsWebEjb.dummies.CompraDetalleDummy;
import JaxrsEjb.jaxrsWebEjb.dummies.CompraDummy;
import JaxrsEjb.jaxrsWebEjb.dummies.ProductoDummy;
import JaxrsEjb.jaxrsWebEjb.dummies.VentaDetalleDummy;

import JaxrsEjb.jaxrsWebEjb.mybatis.manager.ProductoManager;
import JaxrsEjb.jaxrsWebEjb.mybatis.manager.ProveedorManager;
import JaxrsEjb.jaxrsWebEjb.mybatis.manager.VentaManager;
import JaxrsEjb.jaxrsWebEjb.mybatis.manager.CompraManager;

import JaxrsEjb.jaxrsWebEjb.data.ProveedorRepository;
import JaxrsEjb.jaxrsWebEjb.data.ProductoDuplicadoRepository;

import JaxrsEjb.jaxrsWebEjb.model.ProductoDuplicado;

import JaxrsEjb.jaxrsWebEjb.exceptions.ProductoDuplicadoException;
import JaxrsEjb.jaxrsWebEjb.exceptions.VentaDetalleCantidadException;
import JaxrsEjb.jaxrsWebEjb.exceptions.InsuficienciaStockVentaDetalleException;

import javax.annotation.Resource;
import javax.ejb.EJB;
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
	
	@EJB
	private ProductoManager productoManager;
	
	@EJB
	private ProveedorManager proveedorManager;
	
	@EJB
	private VentaManager ventaManager;
	
	@EJB
	private CompraManager compraManager;

	@Inject
	private ProductoDuplicadoRepository productoDuplicadoRepository;

	@Inject
	private ProveedorRepository proveedorRepository;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void register(Producto producto) throws ProductoDuplicadoException, Exception {
		log.info("Registering " + producto.getNombre());

		try {

			if (!productoManager.isExistByName(producto.getNombre())) {
				persistir(producto);
			} else {
				throw new ProductoDuplicadoException("Ocurrio una violacion de constraint unique!.");
			}

		} catch(ProductoDuplicadoException e) {
			log.info("El producto ya existe, guardando duplicado!..");
			guardarProductoDuplicado(producto);
		} catch(Exception e) {
			log.info("Producto constrain violation exception!. Exception: " + e.getMessage());
		}

		log.info("Transaccion exitosa!!.");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void persistir(Producto producto) throws Exception {
		log.info("Guardando... " + producto.getNombre());
		productoManager.newProducto(producto);
		//em.persist(producto);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteProducto(Producto producto) throws Exception {
		log.info("Sera eliminado el cliente con nombre: " + producto.getNombre());
		//if (productoRepository.isExist(producto.getId())) {

		if (productoManager.isExist(producto.getId())) {
			productoManager.deleteProducto(producto.getId());
			//em.remove(producto);
		} else {
			log.info("El producto no existe!!.");
		}
		log.info("Transaccion exitosa!!.");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void realizarCompra(CompraDummy comprad) throws Exception {
		log.info("Se realizara la Compra en el ProductoServices de la compra: " + comprad.getDescripcion());

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

		for (int i = 0; i < detalles.size(); i++) {

			Producto producto = productoManager.getProductoById(Integer.valueOf(detalles.get(i).getProducto_id().toString()));
			comprarProducto(producto, detalles.get(i).getCantidad());

		}
		
		guardarCompra(comprad);

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void comprarProducto(Producto producto, Integer cantidad) throws Exception {
		
		log.info("Comprando producto: " + producto.getNombre());
		
		if (producto != null) {
			if (cantidad > 0) {

				producto.setStock(producto.getStock() + cantidad);
				productoManager.updateProducto(producto);
				
			} else {
				log.info("Cantidad menor a cero!.");
				throw new Exception("Cantidad invalida!.");
			}
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Compra guardarCompra(CompraDummy comprad) throws Exception {
		
		log.info("Guardando la compra " + comprad.getDescripcion() + "...");
		
		for(int i = 0; i<comprad.getCompra_detalle().size(); i++){
			log.info("Extrayendo detalles del producto: " + productoManager.getProductoById(Integer.valueOf(comprad.getCompra_detalle().get(i).getProducto_id().toString())).getNombre()
					+ "... con cantidad: " + comprad.getCompra_detalle().get(i).getCantidad() + ".");
			
			comprad.setTotal(comprad.getTotal() + comprad.getCompra_detalle().get(i).getCantidad() * productoManager.getProductoById(Integer.valueOf(comprad.getCompra_detalle().get(i).getProducto_id().toString())).getPrecio());
		}
		
		Compra compra = new Compra(comprad);
		
		compraManager.newCompra(compra);
		
		log.info("Transaccion exitosa!!.");
		return compra;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void realizarVenta(VentaDummy ventad) throws Exception {
		log.info("Se realizara la Venta en el ProductoServices de la venta: " + ventad.getDescripcion());

		try {
			ventaMasiva(ventad);
			log.info("Venta realizada exitosamente!!.");
		} catch (Exception e) {
			log.info("Ocurrio un error: " + e.getMessage());
			context.setRollbackOnly();
		}
		
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void ventaMasiva(VentaDummy ventad)
			throws VentaDetalleCantidadException, InsuficienciaStockVentaDetalleException, Exception {
		
		log.info("Se realizara la venta Masiva en el ProductoServices de la venta: " + ventad.getDescripcion());

		List<VentaDetalleDummy> detalles = ventad.getVenta_detalle();

		for (int i = 0; i < detalles.size(); i++) {

			Producto producto = productoManager.getProductoById(Integer.valueOf(detalles.get(i).getProducto_id().toString()));
			venderProducto(producto, detalles.get(i).getCantidad());

		}

		guardarVenta(ventad);

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void venderProducto(Producto producto, Integer cantidad)
			throws VentaDetalleCantidadException, InsuficienciaStockVentaDetalleException, Exception {
		
		log.info("Vendiendo producto: " + producto.getNombre());
		
		if (producto != null) {
			if (cantidad > 0) {
				if (producto.getStock() >= cantidad) {

					producto.setStock(producto.getStock() - cantidad);
					productoManager.updateProducto(producto);

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
		
		for(int i = 0; i<ventad.getVenta_detalle().size(); i++){
			log.info("Extrayendo detalles del producto: " + productoManager.getProductoById(Integer.valueOf(ventad.getVenta_detalle().get(i).getProducto_id().toString())).getNombre()
					+ "... con cantidad: " + ventad.getVenta_detalle().get(i).getCantidad() + ".");
			
			ventad.setTotal(ventad.getTotal() + ventad.getVenta_detalle().get(i).getCantidad() * productoManager.getProductoById(Integer.valueOf(ventad.getVenta_detalle().get(i).getProducto_id().toString())).getPrecio());
		}
		
		Venta venta = new Venta(ventad);
		
		ventaManager.newVenta(venta);

		log.info("Transaccion exitosa!!.");
		return venta;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void guardarProductoDuplicado(Producto producto) throws Exception {
		log.info("Guardando producto duplicado para producto: " + producto.getNombre());

		ProductoDuplicado productoDuplicado = productoDuplicadoRepository.findByProducto(producto.getNombre());
		Producto productoOriginal = productoManager.getProductoByName(producto.getNombre());
		if (productoDuplicado == null) {
			productoDuplicado = new ProductoDuplicado(productoOriginal, 1, proveedorRepository.findById( Long.valueOf(producto.getProveedor().toString())) );
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
			log.info("Producto Json: " + linea);

			try {
				ProductoDummy productod = gson.fromJson(linea, ProductoDummy.class);
				Proveedor proveedor = proveedorManager.getProveedorById(Integer.valueOf(productod.getProveedor_id().toString()));
				log.info("Producto procesado: " + productod.getNombre());
				Producto producto = new Producto(productod.getNombre().trim(), productod.getPrecio(), productod.getStock(), productod.getDescripcion(), proveedor.getId());

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
