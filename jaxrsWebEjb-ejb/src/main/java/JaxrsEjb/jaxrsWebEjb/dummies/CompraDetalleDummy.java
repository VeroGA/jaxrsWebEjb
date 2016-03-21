package JaxrsEjb.jaxrsWebEjb.dummies;

public class CompraDetalleDummy {

	private Long compra_id;

	private Long producto_id;
	
	private Integer cantidad;
	
	public CompraDetalleDummy() {
		this.compra_id = -1L;
		this.producto_id = -1L;
		this.cantidad = 0;
	}

	public CompraDetalleDummy(Long compra_id, Long producto_id, Integer cantidad) {
		this.compra_id = compra_id;
		this.producto_id = producto_id;
		this.cantidad = cantidad;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Long getCompra_id() {
		return compra_id;
	}

	public void setCompra_id(Long compra_id) {
		this.compra_id = compra_id;
	}

	public Long getProducto_id() {
		return producto_id;
	}

	public void setProducto_id(Long producto_id) {
		this.producto_id = producto_id;
	}

}