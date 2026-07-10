package CicloDeVidaDelPedido;

import Clases.Pedido;
import Excepciones.operacionInvalidaExeption;
import NotificacionesDelPedido.NotificadorDeEmail;

public class EstadoDePedidoCancelado implements EstadoDePedido {

    @Override
    public void confirmar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido no esta en borrador");

    }

    @Override
    public void cancelar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido ya fue cancelado");

    }

    @Override
    public void pagar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido esta cancelado");

    }

    @Override
    public void enviar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido esta cencelado");

    }

    @Override
    public void entregar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido esta cencelado");
    }

    @Override
    public void notificarMail(Pedido pedido, NotificadorDeEmail notificadorDeEmail) {
    }
}
