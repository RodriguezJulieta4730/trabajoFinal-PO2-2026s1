package Clases;

import lombok.Getter;

@Getter
public abstract class CatalogoDeProductos {
    String nombre;
    String descripcion;

    public abstract double getPrecioFinal();
}
