package JaxrsEjb.jaxrsWebEjb.mybatis.bean;

public class Usuario {
    private Integer id;
    
    private String username;
    
    private String password;
    
    private String rol;
    
    private String token;
    
    public Usuario(){
    	this.username = "";
    	this.password = "";
    	this.rol = "";
    	this.token = "";
    }
    
    public Usuario(String username, String password, String rol, String token){
    	this.username = username;
    	this.password = password;
    	this.rol = rol;
    	this.token = token;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
