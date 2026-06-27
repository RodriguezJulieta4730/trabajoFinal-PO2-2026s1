package Clases;

public class CriteriosConjuncion implements CriterioDeBusqueda {
    private CriterioDeBusqueda criterio1;
    private  CriterioDeBusqueda criterio2;

    public CriteriosConjuncion(CriterioDeBusqueda criterio1, CriterioDeBusqueda criterio2) {
        this.criterio1 = criterio1;
        this.criterio2 = criterio2;
    }

    @Override
    public boolean cumpleCondicion(Producto producto) {
        return criterio1.cumpleCondicion(producto) && criterio2.cumpleCondicion(producto);
    }
}
