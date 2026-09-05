package com.patrones.u2;

import java.util.List;

public class ExcelReportBody implements ReportBody {
    public String render(List<GradeRecord> records) {
        StringBuilder sb = new StringBuilder("[XLSX:cuerpo] Estudiante;Curso;Nota\n");
        for (GradeRecord r : records) {
            sb.append(String.format("  %s;%s;%.1f%n", r.getStudentName(), r.getCourseCode(), r.getGrade()));
        }
        return sb.toString();
    }
}