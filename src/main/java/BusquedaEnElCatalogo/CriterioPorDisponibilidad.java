package BusquedaEnElCatalogo;

import Clases.Producto;
import Clases.Sucursal;

public class CriterioPorDisponibilidad implements CriterioDeBusqueda {
    private final Sucursal sucursal;

    public CriterioPorDisponibilidad(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public boolean cumpleCondicion(Producto producto) {
        return sucursal.getStockDeProductos().containsKey(producto);
    }
}
