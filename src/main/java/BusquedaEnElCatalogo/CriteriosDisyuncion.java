package BusquedaEnElCatalogo;

import Clases.Producto;

public class CriteriosDisyuncion implements CriterioDeBusqueda {
    private final CriterioDeBusqueda criterio1;
    private final CriterioDeBusqueda criterio2;

    public CriteriosDisyuncion(CriterioDeBusqueda criterio1, CriterioDeBusqueda criterio2) {
        this.criterio1 = criterio1;
        this.criterio2 = criterio2;
    }

    @Override
    public boolean cumpleCondicion(Producto producto) {
        return criterio1.cumpleCondicion(producto) || criterio2.cumpleCondicion(producto);
    }
}
