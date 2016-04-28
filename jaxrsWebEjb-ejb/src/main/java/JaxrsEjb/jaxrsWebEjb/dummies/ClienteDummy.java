package JaxrsEjb.jaxrsWebEjb.dummies;

public class ClienteDummy{

    private String name;

    private String email;

    private String phoneNumber;
    
    private Integer saldoPagar;
    
    public ClienteDummy() {
		this.name = "";
		this.email = "";
		this.phoneNumber = "";
		this.saldoPagar = 0;
	}

    public ClienteDummy(String name, String email, String phoneNumber, Integer saldoPagar) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.saldoPagar = saldoPagar;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    
	public Integer getSaldoPagar() {
		return saldoPagar;
	}

	public void setSaldoPagar(Integer saldoPagar) {
		this.saldoPagar = saldoPagar;
	}
}
