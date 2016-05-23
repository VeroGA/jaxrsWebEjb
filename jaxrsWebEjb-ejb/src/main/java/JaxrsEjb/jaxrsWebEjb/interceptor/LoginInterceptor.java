package JaxrsEjb.jaxrsWebEjb.interceptor;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;

import JaxrsEjb.jaxrsWebEjb.annotation.LoginRequired;
import JaxrsEjb.jaxrsWebEjb.service.UsuarioServices;
import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Usuario;

@LoginRequired
@Interceptor
public class LoginInterceptor {
	
	@Inject
	private Logger log;
	
	@EJB
	private UsuarioServices usuarioServices;

	@Inject
    HttpServletRequest request;
	
	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic) throws Exception {
		
		log.info("Activando interceptor..");
		
		String rolNeed = ic.getMethod().getAnnotation(LoginRequired.class).rol().toString();
		
		log.info("Rol activado es: " + rolNeed);
		
		String token = request.getHeader("auth");
		
		if(token!=null){
			
			log.info("El token obtenido del header es: " + token);
			
			Usuario usuario = usuarioServices.buscarUsuarioPorToken(token);
			
			log.info("El usuario propietario del token es: " + usuario.getUsername());
			
			if(token.equals(usuario.getToken())){
				log.info("Se trata de una peticion con un token activo..");
				if(usuario.getRol().equals(rolNeed)){
					log.info("Cuenta con los permisos necesarios para la operacion, con rol: " + rolNeed);
					return ic.proceed();
				}else{
					log.info("No cuenta con los permisos necesarios para la operacion, necesita el rol: " + rolNeed);
				}
			}else{
				log.info("El token no existe..");
			}
		}
		
		return null;
	}

}