package State;

import Clases.Pedido;

public class EstadoDePedidoEnviado implements EstadoDePedido {

    @Override
    public void confirmarPedido(Pedido pedido) {

    }


    @Override
    public void cancelarPedido(Pedido pedido) {
        pedido.getTienda().reembolsarCostoProductos(pedido);
        pedido.setEstado(new EstadoDePedidoCancelado());
    }

    @Override
    public void pagado(Pedido pedido) {

    }

    @Override
    public void enviado(Pedido pedido) {

    }

    @Override
    public void entregado(Pedido pedido) {
        pedido.setEstado(new EstadoDePedidoEntregado());
    }
}
