import Clases.*;
import Excepciones.NoHayStockException;
import State.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static Clases.Categoria.Electronica;
import static org.junit.jupiter.api.Assertions.*;

public class CicloDeVidaDelPedidoTest {
    Producto producto1;
    Tienda tienda1;
    Pedido pedido1;
    ProductoIndividual producto2;
    ProductoIndividual producto3;

    @BeforeEach
    void setUp(){
        tienda1 = new Tienda();
        pedido1 = new Pedido(tienda1);
        producto1 = new ProductoIndividual("E0123", "Cable USB-C","una descripcion", "Samsung", Electronica, 800);
        producto2 = new ProductoIndividual("E1235", "Funda Protector","una descripcion", "Samsung", Electronica, 1500,0.15);
        producto3 = new ProductoIndividual("E0126", "Cable USB-C","una descripcion", "Samsung", Electronica, 800);
    }

    @Test
    void test001_unClienteAgregaUnProductoASuPedido(){
        tienda1.agregarStock(producto1,4);
        pedido1.agregarProducto(producto1,1);
        assertInstanceOf(EstadoDePedidoBorrador.class,pedido1.getEstadoActual());
        assertTrue(pedido1.getCarritoDeProductos().containsKey(producto1));
    }

    @Test
    void test002_unClienteAgregaUnProductoYUnPaqueteASuPedido(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto1,producto2);
        tienda1.agregarStock(producto1,4);
        tienda1.agregarStock(paquete1,1);
        pedido1.agregarProducto(producto1,1);
        pedido1.agregarProducto(paquete1,1);

        assertInstanceOf(EstadoDePedidoBorrador.class,pedido1.getEstadoActual());
        assertTrue(pedido1.getCarritoDeProductos().containsKey(producto1));
        assertTrue(pedido1.getCarritoDeProductos().containsKey(paquete1));
    }

    @Test
    void test003_unClienteIntentaAgregarUnProductoSinStock(){
        assertThrows(NoHayStockException.class,() -> pedido1.agregarProducto(producto1,1));
    }

    @Test
    void test004_laTiendaNoPuedeAgregarStockNegativo(){
        assertThrows(RuntimeException.class,() -> tienda1.agregarStock(producto1,-1));
    }

    @Test
    void test005_unClienteAgrega3CantidadesDeUnProductoYQuita2Cantidades() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 3);
        pedido1.quitarProducto(producto1, 2);

        assertEquals(1,pedido1.getCarritoDeProductos().get(producto1));
    }

    @Test
    void test006_unClienteAgrega3CantidadesDeUnProductoYQuita3Cantidades() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 3);
        pedido1.quitarProducto(producto1, 3);

        assertFalse(pedido1.getCarritoDeProductos().containsKey(producto1));
    }

    @Test
    void test007_unClienteIntentaQuitarUnProductoQueNuncaAgregoASuPedido() {
        assertThrows(RuntimeException.class,() -> pedido1.quitarProducto(producto1, 3));
    }

    @Test
    void test008_unClienteIntentaQuitarMasCantidadesDeUnProductoQueLasQueAgregoASuPedido() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 3);
        assertThrows(RuntimeException.class,() -> pedido1.quitarProducto(producto1, 4));
    }

    @Test
    void test009_unClienteCancelaUnPedidoEnBorrador() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2, 4);
        pedido1.agregarProducto(producto1, 3);
        pedido1.agregarProducto(producto2, 3);
        pedido1.cancelar();

        assertTrue(pedido1.getCarritoDeProductos().isEmpty());
    }

    @Test
    void test0010_unClienteConfirmaElPedido() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2,4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.agregarProducto(producto2, 2);
        pedido1.confirmar();

        assertInstanceOf(EstadoDePedidoConfirmado.class, pedido1.getEstadoActual());
        assertEquals(2, tienda1.getStockProductos().get(producto1));
        assertEquals(2, tienda1.getStockProductos().get(producto2));
    }

    @Test
    void test0011_unClienteCancelaUnPedidoConfirmado() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2, 4);
        pedido1.agregarProducto(producto1, 3);
        pedido1.agregarProducto(producto2, 3);
        pedido1.confirmar();
        pedido1.cancelar();

        assertTrue(pedido1.getCarritoDeProductos().isEmpty());
        assertEquals(4,tienda1.getStockProductos().get(producto1));
        assertEquals(4,tienda1.getStockProductos().get(producto2));
    }

    @Test
    void test0012_unClienteConfirmaYLoPaga() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2,4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.agregarProducto(producto2, 2);
        pedido1.confirmar();
        pedido1.pagar();

        assertInstanceOf(EstadoPedidoEnPreparacion.class, pedido1.getEstadoActual());
        assertEquals(2,tienda1.getStockProductos().get(producto1));
        assertEquals(2,tienda1.getStockProductos().get(producto2));
    }

    @Test
    void test0013_tiendaCancelaPedidoEnPreparacion() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2,4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.agregarProducto(producto2, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.cancelar();

        assertInstanceOf(EstadoDePedidoCancelado.class, pedido1.getEstadoActual());
        assertEquals(4,tienda1.getStockProductos().get(producto1));
        assertEquals(4,tienda1.getStockProductos().get(producto2));
    }

    @Test
    void test0014_tiendaEnviaElPedido() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2,4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.agregarProducto(producto2, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();

        assertInstanceOf(EstadoDePedidoEnviado.class, pedido1.getEstadoActual());
        assertEquals(2,tienda1.getStockProductos().get(producto1));
        assertEquals(2,tienda1.getStockProductos().get(producto2));
    }

    @Test
    void test0015_tiendaCancelaPedidoEnviado() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2,4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.agregarProducto(producto2, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();
        pedido1.cancelar();

        assertInstanceOf(EstadoDePedidoCancelado.class, pedido1.getEstadoActual());
        assertEquals(4,tienda1.getStockProductos().get(producto1));
        assertEquals(4,tienda1.getStockProductos().get(producto2));
    }

    @Test
    void test0016_tiendaEntregaElPedido() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2,4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.agregarProducto(producto2, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();
        pedido1.entregar();

        assertInstanceOf(EstadoDePedidoEntregado.class, pedido1.getEstadoActual());
    }
}
