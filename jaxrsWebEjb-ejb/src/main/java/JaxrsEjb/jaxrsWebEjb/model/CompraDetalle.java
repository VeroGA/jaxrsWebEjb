package JaxrsEjb.jaxrsWebEjb.model;

import java.io.Serializable;

//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
//import javax.persistence.UniqueConstraint;
//import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
//import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

//import org.hibernate.validator.constraints.Email;
//import org.hibernate.validator.constraints.NotEmpty;

@Entity
@XmlRootElement
@Table(name = "compra_detalle")//, uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class CompraDetalle implements Serializable {
    /** Default value included to remove warning. Remove or modify at will. **/
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    
    @NotNull
    private Long cantidad;

    @NotNull
    @ManyToOne
    private Compra compra;
    
    @NotNull
    @ManyToOne
    private Producto producto;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;    
    }
    
    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
    
}