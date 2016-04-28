package JaxrsEjb.jaxrsWebEjb.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import JaxrsEjb.jaxrsWebEjb.model.Cliente;

@Entity
@XmlRootElement
@Table(name = "pago")
public class Pago implements Serializable {
    /** Default value included to remove warning. Remove or modify at will. **/
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String observacion;

    @NotNull
    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="venta_id")
    private Venta venta;
    
    @Column(name="monto")
    private Integer monto;
    
    @NotNull
    @Column(name="fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    public Pago() {
		this.observacion = "";
		this.cliente = null;
		this.venta = null;
		this.monto = 0;
		this.fecha = new Date();
	}

	public Pago(String observacion, Cliente cliente, Venta venta, Integer monto, Date fecha) {
		this.observacion = observacion;
		this.cliente = cliente;
		this.venta = venta;
		this.monto = monto;
		this.fecha = fecha;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
    public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
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
