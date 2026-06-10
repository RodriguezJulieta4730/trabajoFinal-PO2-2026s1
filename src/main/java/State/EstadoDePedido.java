package State;

public interface EstadoDePedido {
    void agregarProducto();
    void sacarProducto();
    void confirmarPedido();
    void pagarPedido();
    void enviarPedido();
    void entregarPedido();
    void cancelarPedido();
}
