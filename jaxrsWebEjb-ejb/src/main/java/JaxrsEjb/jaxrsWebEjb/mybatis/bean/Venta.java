package JaxrsEjb.jaxrsWebEjb.mybatis.bean;

import java.util.ArrayList;
import java.util.List;

import JaxrsEjb.jaxrsWebEjb.dummies.VentaDummy;

public class Venta {
	
	private Integer id;

	private String descripcion;

	private Integer clienteId;

	private Integer total;
	
	private List<VentaDetalle> detalles;
	
	public Venta() {
		this.descripcion = "";
		this.total = 0;
	}

	public Venta(String descripcion, Integer clienteId, Integer total) {
		this.descripcion = descripcion;
		this.clienteId = clienteId;
		this.total = total;
	}
	
	public Venta(VentaDummy ventad) {
		this.clienteId = Integer.valueOf(ventad.getCliente_id().toString());
		this.descripcion = ventad.getDescripcion();
		this.total = ventad.getTotal();
		
		this.detalles = new ArrayList<VentaDetalle>();
		
		for(int i = 0; i<ventad.getVenta_detalle().size(); i++){
			this.detalles.add(new VentaDetalle(ventad.getVenta_detalle().get(i)));
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
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

	public List<VentaDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<VentaDetalle> detalles) {
		this.detalles = detalles;
	}
	
}
