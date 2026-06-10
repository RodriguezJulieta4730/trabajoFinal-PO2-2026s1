import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CatalogoDeProductosTest {
    @Test
    void test001_Producto(){
        Producto producto1 = new Producto("E0123", "Cable USB-C", "Samsung", "Electrodomestico", 800);

        assertEquals("Cable USB-C", producto1.getNombre());
        assertEquals("Samsung", producto1.getMarca());
        assertEquals("Electrodomestico", producto1.getCategoria());
        assertEquals(800, producto1.getPrecioBase());
        assertEquals(800, producto1.getPrecioFinal());
    }

    @Test
    void test002_ProductoConDescuento(){
        Producto producto1 = new Producto("E0123", "Cable USB-C", "Samsung", "Electrodomestico",800,0.15);

        assertEquals("Cable USB-C", producto1.getNombre());
        assertEquals("Samsung", producto1.getMarca());
        assertEquals("Electrodomestico", producto1.getCategoria());
        assertEquals(800, producto1.getPrecioBase());
        assertEquals(680, producto1.getPrecioFinal());
    }

    @Test
    void test003_PaquetoConDosProductosConDescuento(){
        CatalogoDeProductos producto1 = new Producto("E0123", "Cable USB-C", "Samsung", "Electrodomestico", 800);
        CatalogoDeProductos producto2 = new Producto("E1235", "Funda Protector", "Samsung", "Electrodomestico", 1500);

        CatalogoDeProductos paquete1 = producto1.compose(producto2,0.15);
        assertEquals(1955,paquete1.getPrecioFinal());
    }

    @Test
    void test004_PaquetoConDosProductosSinDescuento(){
        CatalogoDeProductos producto1 = new Producto("E0123", "Cable USB-C", "Samsung", "Electrodomestico", 800);
        CatalogoDeProductos producto2 = new Producto("E1235", "Funda Protector", "Samsung", "Electrodomestico", 1500);
        CatalogoDeProductos paquete1 = producto1.compose(producto2);
        assertEquals(2300,paquete1.getPrecioFinal());
    }

    @Test
    void test005_PaquetoConUnProductoYUnPaquete(){
        CatalogoDeProductos producto1 = new Producto("E0123", "Cable USB-C", "Samsung", "Electrodomestico", 800);
        CatalogoDeProductos producto2 = new Producto("E1235", "Funda Protector", "Samsung", "Electrodomestico", 1500);
        CatalogoDeProductos producto3 = new Producto("E0126", "Cable USB-C", "Samsung", "Electrodomestico", 800);

        CatalogoDeProductos paquete1 = producto2.compose(producto3, 0.15);
        CatalogoDeProductos paquete2 = producto1.compose(paquete1, 0.15);
        assertEquals(2300*0.85,paquete1.getPrecioFinal());
    }

    @Test
    void test006_ProductoConAtributosExtra(){
        Producto producto1 = new Producto("E0123", "Cable USB-C", "Samsung", "Electrodomestico", 800);
        producto1.setAtributoExtra("Alto", 1.9);
        assertEquals(1.9, producto1.getAtributoExtra("Alto"));
    }

    @Test
    void test007_ProductoConAtributosExtras(){
        Producto producto1 = new Producto("E0123", "Cable USB-C", "Samsung", "Electrodomestico", 800);
        producto1.setAtributoExtra("Alto", 1.9);
        producto1.setAtributoExtra("Ancho", 1.9);
        producto1.setAtributoExtra("Peso", 5);
        assertEquals(1.9, producto1.getAtributoExtra("Alto"));
        assertEquals(1.9, producto1.getAtributoExtra("Ancho"));
        assertEquals(5, producto1.getAtributoExtra("Peso"));
    }
}
