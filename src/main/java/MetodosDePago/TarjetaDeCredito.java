package MetodosDePago;

import Clases.Cliente;
import lombok.Getter;

@Getter
public class TarjetaDeCredito extends MedioDePago {
    private final TarjetaApi tarjetaApi;
    private final Cliente cliente;
    private  final String datosDeTarjeta;


    public TarjetaDeCredito(TarjetaApi tarjetaApi, Cliente cliente, String datosDeTarjeta){
        this.tarjetaApi = tarjetaApi;
        this.cliente = cliente;
        this.datosDeTarjeta = datosDeTarjeta;
    }

    @Override
    public boolean validarDatos(double montoAPagar,Cliente cliente) {
        return tarjetaApi.validarDatos(this.datosDeTarjeta);
    }

    @Override
    public boolean reservarFondos(double montoAPagar, Cliente cliente) {
        return tarjetaApi.reservarFondos(montoAPagar,this.datosDeTarjeta);
    }

    @Override
    public boolean ejecutarTransaccion(double montoAPagar, Cliente cliente) {
        return tarjetaApi.ejecutarTransaccion(montoAPagar,this.datosDeTarjeta);
    }

    @Override
    public String notificarResultado(Cliente cliente) {
        return "Pago exitoso con Tarjeta de Crédito para el cliente: "
                + this.cliente
                + " usando la tarjeta: " + this.datosDeTarjeta;
    }
}
