package Reportes;

public class TextoPlano implements FormatoDeExportacionDeReporte {
    @Override
    public String visit(ReporteProductosMasVendidos reporte) {
        StringBuilder sb = new StringBuilder();
        sb.append("REPORTE DE PRODUCTOS MÁS VENDIDOS\n");
        sb.append("=================================\n");
        for (LineaDeReporte linea : reporte.getLineas()) {
            sb.append(linea.getNombre())
                    .append(" - Unidades: ").append(linea.getCantidadVendida())
                    .append(" - P.Promedio: $").append(linea.getPrecioPromedioCobrado()).append("\n");
        }
        return sb.toString();
    }
}