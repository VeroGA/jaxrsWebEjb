package JaxrsEjb.jaxrsWebEjb.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "token"}))
public class Usuario implements Serializable {
    /** Default value included to remove warning. Remove or modify at will. **/
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 55)
    private String username;

    @NotNull
    @Size(min = 1, max = 255)
    private String password;

    @NotNull
    private String rol;
    
    @NotNull
    @Size(min = 1, max = 255)
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
