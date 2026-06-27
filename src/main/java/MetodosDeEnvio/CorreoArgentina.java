package MetodosDeEnvio;

import Excepciones.PesoInvalidoException;

public class CorreoArgentina {
    public static float estimarEnvio(float peso, String ignoredDireccionEnvio) {
        if (peso <= 0) {
            throw new PesoInvalidoException("El peso debe ser positivo");
        }
        return 5;
    }
}
