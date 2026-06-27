package BusquedaEnElCatalogo;

import Clases.Producto;

public class CriterioNegacion implements CriterioDeBusqueda {
    private final CriterioDeBusqueda criterio;

    public CriterioNegacion(CriterioDeBusqueda criterio) {
        this.criterio = criterio;
    }

    @Override
    public boolean cumpleCondicion(Producto producto) {
        return ! criterio.cumpleCondicion(producto);
    }
}
