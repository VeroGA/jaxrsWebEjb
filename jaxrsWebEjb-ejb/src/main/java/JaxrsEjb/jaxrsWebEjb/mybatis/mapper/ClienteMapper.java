package JaxrsEjb.jaxrsWebEjb.mybatis.mapper;

import java.util.List;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Cliente;

public interface ClienteMapper{

    public Boolean isExist(Integer id);

    public Cliente getClienteById(Integer id);

    public Cliente findByEmail(String email);
    
    public Integer newCliente(Cliente cliente);
    
    public List<Cliente> getAll();
    
    public Integer updateCliente(Cliente cliente);
    
    public void deleteCliente(Integer id);
}
