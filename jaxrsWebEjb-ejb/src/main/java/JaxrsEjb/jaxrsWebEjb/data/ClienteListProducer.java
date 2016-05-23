package JaxrsEjb.jaxrsWebEjb.data;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import JaxrsEjb.jaxrsWebEjb.model.Cliente;

@RequestScoped
public class ClienteListProducer {

    @Inject
    private ClienteRepository clienteRepository;

    private List<Cliente> clientes;

    // @Named provides access the return value via the EL variable name "members" in the UI (e.g.,
    // Facelets or JSP view)
    @Produces
    @Named
    public List<Cliente> getClientes() {
        return clientes;
    }

    public void onClienteListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Cliente cliente) {
        retrieveAllClientesOrderedByName();
    }

    @PostConstruct
    public void retrieveAllClientesOrderedByName() {
        clientes = clienteRepository.findAllOrderedByName();
    }
}
