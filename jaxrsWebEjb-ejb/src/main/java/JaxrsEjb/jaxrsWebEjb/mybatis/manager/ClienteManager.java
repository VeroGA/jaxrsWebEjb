package JaxrsEjb.jaxrsWebEjb.mybatis.manager;

//import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSession;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Cliente;
import JaxrsEjb.jaxrsWebEjb.mybatis.mapper.ClienteMapper;
import JaxrsEjb.jaxrsWebEjb.mybatis.util.ConnectionFactory;

@Stateless
@LocalBean
public class ClienteManager implements ClienteMapper {
	
	@EJB
	private ConnectionFactory connectionFactory;
	
	private SqlSession sqlSession;
	
	@PostConstruct
	void init(){
		sqlSession = connectionFactory.getSqlSessionFactory();
	}

	public Boolean isExist(Integer id) {
		
		Boolean exist = false;

		try {

			ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);

			exist = clienteMapper.getClienteById(id) == null;

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return exist;
	}

	public Integer newCliente(Cliente cliente) {
		
		try {

			ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);
			
			clienteMapper.newCliente(cliente);

		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			System.out.println("Existio un error al ejecutar la instruccion con la base de datos: " + e.toString());
		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return cliente.getId();
	}

	public Cliente getClienteById(Integer id) {
		
		Cliente cliente = null;

		try {

			ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);

			cliente = clienteMapper.getClienteById(id);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (cliente);
	}

	public Cliente findByEmail(String email){
		
		Cliente cliente = null;

		try {

			ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);

			cliente = clienteMapper.findByEmail(email);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (cliente);
	}

	public List<Cliente> findAllOrderedByName(){
		
		List<Cliente> clientes = null;

		try {

			ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);

			clientes = clienteMapper.getAll();
			
			//Collections.sort(clientes);//, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (clientes);
	}

	public List<Cliente> getAll(){
		
		List<Cliente> clientes = null;

		try {

			ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);

			clientes = clienteMapper.getAll();

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (clientes);
	}

	public Integer updateCliente(Cliente cliente_mod){
		
		Integer id_cliente = null;

		try {

			ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);

			id_cliente = clienteMapper.updateCliente(cliente_mod);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return id_cliente;
	}

	public void deleteCliente(Integer id){

		try {

			ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);

			clienteMapper.deleteCliente(id);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}
	}
	
	@PreDestroy
	public void close(){
		connectionFactory.close();
	}

}