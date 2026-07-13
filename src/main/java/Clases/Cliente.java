package Clases;

import lombok.Getter;

@Getter
public class Cliente {
    private final String alias;
    private final long cbu;
    private String email;
    private String direccion;

    public Cliente(String alias, long cbu, String email, String direccion) {
        this.alias = alias;
        this.cbu = cbu;
        this.email = email;
        this.direccion = direccion;
    }
}