package MetodosDePago;

import Clases.Cliente;

public class BilleteraVirtual extends MedioDePago {
    private final BilleteraVirtualApi billeteraVirtualApi;

    public BilleteraVirtual(BilleteraVirtualApi billeteraVirtualApi) {
        this.billeteraVirtualApi = billeteraVirtualApi;
    }

    @Override
    public boolean validarDatos(double montoAPagar,Cliente cliente) {
        return billeteraVirtualApi.validarDatos(montoAPagar,cliente.getCbu(),cliente.getAlias());
    }

    @Override
    public boolean reservarFondos(double montoAPagar, Cliente cliente) {
        return billeteraVirtualApi.reservarFondos(montoAPagar, cliente.getCbu(), cliente.getAlias());
    }

    @Override
    public boolean ejecutarTransaccion(double montoAPagar, Cliente cliente) {
        return billeteraVirtualApi.ejecutarTransaccion(montoAPagar);
    }

    @Override
    public String notificarResultado(Cliente cliente){
        return billeteraVirtualApi.notificarResultado(cliente.getAlias());
    }
}
