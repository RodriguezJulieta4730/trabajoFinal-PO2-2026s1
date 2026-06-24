package Excepciones;

public class operacionInvalidaExeption extends RuntimeException {
    public operacionInvalidaExeption(String mensaje) {
        super(mensaje);
    }
}
