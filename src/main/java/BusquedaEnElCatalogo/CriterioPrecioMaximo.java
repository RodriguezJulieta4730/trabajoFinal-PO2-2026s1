package BusquedaEnElCatalogo;

import Clases.Producto;

public class CriterioPrecioMaximo implements CriterioDeBusqueda {
    private final double precioMaximo;

    public CriterioPrecioMaximo(double precioMaximo) {
        this.precioMaximo = precioMaximo;

    }

    @Override
    public boolean cumpleCondicion(Producto producto) {
        return producto.getPrecioBase() <= precioMaximo;
    }
}
