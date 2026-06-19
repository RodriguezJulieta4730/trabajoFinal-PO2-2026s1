package State;

import Clases.Pedido;

public interface EstadoDePedido {
    void confirmarPedido(Pedido pedido);
    void cancelarPedido(Pedido pedido);
    void pagado(Pedido pedido);
    void enviado(Pedido pedido);
    void entregado(Pedido pedido);
}
