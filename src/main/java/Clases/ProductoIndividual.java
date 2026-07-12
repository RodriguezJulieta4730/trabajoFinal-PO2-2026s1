package Clases;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class ProductoIndividual extends Producto {
    private final String sku;
    private final String marca;
    private double descuento = 0;
    private final Map<String,Object> atributosExtra = new HashMap<>();

    public ProductoIndividual(String sku,
                              String nombre,
                              String descripcion,
                              String marca,
                              Categoria categoria,
                              double precioBase) {
        this.sku=sku;
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.marca=marca;
        this.categoria=categoria;
        this.precioBase=precioBase;

    }

    public ProductoIndividual(String sku,
                              String nombre,
                              String descripcion,
                              String marca,
                              Categoria categoria,
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
    public float getPeso() {
        Object pesoObj = this.getAtributoExtra("peso");

        if (pesoObj == null) {
            return 0.0f;
        }
        return ((Number) pesoObj).floatValue();
    }

    @Override
    public double getPrecioBase() {
        return precioBase;
    }

    public void setAtributoExtra(String atributoExtra, Object valor) {
        atributosExtra.put(atributoExtra,valor);
    }

    public Object getAtributoExtra(String atributoExtra) {
        return atributosExtra.get(atributoExtra);
    }
}
