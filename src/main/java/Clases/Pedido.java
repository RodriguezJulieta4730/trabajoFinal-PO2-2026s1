package Clases;

import State.EstadoDePedido;
import State.EstadoDePedidoBorrador;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Pedido {
    Map<CatalogoDeProductos, Integer> carritoDeproductos;
    EstadoDePedido estadoActual;
    private Tienda tienda;

    public Pedido(Tienda tienda) {
        this.tienda = tienda;
        this.carritoDeproductos = new HashMap<>();
        this.estadoActual = new EstadoDePedidoBorrador();
    }

    // Permite a los estados cambiar el estado del pedido
    public void setEstado(EstadoDePedido nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    public void confirmarPedido() {
        this.estadoActual.confirmarPedido(this);
    }

    public void cancelarPedido() {
        this.estadoActual.cancelarPedido(this);
    }

    public void pagado() {
        this.estadoActual.pagado(this);
    }

    public void enviado() {
        this.estadoActual.enviado(this);
    }

    public void entregado() {
        this.estadoActual.entregado(this);
    }


    public void agregarProducto(CatalogoDeProductos producto, int cantProducto) {
        if (tienda.tieneStock(producto, cantProducto)) {
            carritoDeproductos.put(producto, cantProducto);
        } else {
            throw new RuntimeException("No hay suficiente stock");
        }
    }

    public void quitarProducto(CatalogoDeProductos producto, int cantProducto) {
        if (!carritoDeproductos.containsKey(producto)) {
            throw new RuntimeException("No existe ese producto en el pedido");
        }

        int cantidadActual = carritoDeproductos.get(producto);

        if (cantidadActual < cantProducto) {
            throw new RuntimeException("No hay cantidad suficiente para quitar");
        }

        if (cantidadActual == cantProducto) {
            carritoDeproductos.remove(producto);
        } else {
            carritoDeproductos.put(producto, cantidadActual - cantProducto);
        }
    }

    public void reembolsarCostoProductos() {
        //deberiamos guardar en algun momento al cliente como para despues
        //mandarle un mensaje tipo "Se ha reembolsado el costo de los productos"
    }

    public void reembolsarEnvio() {
        //mandarle un mensaje tipo "Se ha reembolsado el costo del envio"
    }

    public void borrarCarrito() {
        this.carritoDeproductos=new HashMap<>();
    }
}