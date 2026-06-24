package State;

import Clases.Pedido;

public class EstadoDePedidoConfirmado implements EstadoDePedido {

    @Override
    public void confirmar(Pedido pedido) {

    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.getTienda().cancelarPedido(pedido.getCarritoDeProductos());
        pedido.getTienda().reembolsarCostoProductos(pedido);
        pedido.getTienda().reembolsarEnvio(pedido);
        pedido.borrarCarrito();
        pedido.setEstado(new EstadoDePedidoCancelado());
    }

    @Override
    public void pagar(Pedido pedido) {
        pedido.setEstado(new EstadoPedidoEnPreparacion());
    }

    @Override
    public void enviar(Pedido pedido) {

    }

    @Override
    public void entregar(Pedido pedido) {

    }

}
