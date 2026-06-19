package State;

import Clases.Pedido;

public class EstadoDePedidoConfirmado implements EstadoDePedido {

    @Override
    public void confirmarPedido(Pedido pedido) {

    }

    @Override
    public void cancelarPedido(Pedido pedido) {
        pedido.getTienda().cancelarPedido(pedido.getCarritoDeproductos());
        pedido.getTienda().reembolsarCostoProductos(pedido);
        pedido.getTienda().reembolsarEnvio(pedido);
        pedido.borrarCarrito();
        pedido.setEstado(new EstadoDePedidoCancelado());
    }

    @Override
    public void pagado(Pedido pedido) {
        pedido.setEstado(new EstadoPedidoEnPreparacion());
    }

    @Override
    public void enviado(Pedido pedido) {

    }

    @Override
    public void entregado(Pedido pedido) {

    }

}
