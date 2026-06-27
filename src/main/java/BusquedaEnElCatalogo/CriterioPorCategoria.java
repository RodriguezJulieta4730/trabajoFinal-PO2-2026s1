package BusquedaEnElCatalogo;

import Clases.Categoria;
import Clases.Producto;

public class CriterioPorCategoria implements CriterioDeBusqueda {
    private final Categoria categoria;
    public CriterioPorCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean cumpleCondicion(Producto producto) {
        return producto.getCategoria() == categoria;
    }
}
