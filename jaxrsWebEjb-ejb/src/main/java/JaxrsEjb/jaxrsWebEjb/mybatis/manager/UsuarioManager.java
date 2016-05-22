package JaxrsEjb.jaxrsWebEjb.mybatis.manager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
//import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSession;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Usuario;
import JaxrsEjb.jaxrsWebEjb.mybatis.mapper.UsuarioMapper;
import JaxrsEjb.jaxrsWebEjb.mybatis.util.ConnectionFactory;

@Stateless
@LocalBean
public class UsuarioManager implements UsuarioMapper {

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

			UsuarioMapper usuarioMapper = sqlSession.getMapper(UsuarioMapper.class);

			exist = usuarioMapper.getUsuarioById(id) == null;

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return exist;
	}

	public Usuario getUsuarioById(Integer id) {

		Usuario usuario = null;

		try {

			UsuarioMapper usuarioMapper = sqlSession.getMapper(UsuarioMapper.class);

			usuario = usuarioMapper.getUsuarioById(id);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (usuario);
	}

	public Usuario getUsuarioByUsername(String username) {

		Usuario usuario = null;

		try {

			UsuarioMapper usuarioMapper = sqlSession.getMapper(UsuarioMapper.class);

			usuario = usuarioMapper.getUsuarioByUsername(username);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (usuario);
	}

	public Usuario getUsuarioByToken(String token) {

		Usuario usuario = null;

		try {

			UsuarioMapper usuarioMapper = sqlSession.getMapper(UsuarioMapper.class);

			usuario = usuarioMapper.getUsuarioByToken(token);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (usuario);
	}

	public Integer newUsuario(Usuario usuario) {

		try {

			UsuarioMapper usuarioMapper = sqlSession.getMapper(UsuarioMapper.class);
			
			usuario.setPassword(encriptaEnMD5(usuario.getPassword()));

			usuarioMapper.newUsuario(usuario);

		} catch (org.apache.ibatis.exceptions.PersistenceException e) {
			System.out.println("Existio un error al ejecutar la instruccion con la base de datos: " + e.toString());
		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return usuario.getId();
	}

	public List<Usuario> getAll() {

		List<Usuario> usuarios = null;

		try {

			UsuarioMapper usuarioMapper = sqlSession.getMapper(UsuarioMapper.class);

			usuarios = usuarioMapper.getAll();

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return (usuarios);
	}

	public Integer updateUsuario(Usuario usuario_mod) {

		Integer id_usuario = null;

		try {

			UsuarioMapper usuarioMapper = sqlSession.getMapper(UsuarioMapper.class);

			id_usuario = usuarioMapper.updateUsuario(usuario_mod);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return id_usuario;
	}

	public Integer updateUsuarioToken(Usuario usuario_mod) {

		Integer id_usuario = null;

		try {

			UsuarioMapper usuarioMapper = sqlSession.getMapper(UsuarioMapper.class);

			id_usuario = usuarioMapper.updateUsuarioToken(usuario_mod);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

		return id_usuario;
	}

	public void deleteUsuario(Integer id) {

		try {

			UsuarioMapper usuarioMapper = sqlSession.getMapper(UsuarioMapper.class);

			usuarioMapper.deleteUsuario(id);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}
	}
	
	public String login(String username, String password){
		
		Usuario usuario = null;
		
		String token = null;
		
		try {

			UsuarioMapper usuarioMapper = sqlSession.getMapper(UsuarioMapper.class);

			usuario = usuarioMapper.getUsuarioByUsername(username);
			
			if(usuario.getPassword().equals(encriptaEnMD5(password))){
				token = this.crearToken(username);
			}

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}
		
		return token;
	}
	
	public void logout(String token){
		
		Usuario usuario = null;
		
		try {

			UsuarioMapper usuarioMapper = sqlSession.getMapper(UsuarioMapper.class);

			usuario = usuarioMapper.getUsuarioByToken(token);
			
			usuario.setToken("");
			
			usuarioMapper.updateUsuarioToken(usuario);

		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}

	}
	
	public String crearToken(String username){
		
		Usuario usuario = null;
		
		String token = null;
		
		try {

			UsuarioMapper usuarioMapper = sqlSession.getMapper(UsuarioMapper.class);
			
			usuario = usuarioMapper.getUsuarioByUsername(username);
			
			token = encriptaEnMD5(username + (new Date().toString()));
			
			usuario.setToken(token);

			usuarioMapper.updateUsuario(usuario);
			
		} catch (Exception e) {
			System.out.println("Existio un error al ejecutar la instruccion sql: " + e.getMessage()
					+ " ##### Con nombre: " + e.getClass().getName());
		}
		
		return token;
	}
	
	private static String encriptaEnMD5(String stringAEncriptar) {
		
		char[] CONSTS_HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		
		try {
			
			MessageDigest msgd = MessageDigest.getInstance("MD5");
			
			byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
			
			StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
			
			for (int i = 0; i < bytes.length; i++) {
				int bajo = (int) (bytes[i] & 0x0f);
				int alto = (int) ((bytes[i] & 0xf0) >> 4);
				strbCadenaMD5.append(CONSTS_HEX[alto]);
				strbCadenaMD5.append(CONSTS_HEX[bajo]);
			}
			
			return strbCadenaMD5.toString();
			
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	@PreDestroy
	public void close() {
		connectionFactory.close();
	}

}