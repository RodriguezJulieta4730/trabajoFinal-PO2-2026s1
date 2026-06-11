package Clases;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Tienda {
    private Map<CatalogoDeProductos,Integer> stockProductos = new HashMap<>();

    public void agregarStock(CatalogoDeProductos producto, int stock) {
        if(stock>0){
            stockProductos.put(producto,stock);
        }else{
            throw new RuntimeException("El numero debe ser positivo");
        }
    }

    public boolean tieneStock(CatalogoDeProductos producto, int cantProducto) {
        return stockProductos.get(producto)>=cantProducto;
    }

    public void decrementarStock(Map<CatalogoDeProductos, Integer> productos) {
        for(CatalogoDeProductos c: productos.keySet()){
            stockProductos.put(c,stockProductos.get(c)-productos.get(c));
        }
    }

    public void cancelarPedido(Map<CatalogoDeProductos, Integer> productos) {
        if(productos != null) {
            for (CatalogoDeProductos c : productos.keySet()) {
                stockProductos.put(c, stockProductos.get(c) + productos.get(c));
            }
        }
    }

    public void cancelarPedido(Pedido pedido){
        Map<CatalogoDeProductos, Integer> productos = pedido.getProductos();
        if(productos != null) {
            for (CatalogoDeProductos c : productos.keySet()) {
                stockProductos.put(c, stockProductos.get(c) + productos.get(c));
            }
        }
        pedido.cancelarPedido();
    }
}
