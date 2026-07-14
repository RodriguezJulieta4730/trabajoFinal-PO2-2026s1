import Clases.*;
import Excepciones.NoHayStockException;
import Excepciones.TiendaInvalidaException;
import MetodosDeEnvio.MetodoDeEnvio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static Clases.Categoria.Electronica;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductosTest {
    ProductoIndividual producto1;
    ProductoIndividual producto2;
    ProductoIndividual producto3;
    Tienda tienda;
    Sucursal sucursal;

    @BeforeEach
    void setUp(){
        producto1 = new ProductoIndividual("E0123", "Cable USB-C","cable cargador Samsung A15", "Samsung", Electronica, 800);
        producto2 = new ProductoIndividual("E1235", "Funda Protector","funda celular negra", "Samsung", Electronica, 1500, 0.10);
        producto3 = new ProductoIndividual("E0126", "Cable USB-C","cable cargador Samsung A35", "Samsung", Electronica, 800);
        tienda = new Tienda();
        sucursal = new Sucursal(tienda, "Andres Baranda 750");
        tienda.registrarSucursal(sucursal);
    }

    // TESTS DE PRODUCTOS INDIVIDUALES

    @Test
    void test001_ProductoIndividualSinDescuentoRetornaValoresCorrectos(){
        assertEquals("Cable USB-C", producto1.getNombre());
        assertEquals("Samsung", producto1.getMarca());
        assertEquals(Electronica, producto1.getCategoria());
        assertEquals(800, producto1.getPrecioBase());
        assertEquals(800, producto1.getPrecioFinal());
    }

    @Test
    void test002_ProductoIndividualConDescuentoCalculaPrecioFinalCorrecto(){
        assertEquals("Cable USB-C", producto1.getNombre());
        assertEquals("Samsung", producto1.getMarca());
        assertEquals(Electronica, producto1.getCategoria());
        assertEquals(1500, producto2.getPrecioBase());
        assertEquals(1350, producto2.getPrecioFinal());
    }

    // TESTS DE PAQUETES SIMPLES

    @Test
    void test003_PaqueteSinDescuentoConDosProductosSinDescuento(){
        Producto paquete1 = new Paquete(
                "2CargadoresDeCelulares","cable cargador Samsung A15, cable cargador Samsung A35",Electronica, Map.of(producto1,1,producto3,1));
        assertEquals(1600, paquete1.getPrecioFinal());
        assertEquals(1600, paquete1.getPrecioBase());
    }

    @Test
    void test004_PaqueteSinDescuentoConDosProductosConDescuento(){
        Producto paquete1 = new Paquete(
                "2fundasDeCelulares","2 fundas negras para celulares samsung",Electronica,Map.of(producto2,2));
        assertEquals(2700, paquete1.getPrecioFinal());
    }

    @Test
    void test005_PaqueteSinDescuentoConProductoSinDescuentoYProductoConDescuento(){
        Producto paquete1 = new Paquete(
                "CableYFunda","cable cargador Samsung A15, funda celular negra",Electronica,Map.of(producto1,1,producto2,1));
        assertEquals(2150, paquete1.getPrecioFinal());
    }

    @Test
    void test006_PaqueteConDescuentoConProductoSinDescuentoYProductoConDescuento(){
        Producto paquete1 = new Paquete(
                "CableYFunda","cable cargador Samsung A15, funda celular negra",0.10,Electronica,Map.of(producto1,1,producto2,1));
        assertEquals(1935, paquete1.getPrecioFinal());
    }

    @Test
    void test007_PaqueteConDescuentoConDosProductosSinDescuento(){
        Producto paquete1 = new Paquete(
                "2CablesUSB-C"," 2 cables cargadores USB-C Samsung",0.10,Electronica,Map.of(producto1,1,producto3,1));
        assertEquals(1440, paquete1.getPrecioFinal());
    }

    @Test
    void test008_PaqueteConDescuentoConDosProductosConDescuento(){
        Producto paquete1 = new Paquete(
                "2FundasProtectorasCelulares","2 fundas protectoras samsung negra",0.10,Electronica,Map.of(producto2,2));
        assertEquals(2430, paquete1.getPrecioFinal());
    }

    // TESTS DE COMPOSICIÓN (PAQUETE DENTRO DE PAQUETE)

    @Test
    void test009_PaqueteSinDescuentoQueContieneProductoSinDescuentoYPaqueteSinDescuento(){
        Producto paquete1 = new Paquete(
                "ComboCargadores", "2 cables cargadores USB-C Samsung", Electronica, Map.of(producto1, 1, producto3, 1));
        Producto paquete2 = new Paquete(
                "SuperCombo", "2 cables cargadores USB-C Samsung + funda", Electronica, Map.of(producto2, 1, paquete1, 1));
        assertEquals(2950, paquete2.getPrecioFinal());
    }

    @Test
    void test010_PaqueteSinDescuentoQueContieneProductoSinDescuentoYPaqueteConDescuentoInterno(){
        Producto paquete1 = new Paquete(
                "ComboCargadores", "2 cables cargadores USB-C Samsung", 0.10, Electronica, Map.of(producto1, 1, producto3, 1));
        Producto paquete2 = new Paquete(
                "SuperCombo", "2 cables cargadores USB-C Samsung + funda", Electronica, Map.of(producto2, 1, paquete1, 1));
        assertEquals(2790, paquete2.getPrecioFinal());
    }

    @Test
    void test011_PaqueteSinDescuentoQueContieneProductoConDescuentoYPaqueteSinDescuento(){
        Producto paquete1 = new Paquete(
                "2CablesCelular","cable cargador A35, cable cargador A15",Electronica,Map.of(producto3,1,producto1,1));
        Producto paquete2 = new Paquete(
                "DosCablesYFunda","cable cargador A35,cable cargador A15,funda protectora",Electronica,Map.of(producto2,1,paquete1,1));
        assertEquals(2950, paquete2.getPrecioFinal());
    }

    @Test
    void test012_PaqueteSinDescuentoRetornaValoresCorrectos(){
        Producto paquete = new Paquete(
                "CableYFunda", "cable USB-C + funda protectora", Electronica, Map.of(producto1, 1, producto3, 1));
        assertEquals(1600, paquete.getPrecioFinal());
    }

    @Test
    void test013_PaqueteConDescuentoRetornaValoresCorrectos(){
        Producto paquete = new Paquete(
                "CableYFunda", "cable USB-C + funda protectora", 0.10, Electronica, Map.of(producto1, 1, producto3, 1));
        assertEquals(1440, paquete.getPrecioFinal());
    }

    @Test
    void test014_PaqueteConDescuentoQueContieneProductoConDescuento(){
        Producto paquete = new Paquete(
                "CableYFunda", "cable USB-C + funda protectora", 0.10, Electronica, Map.of(producto2, 1, producto1, 1));
        assertEquals(1935, paquete.getPrecioFinal());
    }

    @Test
    void test015_PaqueteConDescuentoQueContieneProductoConDescuentoYPaqueteConDescuentoInterno(){
        Producto paquete1 = new Paquete(
                "CableYFunda", "cable USB-C + funda protectora", 0.10, Electronica, Map.of(producto3, 1, producto1, 1));
        Producto paquete2 = new Paquete(
                "DosCablesYFunda", "2 cable USB-C + funda protectora", 0.10, Electronica, Map.of(producto2, 1, paquete1, 1));
        assertEquals(2511, paquete2.getPrecioFinal());
    }

    // TESTS DE ATRIBUTOS EXTRA

    @Test
    void test016_ProductoPermiteAsignarUnAtributoExtra(){
        producto1.setAtributoExtra("Alto", 1.9);
        assertEquals(1.9, producto1.getAtributoExtra("Alto"));
    }

    @Test
    void test017_ProductoPermiteAsignarAtributosExtras(){
        producto1.setAtributoExtra("Alto", 1.9);
        producto1.setAtributoExtra("Ancho", 1.9);
        producto1.setAtributoExtra("Peso", 5);
        assertEquals(1.9, producto1.getAtributoExtra("Alto"));
        assertEquals(1.9, producto1.getAtributoExtra("Ancho"));
        assertEquals(5, producto1.getAtributoExtra("Peso"));
    }

    @Test
    void test018_ProductoIndividualRetornaPesoCeroSiNoTieneAtributoPeso() {
        assertEquals(0.0f, producto1.getPeso());
    }

    @Test
    void test019_ProductoIndividualRetornaPesoCorrectoCuandoSeLeAsigna() {
        producto1.setAtributoExtra("peso", 150.0f);
        assertEquals(150.0f, producto1.getPeso());
    }

    // CREACIÓN DINÁMICA DE PAQUETES EN SUCURSAL

    @Test
    void test020_FabricarPaqueteEnSucursalConStockSuficienteDescuentaDeIndividuales() {
        Sucursal sucursalRoque = new Sucursal(tienda,"Roque Sáenz Peña 352");
        sucursalRoque.agregarStock(producto1, 10);
        sucursalRoque.agregarStock(producto3, 5);

        Map<Producto, Integer> productos = Map.of(producto1, 2, producto3, 1);

        Paquete comboCables = sucursalRoque.fabricarPaquete(
                "Combo Tecnologico", "Kit Cargadores", 0.15, Electronica, productos);

        assertEquals(8, sucursalRoque.getStockDeProductos().get(producto1));  // 10 - 2 = 8
        assertEquals(4, sucursalRoque.getStockDeProductos().get(producto3));  // 5 - 1 = 4
        assertEquals(1, sucursalRoque.getStockDeProductos().get(comboCables)); // Se fabricó 1 paquete
    }

    @Test
    void test021_FabricarPaqueteEnSucursalSinStockSuficienteLanzaExcepcion() {
        Sucursal sucursalRoque = new Sucursal(tienda,"Roque Sáenz Peña 352");
        sucursalRoque.agregarStock(producto1, 1);
        sucursalRoque.agregarStock(producto3, 10);

        Map<Producto, Integer> productos = Map.of(producto1, 2, producto3, 1);

        assertThrows(RuntimeException.class, () -> {
            sucursalRoque.fabricarPaquete("Combo Fallido", "No se armará", 0.10, Electronica, productos);});

        assertEquals(1, sucursalRoque.getStockDeProductos().get(producto1));
        assertEquals(10, sucursalRoque.getStockDeProductos().get(producto3));
    }

    @Test
    void test022_FabricarPaqueteCompositeQueContieneOtroPaqueteCreadoAnteriormente() {
        Sucursal sucursalRoque = new Sucursal(tienda,"Roque Sáenz Peña 352");
        sucursalRoque.agregarStock(producto1, 10);
        sucursalRoque.agregarStock(producto2, 5);
        sucursalRoque.agregarStock(producto3, 5);

        Map<Producto, Integer> productos1 = Map.of(producto1, 2, producto3, 1);
        Paquete paqueteSimple = sucursalRoque.fabricarPaquete(
                "CableYFunda", "cable USB-C + funda protectora", 0.10, Electronica, productos1
        );

        Map<Producto, Integer> productos2 = Map.of(paqueteSimple, 1, producto2, 1);
        Paquete paqueteCompuesto = sucursalRoque.fabricarPaquete(
                "DosCablesYFunda", "2 cable USB-C + funda protectora", 0.10, Electronica, productos2
        );

        assertEquals(0, sucursalRoque.getStockDeProductos().get(paqueteSimple));
        assertEquals(4, sucursalRoque.getStockDeProductos().get(producto2));
        assertEquals(1, sucursalRoque.getStockDeProductos().get(paqueteCompuesto));
    }

    // TESTS: APLICAR DESCUENTOS

    @Test
    void test023_SucursalPermiteAplicarDescuentoAProductoIndividualQueNoTenia() {
        Sucursal sucursalRoque = new Sucursal(tienda,"Roque Sáenz Peña 352");
        sucursalRoque.agregarStock(producto1, 5);

        assertEquals(800, producto1.getPrecioFinal());
        sucursalRoque.aplicarDescuentoAProducto(producto1, 0.10);
        assertEquals(720, producto1.getPrecioFinal());
    }

    @Test
    void test024_AplicarDescuentoAProductoQueNoEstaEnLaSucursalLanzaExcepcion() {
        Sucursal sucursalRoque = new Sucursal(tienda,"Roque Sáenz Peña 352");
        assertThrows(RuntimeException.class, () -> {
            sucursalRoque.aplicarDescuentoAProducto(producto1, 0.20);
        });
    }

    @Test
    void test025_SucursalPermiteCambiarElDescuentoDeUnProductoQueYaTeniaUno() {
        Sucursal sucursalRoque = new Sucursal(tienda,"Roque Sáenz Peña 352");
        sucursalRoque.agregarStock(producto2, 5);

        assertEquals(1350, producto2.getPrecioFinal());
        sucursalRoque.aplicarDescuentoAProducto(producto2, 0.20);
        assertEquals(1200, producto2.getPrecioFinal());
    }

    // GESTIÓN MULTISUCURSAL

    @Test
    void test026_PedidoSeProcesaCorrectamenteCuandoLaSucursalElegidaTieneStockLocal() {
        Tienda miTienda = new Tienda();
        Sucursal sucursalBernal = new Sucursal(miTienda,"Roque Sáenz Peña 352");
        miTienda.registrarSucursal(sucursalBernal);

        sucursalBernal.agregarStock(producto1, 5);

        Pedido pedido = mock(Pedido.class);
        when(pedido.getCarritoDeProductos()).thenReturn(Map.of(producto1, 2));

        miTienda.procesarPedido(pedido, sucursalBernal);

        assertEquals(3, sucursalBernal.getStockDeProductos().get(producto1));
    }

    @Test
    void test027_PedidoActivaTrasladoInternoCuandoSucursalDestinoNoTieneStockPeroOtraSi() {
        Tienda miTienda = new Tienda();
        Sucursal sucursalBernal = new Sucursal(miTienda,"Roque Sáenz Peña 352");
        Sucursal sucursalQuilmes = new Sucursal(miTienda,"Rivadavia 123");

        miTienda.registrarSucursal(sucursalBernal);
        miTienda.registrarSucursal(sucursalQuilmes);

        sucursalQuilmes.agregarStock(producto1, 10);

        Pedido pedido = mock(Pedido.class);
        when(pedido.getCarritoDeProductos()).thenReturn(Map.of(producto1, 3));

        miTienda.procesarPedido(pedido, sucursalBernal);

        assertEquals(7, sucursalQuilmes.getStockDeProductos().get(producto1));
        assertEquals(0, sucursalBernal.getStockDeProductos().get(producto1));
    }

    @Test
    void test028_PedidoLanzaExcepcionSiNingunaSucursalDeLaTiendaTieneElStockRequerido() {
        Tienda miTienda = new Tienda();
        Sucursal sucursalBernal = new Sucursal(miTienda,"Roque Sáenz Peña 352");
        Sucursal sucursalQuilmes = new Sucursal(miTienda,"Rivadavia 123");

        miTienda.registrarSucursal(sucursalBernal);
        miTienda.registrarSucursal(sucursalQuilmes);

        sucursalBernal.agregarStock(producto1, 1);
        sucursalQuilmes.agregarStock(producto1, 2);

        Pedido pedido = mock(Pedido.class);
        when(pedido.getCarritoDeProductos()).thenReturn(Map.of(producto1, 5));

        assertThrows(RuntimeException.class, () -> {
            miTienda.procesarPedido(pedido, sucursalBernal);
        });
    }

    @Test
    void test029_TiendaNoPermiteRegistrarSucursalesQueNoSonPropias() {
        Tienda otraTiendaCompetencia = new Tienda();
        Sucursal sucursalDeLaCompetencia = new Sucursal(otraTiendaCompetencia, "Calle Falsa 123");

        assertThrows(TiendaInvalidaException.class, () -> {
            tienda.registrarSucursal(sucursalDeLaCompetencia);
        });
    }

    @Test
    void test030_ProcesarPedidoEjecutaTrasladoInternoSiLaSucursalDestinoEstaVaciaPeroOtraTieneStock() {
        Sucursal sucursalQuilmes = new Sucursal(tienda, "Rivadavia 123");
        tienda.registrarSucursal(sucursalQuilmes);

        sucursalQuilmes.agregarStock(producto1, 10);

        Pedido pedidoMock = mock(Pedido.class);
        when(pedidoMock.getCarritoDeProductos()).thenReturn(Map.of(producto1, 3));

        tienda.procesarPedido(pedidoMock, sucursal);

        assertEquals(7, sucursalQuilmes.getStockDeProductos().get(producto1));
        assertEquals(0, sucursal.getStockDeProductos().get(producto1));
    }

    @Test
    void test031_AgregarProductoAlPedidoFallaSiElProductoEstaCatalogadoPeroNadieTieneStockFisico() {
        sucursal.agregarStock(producto1, 5);
        tienda.getCatalogoDeProductos().add(producto2);

        Pedido pedidoReal = new Pedido(sucursal, mock(MetodoDeEnvio.class), mock(Cliente.class));

        assertThrows(NoHayStockException.class, () -> {
            pedidoReal.agregarProducto(producto2, 1);
        });
    }

    @Test
    void test032_AgregarProductoAlPedidoEsExitosoSiLaSucursalDestinoNoTieneStockPeroOtraSi() {
        Sucursal sucursalQuilmes = new Sucursal(tienda, "Rivadavia 123");
        tienda.registrarSucursal(sucursalQuilmes);

        sucursalQuilmes.agregarStock(producto1, 5);

        Pedido pedidoReal = new Pedido(sucursal, mock(MetodoDeEnvio.class), mock(Cliente.class));
        pedidoReal.agregarProducto(producto1, 2);

        assertEquals(2, pedidoReal.getCarritoDeProductos().get(producto1));
    }
}