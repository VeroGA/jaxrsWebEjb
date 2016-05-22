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
import JaxrsEjb.jaxrsWebEjb.mybatis.mapper.CompraMapper;
import JaxrsEjb.jaxrsWebEjb.mybatis.manager.CompraDetalleManager;
import JaxrsEjb.jaxrsWebEjb.mybatis.util.ConnectionFactory;

@Stateless
@LocalBean
public class CompraManager implements CompraMapper {
	
	@EJB
	private ConnectionFactory connectionFactory;
	
	@EJB
	private CompraDetalleManager compraDetalleManager;

	private SqlSession sqlSession;

	@PostConstruct
	void init() {
		sqlSession = connectionFactory.getSqlSessionFactory();
	}

	public Boolean isExist(Integer id) {
		
		Boolean exist = false;

		try {

			CompraMapper compraMapper = sqlSession.getMapper(CompraMapper.class);

			exist = compraMapper.getCompraById(id) == null;

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return exist;
	}

	public Integer newCompra(Compra compra) {
		
		try {

			CompraMapper compraMapper = sqlSession.getMapper(CompraMapper.class);
			
			compraMapper.newCompra(compra);
			
			for(int i=0; i<compra.getDetalles().size(); i++){
				compra.getDetalles().get(i).setCompraId(compra.getId());
				compraDetalleManager.newCompraDetalle(compra.getDetalles().get(i));
			}

		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			System.out.println("Existio un error al ejecutar la instruccion con la base de datos: " + e.toString());
		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return compra.getId();
	}

	public Compra getCompraById(Integer id) {
		
		Compra compra = null;

		try {

			CompraMapper compraMapper = sqlSession.getMapper(CompraMapper.class);

			compra = compraMapper.getCompraById(id);
			
			compra.setDetalles(compraDetalleManager.getAllByCompra(compra));

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (compra);
	}

	public List<Compra> getAll(){
		
		List<Compra> compras = null;

		try {

			CompraMapper compraMapper = sqlSession.getMapper(CompraMapper.class);

			compras = compraMapper.getAll();

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (compras);
	}

	public Compra updateCompra(Compra compra_mod){
		
		Compra compra = null;

		try {

			CompraMapper compraMapper = sqlSession.getMapper(CompraMapper.class);

			compra = compraMapper.updateCompra(compra_mod);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return compra;
	}

	public void deleteCompra(Compra compra){
		
		try {

			CompraMapper compraMapper = sqlSession.getMapper(CompraMapper.class);
			
			List<CompraDetalle> detalles = compra.getDetalles();
			
			for(int i=0; i<detalles.size(); i++){
				compraDetalleManager.deleteCompraDetalle(detalles.get(i));
			}

			compraMapper.deleteCompra(compra);

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