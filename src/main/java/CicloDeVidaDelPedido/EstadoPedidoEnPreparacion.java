package CicloDeVidaDelPedido;

import Clases.Pedido;
import Excepciones.operacionInvalidaExeption;
import NotificacionesDelPedido.MailSender;
import NotificacionesDelPedido.NotificadorDeEmail;

public class EstadoPedidoEnPreparacion implements EstadoDePedido {

    @Override
    public void confirmar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido ya fue confirmado");

    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.setEstado(new EstadoDePedidoCancelado());
        pedido.getSucursal().cancelarPedido(pedido.getCarritoDeProductos());
        pedido.getSucursal().reembolsarCostoProductos(pedido);
        pedido.getSucursal().reembolsarEnvio(pedido);
        pedido.borrarCarrito();
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

    @Override
    public void notificarMail(Pedido pedido, NotificadorDeEmail notificadorDeEmail) {
    }

}
