package Reportes;

import lombok.Getter;

@Getter
public class LineaDeReporte {
    private final String nombre;
    private final int cantidadVendida;
    private final double precioPromedioCobrado;

    public LineaDeReporte(String nombre, int cantidadVendida, double precioPromedioCobrado) {
        this.nombre = nombre;
        this.cantidadVendida = cantidadVendida;
        this.precioPromedioCobrado = precioPromedioCobrado;
    }


}
