package Clases;

public class CriterioPorDisponibilidad implements CriterioDeBusqueda {
    private Sucursal sucursal;

    public CriterioPorDisponibilidad(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public boolean cumpleCondicion(Producto producto) {
        return sucursal.getStockDeProductos().containsKey(producto);
    }
}
