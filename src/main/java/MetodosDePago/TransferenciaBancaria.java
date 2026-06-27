package MetodosDePago;

import Clases.Cliente;

public class TransferenciaBancaria extends MedioDePago {
    private final TransferenciaApi transferenciaApi;
    public TransferenciaBancaria(TransferenciaApi transferenciaApi) {
        this.transferenciaApi = transferenciaApi;
    }

    @Override
    public boolean validarDatos(double montoAPagar,Cliente cliente){
        return transferenciaApi.validarDatos(cliente.getCbu(),cliente.getAlias());
    }

    @Override
    boolean reservarFondos(double montoAPagar, Cliente cliente) {
        return transferenciaApi.reservarFondos(montoAPagar,cliente.getCbu(),cliente.getAlias());
    }

    @Override
    boolean ejecutarTransaccion(double montoAPagar, Cliente cliente) {
        return transferenciaApi.ejecutarTransaccion(montoAPagar,cliente.getCbu(),cliente.getAlias());
    }

    @Override
    String notificarResultado() {
        return transferenciaApi.notificarResultado();
    }

//    @Override
//    boolean reservarFondos(double montoAPagar, Cliente cliente);
//    @Override
//    boolean ejecutarTransaccion(double montoAPagar, Cliente cliente);
//    @Override
//    String notificarResultado();
}
