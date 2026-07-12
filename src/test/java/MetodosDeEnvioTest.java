import Clases.*;
import Excepciones.PesoInvalidoException;
import MetodosDeEnvio.EnvioEstandar;
import MetodosDeEnvio.EnvioExpress;
import MetodosDeEnvio.MetodoDeEnvio;
import MetodosDeEnvio.RetiroEnSucursal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MetodosDeEnvioTest {
    Pedido pedido1;
    Pedido pedido2;
    Pedido pedido3;
    Pedido pedido4;
    Pedido pedido5;

    Sucursal sucursal1;
    Sucursal sucursal2;

    MetodoDeEnvio envioEstandar;
    MetodoDeEnvio envioExpress;
    MetodoDeEnvio retiroEnSucursal;
    UNQShop tienda;

    @BeforeEach
    void setUp() {
        envioEstandar = new EnvioEstandar();
        envioExpress = new EnvioExpress();
        retiroEnSucursal = new RetiroEnSucursal();
        tienda  = mock(UNQShop.class);
        Cliente cliente1 = mock(Cliente.class);

        pedido1 = mock(Pedido.class);
        when(pedido1.getPeso()).thenReturn(1.0F);
        when(pedido1.getCliente()).thenReturn(cliente1);
        when(pedido1.getCliente().getDireccion()).thenReturn("Boedo 671");

        pedido2 = mock(Pedido.class);
        when(pedido2.getPrecioTotal()).thenReturn(1000.0);

        pedido3 = mock(Pedido.class);
        sucursal2 = mock(Sucursal.class);
        when(pedido3.getSucursal()).thenReturn(sucursal2);
        when(sucursal2.tieneStockPara(pedido3)).thenReturn(true);

        pedido4 = mock(Pedido.class);

        pedido5 = mock(Pedido.class);
        when(pedido5.getPeso()).thenReturn(-1.0F);
        when(pedido5.getCliente()).thenReturn(cliente1);
        when(pedido5.getCliente().getDireccion()).thenReturn("Boedo 671");
    }

    @Test
    void test0001_envioEstandar() {
        assertEquals(5,envioEstandar.calcularCosto(pedido1));
        assertEquals(5, envioEstandar.estimarDiasEntrega(pedido1));
    }

    @Test
    void test0002_envioExpress() {
        assertEquals(1000,envioExpress.calcularCosto(pedido2));
        assertEquals(1, envioExpress.estimarDiasEntrega(pedido2));
    }

    @Test
    void test0003_retiroEnSucursalConStock() {
        assertEquals(0, retiroEnSucursal.calcularCosto(pedido3));
        assertEquals(0, retiroEnSucursal.estimarDiasEntrega(pedido3));
    }

    @Test
    void test0004_retiroEnSucursalSinStock() {
        UNQShop tiendaReal = new UNQShop();
        Sucursal sucursalBernal = new Sucursal(tiendaReal, "Roque Sáenz Peña 352");
        Sucursal sucursalQuilmes = new Sucursal(tiendaReal, "Rivadavia 123");

        tiendaReal.registrarSucursal(sucursalBernal);
        tiendaReal.registrarSucursal(sucursalQuilmes);

        Cliente cliente= mock(Cliente.class);
        Producto producto = mock(Producto.class);

        sucursalQuilmes.agregarStock(producto, 5);

        Pedido pedidoReal = new Pedido(sucursalBernal, retiroEnSucursal, cliente);
        pedidoReal.agregarProducto(producto, 1);


        int diasEstimados = retiroEnSucursal.estimarDiasEntrega(pedidoReal);

        assertEquals(3, diasEstimados);
        assertEquals(0, retiroEnSucursal.calcularCosto(pedido4));
    }

    @Test
    void test0005_envioEstandarPesoInvalido() {
        assertThrows(PesoInvalidoException.class, () -> envioEstandar.calcularCosto(pedido5));
    }
}
