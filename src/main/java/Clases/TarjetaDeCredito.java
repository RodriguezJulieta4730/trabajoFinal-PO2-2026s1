package Clases;

public class TarjetaDeCredito extends MedioDePago {
    private TarjetaApi tarjetaApi;

    public TarjetaDeCredito(TarjetaApi tarjetaApi){
        this.tarjetaApi = tarjetaApi;
    }

    @Override
    boolean validarDatos(Cliente cliente) {
        return tarjetaApi.validarDatos(cliente.getDatosDeTarjeta());
    }

    @Override
    boolean reservarFondos(double montoAPagar, Cliente cliente) {
        return tarjetaApi.reservarFondos(montoAPagar,cliente.getDatosDeTarjeta());
    }

    @Override
    boolean ejecutarTransaccion(double montoAPagar, Cliente cliente) {
        return tarjetaApi.ejecutarTransaccion(montoAPagar,cliente.getDatosDeTarjeta());
    }

    @Override
    String notificarResultado() {
        return tarjetaApi.notificarResultado();
    }
}
