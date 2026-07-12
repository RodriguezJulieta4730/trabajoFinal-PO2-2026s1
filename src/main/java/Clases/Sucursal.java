package Clases;

import BusquedaEnElCatalogo.CriterioDeBusqueda;
import Excepciones.ProductoNoEncontradoException;
import Excepciones.StockNegativoException;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Sucursal {
    private final Map<Producto,Integer> stockDeProductos = new HashMap<>();
    private final Set<Producto> catalogoDeProductos = new HashSet<>();
    private final List<Pedido> historialPedidos = new ArrayList<>();

    public void agregarStock(Producto producto, int stock) {
        catalogoDeProductos.add(producto);
        if(stock>0){
            int stockActual = stockDeProductos.getOrDefault(producto, 0);
            stockDeProductos.put(producto, stockActual + stock);
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

    public boolean tieneStockPara(Pedido pedido) {
        return pedido.getCarritoDeProductos().entrySet().stream()
                .allMatch(entry -> this.tieneStock(entry.getKey(), entry.getValue()));
    }

    public List<Producto> filtrar(CriterioDeBusqueda criterioDeBusqueda){
        return catalogoDeProductos.stream().filter(criterioDeBusqueda::cumpleCondicion).collect(Collectors.toList());
    }

    public void registarPedidoEnHistorial(Pedido pedido) {
        historialPedidos.add(pedido);
    }

    public void fabricarPaquete(String nombre, String descripcion, Categoria categoria,
                                Map<Producto, Integer> productos) {

        validarStock(productos);
        descontarStock(productos);
        Paquete nuevoPaquete = new Paquete(nombre, descripcion, categoria, productos);
        this.agregarStock(nuevoPaquete,1);
    }

    public Paquete fabricarPaquete(String nombre, String descripcion, double descuento, Categoria categoria, Map<Producto, Integer> receta) {
        validarStock(receta);
        descontarStock(receta);
        Paquete nuevoPaquete = new Paquete(nombre, descripcion, descuento, categoria, receta);
        this.agregarStock(nuevoPaquete, 1);
        return nuevoPaquete;
    }

    private void validarStock(Map<Producto, Integer> receta) {
        for (Map.Entry<Producto, Integer> entry : receta.entrySet()) {
            Producto componente = entry.getKey();
            int cantidadRequerida = entry.getValue();

            if (!this.tieneStock(componente, cantidadRequerida)) {
                throw new StockNegativoException("No es posible armar el paquete. Stock insuficiente de: " + componente.getNombre());
            }
        }
    }

    private void descontarStock(Map<Producto, Integer> receta) {
        for (Map.Entry<Producto, Integer> entry : receta.entrySet()) {
            Producto componente = entry.getKey();
            int cantidadRequerida = entry.getValue();

            int stockActual = this.stockDeProductos.get(componente);
            this.stockDeProductos.put(componente, stockActual - cantidadRequerida);
        }
    }

    public void aplicarDescuentoAProducto(ProductoIndividual producto, double nuevoDescuento) {
        if (!catalogoDeProductos.contains(producto)) {
            throw new ProductoNoEncontradoException("El producto " + producto.getNombre() + " no pertenece al catálogo de esta sucursal");
        }
        producto.setDescuento(nuevoDescuento);
    }
}

