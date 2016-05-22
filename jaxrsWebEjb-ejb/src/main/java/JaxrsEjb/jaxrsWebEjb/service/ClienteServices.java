package JaxrsEjb.jaxrsWebEjb.service;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import JaxrsEjb.jaxrsWebEjb.model.Venta;
import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Cliente;
import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Pago;
import JaxrsEjb.jaxrsWebEjb.mybatis.manager.ClienteManager;
import JaxrsEjb.jaxrsWebEjb.mybatis.manager.PagoManager;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
@LocalBean
public class ClienteServices {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private Logger log;
	
	@EJB
	private ClienteManager clienteManager;
	
	@EJB
	private PagoManager pagoManager;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void register(Cliente cliente) throws Exception {
		log.info("Registering " + cliente.getName());
		// em.persist(cliente);
		//clienteManager = new ClienteManager();
		clienteManager.newCliente(cliente);
		log.info("Se registro al cliente: " + cliente.getName() + " con id: " + cliente.getId().toString());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Integer update(Cliente cliente) throws Exception {
		log.info("Modificando " + cliente.getName());
		// em.persist(cliente);
		//clienteManager = new ClienteManager();
		return clienteManager.updateCliente(cliente);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Cliente buscarCliente(Integer id) {
		//clienteManager = new ClienteManager();
		return clienteManager.getClienteById(id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteCliente(Cliente cliente) throws Exception {
		log.info("Sera eliminado el cliente con nombre: " + cliente.getName());
		// em.remove(cliente);
		//clienteManager = new ClienteManager();
		clienteManager.deleteCliente(cliente.getId());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void realizarPago(Pago pago_in, Venta venta, Cliente cliente) throws Exception {
		try {
			Pago pago_out = new Pago(pago_in.getObservacion(), cliente.getId(),
					Integer.valueOf(venta.getId().toString()), venta.getTotal(), new Date());
			
			log.info("Se realizo el pago en el ClienteServices de parte del cliente " + cliente.getName());
			
			Integer id_pago_out = pagoManager.newPago(pago_out);
			
			log.info("Se registro el pago exitosamente con id: " + id_pago_out.toString());
			log.info("Transaccion Exitosa!");
			
		} catch (Exception e) {
			log.info("Ocurrio un error durante el Pago: " + e.getMessage());
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Cliente> getAll() throws Exception {
		log.info("Seran consultados todos los clientes..");
		//clienteManager = new ClienteManager();
		return clienteManager.getAll();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean isExist(Integer id) throws Exception {
		log.info("Sera consultado el cliente con id=" + id.toString());
		//clienteManager = new ClienteManager();
		return clienteManager.isExist(id);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Cliente findByEmail(String email) throws Exception {
		log.info("Sera consultado el cliente con email: " + email);
		//clienteManager = new ClienteManager();
		return clienteManager.findByEmail(email);
	}

}
