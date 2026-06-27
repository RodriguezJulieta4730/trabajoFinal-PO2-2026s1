package BusquedaEnElCatalogo;

import Clases.Producto;

public interface CriterioDeBusqueda {

    boolean cumpleCondicion(Producto producto);
}
