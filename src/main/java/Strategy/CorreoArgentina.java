package Strategy;

public class CorreoArgentina {
    public float estimarEnvio(double peso, String direccionEnvio) {
        if (peso <= 0) {
            return 0; //debe ir una excepcion
        }

        return 5;
    }


}
