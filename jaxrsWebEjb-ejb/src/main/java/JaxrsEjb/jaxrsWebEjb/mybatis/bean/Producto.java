package JaxrsEjb.jaxrsWebEjb.mybatis.bean;

public class Producto {

	private Integer id;

	private String nombre;

	private int precio;

	private int stock;

	private Integer proveedorId;

	private String descripcion;

	public Producto() {
		this.nombre = "";
		this.precio = 0;
		this.stock = 0;
		this.proveedorId = null;
		this.descripcion = "";
	}

	public Producto(String nombre, Integer precio, Integer stock, String descripcion, Integer proveedorId) {
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
		this.proveedorId = proveedorId;
		this.descripcion = descripcion;
	}
	
	public Producto(JaxrsEjb.jaxrsWebEjb.model.Producto producto) {
		this.nombre = producto.getNombre();
		this.precio = producto.getPrecio();
		this.stock = producto.getStock();
		this.proveedorId = Integer.valueOf(producto.getProveedor().toString());
		this.descripcion = producto.getDescripcion();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getProveedor() {
		return proveedorId;
	}

	public void setProveedor(Integer proveedorId) {
		this.proveedorId = proveedorId;
	}

}
