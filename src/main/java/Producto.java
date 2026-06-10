import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Producto implements CatalogoDeProductos {
    private String sku;
    private String nombre;
    private String marca;
    private String categoria;
    private double precioBase;
    private double descuento = 0;
    private Map<String,Object> atributosExtra = new HashMap<>();

    public Producto(String sku,
                    String nombre,
                    String marca,
                    String categoria,
                    double precioBase) {
        this.sku=sku;
        this.nombre=nombre;
        this.marca=marca;
        this.categoria=categoria;
        this.precioBase=precioBase;

    }

    public Producto(String sku,
                    String nombre,
                    String marca,
                    String categoria,
                    double precioBase,
                    double descuento) {
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
    public CatalogoDeProductos compose(CatalogoDeProductos producto, double v) {
        return new Paquete(this, producto, v);
    }

    @Override
    public Paquete compose(CatalogoDeProductos producto2) {
        return new Paquete(this,producto2);
    }

    public void setAtributoExtra(String atributoExtra, Object valor) {
        atributosExtra.put(atributoExtra,valor);
    }

    public Object getAtributoExtra(String atributoExtra) {
        return atributosExtra.get(atributoExtra);
    }
}
