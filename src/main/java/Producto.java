import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Producto extends CatalogoDeProductos {
    private final String sku;
    private final String marca;
    private final String categoria;
    private final double precioBase;
    private double descuento = 0;
    private final Map<String,Object> atributosExtra = new HashMap<>();

    public Producto(String sku,
                    String nombre,
                    String descripcion,
                    String marca,
                    String categoria,
                    double precioBase) {
        this.sku=sku;
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.marca=marca;
        this.categoria=categoria;
        this.precioBase=precioBase;

    }

    public Producto(String sku,
                    String nombre,
                    String descripcion,
                    String marca,
                    String categoria,
                    double precioBase,
                    double descuento) {
        this.sku=sku;
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.marca=marca;
        this.categoria=categoria;
        this.precioBase=precioBase;
        this.descuento=descuento;
    }


    public double getPrecioFinal(){
        return precioBase - precioBase*descuento;
    }

    @Override
    public CatalogoDeProductos compose(String nombre, String descripcion, CatalogoDeProductos producto, double descuento) {
        return new Paquete(nombre,descripcion,this, producto, descuento);
    }

    @Override
    public CatalogoDeProductos compose(String nombre, String descripcion,CatalogoDeProductos producto) {
        return new Paquete(nombre,descripcion,this, producto);
    }

    public void setAtributoExtra(String atributoExtra, Object valor) {
        atributosExtra.put(atributoExtra,valor);
    }

    public Object getAtributoExtra(String atributoExtra) {
        return atributosExtra.get(atributoExtra);
    }
}
