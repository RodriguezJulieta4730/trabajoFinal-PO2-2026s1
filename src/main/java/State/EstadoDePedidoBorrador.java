package State;

import Clases.Pedido;

public class EstadoDePedidoBorrador implements EstadoDePedido{

    @Override
    public void confirmar(Pedido pedido) {
        pedido.getTienda().decrementarStock(pedido.getCarritoDeProductos());
        pedido.setEstado(new EstadoDePedidoConfirmado());
    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.getCarritoDeProductos().clear();
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

    }


}
