
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class Pedido {
    private List<Producto> listaDeProductos = new ArrayList<>();
    private final ContextoPedido contextoPedido = new ContextoPedido();

    public void agregarProducto(Producto producto) {
        listaDeProductos.add(producto);
    }
}
