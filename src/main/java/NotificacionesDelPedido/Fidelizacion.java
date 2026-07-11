package NotificacionesDelPedido;

import Clases.Pedido;
import CicloDeVidaDelPedido.EstadoDePedido;

public class Fidelizacion implements Subsistema {
    private final MailSender mailSender;

    public Fidelizacion(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void actualizarEstado(Pedido pedido, EstadoDePedido anterior, EstadoDePedido nuevo) {
        String nombreEstado = nuevo.getClass().getSimpleName();

        if (nombreEstado.contains("Cancelado")) {
            mailSender.enviarMail(
                    pedido.getCliente().getEmail(),
                    "¡Queremos que vuelvas!",
                    "Lamentamos la cancelación. Te regalamos un cupón del 5% de descuento para tu próxima compra.",
                    "CUPON5OFF"
            );
        }
    }
}