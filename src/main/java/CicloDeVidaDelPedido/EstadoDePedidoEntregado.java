package CicloDeVidaDelPedido;

import Clases.Pedido;
import Excepciones.operacionInvalidaExeption;
import NotificacionesDelPedido.NotificadorDeEmail;

public class EstadoDePedidoEntregado implements EstadoDePedido {

    @Override
    public void confirmar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido ya fue entregado");

    }

    @Override
    public void cancelar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido ya fue entregado");

    }

    @Override
    public void pagar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido ya fue entregado");

    }

    @Override
    public void enviar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido ya fue entregado");

    }

    @Override
    public void entregar(Pedido pedido) {
        throw new operacionInvalidaExeption("el pedido ya fue entregado");

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
