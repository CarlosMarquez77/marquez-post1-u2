package com.patrones.u2;

public class PdfHeaderFooter implements ReportHeaderFooter {
    public String renderHeader(String institutionName) {
        return "[PDF:encabezado] " + institutionName + " - Acta de Calificaciones (membrete institucional)";
    }
    public String renderFooter(int pageNumber) {
        return "[PDF:pie] Pagina " + pageNumber + " - documento apto para firma digital";
    }
}