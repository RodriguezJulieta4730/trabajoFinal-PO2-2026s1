package MetodosDePago;

import Clases.Cliente;

public abstract class MedioDePago {
    public String pagar(double montoAPagar, Cliente cliente){
        if(!validarDatos(montoAPagar,cliente)){
            return "No se pudo validar los datos";
        }
        if(!reservarFondos(montoAPagar,cliente)){
            return "No hay fondos suficientes";
        }
        if(!ejecutarTransaccion(montoAPagar,cliente)){
            return "No se pudo ejecutar la transacción";
        }
        return notificarResultado(cliente);
    }

     public abstract boolean validarDatos(double montoAPagar,Cliente cliente);

     public abstract boolean reservarFondos(double montoAPagar, Cliente cliente);

     public abstract boolean ejecutarTransaccion(double montoAPagar, Cliente cliente);

     public abstract String notificarResultado(Cliente cliente);
}
