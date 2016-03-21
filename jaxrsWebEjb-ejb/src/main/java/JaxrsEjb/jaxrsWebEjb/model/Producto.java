package JaxrsEjb.jaxrsWebEjb.model;

import java.io.Serializable;
//import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.ManyToOne;
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
public class Producto implements Serializable {
	/** Default value included to remove warning. Remove or modify at will. **/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@NotEmpty
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
	private String nombre;

	@NotNull
	@NotEmpty
	@Size(min = 4, max = 7)
	@Digits(fraction = 0, integer = 7)
	private Integer precio;

	@NotNull
	private Integer stock;

	@NotNull
	@ManyToOne
	private Proveedor proveedor;

	// @OneToMany(mappedBy = "producto")
	// private List<CompraDetalle> compra_detalle;

	// @OneToMany(mappedBy = "producto")
	// private List<VentaDetalle> venta_detalle;

	@Size(min = 1, max = 100)
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
