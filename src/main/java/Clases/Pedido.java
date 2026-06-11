package Clases;

import State.ContextoPedido;
import State.EstadoDePedido;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Pedido {
    private Map<CatalogoDeProductos, Integer> productos = new HashMap();
    private final ContextoPedido contextoPedido = new ContextoPedido();
    private Tienda tienda;

    public void agregarProducto(CatalogoDeProductos producto, int cantProducto, Tienda tienda) {
        this.tienda=tienda;
        if (tienda.tieneStock(producto, cantProducto)) {
            productos.put(producto, cantProducto);
        } else {
            throw new RuntimeException("No hay suficiente stock");
        }
    }

    public EstadoDePedido getEstado() {
        return contextoPedido.getEstado();
    }

    public void confirmarPedido() {
        contextoPedido.confirmarPedido();
        tienda.decrementarStock(productos);

    }

    public void quitarProducto(CatalogoDeProductos producto, int cantProducto, Tienda tienda) {
        if (!productos.containsKey(producto)) {
            throw new RuntimeException("No existe ese producto en el pedido");
        }

        int cantidadActual = productos.get(producto);

        if (cantidadActual < cantProducto) {
            throw new RuntimeException("No hay cantidad suficiente para quitar");
        }

        if (cantidadActual == cantProducto) {
            productos.remove(producto);
        } else {
            productos.put(producto, cantidadActual - cantProducto);
        }
    }

    public void cancelarPedido() {
        contextoPedido.cancelarPedido(this);
        productos = new HashMap();
    }


    public void pagado() {
        contextoPedido.pagado();
    }
}