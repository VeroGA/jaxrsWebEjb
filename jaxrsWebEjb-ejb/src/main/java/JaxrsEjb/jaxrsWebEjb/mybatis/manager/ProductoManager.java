package JaxrsEjb.jaxrsWebEjb.mybatis.manager;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSession;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Producto;
import JaxrsEjb.jaxrsWebEjb.mybatis.mapper.ProductoMapper;
import JaxrsEjb.jaxrsWebEjb.mybatis.util.ConnectionFactory;

@Stateless
@LocalBean
public class ProductoManager implements ProductoMapper {
	
	@EJB
	private ConnectionFactory connectionFactory;
	
	@EJB
	private VentaDetalleManager ventaDetalleManager;

	private SqlSession sqlSession;

	@PostConstruct
	void init() {
		sqlSession = connectionFactory.getSqlSessionFactory();
	}
	
	public Boolean isExistByName(String Name) {
		Producto producto = null;

		try {

			ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);

			producto = productoMapper.getProductoByName(Name);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (producto != null);
	}

	public Boolean isExist(Integer id) {
		Boolean exist = false;

		try {

			ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);

			exist = productoMapper.getProductoById(id) == null;

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return exist;
	}

	public Integer newProducto(Producto producto) {
		try {

			ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
			
			productoMapper.newProducto(producto);

		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			System.out.println("Existio un error al ejecutar la instruccion con la base de datos: " + e.toString());
		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return producto.getId();
	}

	public Producto getProductoById(Integer id) {
		Producto producto = null;

		try {

			ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);

			producto = productoMapper.getProductoById(id);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (producto);
	}
	
	public Producto getProductoByName(String name) {
		Producto producto = null;

		try {

			ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);

			producto = productoMapper.getProductoByName(name);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (producto);
	}

	public List<Producto> getAll(){
		List<Producto> productos = null;

		try {

			ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);

			productos = productoMapper.getAll();

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (productos);
	}

	public Integer updateProducto(Producto producto_mod){
		Integer productoId = null;

		try {

			ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);

			productoId = productoMapper.updateProducto(producto_mod);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return productoId;
	}

	public void deleteProducto(Integer id){
		try {

			ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);

			productoMapper.deleteProducto(id);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}
	}
	
	@PreDestroy
	public void close() {
		connectionFactory.close();
	}

}