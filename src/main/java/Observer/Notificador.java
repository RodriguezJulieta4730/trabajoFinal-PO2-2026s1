package Observer;

import Clases.Pedido;
import State.EstadoDePedido;

import java.util.LinkedHashSet;
import java.util.Set;

public class Notificador {
    private final Set<Subsistema> observadores = new LinkedHashSet<>();

    public void agregarObservador(Subsistema observador) {
        this.observadores.add(observador);
    }

    public void removerObservador(Subsistema observador) {
        this.observadores.remove(observador);
    }

    public void notificarCambio(Pedido pedido, EstadoDePedido anterior, EstadoDePedido nuevo) {
        for (Subsistema obs : observadores) {
            obs.actualizarEstado(pedido, anterior, nuevo);
        }
    }
}
