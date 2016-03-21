package JaxrsEjb.jaxrsWebEjb.dummies;

public class ProductoDummy {
	
	private String nombre;

	
	private Integer precio;

	
	private Integer stock;

	
	private Long proveedor_id;
	
	private String descripcion;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Long getProveedor_id() {
		return proveedor_id;
	}

	public void setProveedor_id(Long proveedor_id) {
		this.proveedor_id = proveedor_id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
