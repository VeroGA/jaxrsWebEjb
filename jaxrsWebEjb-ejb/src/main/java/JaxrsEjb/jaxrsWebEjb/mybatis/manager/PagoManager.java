package JaxrsEjb.jaxrsWebEjb.mybatis.manager;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSession;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Pago;
import JaxrsEjb.jaxrsWebEjb.mybatis.mapper.PagoMapper;
import JaxrsEjb.jaxrsWebEjb.mybatis.util.ConnectionFactory;

@Stateless
@LocalBean
public class PagoManager implements PagoMapper {

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

			PagoMapper pagoMapper = sqlSession.getMapper(PagoMapper.class);

			exist = pagoMapper.getPagoById(id) == null;

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return exist;
	}

	public Integer newPago(Pago pago) {
		
		try {

			PagoMapper pagoMapper = sqlSession.getMapper(PagoMapper.class);

			pagoMapper.newPago(pago);

		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			System.out.println("Existio un error al ejecutar la instruccion con la base de datos: " + e.toString());
		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return pago.getId();
	}

	public Pago getPagoById(Integer id) {
		
		Pago pago = null;

		try {

			PagoMapper pagoMapper = sqlSession.getMapper(PagoMapper.class);

			pago = pagoMapper.getPagoById(id);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (pago);
	}

	public List<Pago> getAll() {
		
		List<Pago> pagos = null;

		try {

			PagoMapper pagoMapper = sqlSession.getMapper(PagoMapper.class);

			pagos = pagoMapper.getAll();

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (pagos);
	}

	public Pago updatePago(Pago pago_mod) {
		
		Pago pago = null;

		try {

			PagoMapper pagoMapper = sqlSession.getMapper(PagoMapper.class);

			pago = pagoMapper.updatePago(pago_mod);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return pago;
	}

	public void deletePago(Integer id) {
		
		try {

			PagoMapper pagoMapper = sqlSession.getMapper(PagoMapper.class);

			pagoMapper.deletePago(id);

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