package Clases;

public class Cliente {

    public void agregarProducto(CatalogoDeProductos producto,int cantProducto, Pedido pedido) {
        pedido.agregarProducto(producto,cantProducto);
    }

    public void confirmarPedido(Pedido pedido){
        pedido.confirmarPedido();
    }

    public void quitarProducto(CatalogoDeProductos producto, int cantProducto, Pedido pedido) {
        pedido.quitarProducto(producto,cantProducto);
    }

    public void cancelarPedido(Pedido pedido){
        pedido.cancelarPedido();
    }

    public void pagarPedido(Pedido pedido1) {
        pedido1.pagado();
    }
}
