import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CatalogoDeProductosTest {
    Producto producto1;
    CatalogoDeProductos producto2;
    CatalogoDeProductos producto3;

    @BeforeEach
    void setUp(){
         producto1 = new Producto("E0123", "Cable USB-C","una descripcion", "Samsung", "Electrodomestico", 800);
         producto2 = new Producto("E1235", "Funda Protector","una descripcion", "Samsung", "Electrodomestico", 1500,0.15);
         producto3 = new Producto("E0126", "Cable USB-C","una descripcion", "Samsung", "Electrodomestico", 800);
    }

    @Test
    void test001_Producto(){

        assertEquals("Cable USB-C", producto1.getNombre());
        assertEquals("Samsung", producto1.getMarca());
        assertEquals("Electrodomestico", producto1.getCategoria());
        assertEquals(800, producto1.getPrecioBase());
        assertEquals(800, producto1.getPrecioFinal());
    }

    @Test
    void test002_ProductoConDescuento(){
        assertEquals("Cable USB-C", producto1.getNombre());
        assertEquals("Samsung", producto1.getMarca());
        assertEquals("Electrodomestico", producto1.getCategoria());
        assertEquals(1500, producto2.getPrecioBase());
        assertEquals(1275, producto2.getPrecioFinal());
    }

    @Test
    void test003_PaquetoConDosProductosConDescuento(){
        CatalogoDeProductos paquete1 = producto1.compose("CableYFunda","una Descripcion",producto2,0.15);
        assertEquals(1763.75,paquete1.getPrecioFinal());
    }

    @Test
    void test004_PaquetoConDosProductosSinDescuento(){
        CatalogoDeProductos paquete1 = producto1.compose("CableYFunda","una Descripcion",producto2);
        assertEquals(2075,paquete1.getPrecioFinal());
    }

    @Test
    void test005_PaquetoConUnProductoYUnPaquete(){
        CatalogoDeProductos paquete1 = producto2.compose("CableYFunda","una Descripcion", producto3, 0.15);
        CatalogoDeProductos paquete2 = producto1.compose("DosCablesYFunda","una Descripcion",paquete1, 0.15);
        assertEquals(2179.1875,paquete2.getPrecioFinal());
    }

    @Test
    void test006_ProductoConAtributosExtra(){
        producto1.setAtributoExtra("Alto", 1.9);
        assertEquals(1.9, producto1.getAtributoExtra("Alto"));
    }

    @Test
    void test007_ProductoConAtributosExtras(){
        producto1.setAtributoExtra("Alto", 1.9);
        producto1.setAtributoExtra("Ancho", 1.9);
        producto1.setAtributoExtra("Peso", 5);
        assertEquals(1.9, producto1.getAtributoExtra("Alto"));
        assertEquals(1.9, producto1.getAtributoExtra("Ancho"));
        assertEquals(5, producto1.getAtributoExtra("Peso"));
    }
}
