package State;

import Clases.CatalogoDeProductos;
import Clases.Pedido;
import Clases.Tienda;

import java.util.Map;

public class EstadoDePedidoBorrador implements EstadoDePedido{
    private ContextoPedido context;

    public EstadoDePedidoBorrador(ContextoPedido contextoPedido) {
        this.context = contextoPedido;
    }

    @Override
    public void agregarProducto() {

    }

    @Override
    public void sacarProducto() {

    }

    @Override
    public void confirmarPedido() {
        context.setEstado(new EstadoDePedidoConfirmado(context));
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
