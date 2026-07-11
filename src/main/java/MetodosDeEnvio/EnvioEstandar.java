package MetodosDeEnvio;

import Clases.Pedido;

public class EnvioEstandar implements MetodoDeEnvio{

    @Override
    public float calcularCosto(Pedido pedido) {
        return CorreoArgentina.estimarEnvio(pedido.getPeso(), pedido.getCliente().getDireccion());
    }

    @Override
    public int estimarDiasEntrega(Pedido pedido) {
        return 5;
    }
}
