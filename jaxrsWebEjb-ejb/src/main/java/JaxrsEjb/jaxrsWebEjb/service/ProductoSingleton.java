package JaxrsEjb.jaxrsWebEjb.service;

import java.util.Hashtable;

import javax.ejb.Singleton;
import javax.naming.InitialContext;

@Singleton
public class ProductoSingleton {

	Hashtable<String, ProductoStateful> contenedor = new Hashtable<String, ProductoStateful>();

	public void remover(Long id) {
		contenedor.remove(id.toString());
	}

	public Long crear() {
		System.out.println("estoy en el singleton!");
		String s = generarNumero();
		
		ProductoStateful cl = performJNDIlookup();

		contenedor.put(s, cl);
		return Long.parseLong(s);

	}

	public ProductoStateful obtener(Long id) {
		String s = id.toString();
		return contenedor.get(s);

	}

	public ProductoStateful performJNDIlookup() {
		try {
			
			return (ProductoStateful) new InitialContext().lookup("java:module/ProductoStateful");
			
		} catch (Exception e) {
			
			System.out.println("Mensaje de error!");
			System.out.println(e.getMessage());
			
			throw new RuntimeException(e);
			
		}

	}

	private String generarNumero() {
		Long id = System.currentTimeMillis();
		String s = id.toString();
		return s;
	}

}
