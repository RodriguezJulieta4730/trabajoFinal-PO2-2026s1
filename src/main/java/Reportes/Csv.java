package Reportes;

public class Csv implements FormatoDeExportacionDeReporte {
    @Override
    public String visit(ReporteProductosMasVendidos reporte) {
        StringBuilder sb = new StringBuilder();
        sb.append("Producto,Cantidad Vendida,Precio Promedio\n");
        for (LineaDeReporte linea : reporte.getLineas()) {
            sb.append(linea.getNombre()).append(",")
                    .append(linea.getCantidadVendida()).append(",")
                    .append(linea.getPrecioPromedioCobrado()).append("\n");
        }
        return sb.toString();
    }
}