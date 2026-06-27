package NotificacionesDelPedido;

import Clases.Pedido;
import CicloDeVidaDelPedido.EstadoDePedido;

public interface Subsistema {
    void actualizarEstado(Pedido pedido, EstadoDePedido estadoAnterior, EstadoDePedido estadoNuevo);
}
