## Decisiones de diseño

### Decisión 1 — Factory Method vs. Abstract Factory
**Patrón elegido:** Abstract Factory.

**Justificación:** El sistema no crea un único producto, crea dos piezas
por exportación: el cuerpo (`ReportBody`) y el encabezado/pie
(`ReportHeaderFooter`), que deben pertenecer siempre al mismo formato
dentro de una misma exportación (no es válido combinar un cuerpo Excel
con un encabezado PDF). Eso es una familia de productos relacionados
que debe mantenerse coherente, que es justo lo que Abstract Factory
resuelve. Al agregar el futuro formato CSV, habría que crear una
familia completa nueva (`CsvReportBody` + `CsvHeaderFooter` +
`CsvReportFactory`), no una sola clase aislada. Y el riesgo real del
problema no es "instanciar la clase equivocada" de forma aislada, sino
mezclar piezas de familias distintas y dejar el documento
inconsistente — riesgo que Abstract Factory evita al agrupar ambos
métodos de creación (`createBody()` + `createHeaderFooter()`) en una
sola fábrica por formato.

**Por qué se descartó Factory Method:** resuelve bien un único producto
con variantes, pero aquí necesitaríamos dos jerarquías de Factory
Method separadas (una para el cuerpo, otra para el encabezado/pie) sin
ninguna garantía de que el cliente use la combinación correcta — nada
impediría combinar `PdfReportBody` con `ExcelHeaderFooter`.

### Decisión 2 — Mecanismo de extensibilidad de formatos
**Opción elegida:** Registro dinámico con `Map<String, Supplier<ReportFormatFactory>>`
(`ReportFactoryRegistry`).

**Justificación:** Un `switch` o `if/else` sobre el string de formato
obligaría a modificar ese método cada vez que se agregue un formato
nuevo, violando el principio Abierto/Cerrado (OCP). El registro
dinámico permite `register("csv", CsvReportFactory::new)` sin tocar
ninguna línea de código existente. Se usa `Supplier` en vez de
instancias precreadas para no instanciar fábricas que no se van a usar
en una ejecución dada.