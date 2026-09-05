package com.patrones.u2;

/**
 * Fabrica de la familia completa de productos (cuerpo + encabezado/pie)
 * para UN formato de salida. Garantiza que ambos productos de la
 * familia sean siempre compatibles entre si dentro de una misma
 * exportacion.
 */
public interface ReportFormatFactory {
    ReportBody createBody();
    ReportHeaderFooter createHeaderFooter();
}