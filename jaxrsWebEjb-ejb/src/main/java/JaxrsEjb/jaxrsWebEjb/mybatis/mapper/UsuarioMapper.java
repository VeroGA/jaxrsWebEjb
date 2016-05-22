package JaxrsEjb.jaxrsWebEjb.mybatis.mapper;

import java.util.List;

import JaxrsEjb.jaxrsWebEjb.mybatis.bean.Usuario;

public interface UsuarioMapper{

    public Boolean isExist(Integer id);

    public Usuario getUsuarioById(Integer id);
    
    public Usuario getUsuarioByUsername(String username);

    public Usuario getUsuarioByToken(String token);
    
    public Integer newUsuario(Usuario usuario);
    
    public List<Usuario> getAll();
    
    public Integer updateUsuario(Usuario usuario);
    
    public Integer updateUsuarioToken(Usuario usuario);
    
    public void deleteUsuario(Integer id);
}
