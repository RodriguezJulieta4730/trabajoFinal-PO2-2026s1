package State;

import Clases.Pedido;

public class EstadoDePedidoEnviado implements EstadoDePedido {
    private ContextoPedido contexto;
    public EstadoDePedidoEnviado(ContextoPedido contexto) {
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
        contexto.setEstado(new EstadoDePedidoCancelado(contexto));
        pedido.getTienda().reembolsarCostoProductos(pedido);
    }

    @Override
    public void pagado() {

    }

    @Override
    public void enviado() {

    }

    @Override
    public void pedidoEntregado() {
        contexto.setEstado(new EstadoDePedidoEntregado(contexto));
    }
}
