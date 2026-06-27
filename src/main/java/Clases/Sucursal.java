package Clases;

import Excepciones.StockNegativoException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class Sucursal {
    private Map<Producto,Integer> stockProductos = new HashMap<>();

    public void agregarStock(Producto producto, int stock) {
        if(stock>0){
            stockProductos.put(producto,stock);
        }else{
            throw new StockNegativoException("El numero debe ser positivo");
        }
    }

    public boolean tieneStock(Producto producto, int cantProducto) {
        return stockProductos.getOrDefault(producto,0)>=cantProducto;
    }

    public void decrementarStock(Map<Producto, Integer> productos) {
        for(Producto c: productos.keySet()){
            stockProductos.put(c,stockProductos.get(c)-productos.get(c));
        }
    }

    public void cancelarPedido(Map<Producto, Integer> productos) {
        if(productos != null) {
            for (Producto c : productos.keySet()) {
                stockProductos.put(c, stockProductos.get(c) + productos.get(c));
            }
        }
    }

    public void reembolsarCostoProductos(Pedido pedido) {
        pedido.reembolsarCostoProductos();
    }

    public void reembolsarEnvio(Pedido pedido) {
        pedido.reembolsarEnvio();
    }

    public void entregar(Pedido pedido) {
        pedido.entregar();
    }

    public boolean tieneStockPara(Pedido pedido) {
        return pedido.getCarritoDeProductos().entrySet().stream()
                .allMatch(entry -> this.tieneStock(entry.getKey(), entry.getValue()));
    }

    public Map<Producto,Integer> filtrarPorNombre(String nombre) {
        String nombreMinuscula = nombre.toLowerCase();

        return stockProductos.entrySet().stream()
                .filter(entry -> entry.getKey().getNombre().toLowerCase().contains(nombreMinuscula) ||
                        entry.getKey().getDescripcion().toLowerCase().contains(nombreMinuscula))
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue()
                ));
    }

    public Map<Producto, Integer> filtrarPorCategoria(Categoria categoria) {
        return stockProductos.entrySet().stream()
                .filter(entry -> entry.getKey().getCategoria().equals(categoria)).collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue()
                ));
    }

//    public Map<Producto, Integer> filtrarPorDisponibilidad(Producto producto) {
//        return stockProductos.entrySet().stream().filter(entry -> entry.getValue(producto).)
//    }

//    public Map<Producto, Integer> filtrarPorPrecioMaximo(double monto) {
//        return stockProductos.entrySet().stream().filter(entry -> entry.getKey().g )
//    }
// aca hay un problema porque en este filtro nos pide trabajar con el precio base del item y en paquete nosotras solo calculamos en final
    // ese final deberia ser precio base, pero no se bien como cambiarlo
    // paquete deberia tener precio base que es la suma de todos los precios mas el descuento y producto individual, precio bas
}

