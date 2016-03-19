package JaxrsEjb.jaxrsWebEjb.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
//import javax.persistence.UniqueConstraint;
//import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
//import javax.validation.constraints.Pattern;
//import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

//import org.hibernate.validator.constraints.Email;
//import org.hibernate.validator.constraints.NotEmpty;

@Entity
@XmlRootElement
@Table(name = "venta")//, uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Venta implements Serializable {
    /** Default value included to remove warning. Remove or modify at will. **/
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="venta")
    private List<Pago> pago;
    
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="venta")
    private List<VentaDetalle> venta_detalle;
    
    @Size(min = 1, max = 100)
    //@Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String descripcion;
    
    @NotNull
    private Long total;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

	public List<Pago> getPago() {
		return pago;
	}

	public void setPago(List<Pago> pago) {
		this.pago = pago;
	}

	public List<VentaDetalle> getVenta_detalle() {
		return venta_detalle;
	}

	public void setVenta_detalle(List<VentaDetalle> venta_detalle) {
		this.venta_detalle = venta_detalle;
	}
}
