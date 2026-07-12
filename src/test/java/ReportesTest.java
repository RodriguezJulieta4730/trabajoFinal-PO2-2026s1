import Clases.*;
import Reportes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportesTest {
    private UNQShop tienda;
    private Sucursal sucursal;
    private ReporteProductosMasVendidos reporte;
    @Mock private Pedido pedido1;
    @Mock private Pedido pedido2;
    @Mock private Pedido pedido3;

    @Mock private ProductoIndividual producto1;
    @Mock private Paquete paquete1;

    @BeforeEach
    void setUp() {
        tienda = new UNQShop();
        sucursal = new Sucursal(tienda,"Roque Sáenz Peña 352");
        tienda.registrarSucursal(sucursal);

        when(producto1.getNombre()).thenReturn("Auriculares Bluetooth");
        when(producto1.getPrecioFinal()).thenReturn(8000.0);

        when(paquete1.getNombre()).thenReturn("Pack Audio");
        when(paquete1.getPrecioFinal()).thenReturn(15000.0);

        Map<Producto, Integer> carrito1 = new HashMap<>();
        carrito1.put(producto1, 2);

        Map<Producto, Integer> carrito3 = new HashMap<>();
        carrito3.put(producto1, 1);
        carrito3.put(paquete1, 1);

        when(pedido1.getFecha()).thenReturn(LocalDate.now().minusDays(5));

        when(pedido2.getFecha()).thenReturn(LocalDate.now());
        when(pedido2.getCarritoDeProductos()).thenReturn(carrito1);

        when(pedido3.getFecha()).thenReturn(LocalDate.now().plusDays(1));
        when(pedido3.getCarritoDeProductos()).thenReturn(carrito3);

        sucursal.getHistorialPedidos().add(pedido1);
        sucursal.getHistorialPedidos().add(pedido2);
        sucursal.getHistorialPedidos().add(pedido3);

        LocalDate inicio = LocalDate.now();
        LocalDate fin = LocalDate.now().plusDays(1);

        reporte = new ReporteProductosMasVendidos(
                sucursal.getHistorialPedidos(),
                inicio,
                fin
        );
    }

    @Test
    void test01_lineasDeReporte() {

        assertEquals(2, reporte.getLineas().size());

        LineaDeReporte linea1 = reporte.getLineas().get(0);
        assertEquals("Auriculares Bluetooth", linea1.getNombre());
        assertEquals(3, linea1.getCantidadVendida());
        assertEquals(8000.0, linea1.getPrecioPromedioCobrado());

        LineaDeReporte linea2 = reporte.getLineas().get(1);
        assertEquals("Pack Audio", linea2.getNombre());
        assertEquals(1, linea2.getCantidadVendida());
        assertEquals(15000.0, linea2.getPrecioPromedioCobrado());
        verify(pedido1, never()).getCarritoDeProductos();
    }
    @Test
    void test02_exportacionCsv() {

        String csv = reporte.exportar(new Csv());
        assertTrue(csv.contains("Auriculares Bluetooth,3,8000.0"));
        assertTrue(csv.contains("Pack Audio,1,15000.0"));
    }
    @Test
    void test03_exportacionHtml(){

        String html = reporte.exportar(new Html());
        assertTrue(html.contains("<td>Auriculares Bluetooth</td><td>3</td><td>8000.0</td>"));
        assertTrue(html.contains("<td>Pack Audio</td><td>1</td><td>15000.0</td>"));
    }
    @Test
    void test04_exportacionTextoPlano(){
        String textoPlano = reporte.exportar(new TextoPlano());
        assertTrue(textoPlano.contains("Auriculares Bluetooth - Unidades: 3 - P.Promedio: $8000.0"));
        assertTrue(textoPlano.contains("Pack Audio - Unidades: 1 - P.Promedio: $15000.0"));
    }
}
