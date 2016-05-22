package JaxrsEjb.jaxrsWebEjb.mybatis.util;

import java.io.IOException;
import java.io.Reader;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

@Singleton
public class MyBatisSingleton {
	private static SqlSessionFactory sqlMapper;

	private static Reader reader;

	@PostConstruct
	private void init(){
		try {
			reader = Resources.getResourceAsReader("myBatisDB-config.xml");
			sqlMapper = new SqlSessionFactoryBuilder().build(reader, "desarrollo", getDatabaseProperties());
			// Ejemplo especificando el enviorement: sqlMapper = new
			// SqlSessionFactoryBuilder().build(reader,"enviorement",
			// getDatabaseProperties());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlMapper;
	}

	private static Properties getDatabaseProperties() {
		Resource resource = new ClassPathResource("mybatis.properties");
		Properties databaseProperties = null;

		try {
			databaseProperties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return databaseProperties;
	}
	
}
