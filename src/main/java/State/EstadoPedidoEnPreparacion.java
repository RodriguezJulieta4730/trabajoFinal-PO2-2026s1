package State;

import Clases.CatalogoDeProductos;
import Clases.Pedido;
import Clases.Tienda;

import java.util.Map;

public class EstadoPedidoEnPreparacion implements EstadoDePedido {
    private ContextoPedido contexto;
    public EstadoPedidoEnPreparacion(ContextoPedido contexto) {
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
        contexto.setEstado(new EstadoDePedidoCancelado(contexto));
    }

    @Override
    public void cancelarPedido(Pedido pedido) {
        contexto.setEstado(new EstadoDePedidoCancelado(contexto));
    }

    @Override
    public void pagado() {

    }
}
