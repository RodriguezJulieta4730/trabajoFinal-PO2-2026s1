package CicloDeVidaDelPedido;

import Clases.Pedido;
import Excepciones.operacionInvalidaExeption;

public class EstadoDePedidoEnviado implements EstadoDePedido {

    @Override
    public void confirmar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido ya fue confirmado");

    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.getSucursal().cancelarPedido(pedido.getCarritoDeProductos());
        pedido.getSucursal().reembolsarCostoProductos(pedido);
        pedido.borrarCarrito();
        pedido.setEstado(new EstadoDePedidoCancelado());
    }

    @Override
    public void pagar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido ya fue pagado");

    }

    @Override
    public void enviar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido ya fue enviado");
    }

    @Override
    public void entregar(Pedido pedido) {
        pedido.setEstado(new EstadoDePedidoEntregado());
    }
}
