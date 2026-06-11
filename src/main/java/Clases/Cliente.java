package Clases;

public class Cliente {

    public void agregarProducto(CatalogoDeProductos producto,int cantProducto, Pedido pedido,Tienda tienda) {
        pedido.agregarProducto(producto,cantProducto,tienda);
    }

    public void confirmarPedido(Pedido pedido){
        pedido.confirmarPedido();
    }

    public void quitarProducto(CatalogoDeProductos producto, int cantProducto, Pedido pedido, Tienda tienda) {
        pedido.quitarProducto(producto,cantProducto,tienda);
    }
}
