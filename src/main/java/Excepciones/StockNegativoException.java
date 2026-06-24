package Excepciones;

public class StockNegativoException extends RuntimeException {
    public StockNegativoException(String mensaje) {
        super(mensaje);
    }
}
