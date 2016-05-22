package JaxrsEjb.jaxrsWebEjb.mybatis.mapper;

import java.util.List;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Compra;
import JaxrsEjb.jaxrsWebEjb.mybatis.bean.CompraDetalle;

public interface CompraDetalleMapper{

    public Boolean isExist(Integer id);

    public CompraDetalle getCompraDetalleById(Integer Id);

    public Integer newCompraDetalle(CompraDetalle ventaDetalle);

    public List<CompraDetalle> getAll();
    
    public List<CompraDetalle> getAllByCompra(Compra compra);

    public CompraDetalle updateCompraDetalle(CompraDetalle compraDetalle);

    public void deleteCompraDetalle(CompraDetalle compraDetalle);
}
