package Strategy;

import Excepciones.PesoInvalidoException;

public class CorreoArgentina {
    public float estimarEnvio(float peso, String direccionEnvio) {
        if (peso <= 0) {
            throw new PesoInvalidoException("El peso debe ser positivo");
        }

        return 5;
    }


}
