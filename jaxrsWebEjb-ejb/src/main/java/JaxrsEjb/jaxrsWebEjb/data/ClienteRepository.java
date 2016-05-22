package JaxrsEjb.jaxrsWebEjb.data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import JaxrsEjb.jaxrsWebEjb.model.Cliente;

@ApplicationScoped
public class ClienteRepository {

    @Inject
    private EntityManager em;
    
    public boolean isExist(Long id) {
		return (null != findById(id));
	}

    public Cliente findById(Long id) {
        return em.find(Cliente.class, id);
    }

    public Cliente findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteria = cb.createQuery(Cliente.class);
        Root<Cliente> cliente = criteria.from(Cliente.class);
        criteria.select(cliente).where(cb.equal(cliente.get("email"), email));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<Cliente> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteria = cb.createQuery(Cliente.class);
        Root<Cliente> cliente = criteria.from(Cliente.class);
        criteria.select(cliente).orderBy(cb.asc(cliente.get("name")));
        return em.createQuery(criteria).getResultList();
    }
}
