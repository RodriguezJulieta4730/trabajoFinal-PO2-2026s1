import Clases.*;
import MetodosDeEnvio.EnvioEstandar;
import MetodosDeEnvio.MetodoDeEnvio;
import NotificacionesDelPedido.*;
import CicloDeVidaDelPedido.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class NotificacionesDePedidoTest {

    private MailSender mockMailSender;
    private Sucursal mockTienda;
    private Pedido pedido;

    private NotificadorDeEmail notificadorEmail;
    private GeneradorDeFactura generadorFactura;
    private Fidelizacion sistemaFidelizacion;
    private MetodoDeEnvio envioEstandar;

    @BeforeEach
    void setUp() {
        mockMailSender = mock(MailSender.class);
        mockTienda = mock(Sucursal.class);
        envioEstandar = new EnvioEstandar();
        pedido = new Pedido(mockTienda, "boedo 671","Julieta@email.com",envioEstandar);

        notificadorEmail = new NotificadorDeEmail(mockMailSender);
        generadorFactura = new GeneradorDeFactura();
        sistemaFidelizacion = new Fidelizacion(mockMailSender);

        pedido.getNotificador().agregarObservador(notificadorEmail);
        pedido.getNotificador().agregarObservador(generadorFactura);
        pedido.getNotificador().agregarObservador(sistemaFidelizacion);
    }

    // TESTS NOTIFICADOR DE EMAIL
    @Test
    void test01_notificadorEmailActuaAlPasarAEstadoConfirmado() {
        EstadoDePedido estadoConfirmado = new EstadoDePedidoConfirmado();

        pedido.setEstado(estadoConfirmado);

        verify(mockMailSender, times(1)).enviarMail(
                eq("Julieta@email.com"),
                contains("Actualización de tu Pedido"),
                contains("Confirmado"),
                isNull()
        );
    }

    @Test
    void test02_notificadorEmailActuaAlPasarAEstadoEnviado() {
        EstadoDePedido estadoEnviado = new EstadoDePedidoEnviado();

        pedido.setEstado(estadoEnviado);

        verify(mockMailSender, times(1)).enviarMail(
                eq("Julieta@email.com"),
                contains("Actualización de tu Pedido"),
                contains("Enviado"),
                isNull()
        );
    }

    @Test
    void test03_notificadorEmailActuaAlPasarAEstadoEntregado() {
        EstadoDePedido estadoEntregado = new EstadoDePedidoEntregado();

        pedido.setEstado(estadoEntregado);

        verify(mockMailSender, times(1)).enviarMail(
                eq("Julieta@email.com"),
                contains("Actualización de tu Pedido"),
                contains("Entregado"),
                isNull()
        );
    }

    @Test
    void test04_notificadorEmailNoActuaEnEstadosNoInteresantes() {
        EstadoDePedido estadoCancelado = new EstadoDePedidoCancelado();

        pedido.setEstado(estadoCancelado);

        verify(mockMailSender, never()).enviarMail(
                anyString(),
                eq("Actualización de tu Pedido"),
                anyString(),
                any()
        );
    }

    // TESTS GENERADOR DE FACTURA
    @Test
    void test05_generadorFacturaCreaComprobanteSoloAlAlcanzarEstadoEntregado() {
        EstadoDePedido estadoEntregado = new EstadoDePedidoEntregado();

        pedido.setEstado(estadoEntregado);

    }

    @Test
    void test06_generadorFacturaNoCreaComprobanteSiElEstadoNoEsEntregado() {
        EstadoDePedido estadoConfirmado = new EstadoDePedidoConfirmado();

        pedido.setEstado(estadoConfirmado);
    }

    // TESTS SUBSISTEMA FIDELIZACION
    @Test
    void test07_sistemaFidelizacionEnviaCuponDeDescuentoAlCancelarPedido() {
        EstadoDePedido estadoCancelado = new EstadoDePedidoCancelado();

        pedido.setEstado(estadoCancelado);

        verify(mockMailSender, times(1)).enviarMail(
                eq("Julieta@email.com"  ),
                contains("vuelvas"),
                contains("5% de descuento"),
                eq("CUPON5OFF")
        );
    }

    @Test
    void test08_sistemaFidelizacionNoEnviaCuponSiElPedidoNoSeCancela() {
        EstadoDePedido estadoEnPreparacion = new EstadoDePedidoBorrador();

        pedido.setEstado(estadoEnPreparacion);

        verify(mockMailSender, never()).enviarMail(
                anyString(),
                anyString(),
                anyString(),
                eq("CUPON5OFF")
        );
    }

    //TESTS CONTROL DEL NOTIFICADOR

    @Test
    void test09_unObservadorRemovidoYaNoRecibeNotificaciones() {
        pedido.getNotificador().removerObservador(notificadorEmail);

        EstadoDePedido estadoConfirmado = new EstadoDePedidoConfirmado();

        pedido.setEstado(estadoConfirmado);

        verify(mockMailSender, never()).enviarMail(
                anyString(),
                contains("Actualización de tu Pedido"),
                anyString(),
                any()
        );
    }
}