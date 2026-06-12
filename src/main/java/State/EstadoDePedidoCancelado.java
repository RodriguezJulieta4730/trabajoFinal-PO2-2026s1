package State;

import Clases.CatalogoDeProductos;
import Clases.Pedido;
import Clases.Tienda;

import java.util.Map;

public class EstadoDePedidoCancelado implements EstadoDePedido {
    private ContextoPedido contexto;
    public EstadoDePedidoCancelado(ContextoPedido contexto) {
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
