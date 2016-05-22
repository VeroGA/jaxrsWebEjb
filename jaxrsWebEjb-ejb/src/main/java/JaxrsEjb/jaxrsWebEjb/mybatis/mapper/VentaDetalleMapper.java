package JaxrsEjb.jaxrsWebEjb.mybatis.mapper;

import java.util.List;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Venta;
import JaxrsEjb.jaxrsWebEjb.mybatis.bean.VentaDetalle;

public interface VentaDetalleMapper{

    public Boolean isExist(Integer id);

    public VentaDetalle getVentaDetalleById(Integer Id);

    public Integer newVentaDetalle(VentaDetalle ventaDetalle);

    public List<VentaDetalle> getAll();
    
    public List<VentaDetalle> getAllByVenta(Venta venta);

    public VentaDetalle updateVentaDetalle(VentaDetalle ventaDetalle);

    public void deleteVentaDetalle(VentaDetalle ventaDetalle);
}
