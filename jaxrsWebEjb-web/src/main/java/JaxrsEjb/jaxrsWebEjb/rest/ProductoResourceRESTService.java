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

import JaxrsEjb.jaxrsWebEjb.data.ProductoRepository;
import JaxrsEjb.jaxrsWebEjb.model.Producto;
import JaxrsEjb.jaxrsWebEjb.service.ProductoServices;
import JaxrsEjb.jaxrsWebEjb.model.Venta;
import JaxrsEjb.jaxrsWebEjb.data.VentaRepository;
import JaxrsEjb.jaxrsWebEjb.dummies.VentaDummy;
import JaxrsEjb.jaxrsWebEjb.model.Compra;
import JaxrsEjb.jaxrsWebEjb.dummies.CompraDummy;
import JaxrsEjb.jaxrsWebEjb.model.Pago;

@Path("/productos")
@RequestScoped
public class ProductoResourceRESTService {
	@Inject
	private Logger log;

	@Inject
	private Validator validator;

	@Inject
	private ProductoRepository productoRepository;

	@Inject
	private VentaRepository ventaRepository;

	@EJB
	private ProductoServices productoServices;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Producto> listAllClientes() {
		return productoRepository.findAllOrderedByName();
	}

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
			// Validates member using bean validation
			validateProducto(producto);

			productoServices.register(producto);

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
	public void deleteProductoById(@PathParam("id") long id) throws Exception {
		// Response.ResponseBuilder builder = null;
		Producto producto = productoRepository.findById(id);
		if (producto == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		productoServices.deleteProducto(producto);
		Response.accepted();
	}

	private void validateProducto(Producto producto) throws ConstraintViolationException, ValidationException {
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Producto>> violations = validator.validate(producto);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}

		// Check the uniqueness of the email address
		/*
		 * if (nombreAlreadyExists(producto.getNombre())) { throw new
		 * ValidationException("Violacion de unique nombre"); }
		 */
	}

	private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
		log.fine("Validation completed. violations found: " + violations.size());

		Map<String, String> responseObj = new HashMap<>();

		for (ConstraintViolation<?> violation : violations) {
			responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
		}

		return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
	}

	public boolean nombreAlreadyExists(String nombre) {
		Producto producto = null;
		try {
			producto = productoRepository.findByName(nombre);
		} catch (NoResultException e) {
			// ignore
		}
		return producto != null;
	}
	
	@POST
	@Path("/venta")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response realizarVenta(VentaDummy ventadummy) {

		Response.ResponseBuilder builder = null;

		try {

			log.info("Se realiza la venta: " + ventadummy.getDescripcion() );

			productoServices.realizarVenta(ventadummy);

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
	@Path("/compra")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response realizarCompra(CompraDummy compradummy) {

		Response.ResponseBuilder builder = null;

		try {

			log.info("Se realiza la compra: " + compradummy.getDescripcion() );

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
}
