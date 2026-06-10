package State;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContextoPedido {
    private EstadoDePedido estado= new EstadoDePedidoBorrador(this);


    public void confirmarPedido() {
        estado.confirmarPedido();
    }
}
