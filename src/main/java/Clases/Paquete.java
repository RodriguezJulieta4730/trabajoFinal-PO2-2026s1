package Clases;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class Paquete extends Producto {
    private final Map<Producto, Integer> productos;
    private double descuento=0;

    public Paquete(String nombre, String descripcion, Categoria categoria, Map<Producto, Integer> componentes) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.productos = new HashMap<>(componentes);
        this.precioBase = this.getPrecioBase();
    }

    public Paquete(String nombre, String descripcion, double descuento, Categoria categoria, Map<Producto, Integer> componentes) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.descuento = descuento;
        this.categoria = categoria;
        this.productos = new HashMap<>(componentes);
        this.precioBase = this.getPrecioBase();
    }

    @Override
    public double getPrecioFinal() {
        double precioTotal = 0;
        for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            precioTotal += entry.getKey().getPrecioFinal() * entry.getValue();
        }
        return precioTotal * (1 - descuento);
    }

    @Override
    public float getPeso() {
        float pesoTotal = 0;
        for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            pesoTotal += entry.getKey().getPeso() * entry.getValue();
        }
        return pesoTotal;
    }

    @Override
    public double getPrecioBase() {
        double totalBase = 0;
        for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            totalBase += entry.getKey().getPrecioBase() * entry.getValue();
        }
        return totalBase;
    }

}
