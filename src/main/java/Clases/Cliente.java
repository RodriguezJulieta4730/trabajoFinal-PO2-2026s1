package Clases;

public class Cliente {

    public void agregarProducto(Producto producto, int cantProducto, Pedido pedido) {
        pedido.agregarProducto(producto,cantProducto);
    }

    public void confirmarPedido(Pedido pedido){
        pedido.confirmar();
    }

    public void quitarProducto(Producto producto, int cantProducto, Pedido pedido) {
        pedido.quitarProducto(producto,cantProducto);
    }

    public void cancelarPedido(Pedido pedido){
        pedido.cancelar();
    }

    public void pagarPedido(Pedido pedido1) {
        pedido1.pagar();
    }
}
