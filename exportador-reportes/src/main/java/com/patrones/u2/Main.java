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

        System.out.println("=== Exportacion con configuracion por defecto ===");
        ExportConfig defaultConfig = new ExportConfig.Builder("pdf").build();
        System.out.println(exportService.export(defaultConfig, records, "UDES"));

        System.out.println("=== Exportacion con configuracion personalizada ===");
        ExportConfig customConfig = new ExportConfig.Builder("excel")
            .pageSize("LETTER")
            .orientation("LANDSCAPE")
            .locale("en-US")
            .watermarkText("BORRADOR")
            .maxRowsPerPage(25)
            .build();
        System.out.println(exportService.export(customConfig, records, "UDES"));

        System.out.println("=== Validacion de estado inconsistente ===");
        try {
            new ExportConfig.Builder("html").compress(true).build();
        } catch (IllegalStateException e) {
            System.out.println("Rechazado correctamente: " + e.getMessage());
        }

    }

    
}