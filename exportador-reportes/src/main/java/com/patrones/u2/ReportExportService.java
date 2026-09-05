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

    public String export(ExportConfig config, java.util.List<GradeRecord> records, String institutionName) {
        ReportFormatFactory factory = ReportFactoryRegistry.resolve(config.getFormat());
        ReportBody body = factory.createBody();
        ReportHeaderFooter headerFooter = factory.createHeaderFooter();

        StringBuilder out = new StringBuilder();
        out.append(String.format("[config] pageSize=%s orientation=%s locale=%s watermark=%s compress=%s%n",
            config.getPageSize(), config.getOrientation(), config.getLocale(),
            config.getWatermarkText() == null ? "ninguna" : config.getWatermarkText(), config.isCompress()));
        out.append(headerFooter.renderHeader(institutionName)).append("\n");
        out.append(body.render(records));
        out.append(headerFooter.renderFooter(1));
        return out.toString();
    }
}