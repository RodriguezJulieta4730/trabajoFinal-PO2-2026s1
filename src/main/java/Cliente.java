public class Cliente {

    public void agregarProducto(Producto producto, Pedido pedido) {
        pedido.agregarProducto(producto);
    }

    public void confirmarPedido(Pedido pedido){
        pedido.confirmarPedido();
    }
}
