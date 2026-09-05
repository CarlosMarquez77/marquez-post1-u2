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

### Decisión 3 — Builder vs. constructor telescópico vs. setters
**Patrón elegido:** Builder (clase interna `ExportConfig.Builder`).

**Justificación:** Con 1 parámetro obligatorio y 8 opcionales, las otras
dos opciones fallan por razones concretas:

- **Constructor con 9 parámetros:** varios parámetros comparten tipo
  (varios `String` seguidos, luego dos `boolean`), así que el
  compilador no detecta si el cliente invierte el orden de dos
  argumentos del mismo tipo.
- **Constructores sobrecargados:** con 8 opcionales, el número de
  combinaciones razonables crece rápido y la clase terminaría con
  demasiados constructores casi idénticos.
- **Setters sueltos sobre un objeto mutable:** el objeto podría quedar a
  medio configurar, y no hay un punto único donde validar una
  combinación inconsistente (por ejemplo, pedir `compress=true` sin
  `outputPath`, que no tiene sentido).

Builder resuelve las tres limitaciones: los métodos encadenables son
autoexplicativos y sin riesgo de orden, solo hay una clase, y `build()`
centraliza la validación de estado antes de construir el objeto — como
demuestra el `IllegalStateException` al pedir `compress(true)` sin
`outputPath`.

### Decisión 4 — ¿ReportFactoryRegistry necesita ser Singleton?
**Conclusión:** NO conviene convertirlo en Singleton.

**Justificación:**
- **Identidad de objeto:** en este proyecto, `ReportFactoryRegistry` se
  usa siempre invocando sus métodos estáticos directamente
  (`resolve()`, `register()`); en ningún punto se necesita tratarlo
  como un objeto sustituible, inyectable o polimórfico.
- **Inicialización costosa:** el bloque `static {}` solo llena un `Map`
  con tres entradas — no hay trabajo costoso que justifique una
  inicialización perezosa.
- **Fuente única de verdad:** el campo `REGISTRY` ya es `static final`,
  lo que garantiza una única copia compartida en toda la JVM sin
  necesitar la maquinaria de Singleton (constructor con guardas,
  `getInstance()`, sincronización).
- **Escenarios futuros:** el único caso donde Singleton cambiaría de
  conveniencia sería una plataforma multi-institución con un registro
  independiente por institución — pero ahí Singleton dejaría de ser
  apropiado por la razón contraria (se necesitarían varias instancias).
  Ese escenario no existe en el alcance actual del proyecto.

Convertirlo en Singleton agregaría ceremonia sin resolver ningún
problema real — el caso que advierte la Sección 2.4 de la Guía Teórica
sobre clases utilitarias con solo miembros estáticos.

## Conclusiones
Este post-contenido dejó claro que elegir un patrón creacional no
depende de cuál se conoce mejor, sino de qué estructura describe
realmente el problema: la necesidad de mantener coherentes dos piezas
relacionadas (cuerpo y encabezado/pie) por formato fue lo que hizo que
Abstract Factory encajara mejor que Factory Method, no una preferencia
arbitraria. De forma similar, Builder resultó adecuado no porque un
objeto con muchos parámetros "siempre" deba usar ese patrón, sino
porque el problema concreto (validar un estado consistente antes de
construir el objeto) era exactamente lo que Builder resuelve y las
otras opciones no. La Parte 2 también mostró que no todo registro
central merece convertirse en Singleton: aplicar un patrón sin evaluar
si resuelve un problema real solo agrega complejidad innecesaria. En
general, el ejercicio reforzó que reconocer cuándo *no* aplicar un
patrón es tan parte del diseño de software como saber cuándo sí
aplicarlo.