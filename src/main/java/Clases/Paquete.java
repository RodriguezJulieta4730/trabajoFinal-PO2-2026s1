package Clases;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Paquete extends Producto {
    private List<Producto> listaDeProducto = new ArrayList<>();
    private double descuento=0;

    public Paquete(String nombre, String descripcion, Producto producto1, Producto producto2) {
        this.nombre=nombre;
        this.descripcion=descripcion;
        listaDeProducto.add(producto1);
        listaDeProducto.add(producto2);
    }

    public Paquete(String nombre, String descripcion, Producto producto1, Producto producto2, double descuento) {
        this.nombre=nombre;
        this.descripcion=descripcion;
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

    @Override
    public float getPeso() {
        float pesoTotal = 0;
        for(Producto p: listaDeProducto){
            pesoTotal+= p.getPeso();
        }
        return 0;
    }
}
