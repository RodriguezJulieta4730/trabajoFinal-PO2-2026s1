package Clases;

public class CriterioPorNombre implements CriterioDeBusqueda{
    private String nombre;

    public CriterioPorNombre(String nombre){
        this.nombre = nombre.toLowerCase();
    }

    @Override
    public boolean cumpleCondicion(Producto producto) {
        return producto.getNombre().toLowerCase().contains(nombre) ||
                producto.getDescripcion().toLowerCase().contains(nombre);
    }
}
