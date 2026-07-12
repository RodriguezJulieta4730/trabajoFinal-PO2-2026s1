import BusquedaEnElCatalogo.*;
import Clases.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static Clases.Categoria.Electronica;
import static Clases.Categoria.Indumentaria;
import static org.junit.jupiter.api.Assertions.*;

public class BusquedaEnElCatalogoTest {
    private UNQShop tienda;
    private Sucursal sucursal;
    private ProductoIndividual producto1;
    private ProductoIndividual producto2;
    private ProductoIndividual producto3;
    private ProductoIndividual producto4;
    private Paquete paquete1;

    @BeforeEach
    public void setUp() {
        tienda = new UNQShop();
        sucursal = new Sucursal(tienda,"Roque Sáenz Peña 352");
        tienda.registrarSucursal(sucursal);

        producto1 = new ProductoIndividual(
                "E0123", "Celular Samsung ", "Celular con buena cámara",
                "Samsung", Electronica, 1500
        );
        producto2 = new ProductoIndividual(
                "E0128", "Cargador Rápido", "Cargador Samsung tipo C",
                "Samsung", Electronica, 5000
        );

        producto3 = new ProductoIndividual(
                "E0124", "Remera de Algodón", "Ropa cómoda color negro",
                "Adidas", Indumentaria, 2500
        );

        producto4 = new ProductoIndividual(
                "E0125", "Remera de Algodón", "Ropa cómoda color negro",
                "Adidas", Indumentaria, 2000
        );

        paquete1 = new Paquete("Combo Samsung", "Celular + Cargador con descuento", Electronica, Map.of(producto1,1, producto2,1));

        sucursal.agregarStock(producto1, 10);
        sucursal.agregarStock(producto3, 5);
        sucursal.agregarStock(producto2, 1);
        sucursal.agregarStock(paquete1, 3);
        tienda.getCatalogoDeProductos().add(producto4);
    }

    // CRITERIOS SIMPLES

    @Test
    public void test01_criterioSimple_PorNombre() {
        CriterioDeBusqueda criterioPorNombre = new CriterioPorNombre("cElUlAr");
        List<Producto> resultado = tienda.filtrar(criterioPorNombre);

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
        List<Producto> resultado = tienda.filtrar(criterioPrecioMaximo);

        assertEquals(3, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(producto4));

        assertFalse(resultado.contains(producto2));
        assertFalse(resultado.contains(paquete1));
    }

    @Test
    public void test03_filtroPorCategoria() {
        CriterioDeBusqueda criterioPorCategoria = new CriterioPorCategoria(Electronica);
        List<Producto> resultado = tienda.filtrar(criterioPorCategoria);

        assertEquals(3, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto2));
        assertTrue(resultado.contains(paquete1));

        assertFalse(resultado.contains(producto3));
        assertFalse(resultado.contains(producto4));
    }

    @Test
    public void test04_filtroPorDisponibilidad() {
        CriterioDeBusqueda criterioPorDisponibilidad = new CriterioPorDisponibilidad(tienda);
        List<Producto> resultado = tienda.filtrar(criterioPorDisponibilidad);

        assertEquals(4, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto2));
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(paquete1));

        assertFalse(resultado.contains(producto4));
    }

    // NEGACIONES

    @Test
    public void test05_filtroNegacionDeFiltroNombre() {
        CriterioDeBusqueda criterioNombre = new CriterioPorNombre("Samsung");
        CriterioDeBusqueda criterioNegacionNombre = new CriterioNegacion(criterioNombre);
        List<Producto> resultado = tienda.filtrar(criterioNegacionNombre);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(producto4));

        assertFalse(resultado.contains(producto1));
        assertFalse(resultado.contains(producto2));
        assertFalse(resultado.contains(paquete1));
    }

    @Test
    public void test06_filtroNegacionDeFiltroPrecioMaximo() {
        CriterioDeBusqueda criterioPrecio = new CriterioPrecioMaximo(2000);
        CriterioDeBusqueda criterioNegacionPrecio = new CriterioNegacion(criterioPrecio);
        List<Producto> resultado = tienda.filtrar(criterioNegacionPrecio);

        assertEquals(3, resultado.size());
        assertTrue(resultado.contains(producto2));
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(paquete1));

        assertFalse(resultado.contains(producto1));
        assertFalse(resultado.contains(producto4));
    }

    @Test
    public void test07_filtroNegacionConNegacionDeFiltroCategoria() {
        CriterioDeBusqueda criterioCategoria = new CriterioPorCategoria(Electronica);
        CriterioDeBusqueda criterioNegacionCategoria = new CriterioNegacion(criterioCategoria);
        List<Producto> resultado = tienda.filtrar(criterioNegacionCategoria);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(producto4));

        assertFalse(resultado.contains(producto1));
        assertFalse(resultado.contains(producto2));
        assertFalse(resultado.contains(paquete1));
    }

    @Test
    public void test08_filtroNegacionConFiltroNegacionDeFiltroDisponibilidad() {
        CriterioDeBusqueda criterioDisponibilidad = new CriterioPorDisponibilidad(tienda);
        CriterioDeBusqueda criterioNegacionDisponibilidad = new CriterioNegacion(criterioDisponibilidad);
        List<Producto> resultado = tienda.filtrar(criterioNegacionDisponibilidad);

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(producto4));

        assertFalse(resultado.contains(producto1));
        assertFalse(resultado.contains(producto2));
        assertFalse(resultado.contains(producto3));
        assertFalse(resultado.contains(paquete1));
    }

    // CRITERIOS COMPUESTOS CONJUNCIONES 2 CRITERIOS SIMPLES

    @Test
    public void test09_filtroConjuncionConFiltrosNombreYPrecioMaximo() {
        CriterioDeBusqueda criterioNombre = new CriterioPorNombre("Samsung");
        CriterioDeBusqueda criterioPrecio = new CriterioPrecioMaximo(2000);
        CriterioDeBusqueda criterioNombreYPrecio = new CriteriosConjuncion(criterioNombre, criterioPrecio);
        List<Producto> resultado = tienda.filtrar(criterioNombreYPrecio);

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(producto1));

        assertFalse(resultado.contains(producto2));
        assertFalse(resultado.contains(producto3));
        assertFalse(resultado.contains(producto4));
        assertFalse(resultado.contains(paquete1));
    }

    @Test
    public void test10_filtroConjuncionConFiltrosNombreYCategoria() {
        CriterioDeBusqueda criterioNombre = new CriterioPorNombre("Remera");
        CriterioDeBusqueda criterioCategoria = new CriterioPorCategoria(Indumentaria);
        CriterioDeBusqueda criterioNombreYCategoria = new CriteriosConjuncion(criterioNombre, criterioCategoria);
        List<Producto> resultado = tienda.filtrar(criterioNombreYCategoria);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(producto4));

        assertFalse(resultado.contains(producto1));
        assertFalse(resultado.contains(producto2));
        assertFalse(resultado.contains(paquete1));
    }

    @Test
    public void test11_filtroConjuncionConFiltrosNombreYDisponibilidad() {
        CriterioDeBusqueda criterioNombre = new CriterioPorNombre("Algodón");
        CriterioDeBusqueda criterioDisponibilidad = new CriterioPorDisponibilidad(tienda);
        CriterioDeBusqueda criterioNombreYDisponibilidad = new CriteriosConjuncion(criterioNombre, criterioDisponibilidad);
        List<Producto> resultado = tienda.filtrar(criterioNombreYDisponibilidad);

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(producto3));

        assertFalse(resultado.contains(producto1));
        assertFalse(resultado.contains(producto2));
        assertFalse(resultado.contains(producto4));
        assertFalse(resultado.contains(paquete1));
    }

    @Test
    public void test12_filtroConjuncionConFiltrosPrecioYCategoria() {
        CriterioDeBusqueda criterioPrecio = new CriterioPrecioMaximo(3000);
        CriterioDeBusqueda criterioCategoria = new CriterioPorCategoria(Indumentaria);
        CriterioDeBusqueda criterioPrecioYCategoria = new CriteriosConjuncion(criterioPrecio, criterioCategoria);
        List<Producto> resultado = tienda.filtrar(criterioPrecioYCategoria);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(producto4));

        assertFalse(resultado.contains(producto1));
        assertFalse(resultado.contains(producto2));
        assertFalse(resultado.contains(paquete1));
    }

    @Test
    public void test13_filtroConjuncionConFiltrosPrecioYDisponibilidad() {
        CriterioDeBusqueda criterioPrecio = new CriterioPrecioMaximo(3000);
        CriterioDeBusqueda criterioDisponibilidad = new CriterioPorDisponibilidad(tienda);
        CriterioDeBusqueda criterioPrecioYDisponibilidad = new CriteriosConjuncion(criterioPrecio, criterioDisponibilidad);
        List<Producto> resultado = tienda.filtrar(criterioPrecioYDisponibilidad);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto3));

        assertFalse(resultado.contains(producto2));
        assertFalse(resultado.contains(producto4));
        assertFalse(resultado.contains(paquete1));
    }

    @Test
    public void test14_filtroConjuncionConFiltrosCategoriaYDisponibilidad() {
        CriterioDeBusqueda criterioCategoria = new CriterioPorCategoria(Electronica);
        CriterioDeBusqueda criterioDisponibilidad = new CriterioPorDisponibilidad(tienda);
        CriterioDeBusqueda criterioCategoriaYDisponibilidad = new CriteriosConjuncion(criterioCategoria, criterioDisponibilidad);
        List<Producto> resultado = tienda.filtrar(criterioCategoriaYDisponibilidad);

        assertEquals(3, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto2));
        assertTrue(resultado.contains(paquete1));

        assertFalse(resultado.contains(producto3));
        assertFalse(resultado.contains(producto4));
    }

    // CRITERIOS COMPUESTOS DISYUNCIONES 2 CRITERIOS SIMPLES

    @Test
    public void test15_filtroDisyuncionConFiltrosNombreOPrecio() {
        CriterioDeBusqueda criterioNombre = new CriterioPorNombre("Cargador");
        CriterioDeBusqueda criterioPrecio = new CriterioPrecioMaximo(2000);
        CriterioDeBusqueda criterioNombreOPrecio = new CriteriosDisyuncion(criterioNombre, criterioPrecio);
        List<Producto> resultado = tienda.filtrar(criterioNombreOPrecio);

        assertEquals(4, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto2));
        assertTrue(resultado.contains(producto4));
        assertTrue(resultado.contains(paquete1));

        assertFalse(resultado.contains(producto3));

    }

    @Test
    public void test16_filtroDisyuncionConFiltrosNombreOCategoria() {
        CriterioDeBusqueda criterioNombre = new CriterioPorNombre("Celular");
        CriterioDeBusqueda criterioCategoria = new CriterioPorCategoria(Indumentaria);
        CriterioDeBusqueda criterioNombreOCategoria = new CriteriosDisyuncion(criterioNombre, criterioCategoria);
        List<Producto> resultado = tienda.filtrar(criterioNombreOCategoria);

        assertEquals(4, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(producto4));
        assertTrue(resultado.contains(paquete1));

        assertFalse(resultado.contains(producto2));
    }

    @Test
    public void test17_filtroDisyuncionConFiltrosNombreODisponibilidad() {
        CriterioDeBusqueda criterioNombre = new CriterioPorNombre("Blanco");
        CriterioDeBusqueda criterioDisponibilidad = new CriterioPorDisponibilidad(tienda);
        CriterioDeBusqueda criterioNombreODisponibilidad = new CriteriosDisyuncion(criterioNombre, criterioDisponibilidad);
        List<Producto> resultado = tienda.filtrar(criterioNombreODisponibilidad);

        assertEquals(4, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto2));
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(paquete1));

        assertFalse(resultado.contains(producto4));
    }

    @Test
    public void test18_filtroDisyuncionConFiltrosPrecioOCategoria() {
        CriterioDeBusqueda criterioPrecio = new CriterioPrecioMaximo(2000);
        CriterioDeBusqueda criterioCategoria = new CriterioPorCategoria(Indumentaria);
        CriterioDeBusqueda criterioPrecioOCategoria = new CriteriosDisyuncion(criterioPrecio, criterioCategoria);
        List<Producto> resultado = tienda.filtrar(criterioPrecioOCategoria);

        assertEquals(3, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(producto4));

        assertFalse(resultado.contains(producto2));
        assertFalse(resultado.contains(paquete1));
    }

    @Test
    public void test19_filtroDisyuncionConFiltrosPrecioODisponibilidad() {
        CriterioDeBusqueda criterioPrecio = new CriterioPrecioMaximo(1000);
        CriterioDeBusqueda criterioDisponibilidad = new CriterioPorDisponibilidad(tienda);
        CriterioDeBusqueda criterioPrecioODisponibilidad = new CriteriosDisyuncion(criterioPrecio, criterioDisponibilidad);
        List<Producto> resultado = tienda.filtrar(criterioPrecioODisponibilidad);

        assertEquals(4, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto2));
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(paquete1));

        assertFalse(resultado.contains(producto4));
    }

    @Test
    public void test20_filtroDisyuncionConfiltrosCategoriaODisponibilidad() {
        CriterioDeBusqueda criterioCategoria = new CriterioPorCategoria(Indumentaria);
        CriterioDeBusqueda criterioDisponibilidad = new CriterioPorDisponibilidad(tienda);
        CriterioDeBusqueda criterioCategoriaODisponibilidad = new CriteriosDisyuncion(criterioCategoria, criterioDisponibilidad);
        List<Producto> resultado = tienda.filtrar(criterioCategoriaODisponibilidad);

        assertEquals(5, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto2));
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(producto4));
        assertTrue(resultado.contains(paquete1));
    }

    // CRITERIOS COMPUESTOS CONJUNCION 3 CONDICIONES

    @Test
    public void test21_filtroConjuncionConFiltrosMultiple_Nombre_Precio_Categoria() {
        CriterioDeBusqueda criterioNombre = new CriterioPorNombre("Samsung");
        CriterioDeBusqueda criterioPrecio = new CriterioPrecioMaximo(8000);
        CriterioDeBusqueda criterioCategoria = new CriterioPorCategoria(Electronica);

        CriterioDeBusqueda criterioNombreYPrecio = new CriteriosConjuncion(criterioNombre, criterioPrecio);
        CriterioDeBusqueda criterioAndMultipleNombrePrecioCategoria = new CriteriosConjuncion(criterioNombreYPrecio, criterioCategoria);

        List<Producto> resultado = tienda.filtrar(criterioAndMultipleNombrePrecioCategoria);

        assertEquals(3, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto2));
        assertTrue(resultado.contains(paquete1));

        assertFalse(resultado.contains(producto3));
        assertFalse(resultado.contains(producto4));
    }

    @Test
    public void test22_filtroConjuncionConFiltrosNombreYPrecioYDisponibilidad() {
        CriterioDeBusqueda criterioNombre = new CriterioPorNombre("Algodón");
        CriterioDeBusqueda criterioPrecio = new CriterioPrecioMaximo(3000);
        CriterioDeBusqueda criterioDisponibilidad = new CriterioPorDisponibilidad(tienda);

        CriterioDeBusqueda criterioNombreYPrecio = new CriteriosConjuncion(criterioNombre, criterioPrecio);
        CriterioDeBusqueda criterioAndMultipleNombrePrecioDisponibilidad = new CriteriosConjuncion(criterioNombreYPrecio, criterioDisponibilidad);

        List<Producto> resultado = tienda.filtrar(criterioAndMultipleNombrePrecioDisponibilidad);

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(producto3));

        assertFalse(resultado.contains(producto1));
        assertFalse(resultado.contains(producto2));
        assertFalse(resultado.contains(producto4));
        assertFalse(resultado.contains(paquete1));
    }

    @Test
    public void test23_filtroConjuncionConFiltrosNombreYCategoriaYDisponibilidad() {
        CriterioDeBusqueda criterioNombre = new CriterioPorNombre("Samsung");
        CriterioDeBusqueda criterioCategoria = new CriterioPorCategoria(Electronica);
        CriterioDeBusqueda criterioDisponibilidad = new CriterioPorDisponibilidad(tienda);

        CriterioDeBusqueda criterioNombreYCategoria = new CriteriosConjuncion(criterioNombre, criterioCategoria);
        CriterioDeBusqueda criterioAndMultipleNombreCategoriaDisponibilidad = new CriteriosConjuncion(criterioNombreYCategoria, criterioDisponibilidad);

        List<Producto> resultado = tienda.filtrar(criterioAndMultipleNombreCategoriaDisponibilidad);

        assertEquals(3, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto2));
        assertTrue(resultado.contains(paquete1));

        assertFalse(resultado.contains(producto3));
        assertFalse(resultado.contains(producto4));
    }

    @Test
    public void test24_filtroConjuncionConFiltrosPrecioYCategoriaYDisponibilidad() {
        CriterioDeBusqueda criterioPrecio = new CriterioPrecioMaximo(3000);
        CriterioDeBusqueda criterioCategoria = new CriterioPorCategoria(Indumentaria);
        CriterioDeBusqueda criterioDisponibilidad = new CriterioPorDisponibilidad(tienda);

        CriterioDeBusqueda criterioPrecioYCategoria = new CriteriosConjuncion(criterioPrecio, criterioCategoria);
        CriterioDeBusqueda criterioAndMultiplePrecioCategoriaDisponibilidad = new CriteriosConjuncion(criterioPrecioYCategoria, criterioDisponibilidad);

        List<Producto> resultado = tienda.filtrar(criterioAndMultiplePrecioCategoriaDisponibilidad);

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(producto3));

        assertFalse(resultado.contains(producto1));
        assertFalse(resultado.contains(producto2));
        assertFalse(resultado.contains(producto4));
        assertFalse(resultado.contains(paquete1));
    }

    // CRITERIOS COMPUESTOS DISYUNCION 3 CONDICIONES

    @Test
    public void test25_filtroDisyuncionConFiltrosNombreOPrecioOCategoria() {
        CriterioDeBusqueda criterioNombre = new CriterioPorNombre("Rápido");
        CriterioDeBusqueda criterioPrecio = new CriterioPrecioMaximo(2000);
        CriterioDeBusqueda criterioCategoria = new CriterioPorCategoria(Indumentaria);

        CriterioDeBusqueda criterioNombreOPrecio = new CriteriosDisyuncion(criterioNombre, criterioPrecio);
        CriterioDeBusqueda criterioOrMultipleNombrePrecioCategoria = new CriteriosDisyuncion(criterioNombreOPrecio, criterioCategoria);

        List<Producto> resultado = tienda.filtrar(criterioOrMultipleNombrePrecioCategoria);

        assertEquals(4, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto2));
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(producto4));

        assertFalse(resultado.contains(paquete1));
    }

    @Test
    public void test26_filtroDisyuncionConFiltrosNombreOPrecioODisponibilidad() {
        CriterioDeBusqueda criterioNombre = new CriterioPorNombre("Blanco");
        CriterioDeBusqueda criterioPrecio = new CriterioPrecioMaximo(1000);
        CriterioDeBusqueda criterioDisponibilidad = new CriterioPorDisponibilidad(tienda);

        CriterioDeBusqueda criterioNombreOPrecio = new CriteriosDisyuncion(criterioNombre, criterioPrecio);
        CriterioDeBusqueda criterioOrMultipleNombrePrecioDisponibilidad = new CriteriosDisyuncion(criterioNombreOPrecio, criterioDisponibilidad);

        List<Producto> resultado = tienda.filtrar(criterioOrMultipleNombrePrecioDisponibilidad);

        assertEquals(4, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto2));
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(paquete1));

        assertFalse(resultado.contains(producto4));
    }

    @Test
    public void test27_filtroDisyuncionConFiltrosNombreOCategoriaODisponibilidad() {
        CriterioDeBusqueda criterioNombre = new CriterioPorNombre("Remera");
        CriterioDeBusqueda criterioCategoria = new CriterioPorCategoria(Electronica);
        CriterioDeBusqueda criterioDisponibilidad = new CriterioPorDisponibilidad(tienda);

        CriterioDeBusqueda criterioNombreOCategoria = new CriteriosDisyuncion(criterioNombre, criterioCategoria);
        CriterioDeBusqueda criterioOrMultipleNombreCategoriaDisponibilidad = new CriteriosDisyuncion(criterioNombreOCategoria, criterioDisponibilidad);

        List<Producto> resultado = tienda.filtrar(criterioOrMultipleNombreCategoriaDisponibilidad);

        assertEquals(5, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto2));
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(producto4));
        assertTrue(resultado.contains(paquete1));
    }

    @Test
    public void test28_filtroDisyuncionConFiltrosPrecioOCategoriaODisponibilidad() {
        CriterioDeBusqueda criterioPrecio = new CriterioPrecioMaximo(1000);
        CriterioDeBusqueda criterioCategoria = new CriterioPorCategoria(Indumentaria);
        CriterioDeBusqueda criterioDisponibilidad = new CriterioPorDisponibilidad(tienda);

        CriterioDeBusqueda criterioPrecioOCategoria = new CriteriosDisyuncion(criterioPrecio, criterioCategoria);
        CriterioDeBusqueda criterioOrMultiplePrecioCategoriaDisponibilidad = new CriteriosDisyuncion(criterioPrecioOCategoria, criterioDisponibilidad);

        List<Producto> resultado = tienda.filtrar(criterioOrMultiplePrecioCategoriaDisponibilidad);

        assertEquals(5, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto2));
        assertTrue(resultado.contains(producto3));
        assertTrue(resultado.contains(producto4));
        assertTrue(resultado.contains(paquete1));
    }

    // COMBINACIONES COMPUESTAS (CONJUNCION, DISYUNCION, NEGACION)

    @Test
    public void test29_filtroConjuncionAnidadoConNegacion() {
        CriterioDeBusqueda criterioNombre = new CriterioPorNombre("Rápido");
        CriterioDeBusqueda criterioPrecio = new CriterioPrecioMaximo(2000);
        CriterioDeBusqueda criterioCategoria = new CriterioPorCategoria(Electronica);

        CriterioDeBusqueda criterioNombreOPrecio = new CriteriosDisyuncion(criterioNombre, criterioPrecio);
        CriterioDeBusqueda criterioAndAnidadoConOrA = new CriteriosConjuncion(criterioNombreOPrecio, criterioCategoria);

        List<Producto> resultado = tienda.filtrar(criterioAndAnidadoConOrA);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto2));

        assertFalse(resultado.contains(producto3));
        assertFalse(resultado.contains(producto4));
        assertFalse(resultado.contains(paquete1));
    }

    @Test
    public void test30_compuesto_AndAnidadoConOr_VariacionB() {
        // Creamos una segunda sucursal para validar la disponibilidad en otra sucursal
        Sucursal sucursalQuilmes = new Sucursal(tienda, "Rivadavia 123");
        tienda.registrarSucursal(sucursalQuilmes);

        // Hacemos que el producto1 NO tenga stock en Bernal (sucursal original),
        // pero que SÍ tenga stock físico en la sucursal de Quilmes.
        sucursal.decrementarStock(Map.of(producto1, 10)); // Bernal queda en 0
        sucursalQuilmes.agregarStock(producto1, 5);       // Quilmes aporta las unidades

        CriterioDeBusqueda criterioCategoria = new CriterioPorCategoria(Indumentaria);
        CriterioDeBusqueda criterioDisponibilidad = new CriterioPorDisponibilidad(tienda);
        CriterioDeBusqueda criterioPrecio = new CriterioPrecioMaximo(3000);

        CriterioDeBusqueda criterioCategoriaODisponibilidad = new CriteriosDisyuncion(criterioCategoria, criterioDisponibilidad);
        CriterioDeBusqueda criterioAndAnidadoConOrB = new CriteriosConjuncion(criterioCategoriaODisponibilidad, criterioPrecio);

        List<Producto> resultado = tienda.filtrar(criterioAndAnidadoConOrB);

        // Verificaciones del resultado final:
        // - producto1: Sale en la lista porque cuesta $1500 (<=3000) y está DISPONIBLE en Quilmes.
        // - producto3: Sale en la lista porque cuesta $2500 (<=3000) y pertenece a Indumentaria.
        assertEquals(3, resultado.size());
        assertTrue(resultado.contains(producto1));
        assertTrue(resultado.contains(producto3));

        // producto4 no entra porque cuesta $4500 (supera el precio máximo de 3000)
        assertTrue(resultado.contains(producto4));
    }
}
