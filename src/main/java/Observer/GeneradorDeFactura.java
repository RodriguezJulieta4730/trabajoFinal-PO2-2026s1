package Observer;

import Clases.Pedido;
import State.EstadoDePedido;

public class GeneradorDeFactura implements Subsistema {
    @Override
    public void actualizarEstado(Pedido pedido, EstadoDePedido anterior, EstadoDePedido nuevo) {
        String nombreEstado = nuevo.getClass().getSimpleName();

        if (nombreEstado.contains("Entregado")) {
            System.out.println("Generando factura fiscal para el pedido por un total de: $" + pedido.getPrecioTotal());
        }
    }
}
