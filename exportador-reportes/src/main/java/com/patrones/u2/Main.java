package com.patrones.u2;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<GradeRecord> records = List.of(
            new GradeRecord("20231001", "Ana Torres", "IS-301", 4.2),
            new GradeRecord("20231002", "Luis Rey",   "IS-301", 3.8),
            new GradeRecord("20231003", "Marta Diaz", "IS-301", 4.7)
        );

        ReportExportService exportService = new ReportExportService();

        System.out.println("=== Exportacion PDF ===");
        System.out.println(exportService.export("pdf", records, "UDES"));

        System.out.println("=== Exportacion Excel ===");
        System.out.println(exportService.export("excel", records, "UDES"));

        System.out.println("=== Exportacion HTML ===");
        System.out.println(exportService.export("html", records, "UDES"));
    }
}