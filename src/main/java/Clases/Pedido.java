package Clases;

import Excepciones.CantidadInsuficienteException;
import Excepciones.NoHayProductoEnPedidoException;
import Excepciones.NoHayStockException;
import Observer.Notificador;
import State.EstadoDePedido;
import State.EstadoDePedidoBorrador;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Pedido {
    Map<Producto, Integer> carritoDeProductos;
    EstadoDePedido estadoActual;
    private final Sucursal tienda;
    private final String direccion;
    private final Notificador notificador;
    private String email;

    public Pedido(Sucursal tienda, String direccion, String email) {
        this.tienda = tienda;
        this.carritoDeProductos = new HashMap<>();
        this.estadoActual = new EstadoDePedidoBorrador();
        this.notificador = new Notificador();
        this.direccion=direccion;
        this.email=email;
    }

    public void setEstado(EstadoDePedido nuevoEstado) {
        EstadoDePedido estadoAnterior = this.estadoActual;
        this.estadoActual = nuevoEstado;
        notificador.notificarCambio(this, estadoAnterior, nuevoEstado);

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

    public double getPrecioTotal() {
        double precioTotal = 0;
        for(Producto p: carritoDeProductos.keySet()){
            precioTotal += p.getPrecioFinal();
        }
        return precioTotal;
    }

    public float getPeso(){
        float[] pesoTotal={0};
        carritoDeProductos.forEach((p,n)-> pesoTotal[0] += p.getPeso() * n);
        return pesoTotal[0];
    }
}