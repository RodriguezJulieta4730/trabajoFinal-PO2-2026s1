import Clases.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static Clases.Categoria.Electronica;
import static Clases.Categoria.Indumentaria;
import static org.junit.jupiter.api.Assertions.*;

public class BusquedaEnElCatalogoTest {

        private Sucursal sucursal;
        private ProductoIndividual producto1;
        private ProductoIndividual producto2;
        private ProductoIndividual producto3;
        private ProductoIndividual producto4;
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

            producto4 = new ProductoIndividual(
                    "E0125", "Remera de Algodón", "Ropa cómoda color negro",
                    "Adidas",Indumentaria, 2000
            );

            paquete1 = new Paquete("Combo Samsung", "Celular + Cargador con descuento", producto1, producto2,Electronica);

            sucursal.agregarStock(producto1, 10);
            sucursal.agregarStock(producto3, 5);
            sucursal.agregarStock(producto2,1);
            sucursal.agregarStock(paquete1, 3);
            sucursal.getCatalogoDeProductos().add(producto4);
        }

    // CRITERIOS SIMPLES

    @Test
    public void test01_criterioSimple_PorNombre() {
        CriterioDeBusqueda criterioPorNombre = new CriterioPorNombre("cElUlAr");
        List<Producto> resultado = sucursal.filtrar(criterioPorNombre);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(paquete1));

        assertFalse(resultado.contains(producto2));
        assertFalse(resultado.contains(producto3));
        assertFalse(resultado.contains(producto4));
    }

    @Test
    public void test02_criterioSimple_PorPrecioMaximo() {
        CriterioDeBusqueda criterioPrecioMaximo = new CriterioPrecioMaximo(4000);
        List<Producto> resultado = sucursal.filtrar(criterioPrecioMaximo);

        assertEquals(3, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(producto4));

        assertFalse(resultado.contains(producto2));
        assertFalse(resultado.contains(paquete1));
    }

    @Test
    public void test03_criterioSimple_PorCategoria() {
        CriterioDeBusqueda criterioPorCategoria = new CriterioPorCategoria(Electronica);
        List<Producto> resultado = sucursal.filtrar(criterioPorCategoria);

        assertEquals(3, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto2));
        assertTrue(resultado.contains(paquete1));

        assertFalse(resultado.contains(producto3));
        assertFalse(resultado.contains(producto4));
    }

    @Test
    public void test04_criterioSimple_PorDisponibilidad() {
        CriterioDeBusqueda criterioPorDisponibilidad = new CriterioPorDisponibilidad(sucursal);
        List<Producto> resultado = sucursal.filtrar(criterioPorDisponibilidad);

        assertEquals(4, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto2));
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(paquete1));

        assertFalse(resultado.contains(producto4));
    }

    //NEGACIONES

    @Test
    public void test05_negacionCriterioNombre() {
        CriterioDeBusqueda criterioNegacion = new CriterioNegacion(new CriterioPorNombre("Samsung"));
        List<Producto> resultado = sucursal.filtrar(criterioNegacion);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(producto4));

        assertFalse(resultado.contains(producto1));
        assertFalse(resultado.contains(producto2));
        assertFalse(resultado.contains(paquete1));
    }

    @Test
    public void test06_negacion_NotPrecioMaximo() {
        CriterioDeBusqueda crit = new CriterioNegacion(new CriterioPrecioMaximo(2000));
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(3, resultado.size()); // producto2, producto3, paquete1
        assertFalse(resultado.contains(producto1));
    }

    @Test
    public void test07_negacion_NotCategoria() {
        CriterioDeBusqueda crit = new CriterioNegacion(new CriterioPorCategoria(Electronica));
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(producto4));
    }

    @Test
    public void test08_negacion_NotDisponibilidad() {
        CriterioDeBusqueda crit = new CriterioNegacion(new CriterioPorDisponibilidad(sucursal));
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(producto4));
    }

    // =========================================================================
    // PARTE 3: CONJUNCIONES BINARIAS (AND) - PERMUTACIÓN DE TODOS LOS CRITERIOS
    // =========================================================================

    @Test
    public void test09_and_Nombre_Y_Precio() {
        CriterioDeBusqueda crit = new CriteriosConjuncion(new CriterioPorNombre("Samsung"), new CriterioPrecioMaximo(2000));
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(producto1));
    }

    @Test
    public void test10_and_Nombre_Y_Categoria() {
        CriterioDeBusqueda crit = new CriteriosConjuncion(new CriterioPorNombre("Remera"), new CriterioPorCategoria(Indumentaria));
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(2, resultado.size());
    }

    @Test
    public void test11_and_Nombre_Y_Disponibilidad() {
        CriterioDeBusqueda crit = new CriteriosConjuncion(new CriterioPorNombre("Algodón"), new CriterioPorDisponibilidad(sucursal));
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(1, resultado.size()); // producto3 tiene stock, producto4 no
        assertTrue(resultado.contains(producto3));
    }

    @Test
    public void test12_and_Precio_Y_Categoria() {
        CriterioDeBusqueda crit = new CriteriosConjuncion(new CriterioPrecioMaximo(3000), new CriterioPorCategoria(Indumentaria));
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(2, resultado.size()); // producto3 y producto4
    }

    @Test
    public void test13_and_Precio_Y_Disponibilidad() {
        CriterioDeBusqueda crit = new CriteriosConjuncion(new CriterioPrecioMaximo(3000), new CriterioPorDisponibilidad(sucursal));
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(2, resultado.size()); // producto1 y producto3
    }

    @Test
    public void test14_and_Categoria_Y_Disponibilidad() {
        CriterioDeBusqueda crit = new CriteriosConjuncion(new CriterioPorCategoria(Electronica), new CriterioPorDisponibilidad(sucursal));
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(3, resultado.size()); // producto1, producto2, paquete1
    }

    // =========================================================================
    // PARTE 4: DISYUNCIONES BINARIAS (OR) - PERMUTACIÓN DE TODOS LOS CRITERIOS
    // =========================================================================

    @Test
    public void test15_or_Nombre_O_Precio() {
        CriterioDeBusqueda crit = new CriteriosDisyuncion(new CriterioPorNombre("Cargador"), new CriterioPrecioMaximo(2000));
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(3, resultado.size()); // producto1 (por precio), producto2 y paquete1 (por nombre)
    }

    @Test
    public void test16_or_Nombre_O_Categoria() {
        CriterioDeBusqueda crit = new CriteriosDisyuncion(new CriterioPorNombre("Celular"), new CriterioPorCategoria(Indumentaria));
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(4, resultado.size()); // producto1, producto3, producto4, paquete1
    }

    @Test
    public void test17_or_Nombre_O_Disponibilidad() {
        CriterioDeBusqueda crit = new CriteriosDisyuncion(new CriterioPorNombre("Blanco"), new CriterioPorDisponibilidad(sucursal));
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(5, resultado.size()); // El nombre trae a producto4, disponibilidad trae al resto
    }

    @Test
    public void test18_or_Precio_O_Categoria() {
        CriterioDeBusqueda crit = new CriteriosDisyuncion(new CriterioPrecioMaximo(2000), new CriterioPorCategoria(Indumentaria));
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(3, resultado.size()); // producto1, producto3, producto4
    }

    @Test
    public void test19_or_Precio_O_Disponibilidad() {
        CriterioDeBusqueda crit = new CriteriosDisyuncion(new CriterioPrecioMaximo(1000), new CriterioPorDisponibilidad(sucursal));
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(4, resultado.size()); // Entran los 4 con stock
    }

    @Test
    public void test20_or_Categoria_O_Disponibilidad() {
        CriterioDeBusqueda crit = new CriteriosDisyuncion(new CriterioPorCategoria(Indumentaria), new CriterioPorDisponibilidad(sucursal));
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(5, resultado.size()); // Entran todos (producto4 entra por categoría, el resto por stock)
    }

    // =========================================================================
    // PARTE 5: AND MÚLTIPLES (3 CONDICIONES) - TODAS LAS VARIACIONES DE CRITERIOS SIMPLES
    // =========================================================================

    @Test
    public void test21_andMultiple_Nombre_Precio_Categoria() {
        CriterioDeBusqueda crit = new CriteriosConjuncion(
                new CriteriosConjuncion(new CriterioPorNombre("Samsung"), new CriterioPrecioMaximo(6000)),
                new CriterioPorCategoria(Electronica)
        );
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(3, resultado.size()); // producto1, producto2, paquete1
    }

    @Test
    public void test22_andMultiple_Nombre_Precio_Disponibilidad() {
        CriterioDeBusqueda crit = new CriteriosConjuncion(
                new CriteriosConjuncion(new CriterioPorNombre("Algodón"), new CriterioPrecioMaximo(3000)),
                new CriterioPorDisponibilidad(sucursal)
        );
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(producto3));
    }

    @Test
    public void test23_andMultiple_Nombre_Categoria_Disponibilidad() {
        CriterioDeBusqueda crit = new CriteriosConjuncion(
                new CriteriosConjuncion(new CriterioPorNombre("Samsung"), new CriterioPorCategoria(Electronica)),
                new CriterioPorDisponibilidad(sucursal)
        );
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(3, resultado.size());
    }

    @Test
    public void test24_andMultiple_Precio_Categoria_Disponibilidad() {
        CriterioDeBusqueda crit = new CriteriosConjuncion(
                new CriteriosConjuncion(new CriterioPrecioMaximo(3000), new CriterioPorCategoria(Indumentaria)),
                new CriterioPorDisponibilidad(sucursal)
        );
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(producto3));
    }

    // =========================================================================
    // PARTE 6: OR MÚLTIPLES (3 CONDICIONES) - TODAS LAS VARIACIONES DE CRITERIOS SIMPLES
    // =========================================================================

    @Test
    public void test25_orMultiple_Nombre_Precio_Categoria() {
        CriterioDeBusqueda crit = new CriteriosDisyuncion(
                new CriteriosDisyuncion(new CriterioPorNombre("Rápido"), new CriterioPrecioMaximo(2000)),
                new CriterioPorCategoria(Indumentaria)
        );
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(4, resultado.size()); // producto1 (precio), producto2 (nombre), producto3 y producto4 (categoria)
    }

    @Test
    public void test26_orMultiple_Nombre_Precio_Disponibilidad() {
        CriterioDeBusqueda crit = new CriteriosDisyuncion(
                new CriteriosDisyuncion(new CriterioPorNombre("Blanco"), new CriterioPrecioMaximo(1000)),
                new CriterioPorDisponibilidad(sucursal)
        );
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(5, resultado.size()); // producto4 (nombre), el resto (disponibilidad)
    }

    @Test
    public void test27_orMultiple_Nombre_Categoria_Disponibilidad() {
        CriterioDeBusqueda crit = new CriteriosDisyuncion(
                new CriteriosDisyuncion(new CriterioPorNombre("Negro"), new CriterioPorCategoria(Electronica)),
                new CriterioPorDisponibilidad(sucursal)
        );
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(4, resultado.size()); // Todos excepto producto4
    }

    @Test
    public void test28_orMultiple_Precio_Categoria_Disponibilidad() {
        CriterioDeBusqueda crit = new CriteriosDisyuncion(
                new CriteriosDisyuncion(new CriterioPrecioMaximo(1000), new CriterioPorCategoria(Indumentaria)),
                new CriterioPorDisponibilidad(sucursal)
        );
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(5, resultado.size());
    }

    // =========================================================================
    // PARTE 7: COMBINACIONES COMPUESTAS AVANZADAS (AND + OR + NOT ANIDADOS CRUZADOS)
    // =========================================================================

    @Test
    public void test29_compuesto_AndAnidadoConOr_VariacionA() {
        // (Nombre O Precio) Y Categoría
        CriterioDeBusqueda crit = new CriteriosConjuncion(
                new CriteriosDisyuncion(new CriterioPorNombre("Rápido"), new CriterioPrecioMaximo(2000)),
                new CriterioPorCategoria(Electronica)
        );
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(2, resultado.size()); // producto1 (precio y electronica) y producto2 (nombre y electronica)
        assertFalse(resultado.contains(producto3));
    }

    @Test
    public void test30_compuesto_AndAnidadoConOr_VariacionB() {
        // (Categoria O Disponibilidad) Y Precio
        CriterioDeBusqueda crit = new CriteriosConjuncion(
                new CriteriosDisyuncion(new CriterioPorCategoria(Indumentaria), new CriterioPorDisponibilidad(sucursal)),
                new CriterioPrecioMaximo(3000)
        );
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(3, resultado.size()); // producto1, producto3, producto4
    }

    @Test
    public void test31_compuesto_OrAnidadoConAnd_VariacionA() {
        // (Nombre Y Categoría) O Disponibilidad
        CriterioDeBusqueda crit = new CriteriosDisyuncion(
                new CriteriosConjuncion(new CriterioPorNombre("Blanco"), new CriterioPorCategoria(Indumentaria)),
                new CriterioPorDisponibilidad(sucursal)
        );
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(5, resultado.size()); // El AND da a producto4, la disponibilidad da al resto
    }

    @Test
    public void test32_compuesto_OrAnidadoConAnd_VariacionB() {
        // (Precio Y Disponibilidad) O Categoria
        CriterioDeBusqueda crit = new CriteriosDisyuncion(
                new CriteriosConjuncion(new CriterioPrecioMaximo(2000), new CriterioPorDisponibilidad(sucursal)),
                new CriterioPorCategoria(Indumentaria)
        );
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(3, resultado.size()); // producto1 (por AND), producto3 y producto4 (por OR categoria)
    }

    @Test
    public void test33_compuesto_AlgebraBooleanaCompleta_VariacionA() {
        // (NOT Nombre Y Categoria) O NOT Disponibilidad
        CriterioDeBusqueda crit = new CriteriosDisyuncion(
                new CriteriosConjuncion(new CriterioNegacion(new CriterioPorNombre("Samsung")), new CriterioPorCategoria(Indumentaria)),
                new CriterioNegacion(new CriterioPorDisponibilidad(sucursal))
        );
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(2, resultado.size()); // producto3 (por AND), producto4 (por NOT disponibilidad y AND)
    }

    @Test
    public void test34_compuesto_AlgebraBooleanaCompleta_VariacionB() {
        // (NOT Precio O NOT Categoria) Y Disponibilidad
        CriterioDeBusqueda crit = new CriteriosConjuncion(
                new CriteriosDisyuncion(new CriterioNegacion(new CriterioPrecioMaximo(4000)), new CriterioNegacion(new CriterioPorCategoria(Electronica))),
                new CriterioPorDisponibilidad(sucursal)
        );
        List<Producto> resultado = sucursal.filtrar(crit);
        assertEquals(2, resultado.size()); // producto2 (precio > 4000 y stock), producto3 (no electronica y stock)
    }

}

