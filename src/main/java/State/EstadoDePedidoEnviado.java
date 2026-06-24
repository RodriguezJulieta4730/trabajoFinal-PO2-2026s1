package State;

import Clases.Pedido;

public class EstadoDePedidoEnviado implements EstadoDePedido {

    @Override
    public void confirmar(Pedido pedido) {

    }


    @Override
    public void cancelar(Pedido pedido) {
        pedido.getTienda().cancelarPedido(pedido.getCarritoDeProductos());
        pedido.getTienda().reembolsarCostoProductos(pedido);
        pedido.setEstado(new EstadoDePedidoCancelado());
    }

    @Override
    public void pagar(Pedido pedido) {

    }

    @Override
    public void enviar(Pedido pedido) {

    }

    @Override
    public void entregar(Pedido pedido) {
        pedido.setEstado(new EstadoDePedidoEntregado());
    }
}
