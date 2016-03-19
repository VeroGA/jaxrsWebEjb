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

import JaxrsEjb.jaxrsWebEjb.model.Venta;
import JaxrsEjb.jaxrsWebEjb.model.Cliente;

@ApplicationScoped
public class VentaRepository {

    @Inject
    private EntityManager em;
    
    public boolean isExist(Long id) {
		return (null != findById(id));
	}

    public Venta findById(Long id) {
        return em.find(Venta.class, id);
    }

    public List<Venta> findAllByCliente(Cliente cliente) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Venta> criteria = cb.createQuery(Venta.class);
        Root<Venta> venta = criteria.from(Venta.class);
        criteria.select(venta).where(cb.equal(venta.get("cliente"), cliente));
        return em.createQuery(criteria).getResultList();
    }
}
