package MetodosDePago;

import Clases.Cliente;

public class TransferenciaBancaria extends MedioDePago {
    private final TransferenciaApi transferenciaApi;

    public TransferenciaBancaria(TransferenciaApi transferenciaApi) {
        this.transferenciaApi = transferenciaApi;
    }

    @Override
    public boolean validarDatos(double montoAPagar, Cliente cliente) {
        return transferenciaApi.validarDatos(cliente.getCbu(), cliente.getAlias());
    }

    @Override
    public boolean reservarFondos(double montoAPagar, Cliente cliente) {
        return true;
    } // la transferencia es directa

    @Override
    public boolean ejecutarTransaccion(double montoAPagar, Cliente cliente) {
        return transferenciaApi.ejecutarTransaccion(montoAPagar, cliente.getCbu(), cliente.getAlias());
    }

    @Override
    public String notificarResultado(Cliente cliente) {
        return  "Pago exitoso mediante Transferencia Bancaria para el alias: " + cliente.getAlias()
                + " (CBU: " + cliente.getCbu() + ")";
    }
}
