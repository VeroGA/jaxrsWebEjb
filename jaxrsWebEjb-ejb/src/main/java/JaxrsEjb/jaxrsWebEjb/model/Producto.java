package JaxrsEjb.jaxrsWebEjb.model;

import java.io.Serializable;
//import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
//import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@XmlRootElement
@Table(name = "producto", uniqueConstraints = @UniqueConstraint(columnNames = "nombre"))
@NamedQuery(query = "Select p from Producto p where p.nombre = :nombre", name = "find producto by nombre")
public class Producto implements Serializable {
	/** Default value included to remove warning. Remove or modify at will. **/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name="nombre", length=30)
	private String nombre;

	@Column(name="precio")
	private int precio;

	@Column(name="stock")
	private int stock;

	@NotNull
	@ManyToOne
	private Proveedor proveedor;

	// @OneToMany(mappedBy = "producto")
	// private List<CompraDetalle> compra_detalle;

	// @OneToMany(mappedBy = "producto")
	// private List<VentaDetalle> venta_detalle;

	@Column(name="descripcion",nullable=false,length=100)
	private String descripcion;
	
	public Producto() {
		this.nombre = "";
		this.precio = 0;
		this.stock = 0;
		this.proveedor = null;
		this.descripcion = "";
	}

	public Producto(String nombre, Integer precio, Integer stock, Proveedor proveedor, String descripcion) {
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
		this.proveedor = proveedor;
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

}
