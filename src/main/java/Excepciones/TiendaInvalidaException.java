package Excepciones;

public class TiendaInvalidaException extends RuntimeException {
    public TiendaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
