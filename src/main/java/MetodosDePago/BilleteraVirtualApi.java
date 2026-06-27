package MetodosDePago;

public interface BilleteraVirtualApi {
    boolean validarDatos(double montoAPagar, long cbu, String alias);
    boolean reservarFondos(double montoAPagar, long cbu, String alias);
    boolean ejecutarTransaccion(double precioTotal);
    String notificarResultado();
}