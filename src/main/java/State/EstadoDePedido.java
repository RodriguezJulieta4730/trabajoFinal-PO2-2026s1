package State;

import Clases.Pedido;

public interface EstadoDePedido {
    void confirmar(Pedido pedido);
    void cancelar(Pedido pedido);
    void pagar(Pedido pedido);
    void enviar(Pedido pedido);
    void entregar(Pedido pedido);
}
