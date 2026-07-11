package CicloDeVidaDelPedido;

import Clases.Pedido;
import Excepciones.operacionInvalidaExeption;
import NotificacionesDelPedido.NotificadorDeEmail;

public class EstadoDePedidoConfirmado implements EstadoDePedido {

    @Override
    public void confirmar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido ya esta confirmado");
    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.getSucursal().cancelarPedido(pedido.getCarritoDeProductos());
        pedido.borrarCarrito();
        pedido.setEstado(new EstadoDePedidoCancelado());
    }

    @Override
    public void pagar(Pedido pedido) {
        pedido.setEstado(new EstadoPedidoEnPreparacion());
    }

    @Override
    public void enviar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido no esta en preparacion");

    }

    @Override
    public void entregar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido no fue enviado");

    }

    @Override
    public void notificarMail(Pedido pedido, NotificadorDeEmail notificadorDeEmail) {
        notificadorDeEmail.getMailSender().enviarMail(
                pedido.getCliente().getEmail(),
                "Actualización de tu Pedido",
                "Tu pedido cambió al estado: " + this,
                null
        );
    }
}


