package Strategy;

import Clases.Pedido;
import Clases.Tienda;

public class RetiroEnSucursal implements MetodoDeEnvio{

    @Override
    public float calcularCosto(Pedido pedido) {
        return 0;
    }

    @Override
    public int estimarDiasEntrega(Pedido pedido) {
        if (pedido.getTienda().tieneStockPara(pedido)) {
            return 0; // retiro inmediato 0 días
        } else {
            return 3;
        }
    }
}
