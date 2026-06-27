package BusquedaEnElCatalogo;

import Clases.Producto;

public class CriterioPorNombre implements CriterioDeBusqueda {
    private final String nombre;

    public CriterioPorNombre(String nombre){
        this.nombre = nombre.toLowerCase();
    }

    @Override
    public boolean cumpleCondicion(Producto producto) {
        return producto.getNombre().toLowerCase().contains(nombre) ||
                producto.getDescripcion().toLowerCase().contains(nombre);
    }
}
