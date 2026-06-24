package Excepciones;

public class CantidadInsuficienteException extends RuntimeException {
    public CantidadInsuficienteException(String mensaje) {
        super(mensaje);
    }

}
