package JaxrsEjb.jaxrsWebEjb.mybatis.util;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSession;

@Stateless
public class ConnectionFactory {
	
	private SqlSession sqlSession;
	
	@EJB
	private MyBatisSingleton mb;
	
	@PostConstruct
	void init () {
		try {
			sqlSession = mb.getSqlSessionFactory().openSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SqlSession getSqlSessionFactory() {
		return sqlSession;
	}
	
	public void close(){
		try {
			sqlSession.close();
		} catch (Exception e) {
			e.getMessage();
		}
		
	}

}
