/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package JaxrsEjb.jaxrsWebEjb.data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import JaxrsEjb.jaxrsWebEjb.model.Producto;

@ApplicationScoped
public class ProductoRepository {

	@Inject
	private EntityManager em;

	public boolean isExist(Long id) {
		return (null != findById(id));
	}

	public Producto findById(Long id) {
		return em.find(Producto.class, id);
	}

	public boolean findByName(String nombre) {

		TypedQuery<Producto> query = em.createQuery("SELECT p FROM Producto p WHERE p.nombre = :nombre", Producto.class);
		List<Producto> p = query.setParameter("nombre", nombre.trim()).getResultList();
		
		if(p.size()>0){
			return true;
		}else{
			System.out.println("No existen Productos con este nombre");
			return false;
		}
		
	}

	public List<Producto> findAllOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Producto> criteria = cb.createQuery(Producto.class);
		Root<Producto> producto = criteria.from(Producto.class);
		criteria.select(producto).orderBy(cb.asc(producto.get("nombre")));
		return em.createQuery(criteria).getResultList();
	}
}
