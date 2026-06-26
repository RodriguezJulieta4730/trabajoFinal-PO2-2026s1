package Strategy;

import Clases.Pedido;

public class EnvioExpress implements MetodoDeEnvio{
    private float porcentaje=1;
    private float cargoBase=0;

    @Override
    public float calcularCosto(Pedido pedido) {
        return (float) (pedido.getPrecioTotal() * porcentaje + cargoBase);
    }

    @Override
    public int estimarDiasEntrega(Pedido pedido) {
        return 1;
    }
}
