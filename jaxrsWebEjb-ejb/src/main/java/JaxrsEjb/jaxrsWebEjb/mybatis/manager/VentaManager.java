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
import JaxrsEjb.jaxrsWebEjb.mybatis.mapper.VentaMapper;
import JaxrsEjb.jaxrsWebEjb.mybatis.manager.VentaDetalleManager;
import JaxrsEjb.jaxrsWebEjb.mybatis.util.ConnectionFactory;

@Stateless
@LocalBean
public class VentaManager implements VentaMapper {
	
	@EJB
	private ConnectionFactory connectionFactory;
	
	@EJB
	private VentaDetalleManager ventaDetalleManager;

	private SqlSession sqlSession;

	@PostConstruct
	void init() {
		sqlSession = connectionFactory.getSqlSessionFactory();
	}

	public Boolean isExist(Integer id) {
		
		Boolean exist = false;

		try {

			VentaMapper ventaMapper = sqlSession.getMapper(VentaMapper.class);

			exist = ventaMapper.getVentaById(id) == null;

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return exist;
	}

	public Integer newVenta(Venta venta) {
		
		try {

			VentaMapper ventaMapper = sqlSession.getMapper(VentaMapper.class);
			
			ventaMapper.newVenta(venta);
			
			for(int i=0; i<venta.getDetalles().size(); i++){
				venta.getDetalles().get(i).setVentaId(venta.getId());
				ventaDetalleManager.newVentaDetalle(venta.getDetalles().get(i));
			}

		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			System.out.println("Existio un error al ejecutar la instruccion con la base de datos: " + e.toString());
		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return venta.getId();
	}

	public Venta getVentaById(Integer id) {
		
		Venta venta = null;

		try {

			VentaMapper ventaMapper = sqlSession.getMapper(VentaMapper.class);

			venta = ventaMapper.getVentaById(id);
			
			venta.setDetalles(ventaDetalleManager.getAllByVenta(venta));

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (venta);
	}

	public List<Venta> getAll(){
		
		List<Venta> ventas = null;

		try {

			VentaMapper ventaMapper = sqlSession.getMapper(VentaMapper.class);

			ventas = ventaMapper.getAll();

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (ventas);
	}

	public Venta updateVenta(Venta venta_mod){
		
		Venta venta = null;

		try {

			VentaMapper ventaMapper = sqlSession.getMapper(VentaMapper.class);

			venta = ventaMapper.updateVenta(venta_mod);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return venta;
	}

	public void deleteVenta(Venta venta){
		
		try {

			VentaMapper ventaMapper = sqlSession.getMapper(VentaMapper.class);
			
			List<VentaDetalle> detalles = venta.getDetalles();
			
			for(int i=0; i<detalles.size(); i++){
				ventaDetalleManager.deleteVentaDetalle(detalles.get(i));
			}

			ventaMapper.deleteVenta(venta);

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