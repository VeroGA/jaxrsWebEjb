package JaxrsEjb.jaxrsWebEjb.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "venta")
public class Venta implements Serializable {
	/** Default value included to remove warning. Remove or modify at will. **/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	// @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL,
	// mappedBy="venta")
	// private List<Pago> pago;

	// @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL,
	// mappedBy="venta")
	// private List<VentaDetalle> venta_detalle;

	@Size(min = 1, max = 100)
	private String descripcion;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@NotNull
	private Integer total;
	
	public Venta() {
		this.descripcion = "";
		this.total = 0;
	}

	public Venta(String descripcion, Cliente cliente, Integer total) {
		this.descripcion = descripcion;
		this.cliente = cliente;
		this.total = total;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}
