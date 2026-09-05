package com.patrones.u2;

/**
 * Contrato del encabezado y pie de pagina. Debe coincidir con el
 * mismo formato que el cuerpo del reporte dentro de una misma
 * exportacion - esa es la restriccion central del problema.
 */
public interface ReportHeaderFooter {
    String renderHeader(String institutionName);
    String renderFooter(int pageNumber);
}