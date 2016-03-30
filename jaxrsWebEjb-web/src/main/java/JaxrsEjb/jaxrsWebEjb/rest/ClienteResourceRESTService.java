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
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import JaxrsEjb.jaxrsWebEjb.data.ClienteRepository;
import JaxrsEjb.jaxrsWebEjb.model.Cliente;
import JaxrsEjb.jaxrsWebEjb.service.ClienteServices;
import JaxrsEjb.jaxrsWebEjb.service.ProductoServices;
import JaxrsEjb.jaxrsWebEjb.model.Venta;
import JaxrsEjb.jaxrsWebEjb.data.VentaRepository;
import JaxrsEjb.jaxrsWebEjb.model.Pago;
import JaxrsEjb.jaxrsWebEjb.model.Producto;
import JaxrsEjb.jaxrsWebEjb.dummies.VentaDummy;

@Path("/clientes")
@RequestScoped
public class ClienteResourceRESTService {
	@Inject
	private Logger log;

	@Inject
	private Validator validator;

	@Inject
	private ClienteRepository repository;

	@Inject
	private VentaRepository ventaRepository;

	@EJB
	private ClienteServices clienteServices;

	@EJB
	private ProductoServices productoServices;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Cliente> listAllClientes() {
		return repository.findAllOrderedByName();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Cliente lookupClienteById(@PathParam("id") long id) {
		Cliente cliente = repository.findById(id);
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
			// Validates member using bean validation
			validateCliente(cliente);

			clienteServices.register(cliente);

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
	public void deleteClienteById(@PathParam("id") long id) throws Exception {
		Response.ResponseBuilder builder = null;
		Cliente cliente = repository.findById(id);
		if (cliente == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		clienteServices.deleteCliente(cliente);
		Response.accepted();
	}

	private void validateCliente(Cliente cliente) throws ConstraintViolationException, ValidationException {
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}

		// Check the uniqueness of the email address
		if (emailAlreadyExists(cliente.getEmail())) {
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
		Cliente cliente = null;
		try {
			cliente = repository.findByEmail(email);
		} catch (NoResultException e) {
			// ignore
		}
		return cliente != null;
	}

	@POST
	@Path("/{idcliente:[0-9][0-9]*}/venta/{idventa:[0-9][0-9]*}/pago")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response realizarPago(@PathParam("idcliente") long idcliente, @PathParam("idventa") long idventa,
			Pago pago) { // aca se debe recibir un pago para ralizarse en json

		Response.ResponseBuilder builder = null;

		try {

			Cliente cliente = repository.findById(idcliente);
			Venta venta = ventaRepository.findById(idventa);

			log.info(pago.getObservacion() + " " + pago.getMonto());
			log.info(cliente.getName() + " " + cliente.getEmail());
			log.info("Se realizo el pago: " + pago.getObservacion() + " del cliente: " + cliente.getName() + " "
					+ cliente.getEmail() + " a referente a la venta " + venta.getDescripcion());

			clienteServices.realizarPago(pago, venta, cliente);

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
}
