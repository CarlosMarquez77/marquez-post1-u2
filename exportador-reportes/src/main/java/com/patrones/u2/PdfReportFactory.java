package com.patrones.u2;

public class PdfReportFactory implements ReportFormatFactory {
    public ReportBody createBody()               { return new PdfReportBody(); }
    public ReportHeaderFooter createHeaderFooter() { return new PdfHeaderFooter(); }
}