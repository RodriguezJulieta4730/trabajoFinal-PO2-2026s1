package Reportes;


import java.util.List;

public interface TipoDeReporte {
    List<LineaDeReporte> getLineas();
    String exportar(FormatoDeExportacionDeReporte formato);
}
