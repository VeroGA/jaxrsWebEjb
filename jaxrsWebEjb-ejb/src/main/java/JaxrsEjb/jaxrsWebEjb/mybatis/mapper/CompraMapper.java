package JaxrsEjb.jaxrsWebEjb.mybatis.mapper;

import java.util.List;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Compra;

public interface CompraMapper{

    public Boolean isExist(Integer id);

    public Compra getCompraById(Integer Id);

    public Integer newCompra(Compra compra);

    public List<Compra> getAll();

    public Compra updateCompra(Compra compra);

    public void deleteCompra(Compra compra);
}
