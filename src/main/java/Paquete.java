import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Paquete implements CatalogoDeProductos{
    private List<CatalogoDeProductos> listaDeProducto = new ArrayList<>();
    private double descuento=0;

    public Paquete(CatalogoDeProductos producto1, CatalogoDeProductos producto2){
        listaDeProducto.add(producto1);
        listaDeProducto.add(producto2);
    }

    public Paquete(Producto producto1, Producto producto2, double descuento){
        listaDeProducto.add(producto1);
        listaDeProducto.add(producto2);
        this.descuento=descuento;
    }

    @Override
    public double getPrecioFinal() {
        double precioTotal= 0;
        for(Producto p: listaDeProducto){
            precioTotal+= p.getPrecioFinal();
        }
        return precioTotal * (1-descuento);
    }
}
