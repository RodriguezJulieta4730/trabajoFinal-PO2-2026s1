package State;

import Clases.CatalogoDeProductos;
import Clases.Pedido;
import Clases.Tienda;

import java.util.Map;

public class EstadoDePedidoConfirmado implements EstadoDePedido {
    private ContextoPedido contexto;
    public EstadoDePedidoConfirmado(ContextoPedido contexto) {
        this.contexto=contexto;
    }

    @Override
    public void agregarProducto() {

    }

    @Override
    public void sacarProducto() {

    }

    @Override
    public void confirmarPedido() {

    }

    @Override
    public void pagarPedido() {

    }

    @Override
    public void enviarPedido() {

    }

    @Override
    public void entregarPedido() {

    }

    @Override
    public void cancelarPedido() {

    }

    @Override
    public void cancelarPedido(Pedido pedido) {
        pedido.getTienda().cancelarPedido(pedido.getProductos());
        contexto.setEstado(new EstadoDePedidoCancelado(contexto));
        pedido.getTienda().reembolsarCostoProductos(pedido);
        pedido.getTienda().reembolsarEnvio(pedido);

    }

    @Override
    public void pagado() {
        contexto.setEstado(new EstadoPedidoEnPreparacion(contexto));
    }

    @Override
    public void enviado() {

    }

    @Override
    public void pedidoEntregado() {

    }

}
