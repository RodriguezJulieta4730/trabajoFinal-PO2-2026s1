package TemplateMethod;

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
        return notificarResultado();
    }

    abstract boolean validarDatos(double montoAPagar,Cliente cliente);

    abstract boolean reservarFondos(double montoAPagar, Cliente cliente);

    abstract boolean ejecutarTransaccion(double montoAPagar, Cliente cliente);

    abstract String notificarResultado();
}
