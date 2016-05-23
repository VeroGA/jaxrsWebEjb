package JaxrsEjb.jaxrsWebEjb.service;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Usuario;
import JaxrsEjb.jaxrsWebEjb.mybatis.manager.UsuarioManager;

import java.util.logging.Logger;

@Stateless
@LocalBean
public class UsuarioServices {

	@Inject
	private Logger log;
	
	@EJB
	private UsuarioManager usuarioManager;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean isExist(String username) throws Exception {
		log.info("Verificando usuario: " + username);
		Usuario usuario = usuarioManager.getUsuarioByUsername(username);
		if(usuario != null){
			log.info("Se verifico al usuario: " + usuario.getUsername() + " con id: " + usuario.getId().toString());
			return true;
		}else{
			log.info("No se verifico al usuario: " + username);
			return false;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void crearUsuario(Usuario usuario) throws Exception {
		log.info("Registrando usuario: " + usuario.getUsername());
		usuarioManager.newUsuario(usuario);
		log.info("Se registro al usuario: " + usuario.getUsername() + " con id: " + usuario.getId().toString());
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario buscarUsuario(Integer id){
		log.info("Buscando usuario con id: " + id.toString());
		Usuario usuario = usuarioManager.getUsuarioById(id);
		log.info("Se encontro al usuario: " + usuario.getUsername() + " con id: " + usuario.getId().toString());
		return usuario;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario buscarUsuarioPorToken(String token){
		log.info("Buscando usuario con token: " + token);
		Usuario usuario = usuarioManager.getUsuarioByToken(token);
		log.info("Se encontro al usuario: " + usuario.getUsername() + " con id: " + usuario.getId().toString());
		return usuario;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario auntenticar(String username, String password) {
		log.info("Logueando usuario: " + username);
		Usuario usuario = usuarioManager.login(username, password);
		log.info("Se logueo al usuario con el token: " + usuario.getToken());
		return usuario;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void desauntenticar(String token) {
		log.info("Desauntenticando usuario con token: " + token);
		usuarioManager.logout(token);
		log.info("Se desauntentico al usuario con el token: " + token);
	}

}
