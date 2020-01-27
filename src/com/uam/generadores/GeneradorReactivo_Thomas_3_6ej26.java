package com.uam.generadores;
/*
 *
 *
 *
 * */

import com.uam.auxiliar.GeneradorReactivoCloze;
import com.uam.auxiliar.solucionaSimbolico;
import com.uam.data.DatosReactivos;
import com.uam.executor.EjecutadorGeneradorXML;
import com.uam.utilidades.Utilidades;

import static com.uam.constantes.Constantes.XML_PREFIJO;
import static com.uam.constantes.Constantes.XML_SUFIJO;

public class GeneradorReactivo_Thomas_3_6ej26 implements GeneradorReactivoCloze {
    /**
     * El número de dígitos para el número de reactivo que se pondrá como
     * comentario del reactivo. e.g. si el número de posiciones es 3 entonces el
     * comentario que tendrá el primer reactivo será
     * "<!--Reactivo Thomas_3_6x26_000-->"
     */
    private static final int POSICIONES_CONTADOR_REACTIVO = 3;
    /**
     * El nombre o ruta absoluta del archivo de salida.
     */
    private static final String NOMBRE_ARCHIVO_SALIDA = "reactivos_Thomas_3_6ej26.xml";

    /**
     * El número de reactivos que se generarán y vaciarán al archivo de texto.
     */
    private static final int NUMERO_DE_REACTIVOS = 2;

    /**
     * El texto del reactivo, las variables se encuentran en mayúsculas y
     * encerradas entre signos $. La tildes deben ser colocadas con código utf8.
     */
    private static final String PLANTILLA_REACTIVO
            = "<span style=\"color: #ff0000,2E8B57; font-size: large;\"><strong>\n" +
            "galoisenlinea&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; http://galois.azc.uam.mx </strong></span>\n" +
            "<span style=\"color: #E38E03; font-size: large;\"><strong>\n" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; En este ejercicio aprenderás a aplicar la regla de la cadena</strong></span> <br><br>"
            + "<span style=\"color: #ff0000; font-size: xx-large;\"><strong>\n"
            + "PROBLEMA:\n"
            + "</strong></span>"
            + "<center><span style=\"color: #0000ff; font-size: x-large;\"><strong>"
            + "Considere la función: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            + "$$\\displaystyle y=$EXPRESION$ $$<br/>"
            + "</strong><br/><br/></span><span style=\"color: #ff0000; font-size: x-large;\"><strong>"
            + "<script type=\"math/tex\">\\bullet</script> &nbsp;&nbsp;&nbsp; Calculando la derivada de la función $$f($VARIABLE_INDEPENDIENTE$)$$ obtenemos que: </strong></span><br/><br/>"
            + "$RESPUESTA$"
            + "</strong></span><br/>"
            + "<span style=\"color: #000000; font-size: medium;\"><strong>"
            + "Usted deberá calcular la derivada $$f(x)$$ utilizando la regla de la cadena, indicando en papel todos los pasos. "
            + "<br/>Utilizando el resultado calculado por el sistema, deberás escribir en las cajas correspondientes los números que tú obtuviste. \n"
            + "<br/></strong></span>"
            + "$RESPUESTAS$<br/>"
            + "<span style=\"color: #FF4000; font-size: medium;\"><strong>\n" +
            "¿ Revisión de su ejercicio ? Escribirás en papel el procedimiento detallado que muestre cómo obtuviste tus respuestas. \n" +
            "</strong></span>";

    private static final int[] COTA_CONSTANTE_A = {1, 5};
    private static final int[] COTA_CONSTANTE_B = {2, 9};
    private static final int[] COTA_CONSTANTE_C = {2, 8};
    private static final int[] COTA_CONSTANTE_D = {2, 9};

    private static final String EXPRESION = "\\frac{$CONSTANTEA$}{x}\\sin^{-$CONSTANTEB$}(x) - \\frac{x}{$CONSTANTEC$}\\cos^{$CONSTANTED$}(x)";
    private String UX = "\\frac{$CONSTANTEA$}{x}";
    private String VX = "\\sin^{-$CONSTANTEB$}(x)";
    private String WX = "- \\frac{x}{$CONSTANTEC$}";
    private String ZX = "\\cos^{$CONSTANTED$}(x)";
    private String RESPUESTA= "$$\\displaystyle\\frac{df}{dx}=Ax^B\\sin^C(x)\\cos(x) + Dx^E\\sin^F(x) + \\frac{G}{K}x\\sin(x)\\cos^J(x)+\\frac{H}{N}\\cos^M(x)$$ <br/>";
    private String CAJAS_RESPUESTA = "$$A=$${1:SHORTANSWER:=$RESPUESTA_A$} <br/> $$B=$${1:SHORTANSWER:=$RESPUESTA_B$} <br/> "
            + "$$C=$${1:SHORTANSWER:=$RESPUESTA_C$} <br/> $$D=$${1:SHORTANSWER:=$RESPUESTA_D$}<br/> "
            + "$$E=$${1:SHORTANSWER:=$RESPUESTA_E$} <br/> $$F=$${1:SHORTANSWER:=$RESPUESTA_F$}<br/> "
            + "$$G=$${1:SHORTANSWER:=$RESPUESTA_G$} <br/> $$H=$${1:SHORTANSWER:=$RESPUESTA_H$} <br/> "
            + "$$J=$${1:SHORTANSWER:=$RESPUESTA_J$} <br/> $$K=$${1:SHORTANSWER:=$RESPUESTA_K$} <br/> "
            + "$$M=$${1:SHORTANSWER:=$RESPUESTA_M$} <br/> $$N=$${1:SHORTANSWER:=$RESPUESTA_N$} <br/>"
            + "<span style=\"color: #ff0000; font-size: x-large;\"><strong>"
            + "<script type=\"math/tex\">\\bullet</script> &nbsp;&nbsp;&nbsp; Los números $$A,B,C,D,E,F,G,H,J,K,M,N$$ en este orden y que dan solución correcta al ejercicio son: </strong></span>"
            + " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            +" {20:SHORTANSWER:=$RESPUESTA_A$,$RESPUESTA_B$,$RESPUESTA_C$,$RESPUESTA_D$,$RESPUESTA_E$,$RESPUESTA_F$,$RESPUESTA_G$,$RESPUESTA_H$,"
            + "$RESPUESTA_J$,$RESPUESTA_K$,$RESPUESTA_M$,$RESPUESTA_N$}</center> <br>"
            + "</center>";
/**
 *
 */
    /**
     * El comentario que se pondrá a cada reactivo para etiquetarlo, el sufijo
     * sera el número de reactivo. Éste se insertará como un comentario html
     * para que no sea visible para el usuario. El lugar de inserción de este
     * comentario dentro del texto del reactivo esta dado por la variable
     * $COMENTARIO$ en la plantilla del reactivo.
     */
    private static final String COMENTARIO_REACTIVO_PREFIJO = "Reactivo Thomas_3.6_Ej_26_";
    private static final String SEPARADOR_REACTIVOS = "\r\n";

    @Override
    public String generarReactivoCloze(int numeroReactivo) {
        String solucion = "";
        String ux = UX;
        String vx = VX;
        String wx = WX;
        String zx = ZX;        //Generación de variables aleatorias con parámetros de ejecución
        Integer constanteA = Utilidades.obtenerImparAleatorio(COTA_CONSTANTE_A[0], COTA_CONSTANTE_A[1]);
        Integer constanteB = Utilidades.obtenerImparAleatorioDistintoDe(COTA_CONSTANTE_B[0], COTA_CONSTANTE_B[1], constanteA);
        Integer constanteC = Utilidades.obtenerImparAleatorioDistintoDe(COTA_CONSTANTE_B[0], COTA_CONSTANTE_B[1], constanteB);
        Integer constanteD = Utilidades.obtenerEnteroAleatorioDistintoDe(COTA_CONSTANTE_D[0], COTA_CONSTANTE_D[1], constanteC);
        String comentarioReactivo
                = Utilidades.generaComentario(COMENTARIO_REACTIVO_PREFIJO, numeroReactivo, POSICIONES_CONTADOR_REACTIVO);
        Integer respuestaA = constanteA*constanteB;
        Integer respuestaB = -1;
        Integer respuestaC = -constanteB - 1;
        Integer respuestaD = constanteA;
        Integer respuestaE = -2;
        Integer respuestaF = -constanteB;
        Integer respuestaG = constanteD;
        Integer respuestaH = 1;
        Integer respuestaJ = constanteD - 1;
        Integer respuestaK = constanteC;
        Integer respuestaM = constanteD;
        Integer respuestaN = constanteC;
        String parVariables = DatosReactivos.obtenerParesVariables();
        String variableIndependiente = parVariables.substring(0, 1);
        String variableDependiente = parVariables.substring(1, 2);

        //Sustitución de las variables por sus valores en el texto del reactivo
        String reactivo = XML_PREFIJO + PLANTILLA_REACTIVO + XML_SUFIJO;
        String expresion = EXPRESION;

        expresion = expresion.replace("$CONSTANTEA$", constanteA.toString());
        expresion = expresion.replace("$CONSTANTEB$", constanteB.toString());
        expresion = expresion.replace("$CONSTANTEC$", constanteC.toString());
        expresion = expresion.replace("$CONSTANTED$", constanteD.toString());

        reactivo = reactivo.replace("$RESPUESTAS$", CAJAS_RESPUESTA);
        reactivo = reactivo.replace("$RESPUESTA$", RESPUESTA);
        reactivo = reactivo.replace("$EXPRESION$", expresion);
        reactivo = reactivo.replace("$COMENTARIO$", comentarioReactivo);
        reactivo = reactivo.replace("$VARIABLE_INDEPENDIENTE$", variableIndependiente);
        reactivo = reactivo.replace("$VARIABLE_DEPENDIENTE$", variableDependiente);
        reactivo = reactivo.replace("$RESPUESTA_A$", respuestaA.toString());
        reactivo = reactivo.replace("$RESPUESTA_B$", respuestaB.toString());
        reactivo = reactivo.replace("$RESPUESTA_C$", respuestaC.toString());
        reactivo = reactivo.replace("$RESPUESTA_D$", respuestaD.toString());
        reactivo = reactivo.replace("$RESPUESTA_E$", respuestaE.toString());
        reactivo = reactivo.replace("$RESPUESTA_F$", respuestaF.toString());
        reactivo = reactivo.replace("$RESPUESTA_G$", respuestaG.toString());
        reactivo = reactivo.replace("$RESPUESTA_H$", respuestaH.toString());
        reactivo = reactivo.replace("$RESPUESTA_J$", respuestaJ.toString());
        reactivo = reactivo.replace("$RESPUESTA_K$", respuestaK.toString());
        reactivo = reactivo.replace("$RESPUESTA_M$", respuestaM.toString());
        reactivo = reactivo.replace("$RESPUESTA_N$", respuestaN.toString());


        ux = ux.replace("$CONSTANTEA$", constanteA.toString());
        ux = ux.replace("$CONSTANTEB$", constanteB.toString());
        ux = ux.replace("$CONSTANTEC$", constanteC.toString());
        ux = ux.replace("$CONSTANTED$", constanteD.toString());

        vx = vx.replace("$CONSTANTEA$", constanteA.toString());
        vx = vx.replace("$CONSTANTEB$", constanteB.toString());
        vx = vx.replace("$CONSTANTEC$", constanteC.toString());
        vx = vx.replace("$CONSTANTED$", constanteD.toString());

        wx = wx.replace("$CONSTANTEA$", constanteA.toString());
        wx = wx.replace("$CONSTANTEB$", constanteB.toString());
        wx = wx.replace("$CONSTANTEC$", constanteC.toString());
        wx = wx.replace("$CONSTANTED$", constanteD.toString());

        zx = zx.replace("$CONSTANTEA$", constanteA.toString());
        zx = zx.replace("$CONSTANTEB$", constanteB.toString());
        zx = zx.replace("$CONSTANTEC$", constanteC.toString());
        zx = zx.replace("$CONSTANTED$", constanteD.toString());

        solucion = solucionaSimbolico.sumaProductosCadena(ux, vx, wx, zx);

        reactivo = reactivo.replace("$SOLUCION$", solucion);

        //Concatenando el separador de reactivos
        reactivo = reactivo.concat(SEPARADOR_REACTIVOS);
        return reactivo;
    }

    public static void main(String[] args) {
        EjecutadorGeneradorXML.generarReactivos(NOMBRE_ARCHIVO_SALIDA, NUMERO_DE_REACTIVOS, new GeneradorReactivo_Thomas_3_6ej26());
    }


}
