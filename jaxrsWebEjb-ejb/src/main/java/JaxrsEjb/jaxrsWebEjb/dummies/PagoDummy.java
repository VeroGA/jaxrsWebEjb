package JaxrsEjb.jaxrsWebEjb.dummies;

import java.util.Date;

public class PagoDummy {

	private String observacion;

	private Long cliente_id;

	private Long venta_id;

	private Integer monto;
	
	private Date fecha;
	
	public PagoDummy() {
		this.observacion = "";
		this.cliente_id = -1L;
		this.venta_id = -1L;
		this.monto = 0;
		this.fecha = new Date();
	}
	
	public PagoDummy(String observacion, Long cliente_id, Long venta_id, Integer monto, Date fecha) {
		this.observacion = observacion;
		this.cliente_id = cliente_id;
		this.venta_id = venta_id;
		this.monto = monto;
		this.fecha = fecha;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Long getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(Long cliente_id) {
		this.cliente_id = cliente_id;
	}

	public Long getVenta_id() {
		return venta_id;
	}

	public void setVenta_id(Long venta_id) {
		this.venta_id = venta_id;
	}

	public Integer getMonto() {
		return monto;
	}

	public void setMonto(Integer monto) {
		this.monto = monto;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
