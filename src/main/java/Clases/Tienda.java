package Clases;

import BusquedaEnElCatalogo.CriterioDeBusqueda;
import Excepciones.StockNegativoException;
import Excepciones.TiendaInvalidaException;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Tienda {
    private final List<Sucursal> sucursales = new ArrayList<>();
    private final Set<Producto> catalogoDeProductos = new HashSet<>();

    public void registrarSucursal(Sucursal sucursal) {
        if (sucursal == null || !sucursal.getTienda().equals(this)) {
            throw new TiendaInvalidaException("No se puede registrar una sucursal que no pertenece a esta tienda.");
        }
        this.sucursales.add(sucursal);
    }

    public void procesarPedido(Pedido pedido, Sucursal sucursalDestino) {
        Map<Producto, Integer> productosDelPedido = pedido.getCarritoDeProductos();

        for (Map.Entry<Producto, Integer> entry : productosDelPedido.entrySet()) {
            Producto producto = entry.getKey();
            int cantidadRequerida = entry.getValue();

            //  La sucursal elegida tiene stock de este producto
            if (sucursalDestino.tieneStock(producto, cantidadRequerida)) {
                sucursalDestino.decrementarStock(Map.of(producto, cantidadRequerida));
            }
            //  No hay stock en la sucursal elegida, buscamos en el resto de la tienda
            else {
                Optional<Sucursal> sucursalOrigen = buscarSucursalConStock(producto, cantidadRequerida, sucursalDestino);

                if (sucursalOrigen.isPresent()) {
                    Sucursal origen = sucursalOrigen.get();

                    origen.decrementarStock(Map.of(producto, cantidadRequerida));
                    sucursalDestino.agregarStock(producto, cantidadRequerida);
                    sucursalDestino.decrementarStock(Map.of(producto, cantidadRequerida));

                    System.out.println("Traslado interno exitoso de " + producto.getNombre() + " desde " + origen.getDireccion());
                } else {
                    // Ninguna sucursal de toda la tienda tiene stock suficiente
                    throw new StockNegativoException("No hay stock suficiente de " + producto.getNombre() + " en ninguna sucursal de la tienda.");
                }
            }
        }

        sucursalDestino.getHistorialPedidos().add(pedido);
    }

    private Optional<Sucursal> buscarSucursalConStock(Producto producto, int cantidad, Sucursal sucursalActual) {
        return sucursales.stream()
                .filter(s -> !s.equals(sucursalActual))
                .filter(s -> s.tieneStock(producto, cantidad))
                .findFirst(); 
    }

    public List<Producto> filtrar(CriterioDeBusqueda criterio) {
        return catalogoDeProductos.stream()
                .filter(criterio::cumpleCondicion)
                .collect(Collectors.toList());
    }

    public List<Pedido> historialdePedidosDeTodasLasSucursales() {
        return this.sucursales.stream()
                .flatMap(sucursal -> sucursal.getHistorialPedidos().stream())
                .collect(Collectors.toList());
    }
}
