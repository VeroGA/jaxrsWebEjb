package JaxrsEjb.jaxrsWebEjb.mybatis.bean;

import java.util.ArrayList;
import java.util.List;

import JaxrsEjb.jaxrsWebEjb.dummies.CompraDummy;
import JaxrsEjb.jaxrsWebEjb.mybatis.bean.CompraDetalle;

public class Compra {
    
    private Integer id;

    private Integer proveedorId;
    
    private String descripcion;
    
    private Integer total;
    
    private List<CompraDetalle> detalles;
    
    public Compra() {
		this.proveedorId = null;
		this.descripcion = "";
		this.total = 0;
	}
    
    public Compra(Integer proveedorId, String descripcion, Integer total) {
		this.proveedorId = proveedorId;
		this.descripcion = descripcion;
		this.total = total;
	}
    
    public Compra(CompraDummy comprad) {
		this.proveedorId = Integer.valueOf(comprad.getProveedor_id().toString());
		this.descripcion = comprad.getDescripcion();
		this.total = comprad.getTotal();
		
		this.detalles = new ArrayList<CompraDetalle>();
		
		for(int i = 0; i<comprad.getCompra_detalle().size(); i++){
			this.detalles.add(new CompraDetalle(comprad.getCompra_detalle().get(i)));
		}
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

	public Integer getProveedorId() {
		return proveedorId;
	}

	public void setProveedorId(Integer proveedorId) {
		this.proveedorId = proveedorId;
	}

	public List<CompraDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<CompraDetalle> detalles) {
		this.detalles = detalles;
	}
	
}
