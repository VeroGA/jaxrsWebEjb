package JaxrsEjb.jaxrsWebEjb.mybatis.bean;

import JaxrsEjb.jaxrsWebEjb.dummies.CompraDetalleDummy;

public class CompraDetalle {
	
	private Integer id;

	private Integer cantidad;

	private Integer compraId;

	private Integer productoId;

	public CompraDetalle() {
		this.cantidad = 0;
		this.compraId = null;
		this.productoId = null;
	}

	public CompraDetalle(Integer cantidad, Integer compraId, Integer productoId) {
		this.cantidad = cantidad;
		this.compraId = compraId;
		this.productoId = productoId;
	}
	
	public CompraDetalle(CompraDetalleDummy compradetalled) {
		this.cantidad = compradetalled.getCantidad();
		this.compraId = Integer.valueOf(compradetalled.getCompra_id().toString());
		this.productoId = Integer.valueOf(compradetalled.getProducto_id().toString());
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

	public Integer getCompraId() {
		return compraId;
	}

	public void setCompraId(Integer compraId) {
		this.compraId = compraId;
	}

	public Integer getProductoId() {
		return productoId;
	}

	public void setProductoId(Integer productoId) {
		this.productoId = productoId;
	}
	
}