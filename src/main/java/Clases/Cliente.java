package Clases;

import lombok.Getter;

@Getter
public class Cliente {
    private final DatosDeTarjeta datosDeTarjeta;
    private final long cbu;
    private final String alias;

    public Cliente(DatosDeTarjeta datosDeTarjeta, long cbu, String alias){
        this.datosDeTarjeta = datosDeTarjeta;
        this.alias = alias;
        this.cbu = cbu;
    }
}
