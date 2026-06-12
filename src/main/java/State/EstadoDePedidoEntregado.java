package State;

import Clases.Pedido;

public class EstadoDePedidoEntregado implements EstadoDePedido {
    private ContextoPedido contexto;
    public EstadoDePedidoEntregado(ContextoPedido contexto) {
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

    }

    @Override
    public void pagado() {

    }

    @Override
    public void enviado() {

    }

    @Override
    public void pedidoEntregado() {

    }
}
