package JaxrsEjb.jaxrsWebEjb.test;

import static org.junit.Assert.assertNotNull;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
//import JaxrsEjb.jaxrsWebEjb.model.Cliente;
import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Cliente;
import JaxrsEjb.jaxrsWebEjb.service.ClienteServices;
import JaxrsEjb.jaxrsWebEjb.util.Resources;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ClienteRegistrationTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Cliente.class, ClienteServices.class, Resources.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml", "test-ds.xml");
    }

    @Inject
    ClienteServices clienteServices;

    @Inject
    Logger log;

    @Test
    public void testRegister() throws Exception {
    	Cliente newCliente = new Cliente();
        newCliente.setName("Jesus Aguilar");
        newCliente.setEmail("chuchosoft.239@gmail.com");
        newCliente.setPhoneNumber("2125551234");
        clienteServices.register(newCliente);
        assertNotNull(newCliente.getId());
        log.info(newCliente.getName() + " was persisted with id " + newCliente.getId());
    }

}
