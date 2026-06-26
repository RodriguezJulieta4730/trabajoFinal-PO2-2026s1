package Observer;

import Clases.Pedido;
import State.EstadoDePedido;

public interface Subsistema {
    void actualizarEstado(Pedido pedido, EstadoDePedido estadoAnterior, EstadoDePedido estadoNuevo);
}
