public interface CatalogoDeProductos {
    double getPrecioFinal();
    CatalogoDeProductos compose(CatalogoDeProductos producto);
    double getPrecioBase();
}
