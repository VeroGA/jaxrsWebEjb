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
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import JaxrsEjb.jaxrsWebEjb.data.ProveedorRepository;
import JaxrsEjb.jaxrsWebEjb.model.Cliente;
import JaxrsEjb.jaxrsWebEjb.model.Proveedor;
import JaxrsEjb.jaxrsWebEjb.service.ProveedorServices;

@Path("/proveedores")
@RequestScoped
public class ProveedorResourceRESTService {
    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private ProveedorRepository repository;

    @Inject
    ProveedorServices registration;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Proveedor> listAllProveedores() {
        return repository.findAllOrderedByName();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Proveedor lookupProveedorById(@PathParam("id") long id) {
    	Proveedor proveedor = repository.findById(id);
        if (proveedor == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return proveedor;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProveedor(Proveedor proveedor) {

        Response.ResponseBuilder builder = null;

        try {
            // Validates proveedor using bean validation
            validateProveedor(proveedor);

            registration.register(proveedor);

            // Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            // Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
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
    
	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public void deleteProveedorById(@PathParam("id") long id) throws Exception {
		//Response.ResponseBuilder builder = null;
		Proveedor proveedor = repository.findById(id);
		if (proveedor == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		registration.deleteProveedor(proveedor);
		Response.accepted();
	}

    private void validateProveedor(Proveedor proveedor) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Proveedor>> violations = validator.validate(proveedor);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        // Check the uniqueness of the email address
        if (emailAlreadyExists(proveedor.getEmail())) {
            throw new ValidationException("Unique Email Violation");
        }
    }

    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }

    public boolean emailAlreadyExists(String email) {
    	Proveedor proveedor = null;
        try {
        	proveedor = repository.findByEmail(email);
        } catch (NoResultException e) {
            // ignore
        }
        return proveedor != null;
    }
}
