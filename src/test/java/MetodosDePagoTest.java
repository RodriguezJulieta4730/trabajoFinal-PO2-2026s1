import Clases.*;
import MetodosDePago.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MetodosDePagoTest {
    MedioDePago tarjetaDeCredito;
    TarjetaApi tarjetaApi;
    MedioDePago transferenciaBancaria;
    MedioDePago billeteraVirtual;
    BilleteraVirtualApi billeteraVirtualApi;
    Cliente cliente1;
    Pedido pedido2;
    TransferenciaApi transferenciaApi;
    String datosTarjeta = "23456778,970,07/27";

    @BeforeEach
    void setUp() {
        cliente1 = mock(Cliente.class);
        pedido2 = mock(Pedido.class);

        when(cliente1.getAlias()).thenReturn("julieta123");
        when(cliente1.getCbu()).thenReturn(278976543L);
        when(cliente1.getDatosTarjeta()).thenReturn(datosTarjeta);

        // TARJETA DE CRÉDITO
        tarjetaApi = mock(TarjetaApi.class);
        tarjetaDeCredito = new TarjetaDeCredito(tarjetaApi);

        when(pedido2.getPrecioTotal()).thenReturn(1000.0);

        when(tarjetaApi.validarDatos(datosTarjeta)).thenReturn(true);
        when(tarjetaApi.reservarFondos(pedido2.getPrecioTotal(), datosTarjeta)).thenReturn(true);
        when(tarjetaApi.ejecutarTransaccion(pedido2.getPrecioTotal(), datosTarjeta)).thenReturn(true);

        // TRANSFERENCIA BANCARIA
        transferenciaApi = mock(TransferenciaApi.class);
        transferenciaBancaria = new TransferenciaBancaria(transferenciaApi);

        when(transferenciaApi.validarDatos(cliente1.getCbu(), cliente1.getAlias())).thenReturn(true);
        when(transferenciaApi.ejecutarTransaccion(pedido2.getPrecioTotal(), cliente1.getCbu(), cliente1.getAlias())).thenReturn(true);


        // BILLETERA VIRTUAL
        billeteraVirtualApi = mock(BilleteraVirtualApi.class);
        billeteraVirtual = new BilleteraVirtual(billeteraVirtualApi);

        when(billeteraVirtualApi.validarDatos(pedido2.getPrecioTotal(), cliente1.getCbu(), cliente1.getAlias())).thenReturn(true);
        when(billeteraVirtualApi.reservarFondos(pedido2.getPrecioTotal(), cliente1.getCbu(), cliente1.getAlias())).thenReturn(true);
        when(billeteraVirtualApi.ejecutarTransaccion(pedido2.getPrecioTotal())).thenReturn(true);
        when(billeteraVirtualApi.notificarResultado(cliente1.getAlias())).thenReturn("Pago exitoso");
    }

    // TESTS TARJETA DE CRÉDITO
    @Test
    void test01_seRealizaUnPagoExitosoConTarjetaDeCredito() {
        String resultado = tarjetaDeCredito.pagar(pedido2.getPrecioTotal(), cliente1);
        String mensajeEsperado = "Pago exitoso con Tarjeta de Crédito para el cliente: " +
                cliente1 + " usando la tarjeta: " + datosTarjeta;
        assertEquals(mensajeEsperado, resultado);

        InOrder inOrder = inOrder(tarjetaApi);
        inOrder.verify(tarjetaApi).validarDatos(datosTarjeta);
        inOrder.verify(tarjetaApi).reservarFondos(1000.0, datosTarjeta);
        inOrder.verify(tarjetaApi).ejecutarTransaccion(1000.0, datosTarjeta);
        verifyNoMoreInteractions(tarjetaApi);
    }

    @Test
    void test02_seIntentaValidarLosDatosDeUnaTarjetaDeCredito() {
        when(tarjetaApi.validarDatos(datosTarjeta)).thenReturn(false);
        String resultado = tarjetaDeCredito.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("No se pudo validar los datos", resultado);

        verify(tarjetaApi).validarDatos(datosTarjeta);
        verify(tarjetaApi, never()).reservarFondos(anyDouble(), anyString());
        verify(tarjetaApi, never()).ejecutarTransaccion(anyDouble(), anyString());
    }

    @Test
    void test03_seIntentaReservarFondosDeUnaTarjetaDeCredito() {
        when(tarjetaApi.reservarFondos(pedido2.getPrecioTotal(), datosTarjeta)).thenReturn(false);

        String resultado = tarjetaDeCredito.pagar(pedido2.getPrecioTotal(), cliente1);

        assertEquals("No hay fondos suficientes", resultado);
        InOrder inOrder = inOrder(tarjetaApi);
        inOrder.verify(tarjetaApi).validarDatos(datosTarjeta);
        inOrder.verify(tarjetaApi).reservarFondos(1000.0, datosTarjeta);

        verify(tarjetaApi, never()).ejecutarTransaccion(anyDouble(), anyString());
    }

    @Test
    void test04_seIntentaEjecutarUnaTransaccionDeUnaTarjetaDeCredito() {
        when(tarjetaApi.ejecutarTransaccion(pedido2.getPrecioTotal(), datosTarjeta)).thenReturn(false);

        String resultado = tarjetaDeCredito.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("No se pudo ejecutar la transacción", resultado);

        InOrder inOrder = inOrder(tarjetaApi);
        inOrder.verify(tarjetaApi).validarDatos(datosTarjeta);
        inOrder.verify(tarjetaApi).reservarFondos(1000.0, datosTarjeta);
        inOrder.verify(tarjetaApi).ejecutarTransaccion(1000.0, datosTarjeta);
    }

    // TESTS TRANSFERENCIA BANCARIA
    @Test
    void test01_seRealizaUnPagoExitosoConTransferenciaBancaria() {
        String resultado = transferenciaBancaria.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("Pago exitoso mediante Transferencia Bancaria para el alias: " + cliente1.getAlias()
                + " (CBU: " + cliente1.getCbu() + ")", resultado);

        InOrder inOrder = inOrder(transferenciaApi);
        inOrder.verify(transferenciaApi).validarDatos(278976543L, "julieta123");
        inOrder.verify(transferenciaApi).ejecutarTransaccion(1000.0, 278976543L, "julieta123");
        verifyNoMoreInteractions(transferenciaApi);
    }

    @Test
    void test02_seIntentaValidarLosDatosDeUnaTransferenciaBancaria() {
        when(transferenciaApi.validarDatos(cliente1.getCbu(), cliente1.getAlias())).thenReturn(false);
        String resultado = transferenciaBancaria.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("No se pudo validar los datos", resultado);

        verify(transferenciaApi).validarDatos(278976543L, "julieta123");
        verify(transferenciaApi, never()).ejecutarTransaccion(anyDouble(), anyLong(), anyString());
    }

    @Test
    void test03_seIntentaEjecutarUnaTransaccionDeUnaTransferenciaBancaria() {
        when(transferenciaApi.ejecutarTransaccion(pedido2.getPrecioTotal(), cliente1.getCbu(), cliente1.getAlias())).thenReturn(false);
        String resultado = transferenciaBancaria.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("No se pudo ejecutar la transacción", resultado);

        InOrder inOrder = inOrder(transferenciaApi);
        inOrder.verify(transferenciaApi).validarDatos(278976543L, "julieta123");
        inOrder.verify(transferenciaApi).ejecutarTransaccion(1000.0, 278976543L, "julieta123");
    }

    // TESTS BILLETERA VIRTUAL
    @Test
    void test01_seRealizaUnPagoExitosoConBilleteraVirtual() {
        String resultado = billeteraVirtual.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("Pago exitoso", resultado);

        InOrder inOrder = inOrder(billeteraVirtualApi);
        inOrder.verify(billeteraVirtualApi).validarDatos(1000.0, 278976543L, "julieta123");
        inOrder.verify(billeteraVirtualApi).reservarFondos(1000.0, 278976543L, "julieta123");
        inOrder.verify(billeteraVirtualApi).ejecutarTransaccion(1000.0);
        inOrder.verify(billeteraVirtualApi).notificarResultado(cliente1.getAlias());
        verifyNoMoreInteractions(billeteraVirtualApi);
    }

    @Test
    void test02_seIntentaValidarLosDatosDeUnaBilleteraVirtual() {
        when(billeteraVirtualApi.validarDatos(pedido2.getPrecioTotal(), cliente1.getCbu(), cliente1.getAlias())).thenReturn(false);
        String resultado = billeteraVirtual.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("No se pudo validar los datos", resultado);

        verify(billeteraVirtualApi).validarDatos(1000.0, 278976543L, "julieta123");
        verify(billeteraVirtualApi, never()).reservarFondos(anyDouble(), anyLong(), anyString());
        verify(billeteraVirtualApi, never()).ejecutarTransaccion(anyDouble());
    }

    @Test
    void test03_seIntentaReservarFondosDeUnaBilleteraVirtual() {
        when(billeteraVirtualApi.reservarFondos(pedido2.getPrecioTotal(), cliente1.getCbu(), cliente1.getAlias())).thenReturn(false);
        String resultado = billeteraVirtual.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("No hay fondos suficientes", resultado);

        InOrder inOrder = inOrder(billeteraVirtualApi);
        inOrder.verify(billeteraVirtualApi).validarDatos(1000.0, 278976543L, "julieta123");
        inOrder.verify(billeteraVirtualApi).reservarFondos(1000.0, 278976543L, "julieta123");

        verify(billeteraVirtualApi, never()).ejecutarTransaccion(anyDouble());
    }

    @Test
    void test04_seIntentaEjecutarUnaTransaccionDeUnaBilleteraVirtual() {
        when(billeteraVirtualApi.ejecutarTransaccion(pedido2.getPrecioTotal())).thenReturn(false);
        String resultado = billeteraVirtual.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("No se pudo ejecutar la transacción", resultado);

        InOrder inOrder = inOrder(billeteraVirtualApi);
        inOrder.verify(billeteraVirtualApi).validarDatos(1000.0, 278976543L, "julieta123");
        inOrder.verify(billeteraVirtualApi).reservarFondos(1000.0, 278976543L, "julieta123");
        inOrder.verify(billeteraVirtualApi).ejecutarTransaccion(1000.0);
    }
}
