package JaxrsEjb.jaxrsWebEjb.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "venta_detalle")
public class VentaDetalle implements Serializable {
    /** Default value included to remove warning. Remove or modify at will. **/
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @NotNull
    private Integer cantidad;

    @NotNull
    @ManyToOne
    private Venta venta;
    
    @NotNull
    @ManyToOne
    private Producto producto;
    
    public VentaDetalle() {
		this.cantidad = 0;
	}
    
    public VentaDetalle(Integer cantidad, Venta venta, Producto producto) {
		this.cantidad = cantidad;
		this.venta = venta;
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

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
}