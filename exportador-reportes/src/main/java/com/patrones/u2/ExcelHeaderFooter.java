package com.patrones.u2;

public class ExcelHeaderFooter implements ReportHeaderFooter {
    public String renderHeader(String institutionName) {
        return "[XLSX:encabezado] Hoja: " + institutionName + " - Notas (fila congelada)";
    }
    public String renderFooter(int pageNumber) {
        return "[XLSX:pie] Total de filas exportadas en hoja " + pageNumber;
    }
}