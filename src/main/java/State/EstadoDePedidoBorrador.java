package State;

import Clases.Pedido;

public class EstadoDePedidoBorrador implements EstadoDePedido{

    @Override
    public void confirmarPedido(Pedido pedido) {
        pedido.getTienda().decrementarStock(pedido.getCarritoDeproductos());
        pedido.setEstado(new EstadoDePedidoConfirmado());
    }

    @Override
    public void cancelarPedido(Pedido pedido) {
        pedido.getCarritoDeproductos().clear();
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

    }


}
