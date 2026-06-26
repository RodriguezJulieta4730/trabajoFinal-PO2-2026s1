package TemplateMethod;

import Clases.Cliente;

public class BilleteraVirtual extends MedioDePago {
    private final BilleteraVirtualApi billeteraVirtualApi;

    public BilleteraVirtual(BilleteraVirtualApi billeteraVirtualApi) {
        this.billeteraVirtualApi = billeteraVirtualApi;
    }

    @Override
    boolean validarDatos(double montoAPagar,Cliente cliente) {
        return billeteraVirtualApi.validarDatos(montoAPagar,cliente.getCbu(),cliente.getAlias());
    } // aclarar en informe toma de decision de pasar monto a pagar,cbu y alias

    @Override
    boolean reservarFondos(double montoAPagar, Cliente cliente) {
        return billeteraVirtualApi.reservarFondos(montoAPagar,cliente.getCbu(),cliente.getAlias());
    }

    @Override
    boolean ejecutarTransaccion(double montoAPagar, Cliente cliente) {
        return billeteraVirtualApi.ejecutarTransaccion(montoAPagar);
    }

    @Override
    String notificarResultado() {
        return billeteraVirtualApi.notificarResultado();
    }
}
