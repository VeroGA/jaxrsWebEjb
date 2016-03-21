package JaxrsEjb.jaxrsWebEjb.dummies;

import java.util.ArrayList;
import java.util.List;

public class CompraDummy {

	private Long proveedor_id;

	private List<CompraDetalleDummy> compra_detalle;

	private String descripcion;

	private Integer total;
	
	public CompraDummy() {
		this.proveedor_id = -1L;
		this.compra_detalle = new ArrayList<CompraDetalleDummy>();
		this.descripcion = "";
		this.total = 0;
	}

	public CompraDummy(Long proveedor_id, List<CompraDetalleDummy> compra_detalle, String descripcion, Integer total) {
		this.proveedor_id = proveedor_id;
		this.compra_detalle = compra_detalle;
		this.descripcion = descripcion;
		this.total = total;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getProveedor_id() {
		return proveedor_id;
	}

	public void setProveedor_id(Long proveedor_id) {
		this.proveedor_id = proveedor_id;
	}

	public List<CompraDetalleDummy> getCompra_detalle() {
		return compra_detalle;
	}

	public void setCompra_detalle(List<CompraDetalleDummy> compra_detalle) {
		this.compra_detalle = compra_detalle;
	}
}
