package JaxrsEjb.jaxrsWebEjb.dummies;

public class ProveedorDummy {
	private String nombre;

	private String email;

	private String phoneNumber;
	
	public ProveedorDummy() {
		this.nombre = "";
		this.phoneNumber = "";
	}

	public ProveedorDummy(String nombre, String email, String phoneNumber) {
		this.nombre = nombre;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
