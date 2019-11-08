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
            + "      <text></text>\n"
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
