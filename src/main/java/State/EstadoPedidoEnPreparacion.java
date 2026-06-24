package State;

import Clases.Pedido;
import Excepciones.operacionInvalidaExeption;

public class EstadoPedidoEnPreparacion implements EstadoDePedido {

    @Override
    public void confirmar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido ya fue confirmado");

    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.setEstado(new EstadoDePedidoCancelado());
        pedido.getTienda().cancelarPedido(pedido.getCarritoDeProductos());
    }

    @Override
    public void pagar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido ya fue pagado");

    }

    @Override
    public void enviar(Pedido pedido) {
        pedido.setEstado(new EstadoDePedidoEnviado());
    }

    @Override
    public void entregar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido no fue enviado");

    }
}
