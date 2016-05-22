package JaxrsEjb.jaxrsWebEjb.mybatis.mapper;

import java.util.List;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Proveedor;

public interface ProveedorMapper{
	
    public Boolean isExist(Integer id);
    
    public Proveedor getProveedorById(Integer id);
    
    public Proveedor findByEmail(String email);
    
    public Integer newProveedor(Proveedor proveedor);
    
    public List<Proveedor> getAll();
    
    public Integer updateProveedor(Proveedor proveedor);
    
    public void deleteProveedor(Integer id);
}
