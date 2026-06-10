import State.EstadoDePedidoBorrador;
import State.EstadoDePedidoConfirmado;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CicloDeVidaDelPedidoTest {
    @Test
    void test001_unClienteAgregaUnProductoASuPedido(){
        Tienda tienda1 = new Tienda();
        Pedido pedido1 = new Pedido();
        Cliente cliente1 = new Cliente();
        Producto producto1 = new Producto("E0123", "Cable USB-C", "Samsung", "Electrodomestico", 800);
        tienda1.agregarStock(producto1,4);
        cliente1.agregarProducto(producto1,pedido1);
        assertInstanceOf(EstadoDePedidoBorrador.class,pedido1.getEstado());
        assertTrue(pedido1.getProductos().contains(producto1));
    }

    @Test
    void test002_unClienteConfirmaElPedido(){
        Tienda tienda1 = new Tienda();
        Pedido pedido1 = new Pedido();
        Cliente cliente1 = new Cliente();
        Producto producto1 = new Producto("E0123", "Cable USB-C", "Samsung", "Electrodomestico", 800);
        tienda1.agregarStock(producto1,4);
        cliente1.agregarProducto(producto1,pedido1);
        cliente1.confirmarPedido(pedido1);
        assertInstanceOf(EstadoDePedidoConfirmado.class,pedido1.getEstado());
    }
}
