package Clases;

public class CriterioPorCategoria implements CriterioDeBusqueda {
    private Categoria categoria;
    public CriterioPorCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean cumpleCondicion(Producto producto) {
        return producto.getCategoria() == categoria;
    }
}
