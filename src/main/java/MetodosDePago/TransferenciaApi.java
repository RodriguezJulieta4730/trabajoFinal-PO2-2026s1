package MetodosDePago;

public interface TransferenciaApi {
    boolean validarDatos(long cbu, String alias);
    boolean ejecutarTransaccion(double montoAPagar, long cbu, String alias);
}
