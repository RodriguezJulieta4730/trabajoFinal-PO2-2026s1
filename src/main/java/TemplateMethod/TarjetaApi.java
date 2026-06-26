package TemplateMethod;

import Clases.DatosDeTarjeta;

public interface TarjetaApi {
     boolean validarDatos(DatosDeTarjeta datosDeTarjeta);
     boolean reservarFondos(double montoAPagar, DatosDeTarjeta datosDeTarjeta);
     boolean ejecutarTransaccion(double montoAPagar, DatosDeTarjeta datosDeTarjeta);
     String notificarResultado();

}
