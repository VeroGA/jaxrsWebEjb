package JaxrsEjb.jaxrsWebEjb.mybatis.manager;

//import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSession;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Proveedor;
import JaxrsEjb.jaxrsWebEjb.mybatis.mapper.ProveedorMapper;
import JaxrsEjb.jaxrsWebEjb.mybatis.util.ConnectionFactory;

@Stateless
@LocalBean
public class ProveedorManager implements ProveedorMapper {
	
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

			ProveedorMapper proveedorMapper = sqlSession.getMapper(ProveedorMapper.class);

			exist = proveedorMapper.getProveedorById(id) == null;

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return exist;
	}

	public Integer newProveedor(Proveedor proveedor) {
		
		try {

			ProveedorMapper proveedorMapper = sqlSession.getMapper(ProveedorMapper.class);
			
			proveedorMapper.newProveedor(proveedor);

		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			System.out.println("Existio un error al ejecutar la instruccion con la base de datos: " + e.toString());
		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return proveedor.getId();
	}

	public Proveedor getProveedorById(Integer id) {
		
		Proveedor proveedor = null;

		try {

			ProveedorMapper proveedorMapper = sqlSession.getMapper(ProveedorMapper.class);

			proveedor = proveedorMapper.getProveedorById(id);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (proveedor);
	}

	public Proveedor findByEmail(String email){
		
		Proveedor proveedor = null;

		try {

			ProveedorMapper proveedorMapper = sqlSession.getMapper(ProveedorMapper.class);

			proveedor = proveedorMapper.findByEmail(email);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (proveedor);
	}

	public List<Proveedor> findAllOrderedByName(){
		
		List<Proveedor> proveedores = null;

		try {

			ProveedorMapper clienteMapper = sqlSession.getMapper(ProveedorMapper.class);

			proveedores = clienteMapper.getAll();
			
		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (proveedores);
	}

	public List<Proveedor> getAll(){
		
		List<Proveedor> proveedores = null;

		try {

			ProveedorMapper proveedorMapper = sqlSession.getMapper(ProveedorMapper.class);

			proveedores = proveedorMapper.getAll();

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (proveedores);
	}

	public Integer updateProveedor(Proveedor proveedor_mod){
		
		Integer id_Proveedor = null;

		try {

			ProveedorMapper proveedorMapper = sqlSession.getMapper(ProveedorMapper.class);

			id_Proveedor = proveedorMapper.updateProveedor(proveedor_mod);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return id_Proveedor;
	}

	public void deleteProveedor(Integer id){

		try {

			ProveedorMapper proveedorMapper = sqlSession.getMapper(ProveedorMapper.class);

			proveedorMapper.deleteProveedor(id);

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