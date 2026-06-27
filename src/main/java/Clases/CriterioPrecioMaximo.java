package Clases;

public class CriterioPrecioMaximo implements CriterioDeBusqueda {
    private double precioMaximo;

    public CriterioPrecioMaximo(double precioMaximo) {
        this.precioMaximo = precioMaximo;

    }

    @Override
    public boolean cumpleCondicion(Producto producto) {
        return producto.getPrecioBase() <= precioMaximo;
    }
}
