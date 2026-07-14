package MetodosDePago;

import Clases.Cliente;

public class TarjetaDeCredito extends MedioDePago {
    private final TarjetaApi tarjetaApi;

    public TarjetaDeCredito(TarjetaApi tarjetaApi){
        this.tarjetaApi = tarjetaApi;
    }

    @Override
    public boolean validarDatos(double montoAPagar, Cliente cliente) {
        return tarjetaApi.validarDatos(cliente.getDatosTarjeta());
    }

    @Override
    public boolean reservarFondos(double montoAPagar, Cliente cliente) {
        return tarjetaApi.reservarFondos(montoAPagar, cliente.getDatosTarjeta());
    }

    @Override
    public boolean ejecutarTransaccion(double montoAPagar, Cliente cliente) {
        return tarjetaApi.ejecutarTransaccion(montoAPagar, cliente.getDatosTarjeta());
    }

    @Override
    public String notificarResultado(Cliente cliente) {
        return "Pago exitoso con Tarjeta de Crédito para el cliente: "
                + cliente // Usamos el cliente que está pagando ahora
                + " usando la tarjeta: " + cliente.getDatosTarjeta();
    }
}
