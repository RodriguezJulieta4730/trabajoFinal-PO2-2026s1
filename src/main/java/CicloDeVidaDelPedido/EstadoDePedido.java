package CicloDeVidaDelPedido;

import Clases.Pedido;
import NotificacionesDelPedido.MailSender;
import NotificacionesDelPedido.NotificadorDeEmail;

public interface EstadoDePedido {
    void confirmar(Pedido pedido);
    void cancelar(Pedido pedido);
    void pagar(Pedido pedido);
    void enviar(Pedido pedido);
    void entregar(Pedido pedido);
    void notificarMail(Pedido pedido, NotificadorDeEmail notificadorDeEmail);
}
