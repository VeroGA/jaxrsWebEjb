package JaxrsEjb.jaxrsWebEjb.mybatis.mapper;

import java.util.List;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Producto;

public interface ProductoMapper{

    public Boolean isExist(Integer id);

    public Producto getProductoById(Integer Id);
    
    public Producto getProductoByName(String name);

    public Integer newProducto(Producto producto);

    public List<Producto> getAll();

    public Integer updateProducto(Producto producto);

    public void deleteProducto(Integer id);
}
