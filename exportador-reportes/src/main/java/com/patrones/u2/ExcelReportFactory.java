package com.patrones.u2;

public class ExcelReportFactory implements ReportFormatFactory {
    public ReportBody createBody()               { return new ExcelReportBody(); }
    public ReportHeaderFooter createHeaderFooter() { return new ExcelHeaderFooter(); }
}