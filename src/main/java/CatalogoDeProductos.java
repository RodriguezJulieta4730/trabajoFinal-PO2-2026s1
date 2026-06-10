import lombok.Getter;

@Getter
public abstract class CatalogoDeProductos {
    String nombre;
    String descripcion;

    abstract double getPrecioFinal();
    abstract CatalogoDeProductos compose(String nombre, String descripcion,CatalogoDeProductos producto, double descuento);
    abstract double getPrecioBase();
    abstract CatalogoDeProductos compose(String nombre, String descripcion,CatalogoDeProductos producto);
}
