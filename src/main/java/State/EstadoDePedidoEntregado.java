package State;

import Clases.Pedido;
import Excepciones.operacionInvalidaExeption;

public class EstadoDePedidoEntregado implements EstadoDePedido {

    @Override
    public void confirmar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido ya fue entregado");

    }

    @Override
    public void cancelar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido ya fue entregado");

    }

    @Override
    public void pagar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido ya fue entregado");

    }

    @Override
    public void enviar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido ya fue entregado");

    }

    @Override
    public void entregar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido ya fue entregado");

    }
}
