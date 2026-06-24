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
         producto2 = new ProductoIndividual("E1235", "Funda Protector","una descripcion", "Samsung", Electronica, 1500,0.10);
         producto3 = new ProductoIndividual("E0126", "Cable USB-C","una descripcion", "Samsung", Electronica, 800);
    }

    @Test
    void test001_Producto(){
        assertEquals("Cable USB-C", producto1.getNombre());
        assertEquals("Samsung", producto1.getMarca());
        assertEquals(Electronica, producto1.getCategoria());
        assertEquals(800, producto1.getPrecioBase());
        assertEquals(800, producto1.getPrecioFinal());
    }

    @Test
    void test002_ProductoConDescuento(){
        assertEquals("Cable USB-C", producto1.getNombre());
        assertEquals("Samsung", producto1.getMarca());
        assertEquals(Electronica, producto1.getCategoria());
        assertEquals(1500, producto2.getPrecioBase());
        assertEquals(1350, producto2.getPrecioFinal());
    }
    @Test
    void test004_PaqueteConDosProductosSinDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto1,producto3);
        assertEquals(1600,paquete1.getPrecioFinal());
    }

    @Test
    void test004_PaqueteConDosProductosConDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto2,producto2);
        assertEquals(2700,paquete1.getPrecioFinal());
    }
    @Test
    void test003_PaqueteSinDescuentoQueContieneUnProductoConDescuentoYOtroSinDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto1,producto2);
        assertEquals(2150,paquete1.getPrecioFinal());
    }
    @Test
    void test003_PaqueteConDescuentoQueContieneUnProductoConDescuentoYOtroSinDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto1,producto2,0.10);
        assertEquals(1935,paquete1.getPrecioFinal());
    }

    @Test
    void test003_PaqueteConDescuentoQueContieneDosProductosSinDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto1,producto3,0.10);
        assertEquals(1440,paquete1.getPrecioFinal());
    }
    @Test
    void test003_PaqueteConDescuentoQueContieneDosProductosConDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto2,producto2,0.10);
        assertEquals(2430,paquete1.getPrecioFinal());
    }
    @Test
    void test003_PaqueteConDescuentoQueContieneUnProductosConDescuentoYoOtroSinDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto1,producto2,0.10);
        assertEquals(1935,paquete1.getPrecioFinal());
    }

    @Test
    void test005_PaqueteConUnProductoYUnPaqueteSinDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto3,producto1);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto1,paquete1);
        assertEquals(2400,paquete2.getPrecioFinal());
    }

    @Test
    void test006_PaqueteQueContieneUnProductoSinDescuentoYUnPaqueteConUnProductoConDescuentoYOtroSinDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto3,producto2);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto1,paquete1);
        assertEquals(2950,paquete2.getPrecioFinal());
    }

    @Test
    void test007_PaqueteConUnProductoSinDescuentoYUnPaqueteConDosProductosConDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto2,producto2);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto1,paquete1);
        assertEquals(3500,paquete2.getPrecioFinal());
    }
    @Test
    void test005_PaqueteConUnProductoConDescuentoYUnPaqueteConProductosSinDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto3,producto1);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto2,paquete1);
        assertEquals(2950,paquete2.getPrecioFinal());
    }

    @Test
    void test0060_PaqueteQueContieneUnProductoConDescuentoYUnPaqueteConUnProductoConDescuentoYOtroSinDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto3,producto2);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto2,paquete1);
        assertEquals(3500,paquete2.getPrecioFinal());
    }

    @Test
    void test0007_PaqueteConUnProductoConDescuentoYUnPaqueteConDosProductosConDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto2,producto2);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto2,paquete1);
        assertEquals(4050,paquete2.getPrecioFinal());
    }
    @Test
    void test005_PaqueteConUnProductoSinDescuentoYUnPaqueteConDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto3,producto1,0.10);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto1,paquete1);
        assertEquals(2240,paquete2.getPrecioFinal());
    }
    @Test
    void test005_PaqueteConUnProductoConDescuentoYUnPaqueteConDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto3,producto1,0.10);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto2,paquete1);
        assertEquals(2790,paquete2.getPrecioFinal());
    }

    @Test
    void test00060_PaqueteQueContieneUnProductoConDescuentoYUnPaqueteConUnProductoConDescuentoYOtroSinDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto3,producto2);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto2,paquete1);
        assertEquals(3500,paquete2.getPrecioFinal());
    }

    @Test
    void test00007_PaqueteConUnProductoConDescuentoYUnPaqueteConDosProductosConDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto2,producto2);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto2,paquete1);
        assertEquals(4050,paquete2.getPrecioFinal());
    }
    //AGREGAR TEST PAQUETES CON PAQUETES SIN DESCUENTOS
    //Y REVISAR TODOS

    @Test
    void test007_ProductoConAtributosExtra(){
        producto1.setAtributoExtra("Alto", 1.9);
        assertEquals(1.9, producto1.getAtributoExtra("Alto"));
    }

    @Test
    void test008_ProductoConAtributosExtras(){
        producto1.setAtributoExtra("Alto", 1.9);
        producto1.setAtributoExtra("Ancho", 1.9);
        producto1.setAtributoExtra("Peso", 5);
        assertEquals(1.9, producto1.getAtributoExtra("Alto"));
        assertEquals(1.9, producto1.getAtributoExtra("Ancho"));
        assertEquals(5, producto1.getAtributoExtra("Peso"));
    }
}
