package JaxrsEjb.jaxrsWebEjb.mybatis.manager;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSession;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Venta;
import JaxrsEjb.jaxrsWebEjb.mybatis.bean.VentaDetalle;
import JaxrsEjb.jaxrsWebEjb.mybatis.mapper.VentaDetalleMapper;
import JaxrsEjb.jaxrsWebEjb.mybatis.util.ConnectionFactory;

@Stateless
@LocalBean
public class VentaDetalleManager implements VentaDetalleMapper {
	
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

			VentaDetalleMapper ventaDetalleMapper = sqlSession.getMapper(VentaDetalleMapper.class);

			exist = ventaDetalleMapper.getVentaDetalleById(id) == null;

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return exist;
	}

	public Integer newVentaDetalle(VentaDetalle ventaDetalle) {
		
		try {

			VentaDetalleMapper ventaDetalleMapper = sqlSession.getMapper(VentaDetalleMapper.class);
			
			ventaDetalleMapper.newVentaDetalle(ventaDetalle);

		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			System.out.println("Existio un error al ejecutar la instruccion con la base de datos: " + e.toString());
		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return ventaDetalle.getId();
	}

	public VentaDetalle getVentaDetalleById(Integer id) {
		
		VentaDetalle ventaDetalle = null;

		try {

			VentaDetalleMapper ventaDetalleMapper = sqlSession.getMapper(VentaDetalleMapper.class);

			ventaDetalle = ventaDetalleMapper.getVentaDetalleById(id);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (ventaDetalle);
	}

	public List<VentaDetalle> getAll(){
		
		List<VentaDetalle> ventasDetalle = null;

		try {

			VentaDetalleMapper ventaDetalleMapper = sqlSession.getMapper(VentaDetalleMapper.class);

			ventasDetalle = ventaDetalleMapper.getAll();

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (ventasDetalle);
	}

	public VentaDetalle updateVentaDetalle(VentaDetalle ventaDetalle_mod){
		
		VentaDetalle ventaDetalle = null;

		try {

			VentaDetalleMapper ventaDetalleMapper = sqlSession.getMapper(VentaDetalleMapper.class);

			ventaDetalle = ventaDetalleMapper.updateVentaDetalle(ventaDetalle_mod);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return ventaDetalle;
	}

	public void deleteVentaDetalle(VentaDetalle ventaDetalle){
		
		try {

			VentaDetalleMapper ventaDetalleMapper = sqlSession.getMapper(VentaDetalleMapper.class);

			ventaDetalleMapper.deleteVentaDetalle(ventaDetalle);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}
	}
	
	public List<VentaDetalle> getAllByVenta(Venta venta) {
		
		List<VentaDetalle> ventasDetalle = null;
		
		try {

			VentaDetalleMapper ventaDetalleMapper = sqlSession.getMapper(VentaDetalleMapper.class);

			ventasDetalle = ventaDetalleMapper.getAllByVenta(venta);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}
		
		return ventasDetalle;
	}
	
	@PreDestroy
	public void close() {
		connectionFactory.close();
	}

}