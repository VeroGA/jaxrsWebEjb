package JaxrsEjb.jaxrsWebEjb.dummies;

public class VentaDetalleDummy{
    
    private Integer cantidad;

    private Long venta_id;
    
    private Long producto_id;
    
    public VentaDetalleDummy() {
		this.cantidad = 0;
		this.venta_id = -1L;
		this.producto_id =-1L;
	}
    
	public VentaDetalleDummy(Integer cantidad, Long venta_id, Long producto_id) {
		this.cantidad = cantidad;
		this.venta_id = venta_id;
		this.producto_id = producto_id;
	}

	public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

	public Long getVenta_id() {
		return venta_id;
	}

	public void setVenta_id(Long venta_id) {
		this.venta_id = venta_id;
	}

	public Long getProducto_id() {
		return producto_id;
	}

	public void setProducto_id(Long producto_id) {
		this.producto_id = producto_id;
	}
    
}