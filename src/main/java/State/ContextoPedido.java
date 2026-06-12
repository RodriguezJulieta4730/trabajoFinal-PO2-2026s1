package State;

import Clases.CatalogoDeProductos;
import Clases.Pedido;
import Clases.Tienda;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class ContextoPedido {
    private EstadoDePedido estado= new EstadoDePedidoBorrador(this);


    public void confirmarPedido() {
        estado.confirmarPedido();
    }

    public void cancelarPedido(Pedido pedido) {
        estado.cancelarPedido(pedido);
    }

    public void pagado() {
        estado.pagado();
    }

    public void enviado() {
        estado.enviado();
    }

    public void pedidoEnviado() {
        estado.pedidoEntregado();
    }
}
