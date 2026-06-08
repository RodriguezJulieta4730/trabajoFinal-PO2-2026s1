import lombok.Getter;

@Getter
public class Producto implements CatalogoDeProductos {
    private String sku;
    private String nombre;
    private String marca;
    private String categoria;
    private double precioBase;
    private double descuento = 0;
    public Producto(String sku, String nombre, String marca, String categoria, double precioBase) {
        this.sku=sku;
        this.nombre=nombre;
        this.marca=marca;
        this.categoria=categoria;
        this.precioBase=precioBase;
    }

    public Producto(String sku, String nombre, String marca, String categoria, double precioBase, double descuento) {
        this.sku=sku;
        this.nombre=nombre;
        this.marca=marca;
        this.categoria=categoria;
        this.precioBase=precioBase;
        this.descuento=descuento;
    }


    public double getPrecioFinal(){
        return precioBase - precioBase*descuento;
    }

    @Override
    public Paquete compose(CatalogoDeProductos producto2) {
        return new Paquete(this,producto2);
    }

    @Override
    public Paquete compose(Producto producto2,double descuento) {
        return new Paquete(this,producto2,descuento);
    }
}
