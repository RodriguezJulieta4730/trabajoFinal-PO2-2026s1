package NotificacionesDelPedido;

import Clases.Pedido;
import CicloDeVidaDelPedido.EstadoDePedido;

    public class NotificadorDeEmail implements Subsistema {
        private final MailSender mailSender;

        public NotificadorDeEmail(MailSender mailSender) {
            this.mailSender = mailSender;
        }

        @Override
        public void actualizarEstado(Pedido pedido, EstadoDePedido anterior, EstadoDePedido nuevo) {
            String nombreEstado = nuevo.getClass().getSimpleName();

            if (nombreEstado.contains("Confirmado") ||
                    nombreEstado.contains("Enviado") ||
                    nombreEstado.contains("Entregado")) {

                mailSender.enviarMail(
                        pedido.getEmail(),
                        "Actualización de tu Pedido",
                        "Tu pedido cambió al estado: " + nombreEstado,
                        null
                );
            }
        }
    }


