package BusquedaEnElCatalogo;

import Clases.Producto;
import Clases.Sucursal;
import Clases.UNQShop;

public class CriterioPorDisponibilidad implements CriterioDeBusqueda {
    private final UNQShop tienda;

    public CriterioPorDisponibilidad(UNQShop tienda) {
        this.tienda = tienda;
    }

    @Override
    public boolean cumpleCondicion(Producto producto) {
        return tienda.getSucursales().stream()
                .anyMatch(sucursal -> sucursal.tieneStock(producto, 1));
    }
}
