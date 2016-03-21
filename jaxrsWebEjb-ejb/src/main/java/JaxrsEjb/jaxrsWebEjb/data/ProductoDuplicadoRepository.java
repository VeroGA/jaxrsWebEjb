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

import JaxrsEjb.jaxrsWebEjb.model.ProductoDuplicado;
import JaxrsEjb.jaxrsWebEjb.model.Producto;

@ApplicationScoped
public class ProductoDuplicadoRepository {

	@Inject
	private EntityManager em;

	public boolean isExist(Long id) {
		return (null != findById(id));
	}

	public ProductoDuplicado findById(Long id) {
		return em.find(ProductoDuplicado.class, id);
	}

	public ProductoDuplicado findAllByProducto(Producto producto) {

		/*List<ProductoDuplicado> retorno = null;
		try {

			Session session = (Session) em.getDelegate();
			Criteria criteria = session.createCriteria(Producto.class, "producto");
			criteria.createAlias("producto.proveedor", "proveedor");

			criteria.add(Restrictions.eq("proveedor.id", idProveedor));

			retorno = criteria.list();

		} catch (Exception e) {
			throw e;
		}*/

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ProductoDuplicado> criteria = cb.createQuery(ProductoDuplicado.class);
		Root<ProductoDuplicado> productos = criteria.from(ProductoDuplicado.class);
		criteria.select(productos).where(cb.equal(productos.get("producto"), producto));
		return em.createQuery(criteria).getSingleResult();
	}
}
