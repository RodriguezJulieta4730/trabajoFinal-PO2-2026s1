package MetodosDePago;

public interface TarjetaApi {
     boolean validarDatos(String datosDeTarjeta);
     boolean reservarFondos(double montoAPagar, String datosDeTarjeta);
     boolean ejecutarTransaccion(double montoAPagar, String datosDeTarjeta);
}
