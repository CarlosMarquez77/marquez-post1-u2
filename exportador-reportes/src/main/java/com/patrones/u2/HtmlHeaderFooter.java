package com.patrones.u2;

public class HtmlHeaderFooter implements ReportHeaderFooter {
    public String renderHeader(String institutionName) {
        return "[HTML:encabezado] <header>" + institutionName + " - Portal de Estudiantes</header>";
    }
    public String renderFooter(int pageNumber) {
        return "[HTML:pie] <footer>Vista " + pageNumber + " - generado dinamicamente</footer>";
    }
}