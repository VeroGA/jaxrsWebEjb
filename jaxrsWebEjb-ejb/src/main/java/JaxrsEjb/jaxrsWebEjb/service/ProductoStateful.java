package JaxrsEjb.jaxrsWebEjb.service;

import java.util.Iterator;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import JaxrsEjb.jaxrsWebEjb.model.Producto;

@Stateful
public class ProductoStateful {

	@PersistenceContext
	private EntityManager em;

	private Iterator<Producto> iterator;

	// private boolean iniciado = false;

	/*
	 * public ProductoStateful() { this.iniciado = false; }
	 */

	public void iniciar() {
		// if (!this.iniciado) {

		TypedQuery<Producto> query = em.createQuery("SELECT p FROM Producto p", Producto.class);
		this.iterator = query.getResultList().iterator();

		// this.iniciado = true;
		// }
	}

	public Producto nextProducto() {
		Producto p = iterator.next();
		return p;
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Remove
	public void terminar() {
		System.out.println("fin");
	}

}