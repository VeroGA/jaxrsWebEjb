package JaxrsEjb.jaxrsWebEjb.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Usuario;
import JaxrsEjb.jaxrsWebEjb.service.UsuarioServices;

@Path("/usuarios")
@RequestScoped
public class UsuarioResourceRESTService {
	@Inject
	private Logger log;

	@EJB
	private UsuarioServices usuarioServices;

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario lookupUsuarioById(@PathParam("id") Integer id) {
		Usuario usuario = usuarioServices.buscarUsuario(id);
		if (usuario == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return usuario;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUsuario(Usuario usuario) {

		Response.ResponseBuilder builder = null;

		try {

			usuarioServices.crearUsuario(usuario);

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

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String login(Usuario usuario) throws Exception {
		
		if (!usuarioServices.isExist(usuario.getUsername())) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		String token  = usuarioServices.auntenticar(usuario.getUsername(), usuario.getPassword());
		
		log.info("Usuario logueado exitosamente..!  " + token);
		
		return token;
	}

	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(String token) throws Exception {
		
		usuarioServices.desauntenticar(token);
		
		log.info("Usuario deslogueado exitosamente..!");
		
		return Response.accepted().build();
	}
}
