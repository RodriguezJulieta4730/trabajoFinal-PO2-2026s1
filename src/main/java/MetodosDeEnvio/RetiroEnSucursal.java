package MetodosDeEnvio;

import Clases.Pedido;
import Clases.Sucursal;

public class RetiroEnSucursal implements MetodoDeEnvio{

    @Override
    public float calcularCosto(Pedido pedido) {
        return 0;
    }

    @Override
    public int estimarDiasEntrega(Pedido pedido) {
        Sucursal sucursalRetiro = pedido.getSucursal();

        // Verificamos si la sucursal elegida tiene stock inmediato
        boolean tieneTodoElStockLocal = pedido.getCarritoDeProductos().entrySet().stream()
                .allMatch(entry -> sucursalRetiro.tieneStock(entry.getKey(), entry.getValue()));

        return tieneTodoElStockLocal ? 0 : 3;
    }
}
