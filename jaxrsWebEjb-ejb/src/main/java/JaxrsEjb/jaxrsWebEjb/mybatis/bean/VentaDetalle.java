package JaxrsEjb.jaxrsWebEjb.mybatis.bean;

import JaxrsEjb.jaxrsWebEjb.dummies.VentaDetalleDummy;

public class VentaDetalle{
	
    private Integer id;
    
    private Integer cantidad;

    private Integer ventaId;
    
    private Integer productoId;
    
    public VentaDetalle() {
		this.cantidad = 0;
	}
    
    public VentaDetalle(Integer cantidad, Integer ventaId, Integer productoId) {
		this.cantidad = cantidad;
		this.ventaId = ventaId;
		this.productoId = productoId;
	}
    
    public VentaDetalle(VentaDetalleDummy ventadetalled) {
		this.cantidad = ventadetalled.getCantidad();
		this.ventaId = Integer.valueOf(ventadetalled.getVenta_id().toString());
		this.productoId = Integer.valueOf(ventadetalled.getProducto_id().toString());
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;    
    }
    
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

	public Integer getVentaId() {
		return ventaId;
	}

	public void setVentaId(Integer ventaId) {
		this.ventaId = ventaId;
	}

	public Integer getProducto() {
		return productoId;
	}

	public void setProducto(Integer productoId) {
		this.productoId = productoId;
	}
}