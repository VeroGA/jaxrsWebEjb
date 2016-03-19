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
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import JaxrsEjb.jaxrsWebEjb.model.Proveedor;

@ApplicationScoped
public class ProveedorRepository {

    @Inject
    private EntityManager em;
    
    public boolean isExist(Long id) {
		return (null != findById(id));
	}

    public Proveedor findById(Long id) {
        return em.find(Proveedor.class, id);
    }

    public Proveedor findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Proveedor> criteria = cb.createQuery(Proveedor.class);
        Root<Proveedor> proveedor = criteria.from(Proveedor.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(proveedor).where(cb.equal(proveedor.get(Proveedor_.name), email));
        criteria.select(proveedor).where(cb.equal(proveedor.get("email"), email));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<Proveedor> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Proveedor> criteria = cb.createQuery(Proveedor.class);
        Root<Proveedor> proveedor = criteria.from(Proveedor.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(proveedor).orderBy(cb.asc(proveedor.get(Proveedor_.name)));
        criteria.select(proveedor).orderBy(cb.asc(proveedor.get("name")));
        return em.createQuery(criteria).getResultList();
    }
}
