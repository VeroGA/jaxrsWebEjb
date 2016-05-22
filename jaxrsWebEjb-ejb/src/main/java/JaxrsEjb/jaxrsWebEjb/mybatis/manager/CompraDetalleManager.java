package JaxrsEjb.jaxrsWebEjb.mybatis.manager;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSession;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Compra;
import JaxrsEjb.jaxrsWebEjb.mybatis.bean.CompraDetalle;
import JaxrsEjb.jaxrsWebEjb.mybatis.mapper.CompraDetalleMapper;
import JaxrsEjb.jaxrsWebEjb.mybatis.util.ConnectionFactory;

@Stateless
@LocalBean
public class CompraDetalleManager implements CompraDetalleMapper {
	
	@EJB
	private ConnectionFactory connectionFactory;

	private SqlSession sqlSession;

	@PostConstruct
	void init() {
		sqlSession = connectionFactory.getSqlSessionFactory();
	}

	public Boolean isExist(Integer id) {
		
		Boolean exist = false;

		try {

			CompraDetalleMapper compraDetalleMapper = sqlSession.getMapper(CompraDetalleMapper.class);

			exist = compraDetalleMapper.getCompraDetalleById(id) == null;

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return exist;
	}

	public Integer newCompraDetalle(CompraDetalle compraDetalle) {
		
		try {

			CompraDetalleMapper compraDetalleMapper = sqlSession.getMapper(CompraDetalleMapper.class);
			
			compraDetalleMapper.newCompraDetalle(compraDetalle);

		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			System.out.println("Existio un error al ejecutar la instruccion con la base de datos: " + e.toString());
		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return compraDetalle.getId();
	}

	public CompraDetalle getCompraDetalleById(Integer id) {
		
		CompraDetalle compraDetalle = null;

		try {

			CompraDetalleMapper compraDetalleMapper = sqlSession.getMapper(CompraDetalleMapper.class);

			compraDetalle = compraDetalleMapper.getCompraDetalleById(id);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (compraDetalle);
	}

	public List<CompraDetalle> getAll(){
		
		List<CompraDetalle> comprasDetalle = null;

		try {

			CompraDetalleMapper compraDetalleMapper = sqlSession.getMapper(CompraDetalleMapper.class);

			comprasDetalle = compraDetalleMapper.getAll();

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (comprasDetalle);
	}

	public CompraDetalle updateCompraDetalle(CompraDetalle compraDetalle_mod){
		
		CompraDetalle compraDetalle = null;

		try {

			CompraDetalleMapper compraDetalleMapper = sqlSession.getMapper(CompraDetalleMapper.class);

			compraDetalle = compraDetalleMapper.updateCompraDetalle(compraDetalle_mod);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return compraDetalle;
	}

	public void deleteCompraDetalle(CompraDetalle compraDetalle){
		
		try {

			CompraDetalleMapper compraDetalleMapper = sqlSession.getMapper(CompraDetalleMapper.class);

			compraDetalleMapper.deleteCompraDetalle(compraDetalle);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}
	}
	
	public List<CompraDetalle> getAllByCompra(Compra compra) {
		
		List<CompraDetalle> comprasDetalle = null;
		
		try {

			CompraDetalleMapper compraDetalleMapper = sqlSession.getMapper(CompraDetalleMapper.class);

			comprasDetalle = compraDetalleMapper.getAllByCompra(compra);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}
		
		return comprasDetalle;
	}
	
	@PreDestroy
	public void close() {
		connectionFactory.close();
	}

}