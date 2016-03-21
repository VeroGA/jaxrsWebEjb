package JaxrsEjb.jaxrsWebEjb.rest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/*import JaxrsEjb.jaxrsWebEjb.rest.ClienteResourceRESTService;
import JaxrsEjb.jaxrsWebEjb.rest.ProductoResourceRESTService;
import JaxrsEjb.jaxrsWebEjb.rest.ProveedorResourceRESTService;*/

@ApplicationPath("/rest")
public class JaxRsActivator extends Application {
	/* class body intentionally left blank */
	/*public Set<Class<?>> getClasses() {
		return new HashSet<Class<?>>(Arrays.asList(ClienteResourceRESTService.class, ProductoResourceRESTService.class,
				ProveedorResourceRESTService.class));
	}*/
}
