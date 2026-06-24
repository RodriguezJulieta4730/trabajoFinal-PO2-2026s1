package Excepciones;

public class NoHayStockException extends RuntimeException {
    public NoHayStockException(String mensaje) {
        super(mensaje);
    }
}
