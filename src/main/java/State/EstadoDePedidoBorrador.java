package State;

import Clases.Pedido;
import Excepciones.operacionInvalidaExeption;

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
        throw new operacionInvalidaExeption("el pedido no esta confirmado");

    }

    @Override
    public void enviar(Pedido pedido) {
    throw  new operacionInvalidaExeption("el pedido no esta en prepracion");
    }

    @Override
    public void entregar(Pedido pedido) {
        throw  new operacionInvalidaExeption("el pedido no esta enviado");

    }


}
