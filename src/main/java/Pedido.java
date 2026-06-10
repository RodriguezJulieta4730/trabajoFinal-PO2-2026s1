
import State.ContextoPedido;
import State.EstadoDePedido;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Pedido {
    private List<Producto> productos = new ArrayList<>();
    private final ContextoPedido contextoPedido = new ContextoPedido();

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public EstadoDePedido getEstado() {
        return contextoPedido.getEstado();
    }

    public void confirmarPedido() {
        contextoPedido.confirmarPedido();
    }
}
