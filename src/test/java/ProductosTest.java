import Clases.Producto;
import Clases.Paquete;
import Clases.ProductoIndividual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static Clases.Categoria.Electronica;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductosTest {
    ProductoIndividual producto1;
    ProductoIndividual producto2;
    ProductoIndividual producto3;

    @BeforeEach
    void setUp(){
        producto1 = new ProductoIndividual("E0123", "Cable USB-C","cable cargador Samsung A15", "Samsung", Electronica, 800);
        producto2 = new ProductoIndividual("E1235", "Funda Protector","funda celular negra", "Samsung", Electronica, 1500, 0.10);
        producto3 = new ProductoIndividual("E0126", "Cable USB-C","cable cargador Samsung A35", "Samsung", Electronica, 800);
    }

    //TESTS DE PRODUCTOS INDIVIDUALES

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
                "2CargadoresDeCelulares","cable cargador Samsung A15, cable cargador Samsung A35",producto1,producto3,Electronica);
        assertEquals(1600, paquete1.getPrecioFinal());
        assertEquals(1600, paquete1.getPrecioBase());
    }

    @Test
    void test004_PaqueteSinDescuentoConDosProductosConDescuento(){
        Producto paquete1 = new Paquete(
                "2fundasDeCelulares","2 fundas negras para celulares samsung",producto2,producto2,Electronica);
        assertEquals(2700, paquete1.getPrecioFinal());
    }

    @Test
    void test005_PaqueteSinDescuentoConProductoSinDescuentoYProductoConDescuento(){
        Producto paquete1 = new Paquete(
                "CableYFunda","cable cargador Samsung A15, funda celular negra",producto1,producto2,Electronica);
        assertEquals(2150, paquete1.getPrecioFinal());
    }

    @Test
    void test006_PaqueteConDescuentoConProductoSinDescuentoYProductoConDescuento(){
        Producto paquete1 = new Paquete(
                "CableYFunda","cable cargador Samsung A15, funda celular negra",producto1,producto2,0.10,Electronica);
        assertEquals(1935, paquete1.getPrecioFinal());
    }

    @Test
    void test007_PaqueteConDescuentoConDosProductosSinDescuento(){
        Producto paquete1 = new Paquete(
                "2CablesUSB-C"," 2 cables cargadores USB-C Samsung",producto1,producto3,0.10,Electronica);
        assertEquals(1440, paquete1.getPrecioFinal());
    }

    @Test
    void test008_PaqueteConDescuentoConDosProductosConDescuento(){
        Producto paquete1 = new Paquete(
                "2FundasProtectorasCelulares","2 fundas protectoras samsung negra",producto2,producto2,0.10,Electronica);
        assertEquals(2430, paquete1.getPrecioFinal());
    }

    // TESTS DE COMPOSICIÓN (PAQUETE DENTRO DE PAQUETE)

    @Test
    void test009_PaqueteSinDescuentoQueContieneProductoSinDescuentoYPaqueteSinDescuento(){
        Producto paquete1 = new Paquete(
                "2CablesCelular","cable cargador A35, cable cargador A15",producto3,producto1,Electronica);
        Producto paquete2 = new Paquete(
                "3CablesCelular","cable cargador A35, 2 cable cargador A15",producto1,paquete1,Electronica);
        assertEquals(2400, paquete2.getPrecioFinal());
    }

    @Test
    void test010_PaqueteSinDescuentoQueContieneProductoSinDescuentoYPaqueteConDescuentoInterno(){
        Producto paquete1 = new Paquete(
                "2CablesCelular","cable cargador A35, cable cargador A15",producto3,producto1,0.10,Electronica);
        Producto paquete2 = new Paquete(
                "3CablesCelular","cable cargador A35, 2 cable cargador A15",producto1,paquete1,Electronica);
        assertEquals(2240, paquete2.getPrecioFinal());
    }

    @Test
    void test011_PaqueteSinDescuentoQueContieneProductoConDescuentoYPaqueteSinDescuento(){
        Producto paquete1 = new Paquete(
                "2CablesCelular","cable cargador A35, cable cargador A15",producto3,producto1,Electronica);
        Producto paquete2 = new Paquete(
                "DosCablesYFunda","cable cargador A35,cable cargador A15,funda protectora",producto2,paquete1,Electronica);
        assertEquals(2950, paquete2.getPrecioFinal());
    }

    @Test
    void test012_PaqueteSinDescuentoQueContieneProductoConDescuentoYPaqueteConDescuentoInterno(){
        Producto paquete1 = new Paquete(
                "2CablesCelular","cable cargador A35, cable cargador A15",producto3,producto1,0.10,Electronica);
        Producto paquete2 = new Paquete(
                "DosCablesYFunda","cable cargador A15,funda protectora",producto2,paquete1,Electronica);
        assertEquals(2790, paquete2.getPrecioFinal());
    }

    @Test
    void test013_PaqueteSinDescuentoQueContieneProductoSinDescuentoYPaqueteConProductosMezclados(){
        Producto paquete1 = new Paquete(
                "CableYFunda","cable cargador A35, funda protectora",producto3,producto2,Electronica);
        Producto paquete2 = new Paquete(
                "DosCablesYFunda","cable cargador A35, funda protectora, cable cargador A15",producto1,paquete1,Electronica);
        assertEquals(2950, paquete2.getPrecioFinal());
    }

    @Test
    void test014_PaqueteSinDescuentoQueContieneProductoSinDescuentoYPaqueteConDosProductosConDescuento(){
        Producto paquete1 = new Paquete(
                "2Fundas","2 fundas protectoras para celular",producto2,producto2,Electronica);
        Producto paquete2 = new Paquete(
                "2FundasYUnCable","2 fundas protectoras para celular + cable USB-C",producto1,paquete1,Electronica);
        assertEquals(3500, paquete2.getPrecioFinal());
    }

    @Test
    void test015_PaqueteSinDescuentoQueContieneProductoConDescuentoYPaqueteConProductosMezclados(){
        Producto paquete1 = new Paquete(
                "CableYFunda","funda protectora para celular + cable USB-C",producto3,producto2,Electronica);
        Producto paquete2 = new Paquete(
                "DosFundasYCable","2 fundas protectoras para celular + cable USB-C",producto2,paquete1,Electronica);
        assertEquals(3500, paquete2.getPrecioFinal());
    }

    @Test
    void test016_PaqueteSinDescuentoQueContieneProductoConDescuentoYPaqueteConDosProductosConDescuento(){
        Producto paquete1 = new Paquete(
                "2FundasProtectoras","2 fundas protectoras para celular",producto2,producto2,Electronica);
        Producto paquete2 = new Paquete(
                "3FundasProtectoras","3 fundas protectoras para celular",producto2,paquete1,Electronica);
        assertEquals(4050, paquete2.getPrecioFinal());
    }

    @Test
    void test017_PaqueteConDescuentoQueContieneProductoSinDescuentoYPaqueteSinDescuento(){
        Producto paquete1 = new Paquete(
                "CableYFunda","cable USB-C + funda protectora",producto3,producto1,Electronica);
        Producto paquete2 = new Paquete(
                "DosCablesYFunda","2 cable USB-C + funda protectora",producto1,paquete1,0.10,Electronica);
        assertEquals(2160, paquete2.getPrecioFinal());
    }

    @Test
    void test018_PaqueteConDescuentoQueContieneProductoConDescuentoYPaqueteSinDescuento(){
        Producto paquete1 = new Paquete(
                "CableYFunda","cable USB-C + funda protectora",producto3,producto1,Electronica);
        Producto paquete2 = new Paquete(
                "DosFundasYCable","cable USB-C + 2 funda protectora",producto2,paquete1,0.10,Electronica);
        assertEquals(2655, paquete2.getPrecioFinal());
    }

    @Test
    void test019_PaqueteConDescuentoQueContieneProductoConDescuentoYPaqueteConDescuentoInterno(){
        Producto paquete1 = new Paquete(
                "CableYFunda","cable USB-C + funda protectora",producto3,producto1,0.10,Electronica);
        Producto paquete2 = new Paquete(
                "DosCablesYFunda","2 cable USB-C + funda protectora",producto2,paquete1,0.10,Electronica);
        assertEquals(2511, paquete2.getPrecioFinal());
    }

    //  TESTS DE ATRIBUTOS EXTRA

    @Test
    void test020_ProductoPermiteAsignarUnAtributoExtra(){
        producto1.setAtributoExtra("Alto", 1.9);
        assertEquals(1.9, producto1.getAtributoExtra("Alto"));
    }

    @Test
    void test021_ProductoPermiteAsignarAtributosExtras(){
        producto1.setAtributoExtra("Alto", 1.9);
        producto1.setAtributoExtra("Ancho", 1.9);
        producto1.setAtributoExtra("Peso", 5);
        assertEquals(1.9, producto1.getAtributoExtra("Alto"));
        assertEquals(1.9, producto1.getAtributoExtra("Ancho"));
        assertEquals(5, producto1.getAtributoExtra("Peso"));
    }

    @Test
    void test022_ProductoIndividualRetornaPesoCeroSiNoTieneAtributoPeso() {
        assertEquals(0.0f, producto1.getPeso());
    }

    @Test
    void test023_ProductoIndividualRetornaPesoCorrectoCuandoSeLeAsigna() {
        producto1.setAtributoExtra("peso", 150.0f);
        assertEquals(150.0f, producto1.getPeso());
    }
}
