import Clases.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MetodosDePagoTest {
    MedioDePago tarjetaDeCredito;
    TarjetaApi tarjetaApi;
    MedioDePago transferenciaBancaria;
    MedioDePago billeteraVirtual;
    BilleteraVirtualApi billeteraVirtualApi;
    Cliente cliente1;
    DatosDeTarjeta datosDeCliente1;
    Pedido pedido2;

    @BeforeEach
    void setUp() {
        tarjetaApi = mock(TarjetaApi.class);
        tarjetaDeCredito = new TarjetaDeCredito(tarjetaApi);

        billeteraVirtualApi = mock(BilleteraVirtualApi.class);
        billeteraVirtual = new BilleteraVirtual(billeteraVirtualApi);

        cliente1 = mock(Cliente.class);
        pedido2 = mock(Pedido.class);
        datosDeCliente1 = mock(DatosDeTarjeta.class);

        when(pedido2.getPrecioTotal()).thenReturn(1000.0);
        when(cliente1.getDatosDeTarjeta()).thenReturn(datosDeCliente1);

        when(tarjetaApi.validarDatos(cliente1.getDatosDeTarjeta()))
                .thenReturn(true);
        when(tarjetaApi.reservarFondos(pedido2.getPrecioTotal(), cliente1.getDatosDeTarjeta()))
                .thenReturn(true);
        when(tarjetaApi.ejecutarTransaccion(pedido2.getPrecioTotal(), cliente1.getDatosDeTarjeta()))
                .thenReturn(true);
        when(tarjetaApi.notificarResultado())
                .thenReturn("Pago exitoso");

        when(cliente1.getAlias()).thenReturn("raul.fernandez5");
        when(cliente1.getCbu()).thenReturn(278976543L);

        when(billeteraVirtualApi.validarDatos(cliente1.getCbu(), cliente1.getAlias()))
                .thenReturn(true);
        when(billeteraVirtualApi.reservarFondos()).thenReturn(true); // ???????????????????????????????????????
        when(billeteraVirtualApi.ejecutarTransaccion(pedido2.getPrecioTotal())).thenReturn(true);
        when(billeteraVirtualApi.notificarResultado()).thenReturn("Pago exitoso");

    }

    @Test
    void test01_seRealizaUnPagoExitosoConTarjetaDeCredito() {
        String resultado = tarjetaDeCredito.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("Pago exitoso", resultado);
    }
    @Test
    void test02_seIntentaValidarLosDatosDeUnaTarjetaDeCredito() {
        when(tarjetaApi.validarDatos(cliente1.getDatosDeTarjeta()))
                .thenReturn(false);
        String resultado = tarjetaDeCredito.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("No se pudo validar los datos", resultado);
    }
    @Test
    void test02_seIntentaReservarFondosDeUnaTarjetaDeCredito() {
        when(tarjetaApi.reservarFondos(pedido2.getPrecioTotal(),cliente1.getDatosDeTarjeta()))
                .thenReturn(false);
        String resultado = tarjetaDeCredito.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("No hay fondos suficientes", resultado);
    }
    @Test
    void test04_seIntentaEjecutarUnaTransacciónDeUnaTarjetaDeCredito() {
        when(tarjetaApi.ejecutarTransaccion(pedido2.getPrecioTotal(),cliente1.getDatosDeTarjeta()))
                .thenReturn(false);
        String resultado = tarjetaDeCredito.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("No se pudo ejecutar la transacción", resultado);
    }
}
