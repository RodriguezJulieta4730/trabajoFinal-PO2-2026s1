package Clases;

import Excepciones.CantidadInsuficienteException;
import Excepciones.NoHayProductoEnPedidoException;
import Excepciones.NoHayStockException;
import State.EstadoDePedido;
import State.EstadoDePedidoBorrador;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Pedido {
    Map<Producto, Integer> carritoDeProductos;
    EstadoDePedido estadoActual;
    private Tienda tienda;

    public Pedido(Tienda tienda) {
        this.tienda = tienda;
        this.carritoDeProductos = new HashMap<>();
        this.estadoActual = new EstadoDePedidoBorrador();
    }

    // Permite a los estados cambiar el estado del pedido
    public void setEstado(EstadoDePedido nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    public void confirmar() {
        this.estadoActual.confirmar(this);
    }

    public void cancelar() {
        this.estadoActual.cancelar(this);
    }

    public void pagar() {
        this.estadoActual.pagar(this);
    }

    public void enviar() {
        this.estadoActual.enviar(this);
    }

    public void entregar() {
        this.estadoActual.entregar(this);
    }


    public void agregarProducto(Producto producto, int cantProducto) {
        if (tienda.tieneStock(producto, cantProducto)) {
            carritoDeProductos.put(producto, carritoDeProductos.getOrDefault(producto,0) + cantProducto);
        } else {
            throw new NoHayStockException("No hay suficiente stock");
        }
    }

    public void quitarProducto(Producto producto, int cantProducto) {
        if (!carritoDeProductos.containsKey(producto)) {
            throw new NoHayProductoEnPedidoException("No existe ese producto en el pedido");
        }

        int cantidadActual = carritoDeProductos.get(producto);

        if (cantidadActual < cantProducto) {
            throw new CantidadInsuficienteException("No hay cantidad suficiente para quitar");
        }

        if (cantidadActual == cantProducto) {
            carritoDeProductos.remove(producto);
        } else {
            carritoDeProductos.put(producto, cantidadActual - cantProducto);
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
        this.carritoDeProductos =new HashMap<>();
    }
}