package JaxrsEjb.jaxrsWebEjb.dummies;

import java.util.ArrayList;
import java.util.List;

public class VentaDummy{
    
    private List<VentaDetalleDummy> venta_detalle;
    
    private String descripcion;
    
    private Long cliente_id;
    
    private Integer total;
    
    public VentaDummy() {
		this.venta_detalle = new ArrayList<VentaDetalleDummy>();
		this.descripcion = "";
		this.cliente_id = -1L;
		this.total = 0;
	}

	public VentaDummy(List<VentaDetalleDummy> venta_detalle, String descripcion, Long cliente_id, Integer total) {
		this.venta_detalle = venta_detalle;
		this.descripcion = descripcion;
		this.cliente_id = cliente_id;
		this.total = total;
	}

	public List<VentaDetalleDummy> getVenta_detalle() {
		return venta_detalle;
	}

	public void setVenta_detalle(List<VentaDetalleDummy> venta_detalle) {
		this.venta_detalle = venta_detalle;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(Long cliente_id) {
		this.cliente_id = cliente_id;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
