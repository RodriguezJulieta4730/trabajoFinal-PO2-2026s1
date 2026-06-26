package Clases;

import lombok.Getter;

@Getter
public abstract class Producto {
    String nombre;
    String descripcion;

    public abstract double getPrecioFinal();

    public abstract float getPeso();
}
