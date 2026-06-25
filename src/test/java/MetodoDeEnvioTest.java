import Clases.Pedido;
import Clases.Tienda;
import Excepciones.PesoInvalidoException;
import Excepciones.operacionInvalidaExeption;
import Strategy.EnvioEstandar;
import Strategy.EnvioExpress;
import Strategy.MetodoDeEnvio;
import Strategy.RetiroEnSucursal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MetodoDeEnvioTest {
    Pedido pedido1;
    Pedido pedido2;
    Pedido pedido3;
    Pedido pedido4;
    Pedido pedido5;

    Tienda tienda;
    Tienda tienda2;

    MetodoDeEnvio envioEstandar;
    MetodoDeEnvio envioExpress;
    MetodoDeEnvio retiroEnSucursal;

    @BeforeEach
    void setUp() {
        envioEstandar = new EnvioEstandar();
        envioExpress = new EnvioExpress();
        retiroEnSucursal = new RetiroEnSucursal();

        pedido1 = mock(Pedido.class);
        when(pedido1.getPeso()).thenReturn(1.0F);
        when(pedido1.getDireccion()).thenReturn("Boedo 671");
        when(pedido1.getMetodoDeEnvio()).thenReturn(envioEstandar);

        pedido2 = mock(Pedido.class);
        when(pedido2.getPrecioTotal()).thenReturn(1000.0);

        pedido3 = mock(Pedido.class);
        tienda = mock(Tienda.class);
        when(pedido3.getTienda()).thenReturn(tienda);
        when(tienda.tieneStockPara(pedido3)).thenReturn(true);

        pedido4 = mock(Pedido.class);
        tienda2 = mock(Tienda.class);
        when(pedido4.getTienda()).thenReturn(tienda2);
        when(tienda2.tieneStockPara(pedido4)).thenReturn(false);

        pedido5 = mock(Pedido.class);
        when(pedido5.getPeso()).thenReturn(-1.0F);
        when(pedido5.getDireccion()).thenReturn("Boedo 671");
        when(pedido5.getMetodoDeEnvio()).thenReturn(envioEstandar);
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
        assertEquals(0, retiroEnSucursal.calcularCosto(pedido4));
        assertEquals(3, retiroEnSucursal.estimarDiasEntrega(pedido4));
    }

    @Test
    void test0005_envioEstandarPesoInvalido() {
        assertThrows(PesoInvalidoException.class, () -> envioEstandar.calcularCosto(pedido5));
    }
}
