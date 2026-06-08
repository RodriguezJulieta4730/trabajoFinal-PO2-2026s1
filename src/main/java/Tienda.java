import java.util.HashMap;
import java.util.Map;

public class Tienda {
    private Map<Producto,Integer> stockProductos = new HashMap<>();

    public void agregarStock(Producto producto, int stock) {
        stockProductos.put(producto,stock);
    }
}
