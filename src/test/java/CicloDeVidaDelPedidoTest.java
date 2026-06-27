import Clases.*;
import Excepciones.*;
import CicloDeVidaDelPedido.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class CicloDeVidaDelPedidoTest {
    Producto producto1;
    Sucursal tienda1;
    Pedido pedido1;
    ProductoIndividual producto2;
    ProductoIndividual producto3;

    @BeforeEach
    void setUp(){
        tienda1 = new Sucursal();
        pedido1 = new Pedido(tienda1, "Boedo 671","Julieta@email.com");
        producto1 = mock(ProductoIndividual.class);
        producto2 = mock(ProductoIndividual.class);
        producto3 = mock(ProductoIndividual.class);
    }

    @Test
    void test001_seAgregaUnProductoAlPedido(){
        tienda1.agregarStock(producto1,4);
        pedido1.agregarProducto(producto1,1);
        assertInstanceOf(EstadoDePedidoBorrador.class,pedido1.getEstadoActual());
        assertTrue(pedido1.getCarritoDeProductos().containsKey(producto1));
    }

    @Test
    void test002_seAgregaUnProductoYUnPaqueteAlPedido(){
        Producto paquete1 = mock(Paquete.class);
        tienda1.agregarStock(producto1,4);
        tienda1.agregarStock(paquete1,1);
        pedido1.agregarProducto(producto1,1);
        pedido1.agregarProducto(paquete1,1);

        assertInstanceOf(EstadoDePedidoBorrador.class,pedido1.getEstadoActual());
        assertTrue(pedido1.getCarritoDeProductos().containsKey(producto1));
        assertTrue(pedido1.getCarritoDeProductos().containsKey(paquete1));
    }

    @Test
    void test003_seIntentaAgregarUnProductoSinStock(){
        assertThrows(NoHayStockException.class,() -> pedido1.agregarProducto(producto1,1));
    }

    @Test
    void test004_laTiendaNoPuedeAgregarStockNegativo(){
        assertThrows(StockNegativoException.class,() -> tienda1.agregarStock(producto1,-1));
    }

    @Test
    void test005_seAgrega3CantidadesDeUnProductoYSeQuitan2Cantidades() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 3);
        pedido1.quitarProducto(producto1, 2);

        assertEquals(1,pedido1.getCarritoDeProductos().get(producto1));
    }

    @Test
    void test006_seAgregan3CantidadesDeUnProductoYSeQuitan3Cantidades() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 3);
        pedido1.quitarProducto(producto1, 3);

        assertFalse(pedido1.getCarritoDeProductos().containsKey(producto1));
    }

    @Test
    void test007_seIntentaQuitarUnProductoQueNuncaSeAgregoAlPedido() {
        assertThrows(NoHayProductoEnPedidoException.class,() -> pedido1.quitarProducto(producto1, 3));
    }

    @Test
    void test008_seIntentaQuitarMasCantidadesDeUnProductoQueLasQueSeAgregaronAlPedido() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 3);
        assertThrows(CantidadInsuficienteException.class,() -> pedido1.quitarProducto(producto1, 4));
    }

    @Test
    void test009_seCancelaUnPedidoEnBorrador() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2, 4);
        pedido1.agregarProducto(producto1, 3);
        pedido1.agregarProducto(producto2, 3);
        pedido1.cancelar();
        assertInstanceOf(EstadoDePedidoCancelado.class, pedido1.getEstadoActual());
        assertTrue(pedido1.getCarritoDeProductos().isEmpty());
    }

    @Test
    void test0010_seConfirmaElPedido() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2,4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.agregarProducto(producto2, 2);
        pedido1.confirmar();

        assertInstanceOf(EstadoDePedidoConfirmado.class, pedido1.getEstadoActual());
        assertEquals(2, tienda1.getStockDeProductos().get(producto1));
        assertEquals(2, tienda1.getStockDeProductos().get(producto2));
    }

    @Test
    void test0011_seCancelaUnPedidoConfirmado() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2, 4);
        pedido1.agregarProducto(producto1, 3);
        pedido1.agregarProducto(producto2, 3);
        pedido1.confirmar();
        pedido1.cancelar();

        assertTrue(pedido1.getCarritoDeProductos().isEmpty());
        assertEquals(4,tienda1.getStockDeProductos().get(producto1));
        assertEquals(4,tienda1.getStockDeProductos().get(producto2));
    }

    @Test
    void test0012_seConfirmaYSePagaUnPedido() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2,4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.agregarProducto(producto2, 2);
        pedido1.confirmar();
        pedido1.pagar();

        assertInstanceOf(EstadoPedidoEnPreparacion.class, pedido1.getEstadoActual());
        assertEquals(2,tienda1.getStockDeProductos().get(producto1));
        assertEquals(2,tienda1.getStockDeProductos().get(producto2));
    }

    @Test
    void test0013_seCancelaUnPedidoEnPreparacion() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2,4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.agregarProducto(producto2, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.cancelar();

        assertInstanceOf(EstadoDePedidoCancelado.class, pedido1.getEstadoActual());
        assertEquals(4,tienda1.getStockDeProductos().get(producto1));
        assertEquals(4,tienda1.getStockDeProductos().get(producto2));
    }

    @Test
    void test0014_seEnviaElPedido() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2,4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.agregarProducto(producto2, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();

        assertInstanceOf(EstadoDePedidoEnviado.class, pedido1.getEstadoActual());
        assertEquals(2,tienda1.getStockDeProductos().get(producto1));
        assertEquals(2,tienda1.getStockDeProductos().get(producto2));
    }

    @Test
    void test0015_seCancelaUnPedidoEnviado() {
        tienda1.agregarStock(producto1, 4);
        tienda1.agregarStock(producto2,4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.agregarProducto(producto2, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();
        pedido1.cancelar();

        assertInstanceOf(EstadoDePedidoCancelado.class, pedido1.getEstadoActual());
        assertEquals(4,tienda1.getStockDeProductos().get(producto1));
        assertEquals(4,tienda1.getStockDeProductos().get(producto2));
    }

    @Test
    void test0016_seEntregaElPedido() {
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

    //EXCEPCIONES EN ESTADO: BORRADOR
    @Test
    void test0017_borradorNoSePuedePagar() {
        assertThrows(operacionInvalidaExeption.class, () -> pedido1.pagar());
    }

    @Test
    void test0018_borradorNoSePuedeEnviar() {
        assertThrows(operacionInvalidaExeption.class, () -> pedido1.enviar());
    }

    @Test
    void test0019_borradorNoSePuedeEntregar() {
        assertThrows(operacionInvalidaExeption.class, () -> pedido1.entregar());
    }

    // EXCEPCIONES EN ESTADO: CONFIRMADO
    @Test
    void test0021a_confirmadoNoSeConfirmar() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.confirmar());
    }

    @Test
    void test0020b_confirmadoNoSePuedeEnviar() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.enviar());
    }

    @Test
    void test0021_confirmadoNoSePuedeEntregar() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.entregar());
    }

    //EXCEPCIONES EN ESTADO: EN PREPARACION
    @Test
    void test0022_enPreparacionNoSePuedeConfirmar() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.confirmar());
    }

    @Test
    void test0023_enPreparacionNoSePuedePagar() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.pagar());
    }

    @Test
    void test0024_enPreparacionNoSePuedeEntregar() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.entregar());
    }

    // EXCEPCIONES EN ESTADO: ENVIADO
    @Test
    void test0025_enviadoNoSePuedeConfirmar() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.confirmar());
    }

    @Test
    void test0026_enviadoNoSePuedePagar() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.pagar());
    }

    @Test
    void test0027_enviadoNoSePuedeEnviar() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.enviar());
    }

    //EXCEPCIONES EN ESTADO: ENTREGADO
    @Test
    void test0028_entregadoNoSePuedeConfirmar() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();
        pedido1.entregar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.confirmar());
    }

    @Test
    void test0029_entregadoNoSePuedeCancelar() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();
        pedido1.entregar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.cancelar());
    }

    @Test
    void test0030_entregadoNoSePuedePagar() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();
        pedido1.entregar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.pagar());
    }

    @Test
    void test0031_entregadoNoSePuedeEnviar() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();
        pedido1.entregar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.enviar());
    }

    @Test
    void test0032_entregadoNoSePuedeEntregar() {
        tienda1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();
        pedido1.entregar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.entregar());
    }

    //EXCEPCIONES EN ESTADO: CANCELADO
    @Test
    void test0033_canceladoNoSePuedeConfirmar() {
        pedido1.cancelar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.confirmar());
    }

    @Test
    void test0034_canceladoNoSePuedeCancelar() {
        pedido1.cancelar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.cancelar());
    }

    @Test
    void test0035_canceladoNoSePuedePagar() {
        pedido1.cancelar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.pagar());
    }

    @Test
    void test0036_canceladoNoSePuedeEnviar() {
        pedido1.cancelar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.enviar());
    }

    @Test
    void test0037_canceladoNoSePuedeEntregar() {
        pedido1.cancelar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.entregar());
    }
}