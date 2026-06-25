package Strategy;

import Clases.Pedido;

public interface MetodoDeEnvio {
    float calcularCosto(Pedido pedido);
    int estimarDiasEntrega(Pedido pedido);
}
