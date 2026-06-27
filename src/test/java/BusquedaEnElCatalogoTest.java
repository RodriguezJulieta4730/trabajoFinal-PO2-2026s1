import Clases.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static Clases.Categoria.Electronica;
import static Clases.Categoria.Indumentaria;
import static org.junit.jupiter.api.Assertions.*;

public class BusquedaEnElCatalogoTest {

        private Sucursal sucursal;
        private ProductoIndividual producto1;
        private ProductoIndividual producto2;
        private ProductoIndividual producto3;
        private Paquete paquete1;

        @BeforeEach
        public void setUp() {
            sucursal = new Sucursal();

            producto1 = new ProductoIndividual(
                    "E0123", "Celular Samsung ", "Celular con buena cámara",
                    "Samsung",Electronica, 1500
            );
            producto2 = new ProductoIndividual(
                    "E0128", "Cargador Rápido", "Cargador Samsung tipo C",
                    "Samsung", Electronica, 5000
            );

            producto3 = new ProductoIndividual(
                    "E0124", "Remera de Algodón", "Ropa cómoda color negro",
                    "Adidas",Indumentaria, 2500
            );

            paquete1 = new Paquete("Combo Samsung", "Celular + Cargador con descuento", producto1, producto2,Electronica);

            sucursal.agregarStock(producto1, 10);
            sucursal.agregarStock(producto3, 5);
            sucursal.agregarStock(producto2,1);
            sucursal.agregarStock(paquete1, 3);
        }

        @Test
        public void test01_seFiltraPorNombreDeProductosQueCoincidenIgnorandoMayusculas() {

            Map<Producto,Integer> resultado = sucursal.filtrarPorNombre("cElUlAr");

            assertEquals(2, resultado.size());

            assertTrue(resultado.containsKey(producto1));
            assertTrue(resultado.containsKey(paquete1));

            assertFalse(resultado.containsKey(producto3));
            assertFalse(resultado.containsKey(producto2));
        }

//        @Test
//        public void test02_seFiltraPorMayorPrecio(){
//            Map<Producto,Integer> resultado = sucursal.filtrarPorPrecioMaximo(4000);
//
//            assertEquals(2,resultado.size());
//
//            assertTrue(resultado.containsKey(producto1));
//            assertTrue(resultado.containsKey(producto2));
//
//            assertFalse(resultado.containsKey(producto3));
//            assertFalse(resultado.containsKey(paquete1));
//        }

    @Test
    public void test03_seFiltraPorCategoriaDeProductos() {

        Map<Producto,Integer> resultado = sucursal.filtrarPorCategoria(Electronica);

        assertEquals(3, resultado.size());

        assertTrue(resultado.containsKey(producto1));
        assertTrue(resultado.containsKey(producto2));
        assertTrue(resultado.containsKey(paquete1));

        assertFalse(resultado.containsKey(producto3));
    }

//    @Test
//    public void test04_seFiltraPorDisponibilidadDelProducto(){
//        Map<Producto,Integer> resultado = sucursal.filtrarPorDisponibilidad(producto1);
//
//        assertEquals(3, resultado.size());
//
//    }

}

