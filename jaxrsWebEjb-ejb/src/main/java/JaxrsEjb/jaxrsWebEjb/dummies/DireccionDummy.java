package JaxrsEjb.jaxrsWebEjb.dummies;

public class DireccionDummy{

    private String direccion;
    
    public DireccionDummy() {
		this.direccion = "";
	}
    
	public DireccionDummy(String direccion) {
		this.direccion = direccion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
}