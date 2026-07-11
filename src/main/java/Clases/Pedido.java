package Clases;

import Excepciones.CantidadInsuficienteException;
import Excepciones.NoHayProductoEnPedidoException;
import Excepciones.NoHayStockException;
import MetodosDeEnvio.MetodoDeEnvio;
import NotificacionesDelPedido.Notificador;
import CicloDeVidaDelPedido.EstadoDePedido;
import CicloDeVidaDelPedido.EstadoDePedidoBorrador;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Pedido {
    Map<Producto, Integer> carritoDeProductos;
    EstadoDePedido estadoActual;
    private final Sucursal sucursal;
    private final Notificador notificador;
    private final MetodoDeEnvio metodoDeEnvio;
    private final LocalDate fecha;
    private final Cliente cliente;

    public Pedido(Sucursal tienda, MetodoDeEnvio metodoDeEnvio,Cliente cliente) {
        this.sucursal = tienda;
        this.fecha = LocalDate.now();
        this.carritoDeProductos = new HashMap<>();
        this.estadoActual = new EstadoDePedidoBorrador();
        this.notificador = new Notificador();
        this.metodoDeEnvio=metodoDeEnvio;
        this.cliente = cliente;
    }

    public void setEstado(EstadoDePedido nuevoEstado) {
        EstadoDePedido estadoAnterior = estadoActual;
        estadoActual = nuevoEstado;
        notificador.notificarCambio(this, estadoAnterior, nuevoEstado);

    }

    public void confirmar() {
        estadoActual.confirmar(this);
    }

    public void cancelar() {
        estadoActual.cancelar(this);
    }

    public void pagar() {
        estadoActual.pagar(this);
    }

    public void enviar() {
        estadoActual.enviar(this);
    }

    public void entregar() {
        estadoActual.entregar(this);
        sucursal.registarPedidoEnHistorial(this);
    }


    public void agregarProducto(Producto producto, int cantProducto) {
        if (sucursal.tieneStock(producto, cantProducto)) {
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
        System.out.println("Nota de Crédito generada: Se reembolsaron $" + this.getPrecioTotal() + " por los productos del pedido a " + this.cliente.getEmail());
    }

    public void reembolsarEnvio() {
        System.out.println("Nota de Crédito generada: Se reembolsaron $" + metodoDeEnvio.calcularCosto(this) + " por el costo de envío a " + this.cliente.getEmail());
    }

    public void borrarCarrito() {
        this.carritoDeProductos =new HashMap<>();
    }

    public double getPrecioTotal() {
        double precioTotal = 0;
        for (Map.Entry<Producto, Integer> entry : carritoDeProductos.entrySet()) {
            precioTotal += entry.getKey().getPrecioFinal() * entry.getValue();
        }
        return precioTotal;
    }

    public float getPeso(){
        float[] pesoTotal={0};
        carritoDeProductos.forEach((p,n)-> pesoTotal[0] += p.getPeso() * n);
        return pesoTotal[0];
    }
}