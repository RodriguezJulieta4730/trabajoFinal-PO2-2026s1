package Clases;

import java.time.LocalDate;

public record DatosDeTarjeta (
     int nroTarjeta,
     int cvv,
     LocalDate fechaDeVencimiento
){}
