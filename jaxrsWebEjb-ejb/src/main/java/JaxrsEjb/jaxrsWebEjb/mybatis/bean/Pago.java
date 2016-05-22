package JaxrsEjb.jaxrsWebEjb.mybatis.bean;

import java.util.Date;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Cliente;

public class Pago {
	private Integer id;

	private String observacion;

	private Integer cliente_id;

	private Integer venta_id;

	private Integer monto;

	private Date fecha;

	public Pago() {
		this.observacion = "";
		this.cliente_id = null;
		this.venta_id = null;
		this.monto = 0;
		this.fecha = new Date();
	}

	public Pago(String observacion, Integer cliente_id, Integer venta_id, Integer monto, Date fecha) {
		this.observacion = observacion;
		this.cliente_id = cliente_id;
		this.venta_id = venta_id;
		this.monto = monto;
		this.fecha = fecha;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Integer getClienteId() {
		return cliente_id;
	}

	public void setClienteId(Integer cliente_id) {
		this.cliente_id = cliente_id;
	}

	public Integer getVentaId() {
		return venta_id;
	}

	public void setVentaId(Integer venta_id) {
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
