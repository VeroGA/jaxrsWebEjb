package JaxrsEjb.jaxrsWebEjb.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Cliente;
import JaxrsEjb.jaxrsWebEjb.service.ClienteServices;
import JaxrsEjb.jaxrsWebEjb.service.ProductoServices;
import JaxrsEjb.jaxrsWebEjb.model.Venta;
import JaxrsEjb.jaxrsWebEjb.annotation.LoginRequired;
import JaxrsEjb.jaxrsWebEjb.annotation.LoginRequired.Roles;
import JaxrsEjb.jaxrsWebEjb.data.VentaRepository;
import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Pago;

@Path("/clientes")
@RequestScoped
public class ClienteResourceRESTService {
	@Inject
	private Logger log;

	@Inject
	private VentaRepository ventaRepository;

	@EJB
	private ClienteServices clienteServices;

	@EJB
	private ProductoServices productoServices;
	
	@GET
	@Path("/get")
	@LoginRequired(rol = Roles.COMP)
	public Response addUser(@HeaderParam("auth") String userAgent) {

		return Response.status(200)
			.entity("addUser is called, userAgent : " + userAgent)
			.build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Cliente> listAllClientes() throws Exception {
		return clienteServices.getAll();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Cliente lookupClienteById(@PathParam("id") Integer id) {
		Cliente cliente = clienteServices.buscarCliente(id);// repository.findById(id);
		if (cliente == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return cliente;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCliente(Cliente cliente) {

		Response.ResponseBuilder builder = null;

		try {

			clienteServices.register(cliente);

			// Create an "ok" response
			builder = Response.ok();
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("email", "Email taken");
			builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
		} catch (Exception e) {
			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
		}

		return builder.build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateCliente(Cliente cliente) throws Exception {

		if (clienteServices.isExist(cliente.getId())) {
			log.info("No se encontro el Cliente con id=" + cliente.getId());
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		Integer id = clienteServices.update(cliente);
		
		log.info("Cliente actualizado exitosamente..!" + id.toString());
		Response.accepted();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public void deleteClienteById(@PathParam("id") Integer id) throws Exception {
		Cliente cliente = clienteServices.buscarCliente(id);

		if (cliente == null) {
			log.info("No se encontro el Cliente con id=" + id.toString());
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		cliente = clienteServices.findByEmail(cliente.getEmail());

		clienteServices.deleteCliente(cliente);
		Response.noContent();
	}

	@POST
	@Path("/{idcliente:[0-9][0-9]*}/venta/{idventa:[0-9][0-9]*}/pago")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response realizarPago(@PathParam("idcliente") Integer idcliente, @PathParam("idventa") Integer idventa,
			Pago pago) { // aca se debe recibir un pago para ralizarse en json

		Response.ResponseBuilder builder = null;

		try {

			Cliente cliente = clienteServices.buscarCliente(idcliente);
			Venta venta = ventaRepository.findById(Long.valueOf(idventa.toString()));//cambio proximamente

			log.info(cliente.getName() + " " + cliente.getEmail());
			log.info("Se realizo el pago: " + pago.getObservacion() + " del cliente: " + cliente.getName() + " "
					+ cliente.getEmail() + " a referente a la venta " + venta.getDescripcion());

			clienteServices.realizarPago(pago, venta, cliente);

			// Create an "ok" response
			builder = Response.ok();
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("email", "Email taken");
			builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
		} catch (Exception e) {
			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
		}

		return builder.build();
	}
}
