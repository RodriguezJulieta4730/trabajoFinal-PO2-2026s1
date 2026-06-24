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
         producto2 = new ProductoIndividual("E1235", "Funda Protector","una descripcion", "Samsung", Electronica, 1500,0.15);
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
        assertEquals(1275, producto2.getPrecioFinal());
    }

    @Test
    void test003_PaquetoConDosProductosConDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto1,producto2,0.15);
        assertEquals(1763.75,paquete1.getPrecioFinal());
    }

    @Test
    void test004_PaquetoConDosProductosSinDescuento(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto1,producto2);
        assertEquals(2075,paquete1.getPrecioFinal());
    }

    @Test
    void test005_PaquetoConUnProductoYUnPaquete(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto3,producto2,0.15);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto1,paquete1, 0.15);
        assertEquals(2179.1875,paquete2.getPrecioFinal());
    }

    @Test
    void test006_PaquetoConUnProductoYUnPaquete(){
        Producto paquete1 = new Paquete("CableYFunda","una Descripcion",producto3,producto2);
        Producto paquete2 = new Paquete("DosCablesYFunda","una Descripcion",producto1,paquete1, 0.10);
        assertEquals(2587.5,paquete2.getPrecioFinal());
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
