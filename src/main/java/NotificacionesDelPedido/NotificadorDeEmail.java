package NotificacionesDelPedido;

import Clases.Pedido;
import CicloDeVidaDelPedido.EstadoDePedido;
import lombok.Getter;

import javax.annotation.processing.Generated;

@Getter
public class NotificadorDeEmail implements Subsistema {
    private final MailSender mailSender;

    public NotificadorDeEmail(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void actualizarEstado(Pedido pedido, EstadoDePedido anterior, EstadoDePedido nuevo) {
        nuevo.notificarMail(pedido,this);
    }
}
