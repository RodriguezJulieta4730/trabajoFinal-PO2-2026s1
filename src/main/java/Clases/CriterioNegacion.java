package Clases;

public class CriterioNegacion implements CriterioDeBusqueda {
    private CriterioDeBusqueda criterio;

    public CriterioNegacion(CriterioDeBusqueda criterio) {
        this.criterio = criterio;
    }

    @Override
    public boolean cumpleCondicion(Producto producto) {
        return ! criterio.cumpleCondicion(producto);
    }
}
