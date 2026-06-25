package Strategy;

import Clases.Pedido;

public class EnvioEstandar implements MetodoDeEnvio{

    private CorreoArgentina correo = new CorreoArgentina();

    @Override
    public float calcularCosto(Pedido pedido) {
        return this.correo.estimarEnvio(pedido.getPeso(), pedido.getDireccion());
    }

    @Override
    public int estimarDiasEntrega(Pedido pedido) {
        return 5;
    }
}
