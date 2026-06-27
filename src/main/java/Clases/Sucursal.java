package Clases;

import Excepciones.StockNegativoException;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Sucursal {
    private Map<Producto,Integer> stockDeProductos = new HashMap<>();
    private Set<Producto> catalogoDeProductos = new HashSet<>();

    public void agregarStock(Producto producto, int stock) {
        catalogoDeProductos.add(producto);
        if(stock>0){
            stockDeProductos.put(producto,stock);
        }else{
            throw new StockNegativoException("El numero debe ser positivo");
        }
    }

    public boolean tieneStock(Producto producto, int cantProducto) {
        return stockDeProductos.getOrDefault(producto,0)>=cantProducto;
    }

    public void decrementarStock(Map<Producto, Integer> productos) {
        for(Producto c: productos.keySet()){
            stockDeProductos.put(c, stockDeProductos.get(c)-productos.get(c));
        }
    }

    public void cancelarPedido(Map<Producto, Integer> productos) {
        if(productos != null) {
            for (Producto c : productos.keySet()) {
                stockDeProductos.put(c, stockDeProductos.get(c) + productos.get(c));
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

    public List<Producto> filtrar(CriterioDeBusqueda criterioDeBusqueda){
        return catalogoDeProductos.stream().filter(producto -> criterioDeBusqueda
                .cumpleCondicion(producto)).collect(Collectors.toList());
    }

}

