package Excepciones;

public class NoHayProductoEnPedidoException extends RuntimeException {
    public NoHayProductoEnPedidoException(String mensaje) {
        super(mensaje);
    }
}
