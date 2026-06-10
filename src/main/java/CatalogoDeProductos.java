public interface CatalogoDeProductos {
    double getPrecioFinal();
    CatalogoDeProductos compose(CatalogoDeProductos producto, double v);
    double getPrecioBase();
    Paquete compose(CatalogoDeProductos producto2);
}
