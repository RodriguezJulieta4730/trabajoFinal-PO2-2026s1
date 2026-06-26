package Clases;

public interface BilleteraVirtualApi {
    boolean validarDatos(long cbu, String alias);
    boolean reservarFondos();
    boolean ejecutarTransaccion(double precioTotal);
    String notificarResultado();
}