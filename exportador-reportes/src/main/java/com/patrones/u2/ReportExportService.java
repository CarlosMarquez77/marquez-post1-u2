package com.patrones.u2;

import java.util.List;

public class ReportExportService {
    public String export(String format, List<GradeRecord> records, String institutionName) {
        ReportFormatFactory factory = ReportFactoryRegistry.resolve(format);
        ReportBody body = factory.createBody();
        ReportHeaderFooter headerFooter = factory.createHeaderFooter();

        StringBuilder out = new StringBuilder();
        out.append(headerFooter.renderHeader(institutionName)).append("\n");
        out.append(body.render(records));
        out.append(headerFooter.renderFooter(1));
        return out.toString();
    }
}