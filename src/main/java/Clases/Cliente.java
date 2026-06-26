package Clases;

import lombok.Getter;

@Getter
public class Cliente {
    private DatosDeTarjeta datosDeTarjeta;
    private long cbu;
    private String alias;

    public Cliente(DatosDeTarjeta datosDeTarjeta, long cbu, String alias){
        this.datosDeTarjeta = datosDeTarjeta;
        this.alias = alias;
        this.cbu = cbu;
    }
}
