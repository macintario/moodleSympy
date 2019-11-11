package com.uam.constantes;

/**
 *
 * @author Eduardo Mart&iacute;nez Cruz
 */
public class Constantes {

    public static final String HTML_INICIO_COMENTARIO = "<!--";
    public static final String HTML_FIN_COMENTARIO = "-->";
    public static final String TAG_CLOZE_INICIO_FRACCION = "$$\\displaystyle\\frac";
    public static final String TAG_CLOZE_FIN_SECCION = "$$";
    //agregar funcionalidad XML
    public static String XML_PREFIJO = "<!-- question: $COMENTARIO$  -->\n"
            + "  <question type=\"cloze\">\n"
            + "    <name>\n"
            + "      <text>$COMENTARIO$</text>\n"
            + "    </name>\n"
            + "    <questiontext format=\"html\">\n"
            + "      <text><![CDATA[";
    public static String XML_SUFIJO = "]]></text>\n"
            + "    </questiontext>\n"
            + "    <generalfeedback format=\"html\">\n"
            + "      <text><![CDATA["
            + "<span style=\"color: #000000; font-size: large;\"><strong>Instrucciones para hacer autoevaluaciones y poder presentar examen de unidad.</strong></span>"
            +"$$ a_2).$$ Nos interesa que usted domine el ejercicio aquí mostrado; para ello tendrá que seguir el procedimiento detallado que ofrece Symbolab. "
            +"(Cualquier duda con Symbolab, consulta a cualquier profesor de galoisenlinea, para ello acude a Sala SAV-01 en horarios de clase.) <br/>"
            +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$$ a_3).$$ Realice tantas autoevaluaciones como usted lo necesite."
            +"<br/><br/><span style=\"color: #000000; font-size: x-large\"><strong>SOLUCIÓN:</strong></span><br/>"
            +"$SOLUCION$"
            +"<span style=\"color: #000000; font-size: large\"><strong>PROCEDIMIENTO DE SOLUCION PASO A PASO</strong></span>"
            +"<br/><br/>En este ejercicio y con objeto de que uses uno de los mejores Sistemas Algebraicos Computacionales (CAS) llamado "
            +"<span style=\"color: #ff0000\"><strong>Symbolab</strong></span>, obtendrás el procedimiento de solución de este ejercicio paso a paso."
+"Es importante que analices el procedimiento dado por Symbolab; en examen tendrás que hacer el procedimiento detallado de algún ejercicio similar."
+"Si requieres revisión en algún punto de tu desarrollo en papel, muestra tu procedimiento detallado a algún profesor de galoisenlinea y con mucho gusto aclararemos tus dudas."
+"Click en cada fórmula en azul y copia el texto:"
+"La derivada de la función $$f(x)$$ es:"
+"$EXPRESION$"
+"Click en icono de Symbolab:     c. Pega en la ventana de entrada de datos de Symbolab tu texto copiado. Allí obtendrás la solución del ejercicio paso a paso."
+"Biblioteca en galoisenlinea:"
+"Detalles paso a paso para resolver el ejercicio del libro de Thomas en página y número de ejercicio: Thomas147-9, Click.... en imagen   c"
+"LIBRO DE TEXTO Y SOLUCIONARIO AL LIBRO DE TEXTO:"
+"TEXTO: Para teoría y ejercicios consulta el capítulo 3, página 102, 105, 115, 135 del libro: George B. Thomas, Jr. Decimosegunda edición Massachusetts Institute of Technology. c"
+"SOLUCIONARIO AL TEXTO: Consulta el libro: THOMAS' CALCULUS, Solution Manual, Twelfth Edition. c"
+"CAS (Sistemas algebraicos computacionales):"
+"En las autoevaluaciones, una vez que realices cada ejercicio, te sugerimos usar cualquiera de los siguientes Sistemas Algebraicos Computacionales (CAS) para comprobar tus cálculos."
+"En exámenes no podrás usar ninguno de estos CAS. Click en iconos para abrir CAS."
            + "    ]]></text>\n"
            + "    </generalfeedback>\n"
            + "    <penalty>0.3333333</penalty>\n"
            + "    <hidden>0</hidden>\n"
            + "  </question>";
    public static String GLOBAL_XML_PREFIJO = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<quiz>\n"
            + "<!-- question: 0  -->\n"
            + "  <question type=\"category\">\n"
            + "    <category>\n"
            + "        <text>$module$/Por defecto en Derivadas Test Iván</text>\n"
            + "    </category>\n"
            + "  </question>\n"
            + "";
        public static String GLOBAL_XML_SUFIJO = "</quiz>";

}
