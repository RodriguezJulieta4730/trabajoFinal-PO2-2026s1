import Clases.*;
import Excepciones.*;
import CicloDeVidaDelPedido.*;
import MetodosDeEnvio.EnvioEstandar;
import MetodosDeEnvio.MetodoDeEnvio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CicloDeVidaDelPedidoTest {
    private Tienda tienda;
    Producto producto1;
    Sucursal sucursal1;
    Pedido pedido1;
    ProductoIndividual producto2;
    ProductoIndividual producto3;
    MetodoDeEnvio envioEstandar;
    Cliente cliente1;

    @BeforeEach
    void setUp(){
        tienda = new Tienda();
        envioEstandar = new EnvioEstandar();
        sucursal1 = new Sucursal(tienda,"Roque Sáenz Peña 352");
        tienda.registrarSucursal(sucursal1);
        cliente1 = new Cliente("JulietaRodriguez", 20304050607L, "juli@email.com", "Boedo 671");
        pedido1 = new Pedido(sucursal1, envioEstandar, cliente1);
        producto1 = mock(ProductoIndividual.class);
        producto2 = mock(ProductoIndividual.class);
        producto3 = mock(ProductoIndividual.class);

        when(producto1.getPeso()).thenReturn(1.5f);
        when(producto1.getPrecioFinal()).thenReturn(500.0);
    }

    @Test
    void test001_seAgregaUnProductoAlPedido(){
        sucursal1.agregarStock(producto1,4);
        pedido1.agregarProducto(producto1,1);
        assertInstanceOf(EstadoDePedidoBorrador.class,pedido1.getEstadoActual());
        assertTrue(pedido1.getCarritoDeProductos().containsKey(producto1));
    }

    @Test
    void test002_seAgregaUnProductoYUnPaqueteAlPedido(){
        Producto paquete1 = mock(Paquete.class);
        sucursal1.agregarStock(producto1,4);
        sucursal1.agregarStock(paquete1,1);
        pedido1.agregarProducto(producto1,1);
        pedido1.agregarProducto(paquete1,1);

        assertInstanceOf(EstadoDePedidoBorrador.class,pedido1.getEstadoActual());
        assertTrue(pedido1.getCarritoDeProductos().containsKey(producto1));
        assertTrue(pedido1.getCarritoDeProductos().containsKey(paquete1));
    }

    @Test
    void test003_seIntentaAgregarUnProductoSinStock(){
        assertThrows(ProductoNoEncontradoException.class,() -> pedido1.agregarProducto(producto1,1));
        sucursal1.agregarStock(producto1,1);
        sucursal1.decrementarStock(Map.of(producto1,1));
        assertThrows(NoHayStockException.class,() -> pedido1.agregarProducto(producto1,1));
    }

    @Test
    void test004_laSucursalNoPuedeAgregarStockNegativo(){
        assertThrows(StockNegativoException.class,() -> sucursal1.agregarStock(producto1,-1));
    }

    @Test
    void test005_seAgrega3CantidadesDeUnProductoYSeQuitan2Cantidades() {
        sucursal1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 3);
        pedido1.quitarProducto(producto1, 2);

        assertEquals(1,pedido1.getCarritoDeProductos().get(producto1));
    }

    @Test
    void test006_seAgregan3CantidadesDeUnProductoYSeQuitan3Cantidades() {
        sucursal1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 3);
        pedido1.quitarProducto(producto1, 3);

        assertFalse(pedido1.getCarritoDeProductos().containsKey(producto1));
    }

    @Test
    void test007_seIntentaQuitarUnProductoQueNuncaSeAgregoAlPedido() {
        assertThrows(ProductoNoEncontradoException.class,() -> pedido1.quitarProducto(producto1, 3));
    }

    @Test
    void test008_seIntentaQuitarMasCantidadesDeUnProductoQueLasQueSeAgregaronAlPedido() {
        sucursal1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 3);
        assertThrows(CantidadInsuficienteException.class,() -> pedido1.quitarProducto(producto1, 4));
    }

    @Test
    void test009_seCancelaUnPedidoEnBorrador() {
        sucursal1.agregarStock(producto1, 4);
        sucursal1.agregarStock(producto2, 4);
        pedido1.agregarProducto(producto1, 3);
        pedido1.agregarProducto(producto2, 3);
        pedido1.cancelar();
        assertInstanceOf(EstadoDePedidoCancelado.class, pedido1.getEstadoActual());
        assertTrue(pedido1.getCarritoDeProductos().isEmpty());
    }

    @Test
    void test0010_seConfirmaElPedido() {
        sucursal1.agregarStock(producto1, 4);
        sucursal1.agregarStock(producto2,4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.agregarProducto(producto2, 2);
        pedido1.confirmar();

        assertInstanceOf(EstadoDePedidoConfirmado.class, pedido1.getEstadoActual());
        assertEquals(2, sucursal1.getStockDeProductos().get(producto1));
        assertEquals(2, sucursal1.getStockDeProductos().get(producto2));
    }

    @Test
    void test0011_seCancelaUnPedidoConfirmado() {
        sucursal1.agregarStock(producto1, 4);
        sucursal1.agregarStock(producto2, 4);
        pedido1.agregarProducto(producto1, 3);
        pedido1.agregarProducto(producto2, 3);
        pedido1.confirmar();
        pedido1.cancelar();

        assertTrue(pedido1.getCarritoDeProductos().isEmpty());
        assertEquals(4, sucursal1.getStockDeProductos().get(producto1));
        assertEquals(4, sucursal1.getStockDeProductos().get(producto2));
    }

    @Test
    void test0012_seConfirmaYSePagaUnPedido() {
        sucursal1.agregarStock(producto1, 4);
        sucursal1.agregarStock(producto2,4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.agregarProducto(producto2, 2);
        pedido1.confirmar();
        pedido1.pagar();

        assertInstanceOf(EstadoPedidoEnPreparacion.class, pedido1.getEstadoActual());
        assertEquals(2, sucursal1.getStockDeProductos().get(producto1));
        assertEquals(2, sucursal1.getStockDeProductos().get(producto2));
    }

    @Test
    void test0013_seCancelaUnPedidoEnPreparacion() {
        sucursal1.agregarStock(producto1, 4);
        sucursal1.agregarStock(producto2,4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.agregarProducto(producto2, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.cancelar();

        assertInstanceOf(EstadoDePedidoCancelado.class, pedido1.getEstadoActual());
        assertEquals(4, sucursal1.getStockDeProductos().get(producto1));
        assertEquals(4, sucursal1.getStockDeProductos().get(producto2));
    }

    @Test
    void test0014_seEnviaElPedido() {
        sucursal1.agregarStock(producto1, 4);
        sucursal1.agregarStock(producto2,4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.agregarProducto(producto2, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();

        assertInstanceOf(EstadoDePedidoEnviado.class, pedido1.getEstadoActual());
        assertEquals(2, sucursal1.getStockDeProductos().get(producto1));
        assertEquals(2, sucursal1.getStockDeProductos().get(producto2));
    }

    @Test
    void test0015_seCancelaUnPedidoEnviado() {
        sucursal1.agregarStock(producto1, 4);
        sucursal1.agregarStock(producto2,4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.agregarProducto(producto2, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();
        pedido1.cancelar();

        assertInstanceOf(EstadoDePedidoCancelado.class, pedido1.getEstadoActual());
        assertEquals(4, sucursal1.getStockDeProductos().get(producto1));
        assertEquals(4, sucursal1.getStockDeProductos().get(producto2));
    }

    @Test
    void test0016_seEntregaElPedido() {
        sucursal1.agregarStock(producto1, 4);
        sucursal1.agregarStock(producto2,4);
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
        sucursal1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.confirmar());
    }

    @Test
    void test0020b_confirmadoNoSePuedeEnviar() {
        sucursal1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.enviar());
    }

    @Test
    void test0021_confirmadoNoSePuedeEntregar() {
        sucursal1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.entregar());
    }

    //EXCEPCIONES EN ESTADO: EN PREPARACION
    @Test
    void test0022_enPreparacionNoSePuedeConfirmar() {
        sucursal1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.confirmar());
    }

    @Test
    void test0023_enPreparacionNoSePuedePagar() {
        sucursal1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.pagar());
    }

    @Test
    void test0024_enPreparacionNoSePuedeEntregar() {
        sucursal1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.entregar());
    }

    // EXCEPCIONES EN ESTADO: ENVIADO
    @Test
    void test0025_enviadoNoSePuedeConfirmar() {
        sucursal1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.confirmar());
    }

    @Test
    void test0026_enviadoNoSePuedePagar() {
        sucursal1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.pagar());
    }

    @Test
    void test0027_enviadoNoSePuedeEnviar() {
        sucursal1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.enviar());
    }

    //EXCEPCIONES EN ESTADO: ENTREGADO
    @Test
    void test0028_entregadoNoSePuedeConfirmar() {
        sucursal1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();
        pedido1.entregar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.confirmar());
    }

    @Test
    void test0029_entregadoNoSePuedeCancelar() {
        sucursal1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();
        pedido1.entregar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.cancelar());
    }

    @Test
    void test0030_entregadoNoSePuedePagar() {
        sucursal1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();
        pedido1.entregar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.pagar());
    }

    @Test
    void test0031_entregadoNoSePuedeEnviar() {
        sucursal1.agregarStock(producto1, 4);
        pedido1.agregarProducto(producto1, 2);
        pedido1.confirmar();
        pedido1.pagar();
        pedido1.enviar();
        pedido1.entregar();

        assertThrows(operacionInvalidaExeption.class, () -> pedido1.enviar());
    }

    @Test
    void test0032_entregadoNoSePuedeEntregar() {
        sucursal1.agregarStock(producto1, 4);
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