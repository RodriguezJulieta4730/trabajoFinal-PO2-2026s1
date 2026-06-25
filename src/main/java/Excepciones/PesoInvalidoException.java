package Excepciones;

public class PesoInvalidoException extends RuntimeException {
    public PesoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
