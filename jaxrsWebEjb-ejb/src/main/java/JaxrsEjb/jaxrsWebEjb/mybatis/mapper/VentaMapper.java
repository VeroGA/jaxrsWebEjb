package JaxrsEjb.jaxrsWebEjb.mybatis.mapper;

import java.util.List;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Venta;

public interface VentaMapper{

    public Boolean isExist(Integer id);

    public Venta getVentaById(Integer Id);

    public Integer newVenta(Venta venta);

    public List<Venta> getAll();

    public Venta updateVenta(Venta venta);

    public void deleteVenta(Venta venta);
}
