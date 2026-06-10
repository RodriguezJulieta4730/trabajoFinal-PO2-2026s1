import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Paquete extends CatalogoDeProductos{
    private List<CatalogoDeProductos> listaDeProducto = new ArrayList<>();
    private double descuento=0;

    public Paquete(String nombre, String descripcion,CatalogoDeProductos producto1, CatalogoDeProductos producto2){
        this.nombre=nombre;
        this.descuento=descuento;
        listaDeProducto.add(producto1);
        listaDeProducto.add(producto2);
    }

    public Paquete(String nombre, String descripcion,CatalogoDeProductos producto1, CatalogoDeProductos producto2, double descuento){
        this.nombre=nombre;
        this.descuento=descuento;
        listaDeProducto.add(producto1);
        listaDeProducto.add(producto2);
        this.descuento=descuento;
    }

    @Override
    public double getPrecioFinal() {
        double precioTotal= 0;
        for(CatalogoDeProductos p: listaDeProducto){
            precioTotal+= p.getPrecioFinal();
        }
        return precioTotal * (1-descuento);
    }

    @Override
    CatalogoDeProductos compose(String nombre, String descripcion, CatalogoDeProductos producto, double descuento) {
        return null;
    }

    @Override
    public double getPrecioBase() {
        return 0;
    }

    @Override
    CatalogoDeProductos compose(String nombre, String descripcion, CatalogoDeProductos producto) {
        return null;
    }

}
