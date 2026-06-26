package Clases;

public class BilleteraVirtual extends MedioDePago {
    private BilleteraVirtualApi billeteraVirtualApi;

    public BilleteraVirtual(BilleteraVirtualApi billeteraVirtualApi) {
        this.billeteraVirtualApi = billeteraVirtualApi;
    }

    @Override
    boolean validarDatos(Cliente cliente) {
        return billeteraVirtualApi.validarDatos(cliente.getCbu(),cliente.getAlias());
    }

    @Override
    boolean reservarFondos(double montoAPagar, Cliente cliente) {
        return false;
    }

    @Override
    boolean ejecutarTransaccion(double montoAPagar, Cliente cliente) {
        return false;
    }

    @Override
    String notificarResultado() {
        return "";
    }
}
