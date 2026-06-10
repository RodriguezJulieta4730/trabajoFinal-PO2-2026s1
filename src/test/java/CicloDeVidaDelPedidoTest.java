import State.EstadoDePedidoBorrador;
import State.EstadoDePedidoConfirmado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CicloDeVidaDelPedidoTest {
    Producto producto1;
    Tienda tienda1;
    Pedido pedido1;
    Cliente cliente1;

    @BeforeEach
    void setUp(){
        tienda1 = new Tienda();
        pedido1 = new Pedido();
        cliente1 = new Cliente();
        producto1 = new Producto("E0123", "Cable USB-C","una descripcion", "Samsung", "Electrodomestico", 800);
    }

    @Test
    void test001_unClienteAgregaUnProductoASuPedido(){
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
        tienda1.agregarStock(producto1,4);
        cliente1.agregarProducto(producto1,pedido1);
        cliente1.confirmarPedido(pedido1);
        assertInstanceOf(EstadoDePedidoConfirmado.class,pedido1.getEstado());
    }
}
