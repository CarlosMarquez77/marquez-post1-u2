package com.patrones.u2;

import java.util.List;

/**
 * Contrato del cuerpo del reporte: la tabla de calificaciones
 * renderizada en el formato concreto de salida.
 */
public interface ReportBody {
    String render(List<GradeRecord> records);
}