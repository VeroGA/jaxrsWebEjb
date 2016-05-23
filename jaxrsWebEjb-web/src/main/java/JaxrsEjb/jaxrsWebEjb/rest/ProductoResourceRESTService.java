package JaxrsEjb.jaxrsWebEjb.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import JaxrsEjb.jaxrsWebEjb.annotation.LoginRequired;
import JaxrsEjb.jaxrsWebEjb.annotation.LoginRequired.Roles;
import JaxrsEjb.jaxrsWebEjb.data.ProductoRepository;
import JaxrsEjb.jaxrsWebEjb.model.Producto;
import JaxrsEjb.jaxrsWebEjb.service.ProductoServices;
import JaxrsEjb.jaxrsWebEjb.service.ProductoSingleton;
import JaxrsEjb.jaxrsWebEjb.service.ProductoStateful;
import JaxrsEjb.jaxrsWebEjb.dummies.CompraDummy;
import JaxrsEjb.jaxrsWebEjb.dummies.DireccionDummy;
import JaxrsEjb.jaxrsWebEjb.dummies.ProductoDummy;
import JaxrsEjb.jaxrsWebEjb.dummies.VentaDummy;

@Path("/productos")
@RequestScoped
public class ProductoResourceRESTService {
	@Inject
	private Logger log;

	@Inject
	private Validator validator;

	@Inject
	private ProductoRepository productoRepository;

	@EJB
	private ProductoServices productoServices;

	@EJB
	private ProductoStateful productoStateful;

	@EJB
	private ProductoSingleton productoSingleton;

	@POST

	@Path("/session")

	@Produces(MediaType.APPLICATION_JSON)
	public Long crearSessionStateful() {
		return productoSingleton.crear();
	}

	@DELETE

	@Path("/session/{id:[0-9][0-9]*}")

	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarSessionStateful(@PathParam("id") long id) {
		Response.ResponseBuilder builder = null;

		productoSingleton.remover(id);

		builder = Response.ok();
		return builder.build();
	}

	@GET
	@Path("/session/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductoDummy> buscarTodos(@PathParam("id") long id) {

		ProductoStateful productoStateful = productoSingleton.obtener(id);
		List<ProductoDummy> list = new ArrayList<ProductoDummy>();
		int cantidad = 1;

		productoStateful.iniciar();

		while (productoStateful.hasNext() && cantidad <= 100) {
			Producto p = productoStateful.nextProducto();

			if (p != null)
				list.add(new ProductoDummy(p));

			cantidad++;
		}

		return list;
	}

	/*
	 * @GET
	 * 
	 * @Produces("multipart/mixed")
	 * 
	 * @PartType("application/xml") public List<ProductoDummy>
	 * listAllProductos() throws NamingException{ List<ProductoDummy> list = new
	 * ArrayList<ProductoDummy>();
	 * 
	 * try {
	 * 
	 * int cantidad = 1;
	 * 
	 * productoStateful.iniciar();
	 * 
	 * while (productoStateful.hasNext() && cantidad <= 100) { Producto p =
	 * productoStateful.nextProducto();
	 * 
	 * if (p != null) list.add(new ProductoDummy(p));
	 * 
	 * cantidad++; }
	 * 
	 * } catch (Exception e) { log.info(
	 * "Ocurrio un error al intentar leer los prodcutos: " + e.getMessage()); }
	 * 
	 * return list; }
	 */

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Producto lookupProductoById(@PathParam("id") long id) {
		Producto producto = productoRepository.findById(id);
		if (producto == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return producto;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createProducto(Producto producto) {

		Response.ResponseBuilder builder = null;

		try {

			productoServices.register(new JaxrsEjb.jaxrsWebEjb.mybatis.bean.Producto(producto));

			// Create an "ok" response
			builder = Response.ok();
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
		} catch (ValidationException e) {
			// Handle the unique constrain violation
		} catch (Exception e) {
			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
		}

		return builder.build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public void deleteProductoById(@PathParam("id") long id) throws Exception {
		Producto producto = productoRepository.findById(id);
		if (producto == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		productoServices.deleteProducto(new JaxrsEjb.jaxrsWebEjb.mybatis.bean.Producto(producto));
		Response.accepted();
	}

	@POST
	@Path("/venta")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@LoginRequired(rol = Roles.VENT)
	public Response realizarVenta(VentaDummy ventadummy) {

		Response.ResponseBuilder builder = null;

		try {

			log.info("Se realiza la venta: " + ventadummy.getDescripcion());

			productoServices.realizarVenta(ventadummy);

			builder = Response.ok();
		} catch (Exception e) {
			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
		}

		return builder.build();
	}

	@POST
	@Path("/compra")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@LoginRequired(rol = Roles.COMP)
	public Response realizarCompra(CompraDummy compradummy) {

		Response.ResponseBuilder builder = null;

		try {

			log.info("Se realiza la compra: " + compradummy.getDescripcion());

			productoServices.realizarCompra(compradummy);

			// Create an "ok" response
			builder = Response.ok();
		} catch (Exception e) {
			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
		}

		return builder.build();
	}

	@POST
	@Path("/masivos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response realizarCargaProductos(DireccionDummy direccionDummy) {

		Response.ResponseBuilder builder = null;

		try {

			log.info("Se realiza la carga masiva desde: " + direccionDummy.getDireccion());

			productoServices.cargaProductos(direccionDummy.getDireccion());

			builder = Response.ok();

		} catch (Exception e) {
			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
		}

		return builder.build();
	}
}
