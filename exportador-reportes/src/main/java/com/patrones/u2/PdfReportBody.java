package com.patrones.u2;

import java.util.List;

public class PdfReportBody implements ReportBody {
    public String render(List<GradeRecord> records) {
        StringBuilder sb = new StringBuilder("[PDF:cuerpo]\n");
        for (GradeRecord r : records) {
            sb.append(String.format("  %s | %s | %s | %.1f%n",
                r.getStudentId(), r.getStudentName(), r.getCourseCode(), r.getGrade()));
        }
        return sb.toString();
    }
}