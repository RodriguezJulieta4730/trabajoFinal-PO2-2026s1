package Reportes;

import Clases.Pedido;
import Clases.Tienda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


    public class ReporteProductosMasVendidos implements TipoDeReporte {
        private final List<LineaDeReporte> lineas;

        public ReporteProductosMasVendidos(Tienda tienda, LocalDate fechaInicio, LocalDate fechaFin) {
            Map<String, Integer> cantidades = new HashMap<>();
            Map<String, Double> recaudacionTotal = new HashMap<>();

            List<Pedido> pedidosDelPeriodo = tienda.historialdePedidosDeTodasLasSucursales().stream()
                    .filter(pedido -> !pedido.getFecha().isBefore(fechaInicio) && !pedido.getFecha().isAfter(fechaFin))
                    .collect(Collectors.toList());

            for (Pedido pedido : pedidosDelPeriodo) {
                pedido.getCarritoDeProductos().forEach((producto, cantidad) -> {
                    String nombre = producto.getNombre();
                    cantidades.put(nombre, cantidades.getOrDefault(nombre, 0) + cantidad);

                    double totalCobrado = producto.getPrecioFinal() * cantidad;
                    recaudacionTotal.put(nombre, recaudacionTotal.getOrDefault(nombre, 0.0) + totalCobrado);
                });
            }

            lineas = cantidades.entrySet().stream()
                    .map(entry -> {
                        String nombre = entry.getKey();
                        int cantidad = entry.getValue();
                        double promedio = recaudacionTotal.get(nombre) / cantidad;
                        return new LineaDeReporte(nombre, cantidad, promedio);
                    })
                    .sorted((l1, l2) -> Integer.compare(l2.getCantidadVendida(), l1.getCantidadVendida()))
                    .collect(Collectors.toList());
        }

        public List<LineaDeReporte> getLineas() {
            return lineas;
        }

        public String exportar(FormatoDeExportacionDeReporte visitor) {
            return visitor.visit(this);
        }
}