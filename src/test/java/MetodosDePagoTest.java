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

    @BeforeEach
    void setUp() {
        cliente1 = mock(Cliente.class);
        pedido2 = mock(Pedido.class);

        //TARJETA DE CRÉDITO
        tarjetaApi = mock(TarjetaApi.class);
        tarjetaDeCredito = new TarjetaDeCredito(tarjetaApi);

        when(pedido2.getPrecioTotal()).thenReturn(1000.0);
        when(cliente1.getDatosDeTarjeta()).thenReturn("23456778,970,07/27");

        when(tarjetaApi.validarDatos(cliente1.getDatosDeTarjeta()))
                .thenReturn(true);
        when(tarjetaApi.reservarFondos(pedido2.getPrecioTotal(), cliente1.getDatosDeTarjeta()))
                .thenReturn(true);
        when(tarjetaApi.ejecutarTransaccion(pedido2.getPrecioTotal(), cliente1.getDatosDeTarjeta()))
                .thenReturn(true);
        when(tarjetaApi.notificarResultado())
                .thenReturn("Pago exitoso");

        //TRANSFERENCIA BANCARIA
        transferenciaApi = mock(TransferenciaApi.class);
        transferenciaBancaria = new TransferenciaBancaria(transferenciaApi);

        when(cliente1.getAlias()).thenReturn("raul.fernandez5");
        when(cliente1.getCbu()).thenReturn(278976543L);

        when(transferenciaApi.validarDatos(cliente1.getCbu(), cliente1.getAlias()))
                .thenReturn(true);
        when(transferenciaApi.reservarFondos(pedido2.getPrecioTotal(), cliente1.getCbu(),cliente1.getAlias())).thenReturn(true);
        when(transferenciaApi.ejecutarTransaccion(pedido2.getPrecioTotal(),cliente1.getCbu(),cliente1.getAlias())).thenReturn(true);
        when(transferenciaApi.notificarResultado()).thenReturn("Pago exitoso");


        //BILLETERA VIRTUAL
        billeteraVirtualApi = mock(BilleteraVirtualApi.class);
        billeteraVirtual = new BilleteraVirtual(billeteraVirtualApi);

        when(billeteraVirtualApi.validarDatos(pedido2.getPrecioTotal(), cliente1.getCbu(), cliente1.getAlias()))
                .thenReturn(true);
        when(billeteraVirtualApi.reservarFondos(pedido2.getPrecioTotal(),cliente1.getCbu(), cliente1.getAlias())).thenReturn(true);
        when(billeteraVirtualApi.ejecutarTransaccion(pedido2.getPrecioTotal())).thenReturn(true);
        when(billeteraVirtualApi.notificarResultado()).thenReturn("Pago exitoso");

    }
// Mockito inorder
//TESTS TARJETA DE CRÉDITO
    @Test
    void test01_seRealizaUnPagoExitosoConTarjetaDeCredito() {
        String resultado = tarjetaDeCredito.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("Pago exitoso", resultado);

        InOrder inOrder = inOrder(tarjetaApi);
        inOrder.verify(tarjetaApi).validarDatos("23456778,970,07/27");
        inOrder.verify(tarjetaApi).reservarFondos(1000.0, "23456778,970,07/27");
        inOrder.verify(tarjetaApi).ejecutarTransaccion(1000.0, "23456778,970,07/27");
        inOrder.verify(tarjetaApi).notificarResultado();
        verifyNoMoreInteractions(tarjetaApi);
    }

    @Test
    void test02_seIntentaValidarLosDatosDeUnaTarjetaDeCredito() {
        when(tarjetaApi.validarDatos(cliente1.getDatosDeTarjeta()))
                .thenReturn(false);
        String resultado = tarjetaDeCredito.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("No se pudo validar los datos", resultado);

        verify(tarjetaApi).validarDatos("23456778,970,07/27");
        verify(tarjetaApi, never()).reservarFondos(anyDouble(), any());
        verify(tarjetaApi, never()).ejecutarTransaccion(anyDouble(), any());
        verify(tarjetaApi, never()).notificarResultado();
    }

    @Test
    void test03_seIntentaReservarFondosDeUnaTarjetaDeCredito() {
        when(tarjetaApi.reservarFondos(pedido2.getPrecioTotal(),cliente1.getDatosDeTarjeta()))
                .thenReturn(false);
        String resultado = tarjetaDeCredito.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("No hay fondos suficientes", resultado);
        InOrder inOrder = inOrder(tarjetaApi);
        inOrder.verify(tarjetaApi).validarDatos("23456778,970,07/27");
        inOrder.verify(tarjetaApi).reservarFondos(1000.0, "23456778,970,07/27");

        verify(tarjetaApi, never()).ejecutarTransaccion(anyDouble(), any());
        verify(tarjetaApi, never()).notificarResultado();
    }

    @Test
    void test04_seIntentaEjecutarUnaTransaccionDeUnaTarjetaDeCredito() {
        when(tarjetaApi.ejecutarTransaccion(pedido2.getPrecioTotal(),cliente1.getDatosDeTarjeta()))
                .thenReturn(false);
        String resultado = tarjetaDeCredito.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("No se pudo ejecutar la transacción", resultado);
        InOrder inOrder = inOrder(tarjetaApi);
        inOrder.verify(tarjetaApi).validarDatos("23456778,970,07/27");
        inOrder.verify(tarjetaApi).reservarFondos(1000.0, "23456778,970,07/27");
        inOrder.verify(tarjetaApi).ejecutarTransaccion(1000.0, "23456778,970,07/27");

        verify(tarjetaApi, never()).notificarResultado();
    }


    //TESTS TRANSFERENCIA BANCARIA
    @Test
    void test01_seRealizaUnPagoExitosoConTransferenciaBancaria() {
        String resultado = transferenciaBancaria.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("Pago exitoso", resultado);
        InOrder inOrder = inOrder(transferenciaApi);
        inOrder.verify(transferenciaApi).validarDatos(278976543L, "raul.fernandez5");
        inOrder.verify(transferenciaApi).reservarFondos(1000.0, 278976543L, "raul.fernandez5");
        inOrder.verify(transferenciaApi).ejecutarTransaccion(1000.0, 278976543L, "raul.fernandez5");
        inOrder.verify(transferenciaApi).notificarResultado();
        verifyNoMoreInteractions(transferenciaApi);
    }

    @Test
    void test02_seIntentaValidarLosDatosDeUnaTransferenciaBancaria() {
        when(transferenciaApi.validarDatos(cliente1.getCbu(),cliente1.getAlias()))
                .thenReturn(false);
        String resultado = transferenciaBancaria.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("No se pudo validar los datos", resultado);
        verify(transferenciaApi).validarDatos(278976543L, "raul.fernandez5");
        verify(transferenciaApi, never()).reservarFondos(anyDouble(), anyLong(), anyString());
        verify(transferenciaApi, never()).ejecutarTransaccion(anyDouble(), anyLong(), anyString());
        verify(transferenciaApi, never()).notificarResultado();
    }


    @Test
    void test03_seIntentaReservarFondosDeUnaTransferenciaBancaria() {
        when(transferenciaApi.reservarFondos(pedido2.getPrecioTotal(),cliente1.getCbu(),cliente1.getAlias()))
                .thenReturn(false);
        String resultado = transferenciaBancaria.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("No hay fondos suficientes", resultado);
        InOrder inOrder = inOrder(transferenciaApi);
        inOrder.verify(transferenciaApi).validarDatos(278976543L, "raul.fernandez5");
        inOrder.verify(transferenciaApi).reservarFondos(1000.0, 278976543L, "raul.fernandez5");

        verify(transferenciaApi, never()).ejecutarTransaccion(anyDouble(), anyLong(), anyString());
        verify(transferenciaApi, never()).notificarResultado();
    }

    @Test
    void test04_seIntentaEjecutarUnaTransaccionDeUnaTransferenciaBancaria() {
        when(transferenciaApi.ejecutarTransaccion(pedido2.getPrecioTotal(),cliente1.getCbu(),cliente1.getAlias()))
                .thenReturn(false);
        String resultado = transferenciaBancaria.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("No se pudo ejecutar la transacción", resultado);

        InOrder inOrder = inOrder(transferenciaApi);
        inOrder.verify(transferenciaApi).validarDatos(278976543L, "raul.fernandez5");
        inOrder.verify(transferenciaApi).reservarFondos(1000.0, 278976543L, "raul.fernandez5");
        inOrder.verify(transferenciaApi).ejecutarTransaccion(1000.0, 278976543L, "raul.fernandez5");

        verify(transferenciaApi, never()).notificarResultado();
    }

    //TESTS BILLETERA VIRTUAL
    @Test
    void test01_seRealizaUnPagoExitosoConBilleteraVirtual() {
        String resultado = billeteraVirtual.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("Pago exitoso", resultado);

        InOrder inOrder = inOrder(billeteraVirtualApi);
        inOrder.verify(billeteraVirtualApi).validarDatos(1000.0, 278976543L, "raul.fernandez5");
        inOrder.verify(billeteraVirtualApi).reservarFondos(1000.0, 278976543L, "raul.fernandez5");
        inOrder.verify(billeteraVirtualApi).ejecutarTransaccion(1000.0);
        inOrder.verify(billeteraVirtualApi).notificarResultado();
        verifyNoMoreInteractions(billeteraVirtualApi);
    }

    @Test
    void test02_seIntentaValidarLosDatosDeUnaBilleteraVirtual() {
        when(billeteraVirtualApi.validarDatos(pedido2.getPrecioTotal(),cliente1.getCbu(),cliente1.getAlias()))
                .thenReturn(false);
        String resultado = billeteraVirtual.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("No se pudo validar los datos", resultado);

        verify(billeteraVirtualApi).validarDatos(1000.0, 278976543L, "raul.fernandez5");
        verify(billeteraVirtualApi, never()).reservarFondos(anyDouble(), anyLong(), anyString());
        verify(billeteraVirtualApi, never()).ejecutarTransaccion(anyDouble());
        verify(billeteraVirtualApi, never()).notificarResultado();
    }


    @Test
    void test03_seIntentaReservarFondosDeUnaBilleteraVirtual() {
        when(billeteraVirtualApi.reservarFondos(pedido2.getPrecioTotal(),cliente1.getCbu(),cliente1.getAlias()))
                .thenReturn(false);
        String resultado = billeteraVirtual.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("No hay fondos suficientes", resultado);

        InOrder inOrder = inOrder(billeteraVirtualApi);
        inOrder.verify(billeteraVirtualApi).validarDatos(1000.0, 278976543L, "raul.fernandez5");
        inOrder.verify(billeteraVirtualApi).reservarFondos(1000.0, 278976543L, "raul.fernandez5");

        verify(billeteraVirtualApi, never()).ejecutarTransaccion(anyDouble());
        verify(billeteraVirtualApi, never()).notificarResultado();
    }

    @Test
    void test04_seIntentaEjecutarUnaTransaccionDeUnaBilleteraVirtual() {
        when(billeteraVirtualApi.ejecutarTransaccion(pedido2.getPrecioTotal()))
                .thenReturn(false);
        String resultado = billeteraVirtual.pagar(pedido2.getPrecioTotal(), cliente1);
        assertEquals("No se pudo ejecutar la transacción", resultado);

        InOrder inOrder = inOrder(billeteraVirtualApi);
        inOrder.verify(billeteraVirtualApi).validarDatos(1000.0, 278976543L, "raul.fernandez5");
        inOrder.verify(billeteraVirtualApi).reservarFondos(1000.0, 278976543L, "raul.fernandez5");
        inOrder.verify(billeteraVirtualApi).ejecutarTransaccion(1000.0);

        verify(billeteraVirtualApi, never()).notificarResultado();
    }
}
