package Clases;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Tienda {
    private Map<Producto,Integer> stockProductos = new HashMap<>();

    public void agregarStock(Producto producto, int stock) {
        if(stock>0){
            stockProductos.put(producto,stock);
        }else{
            throw new RuntimeException("El numero debe ser positivo");
        }
    }

    public boolean tieneStock(Producto producto, int cantProducto) {
        return stockProductos.getOrDefault(producto,0)>=cantProducto;
    }

    public void decrementarStock(Map<Producto, Integer> productos) {
        for(Producto c: productos.keySet()){
            stockProductos.put(c,stockProductos.get(c)-productos.get(c));
        }
    }

    public void cancelarPedido(Map<Producto, Integer> productos) {
        if(productos != null) {
            for (Producto c : productos.keySet()) {
                stockProductos.put(c, stockProductos.get(c) + productos.get(c));
            }
        }
    }

    public void reembolsarCostoProductos(Pedido pedido) {
        pedido.reembolsarCostoProductos();
    }

    public void reembolsarEnvio(Pedido pedido) {
        pedido.reembolsarEnvio();
    }

    public void entregar(Pedido pedido) {
        pedido.entregar();
    }
}
