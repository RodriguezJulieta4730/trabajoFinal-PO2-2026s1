package State;

import Clases.CatalogoDeProductos;
import Clases.Pedido;
import Clases.Tienda;

import java.util.Map;

public interface EstadoDePedido {
    void agregarProducto();
    void sacarProducto();
    void confirmarPedido();
    void pagarPedido();
    void enviarPedido();
    void entregarPedido();
    void cancelarPedido();

    void cancelarPedido(Pedido pedido);

    void pagado();

    void enviado();

    void pedidoEntregado();
}
