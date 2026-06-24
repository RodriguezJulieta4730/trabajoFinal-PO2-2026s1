package State;

import Clases.Pedido;

public class EstadoPedidoEnPreparacion implements EstadoDePedido {

    @Override
    public void confirmar(Pedido pedido) {

    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.setEstado(new EstadoDePedidoCancelado());
        pedido.getTienda().cancelarPedido(pedido.getCarritoDeProductos());
    }

    @Override
    public void pagar(Pedido pedido) {

    }

    @Override
    public void enviar(Pedido pedido) {
        pedido.setEstado(new EstadoDePedidoEnviado());
    }

    @Override
    public void entregar(Pedido pedido) {

    }
}
