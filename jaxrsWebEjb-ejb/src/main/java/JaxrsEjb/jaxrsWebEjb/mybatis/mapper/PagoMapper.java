package JaxrsEjb.jaxrsWebEjb.mybatis.mapper;

import java.util.List;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Pago;

public interface PagoMapper{

    public Boolean isExist(Integer id);

    public Pago getPagoById(Integer Id);

    public Integer newPago(Pago pago);

    public List<Pago> getAll();

    public Pago updatePago(Pago pago);

    public void deletePago(Integer id);
}
