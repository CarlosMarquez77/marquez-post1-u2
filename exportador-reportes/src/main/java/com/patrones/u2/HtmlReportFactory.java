package com.patrones.u2;

public class HtmlReportFactory implements ReportFormatFactory {
    public ReportBody createBody()               { return new HtmlReportBody(); }
    public ReportHeaderFooter createHeaderFooter() { return new HtmlHeaderFooter(); }
}