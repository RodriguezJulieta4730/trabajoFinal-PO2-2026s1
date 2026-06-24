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
        producto1 = new ProductoIndividual("E0123", "Cable USB-C","una descripcion", "Samsung", Electronica, 800);
        producto2 = new ProductoIndividual("E1235", "Funda Protector","una descripcion", "Samsung", Electronica, 1500, 0.10);
        producto3 = new ProductoIndividual("E0126", "Cable USB-C","una descripcion", "Samsung", Electronica, 800);
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
    void test002_ProductoIndividualConDescuentoCalculaPrecioFinalCorrectamente(){
        assertEquals("Cable USB-C", producto1.getNombre());
        assertEquals("Samsung", producto1.getMarca());
        assertEquals(Electronica, producto1.getCategoria());
        assertEquals(1500, producto2.getPrecioBase());
        assertEquals(1350, producto2.getPrecioFinal());
    }

    // TESTS DE PAQUETES SIMPLES


    @Test
    void test003_PaqueteSinDescuentoConDosProductosSinDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto1,producto3);
        assertEquals(1600, paquete1.getPrecioFinal());
    }

    @Test
    void test004_PaqueteSinDescuentoConDosProductosConDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto2,producto2);
        assertEquals(2700, paquete1.getPrecioFinal());
    }

    @Test
    void test005_PaqueteSinDescuentoConProductoSinDescuentoYProductoConDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto1,producto2);
        assertEquals(2150, paquete1.getPrecioFinal());
    }

    @Test
    void test006_PaqueteConDescuentoConProductoSinDescuentoYProductoConDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto1,producto2,0.10);
        assertEquals(1935, paquete1.getPrecioFinal());
    }

    @Test
    void test007_PaqueteConDescuentoConDosProductosSinDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto1,producto3,0.10);
        assertEquals(1440, paquete1.getPrecioFinal());
    }

    @Test
    void test008_PaqueteConDescuentoConDosProductosConDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto2,producto2,0.10);
        assertEquals(2430, paquete1.getPrecioFinal());
    }

    // TESTS DE COMPOSICIÓN (PAQUETE DENTRO DE PAQUETE)

    @Test
    void test009_PaqueteSinDescuentoQueContieneProductoSinDescuentoYPaqueteSinDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto3,producto1);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto1,paquete1);
        assertEquals(2400, paquete2.getPrecioFinal());
    }

    @Test
    void test010_PaqueteSinDescuentoQueContieneProductoSinDescuentoYPaqueteConDescuentoInterno(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto3,producto1,0.10);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto1,paquete1);
        assertEquals(2240, paquete2.getPrecioFinal());
    }

    @Test
    void test011_PaqueteSinDescuentoQueContieneProductoConDescuentoYPaqueteSinDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto3,producto1);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto2,paquete1);
        assertEquals(2950, paquete2.getPrecioFinal());
    }

    @Test
    void test012_PaqueteSinDescuentoQueContieneProductoConDescuentoYPaqueteConDescuentoInterno(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto3,producto1,0.10);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto2,paquete1);
        assertEquals(2790, paquete2.getPrecioFinal());
    }

    @Test
    void test013_PaqueteSinDescuentoQueContieneProductoSinDescuentoYPaqueteConProductosMezclados(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto3,producto2);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto1,paquete1);
        assertEquals(2950, paquete2.getPrecioFinal());
    }

    @Test
    void test014_PaqueteSinDescuentoQueContieneProductoSinDescuentoYPaqueteConDosProductosConDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto2,producto2);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto1,paquete1);
        assertEquals(3500, paquete2.getPrecioFinal());
    }

    @Test
    void test015_PaqueteSinDescuentoQueContieneProductoConDescuentoYPaqueteConProductosMezclados(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto3,producto2);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto2,paquete1);
        assertEquals(3500, paquete2.getPrecioFinal());
    }

    @Test
    void test016_PaqueteSinDescuentoQueContieneProductoConDescuentoYPaqueteConDosProductosConDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto2,producto2);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto2,paquete1);
        assertEquals(4050, paquete2.getPrecioFinal());
    }


    @Test
    void test017_PaqueteConDescuentoQueContieneProductoSinDescuentoYPaqueteSinDescuento(){
        // paquete1 precio final = 800 + 800 = 1600
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto3,producto1);
        // paquete2 base = producto1 (800) + paquete1 (1600) = 2400. Con 10% desc = 2160
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto1,paquete1,0.10);
        assertEquals(2160, paquete2.getPrecioFinal());
    }

    @Test
    void test018_PaqueteConDescuentoQueContieneProductoConDescuentoYPaqueteSinDescuento(){
        // paquete1 precio final = 1600
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto3,producto1);
        // paquete2 base = producto2 (1350) + paquete1 (1600) = 2950. Con 10% desc = 2655
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto2,paquete1,0.10);
        assertEquals(2655, paquete2.getPrecioFinal());
    }

    @Test
    void test019_PaqueteConDescuentoQueContieneProductoConDescuentoYPaqueteConDescuentoInterno(){
        // paquete1 base = 800 + 800 = 1600. Con 10% desc = 1440
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto3,producto1,0.10);
        // paquete2 base = producto2 (1350) + paquete1 (1440) = 2790. Con 10% desc = 2511
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto2,paquete1,0.10);
        assertEquals(2511, paquete2.getPrecioFinal());
    }

    //  TESTS DE ATRIBUTOS EXTRA (METADATA)

    @Test
    void test020_ProductoPermiteAsignarYRecuperarUnAtributoExtra(){
        producto1.setAtributoExtra("Alto", 1.9);
        assertEquals(1.9, producto1.getAtributoExtra("Alto"));
    }

    @Test
    void test021_ProductoPermiteAsignarYRecuperarMultiplesAtributosExtras(){
        producto1.setAtributoExtra("Alto", 1.9);
        producto1.setAtributoExtra("Ancho", 1.9);
        producto1.setAtributoExtra("Peso", 5);
        assertEquals(1.9, producto1.getAtributoExtra("Alto"));
        assertEquals(1.9, producto1.getAtributoExtra("Ancho"));
        assertEquals(5, producto1.getAtributoExtra("Peso"));
    }
}
