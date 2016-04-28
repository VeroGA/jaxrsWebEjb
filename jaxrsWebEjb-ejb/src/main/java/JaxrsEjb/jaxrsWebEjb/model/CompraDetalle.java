package JaxrsEjb.jaxrsWebEjb.model;

import java.io.Serializable;

//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "compra_detalle")
public class CompraDetalle implements Serializable {
    /** Default value included to remove warning. Remove or modify at will. **/
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    
    @NotNull
    private Integer cantidad;

    @NotNull
    @ManyToOne
    private Compra compra;
    
    @NotNull
    @ManyToOne
    private Producto producto;
    
    public CompraDetalle() {
		this.cantidad = 0;
		this.compra = null;
		this.producto = null;
	}
    
    public CompraDetalle(Integer cantidad, Compra compra, Producto producto) {
		this.cantidad = cantidad;
		this.compra = compra;
		this.producto = producto;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;    
    }
    
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
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