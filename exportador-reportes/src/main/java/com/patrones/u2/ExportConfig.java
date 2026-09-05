package com.patrones.u2;

/**
 * Configuracion de una exportacion concreta. Un solo parametro es
 * obligatorio (format); los demas son opcionales con valores por
 * defecto razonables (ver Decision 3 en el README para la
 * justificacion de este diseno).
 */
public class ExportConfig {
    private final String format;
    private final String outputPath;
    private final String pageSize;
    private final String orientation;
    private final String locale;
    private final String watermarkText;
    private final boolean includeLogo;
    private final boolean compress;
    private final int maxRowsPerPage;

    private ExportConfig(Builder b) {
        this.format         = b.format;
        this.outputPath      = b.outputPath;
        this.pageSize        = b.pageSize;
        this.orientation     = b.orientation;
        this.locale          = b.locale;
        this.watermarkText   = b.watermarkText;
        this.includeLogo     = b.includeLogo;
        this.compress        = b.compress;
        this.maxRowsPerPage  = b.maxRowsPerPage;
    }

    public String getFormat()          { return format; }
    public String getOutputPath()      { return outputPath; }
    public String getPageSize()        { return pageSize; }
    public String getOrientation()     { return orientation; }
    public String getLocale()          { return locale; }
    public String getWatermarkText()   { return watermarkText; }
    public boolean isIncludeLogo()     { return includeLogo; }
    public boolean isCompress()        { return compress; }
    public int getMaxRowsPerPage()     { return maxRowsPerPage; }

    public static class Builder {
        private final String format;              // requerido
        private String outputPath     = null;
        private String pageSize       = "A4";
        private String orientation    = "PORTRAIT";
        private String locale         = "es-CO";
        private String watermarkText  = null;
        private boolean includeLogo   = true;
        private boolean compress      = false;
        private int maxRowsPerPage    = 40;

        public Builder(String format) {
            if (format == null || format.isBlank()) {
                throw new IllegalArgumentException("format es obligatorio");
            }
            this.format = format;
        }

        public Builder outputPath(String path)     { this.outputPath = path;     return this; }
        public Builder pageSize(String size)        { this.pageSize = size;       return this; }
        public Builder orientation(String o)         { this.orientation = o;       return this; }
        public Builder locale(String l)              { this.locale = l;            return this; }
        public Builder watermarkText(String text)   { this.watermarkText = text;  return this; }
        public Builder includeLogo(boolean include) { this.includeLogo = include; return this; }
        public Builder compress(boolean c)           { this.compress = c;          return this; }
        public Builder maxRowsPerPage(int max)       { this.maxRowsPerPage = max;  return this; }

        public ExportConfig build() {
            // Validacion de estado consistente - imposible de expresar
            // de forma centralizada con setters sueltos.
            if (compress && outputPath == null) {
                throw new IllegalStateException(
                    "compress=true requiere especificar outputPath: no se puede comprimir un resultado en memoria");
            }
            if (maxRowsPerPage <= 0) {
                throw new IllegalStateException("maxRowsPerPage debe ser mayor que 0");
            }
            return new ExportConfig(this);
        }
    }
}