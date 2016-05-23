package JaxrsEjb.jaxrsWebEjb.model;

import java.io.Serializable;
//import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
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
@Table(name = "compra")//, uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Compra implements Serializable {
    /** Default value included to remove warning. Remove or modify at will. **/
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="proveedor_id")
    private Proveedor proveedor;
    
    //@OneToMany(mappedBy="compra")
    //private List<CompraDetalle> compra_detalle;
    
    @Size(min = 1, max = 100)
    private String descripcion;
    
    @NotNull
    private Integer total;
    
    public Compra() {
		this.proveedor = null;
		this.descripcion = "";
		this.total = 0;
	}
    
    public Compra(Proveedor proveedor, String descripcion, Integer total) {
		this.proveedor = proveedor;
		this.descripcion = descripcion;
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

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
}
