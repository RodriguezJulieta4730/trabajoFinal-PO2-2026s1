import Clases.*;
import State.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static Clases.Categoria.Electronica;
import static org.junit.jupiter.api.Assertions.*;

public class CicloDeVidaDelPedidoTest {
    CatalogoDeProductos producto1;
    Tienda tienda1;
    Pedido pedido1;
    Cliente cliente1;
    ProductoIndividual producto2;
    ProductoIndividual producto3;

    @BeforeEach
    void setUp(){
        tienda1 = new Tienda();
        pedido1 = new Pedido();
        cliente1 = new Cliente();
        producto1 = new ProductoIndividual("E0123", "Cable USB-C","una descripcion", "Samsung", Electronica, 800);
        producto2 = new ProductoIndividual("E1235", "Funda Protector","una descripcion", "Samsung", Electronica, 1500,0.15);
        producto3 = new ProductoIndividual("E0126", "Cable USB-C","una descripcion", "Samsung", Electronica, 800);
    }

    @Test
    void test001_unClienteAgregaUnProductoASuPedido(){
        tienda1.agregarStock(producto1,4);
        cliente1.agregarProducto(producto1,1,pedido1,tienda1);

        assertInstanceOf(EstadoDePedidoBorrador.class,pedido1.getEstado());
        assertTrue(pedido1.getProductos().containsKey(producto1));
    }

    @Test
    void test002_unClienteAgregaUnProductoYUnPaqueteASuPedido(){
        CatalogoDeProductos paquete1 = new Paquete("CableYFunda","una Descripcion",producto1,producto2);
        tienda1.agregarStock(producto1,4);
        tienda1.agregarStock(paquete1,1);
        cliente1.agregarProducto(producto1,1,pedido1,tienda1);
        cliente1.agregarProducto(paquete1,1,pedido1,tienda1);

        assertInstanceOf(EstadoDePedidoBorrador.class,pedido1.getEstado());
        assertTrue(pedido1.getProductos().containsKey(producto1));
        assertTrue(pedido1.getProductos().containsKey(paquete1));
    }

    @Test
    void test003_unClienteIntentaAgregarUnProductoSinStock(){
        assertThrows(RuntimeException.class,() -> cliente1.agregarProducto(producto1,1,pedido1,tienda1));
    }

    @Test
    void test004_laTiendaNoPuedeAgregarStockNegativo(){
        assertThrows(RuntimeException.class,() -> tienda1.agregarStock(producto1,-1));
    }

    @Test
    void test005_unClienteAgrega3CantidadesDeUnProductoYQuita2Cantidades() {
        tienda1.agregarStock(producto1, 4);
        cliente1.agregarProducto(producto1, 3, pedido1, tienda1);
        cliente1.quitarProducto(producto1, 2, pedido1, tienda1);

        assertEquals(1,pedido1.getProductos().get(producto1));
    }

    @Test
    void test006_unClienteAgrega3CantidadesDeUnProductoYQuita3Cantidades() {
        tienda1.agregarStock(producto1, 4);
        cliente1.agregarProducto(producto1, 3, pedido1, tienda1);
        cliente1.quitarProducto(producto1, 3, pedido1, tienda1);

        assertFalse(pedido1.getProductos().containsKey(producto1));
    }

    @Test
    void test007_unClienteIntentaQuitarUnProductoQueNuncaAgregoASuPedido() {
        assertThrows(RuntimeException.class,() -> cliente1.quitarProducto(producto1, 3, pedido1, tienda1));
    }

    @Test
    void test008_unClienteIntentaQuitarMasCantidadesDeUnProductoQueLasQueAgregoASuPedido() {
        tienda1.agregarStock(producto1, 4);
        cliente1.agregarProducto(producto1, 3, pedido1, tienda1);
        assertThrows(RuntimeException.class,() -> cliente1.quitarProducto(producto1, 4, pedido1, tienda1));
    }

    @Test
    void test009_unClienteCancelaUnPedidoEnBorrador() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2, 4);
        cliente1.agregarProducto(producto1, 3, pedido1, tienda1);
        cliente1.agregarProducto(producto2, 3, pedido1, tienda1);
        cliente1.cancelarPedido(pedido1);

        assertTrue(pedido1.getProductos().isEmpty());
    }

    @Test
    void test0010_unClienteConfirmaElPedido() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2,4);
        cliente1.agregarProducto(producto1, 2, pedido1, tienda1);
        cliente1.agregarProducto(producto2, 2, pedido1, tienda1);
        cliente1.confirmarPedido(pedido1);

        assertInstanceOf(EstadoDePedidoConfirmado.class, pedido1.getEstado());
        assertEquals(2, tienda1.getStockProductos().get(producto1));
        assertEquals(2, tienda1.getStockProductos().get(producto2));
    }

    @Test
    void test0011_unClienteCancelaUnPedidoConfirmado() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2, 4);
        cliente1.agregarProducto(producto1, 3, pedido1, tienda1);
        cliente1.agregarProducto(producto2, 3, pedido1, tienda1);
        cliente1.confirmarPedido(pedido1);
        cliente1.cancelarPedido(pedido1);

        assertTrue(pedido1.getProductos().isEmpty());
        assertEquals(4,tienda1.getStockProductos().get(producto1));
        assertEquals(4,tienda1.getStockProductos().get(producto2));
    }

    @Test
    void test0012_unClienteConfirmaYLoPaga() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2,4);
        cliente1.agregarProducto(producto1, 2, pedido1, tienda1);
        cliente1.agregarProducto(producto2, 2, pedido1, tienda1);
        cliente1.confirmarPedido(pedido1);
        cliente1.pagarPedido(pedido1);

        assertInstanceOf(EstadoPedidoEnPreparacion.class, pedido1.getEstado());
        assertEquals(2,tienda1.getStockProductos().get(producto1));
        assertEquals(2,tienda1.getStockProductos().get(producto2));
    }

    @Test
    void test0013_tiendaCancelaPedidoEnPreparacion() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2,4);
        cliente1.agregarProducto(producto1, 2, pedido1, tienda1);
        cliente1.agregarProducto(producto2, 2, pedido1, tienda1);
        cliente1.confirmarPedido(pedido1);
        cliente1.pagarPedido(pedido1);
        tienda1.cancelarPedido(pedido1);

        assertInstanceOf(EstadoDePedidoCancelado.class, pedido1.getEstado());
        assertEquals(4,tienda1.getStockProductos().get(producto1));
        assertEquals(4,tienda1.getStockProductos().get(producto2));
    }

    @Test
    void test0014_tiendaEnviaElPedido() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2,4);
        cliente1.agregarProducto(producto1, 2, pedido1, tienda1);
        cliente1.agregarProducto(producto2, 2, pedido1, tienda1);
        cliente1.confirmarPedido(pedido1);
        cliente1.pagarPedido(pedido1);
        tienda1.enviado(pedido1);

        assertInstanceOf(EstadoDePedidoEnviado.class, pedido1.getEstado());
        assertEquals(2,tienda1.getStockProductos().get(producto1));
        assertEquals(2,tienda1.getStockProductos().get(producto2));
    }

    @Test
    void test0015_tiendaCancelaPedidoEnviado() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2,4);
        cliente1.agregarProducto(producto1, 2, pedido1, tienda1);
        cliente1.agregarProducto(producto2, 2, pedido1, tienda1);
        cliente1.confirmarPedido(pedido1);
        cliente1.pagarPedido(pedido1);
        tienda1.enviado(pedido1);
        tienda1.cancelarPedido(pedido1);

        assertInstanceOf(EstadoDePedidoCancelado.class, pedido1.getEstado());
        assertEquals(4,tienda1.getStockProductos().get(producto1));
        assertEquals(4,tienda1.getStockProductos().get(producto2));
    }

    @Test
    void test0016_tiendaEntregaElPedido() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2,4);
        cliente1.agregarProducto(producto1, 2, pedido1, tienda1);
        cliente1.agregarProducto(producto2, 2, pedido1, tienda1);
        cliente1.confirmarPedido(pedido1);
        cliente1.pagarPedido(pedido1);
        tienda1.enviado(pedido1);
        tienda1.entregar(pedido1);

        assertInstanceOf(EstadoDePedidoEntregado.class, pedido1.getEstado());
    }
}
