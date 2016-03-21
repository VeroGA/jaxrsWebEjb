package JaxrsEjb.jaxrsWebEjb.exceptions;

public class ProductoDuplicadoException extends Exception {

    public ProductoDuplicadoException(String message) {
        super(message);
    }

    public ProductoDuplicadoException(String message, Throwable throwable) {
        super(message, throwable);
    }

}