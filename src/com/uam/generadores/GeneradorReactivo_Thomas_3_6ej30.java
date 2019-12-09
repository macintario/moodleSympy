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
import static com.uam.utilidades.Utilidades.maximoComunDivisor;

public class GeneradorReactivo_Thomas_3_6ej30 implements GeneradorReactivoCloze {
    /**
     * El número de dígitos para el número de reactivo que se pondrá como
     * comentario del reactivo. e.g. si el número de posiciones es 3 entonces el
     * comentario que tendrá el primer reactivo será
     * "<!--Reactivo Thomas_3_6x30_000-->"
     */
    private static final int POSICIONES_CONTADOR_REACTIVO = 3;
    /**
     * El nombre o ruta absoluta del archivo de salida.
     */
    private static final String NOMBRE_ARCHIVO_SALIDA = "reactivos_Thomas_3_6ej30.xml";

    /**
     * El número de reactivos que se generarán y vaciarán al archivo de texto.
     */
    private static final int NUMERO_DE_REACTIVOS = 3;

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
            + "$$\\displaystyle f(x)=$EXPRESION$ $$<br/>"
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

    private static final int[] COTA_CONSTANTE_A = {2, 7};
    private static final int[] COTA_CONSTANTE_B = {2, 9};
    private static final int[] COTA_CONSTANTE_C = {2, 8};
    private static final int[] COTA_CONSTANTE_D = {3, 9};
    private static final int[] COTA_CONSTANTE_E = {2, 9};
    private static final int[] COTA_CONSTANTE_F = {2, 9};
    private static final int[] COTA_CONSTANTE_G = {2, 5};
    private static final int[] COTA_CONSTANTE_H = {2, 5};

    private static final String EXPRESION = "($CONSTANTEA$x+$CONSTANTEB$)^{-$CONSTANTEC$}  ($CONSTANTED$x^{$CONSTANTEE$}-$CONSTANTEF$x^{$CONSTANTEG$})^{$CONSTANTEH$}";
    private String UX = "($CONSTANTEA$x+$CONSTANTEB$)";
    private String VX = "($CONSTANTED$x^{$CONSTANTEE$}-$CONSTANTEF$x^{$CONSTANTEG$})";
    private String N = "-$CONSTANTEC$";
    private String M = "$CONSTANTEH$";
    private String A = "\\frac{1}{$CONSTANTEA$}";
    private String RESPUESTA= "$$\\displaystyle\\frac{df}{dx}=\\frac{A(Bx+C)^{D}}{(Ex+F)^{G}}- \\frac{H(Jx+K)^{L}}{(Mx+N)^{P}} $$ <br/>";
    private String CAJAS_RESPUESTA = "$$A=$${1:SHORTANSWER:=$RESPUESTA_A$} <br/> $$B=$${1:SHORTANSWER:=$RESPUESTA_B$} <br/> "
            + "$$C=$${1:SHORTANSWER:=$RESPUESTA_C$} <br/> $$D=$${1:SHORTANSWER:=$RESPUESTA_D$}<br/> "
            + "$$E=$${1:SHORTANSWER:=$RESPUESTA_E$} <br/> $$F=$${1:SHORTANSWER:=$RESPUESTA_F$}<br/> "
            + "$$G=$${1:SHORTANSWER:=$RESPUESTA_G$} <br/> $$H=$${1:SHORTANSWER:=$RESPUESTA_H$} <br/> "
            + "$$J=$${1:SHORTANSWER:=$RESPUESTA_J$} <br/> $$K=$${1:SHORTANSWER:=$RESPUESTA_K$} <br/>"
            + "$$L=$${1:SHORTANSWER:=$RESPUESTA_L$} <br/> $$M=$${1:SHORTANSWER:=$RESPUESTA_M$} <br/>"
            + "$$N=$${1:SHORTANSWER:=$RESPUESTA_N$} <br/> $$P=$${1:SHORTANSWER:=$RESPUESTA_P$} <br/>"
            + "<span style=\"color: #ff0000; font-size: x-large;\"><strong>"
            + "<script type=\"math/tex\">\\bullet</script> &nbsp;&nbsp;&nbsp; Los números $$A,B,C,D,E,F,G,H,J,K,L,M,N,P$$ en este orden "
            + "y que dan solución correcta al ejercicio son: </strong></span>"
            + " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            +" {20:SHORTANSWER:=$RESPUESTA_A$,$RESPUESTA_B$,$RESPUESTA_C$,$RESPUESTA_D$,$RESPUESTA_E$,$RESPUESTA_F$,$RESPUESTA_G$,$RESPUESTA_H$,"
            + "$RESPUESTA_J$,$RESPUESTA_K$,$RESPUESTA_L$,$RESPUESTA_M$,$RESPUESTA_N$,$RESPUESTA_P$}</center> <br>"
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
     *
     */
    private static final String COMENTARIO_REACTIVO_PREFIJO = "Reactivo Thomas_3.6_Ej_30_";
    private static final String SEPARADOR_REACTIVOS = "\r\n";

    @Override
    public String generarReactivoCloze(int numeroReactivo) {
        String solucion = "";
        String ux = UX;
        String vx = VX;
        String n = N;
        String m = M;
        String a = A;
        //Generación de variables aleatorias con parámetros de ejecución
        Integer constanteA = Utilidades.obtenerImparAleatorio(COTA_CONSTANTE_A[0], COTA_CONSTANTE_A[1]);
        Integer constanteB = Utilidades.obtenerImparAleatorioDistintoDe(COTA_CONSTANTE_B[0], COTA_CONSTANTE_B[1], constanteA);
        Integer constanteC = Utilidades.obtenerImparAleatorioDistintoDe(COTA_CONSTANTE_B[0], COTA_CONSTANTE_B[1], constanteB);
        Integer constanteD = Utilidades.obtenerEnteroAleatorio(COTA_CONSTANTE_D[0], COTA_CONSTANTE_D[1]);
        Integer constanteE = Utilidades.obtenerImparAleatorioDistintoDe(COTA_CONSTANTE_E[0], COTA_CONSTANTE_E[1], constanteA);
        Integer constanteF = Utilidades.obtenerImparAleatorioDistintoDe(COTA_CONSTANTE_F[0], COTA_CONSTANTE_F[1], constanteA);
        Integer constanteG = Utilidades.obtenerEnteroAleatorioDistintoDe(COTA_CONSTANTE_G[0], COTA_CONSTANTE_G[1], constanteE );
        Integer constanteH = Utilidades.obtenerImparAleatorioDistintoDe(COTA_CONSTANTE_H[0], COTA_CONSTANTE_H[1], constanteF);

        String comentarioReactivo
                = Utilidades.generaComentario(COMENTARIO_REACTIVO_PREFIJO, numeroReactivo, POSICIONES_CONTADOR_REACTIVO);
        Integer respuestaA = -constanteB*(-constanteC);
        Integer respuestaB = -constanteB;
        Integer respuestaC = constanteA;
        Integer respuestaD = constanteD+1;
        Integer respuestaE = constanteE*constanteG;
        Integer respuestaF = 1;
        Integer respuestaG = 1;

        Integer respuestaH = constanteG-1;
        Integer respuestaJ = constanteD;
        Integer respuestaK = constanteG+1;
        Integer respuestaL = constanteG+1;
        Integer respuestaM = constanteD;
        Integer respuestaN = constanteC;
        Integer respuestaP = constanteC;
        //Checar fracción reductible respuestaE y respuestaJ
        Integer divisor = maximoComunDivisor(respuestaE, respuestaJ);
        respuestaE /= divisor;
        respuestaJ /= divisor;

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
        expresion = expresion.replace("$CONSTANTEE$", constanteE.toString());
        expresion = expresion.replace("$CONSTANTEF$", constanteF.toString());
        expresion = expresion.replace("$CONSTANTEG$", constanteG.toString());
        expresion = expresion.replace("$CONSTANTEH$", constanteH.toString());

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
        reactivo = reactivo.replace("$RESPUESTA_L$", respuestaL.toString());
        reactivo = reactivo.replace("$RESPUESTA_M$", respuestaM.toString());
        reactivo = reactivo.replace("$RESPUESTA_N$", respuestaN.toString());
        reactivo = reactivo.replace("$RESPUESTA_P$", respuestaP.toString());


        ux = ux.replace("$CONSTANTEA$", constanteA.toString());
        ux = ux.replace("$CONSTANTEB$", constanteB.toString());
        ux = ux.replace("$CONSTANTEC$", constanteC.toString());
        ux = ux.replace("$CONSTANTED$", constanteD.toString());
        ux = ux.replace("$CONSTANTEE$", constanteE.toString());
        ux = ux.replace("$CONSTANTEF$", constanteF.toString());
        ux = ux.replace("$CONSTANTEG$", constanteG.toString());
        ux = ux.replace("$CONSTANTEH$", constanteH.toString());

        vx = vx.replace("$CONSTANTEA$", constanteA.toString());
        vx = vx.replace("$CONSTANTEB$", constanteB.toString());
        vx = vx.replace("$CONSTANTEC$", constanteC.toString());
        vx = vx.replace("$CONSTANTED$", constanteD.toString());
        vx = vx.replace("$CONSTANTEE$", constanteE.toString());
        vx = vx.replace("$CONSTANTEF$", constanteF.toString());
        vx = vx.replace("$CONSTANTEG$", constanteG.toString());
        vx = vx.replace("$CONSTANTEH$", constanteH.toString());

        n = n.replace("$CONSTANTEA$", constanteA.toString());
        n = n.replace("$CONSTANTEB$", constanteB.toString());
        n = n.replace("$CONSTANTEC$", constanteC.toString());
        n = n.replace("$CONSTANTED$", constanteD.toString());
        n = n.replace("$CONSTANTEE$", constanteE.toString());
        n = n.replace("$CONSTANTED$", constanteD.toString());
        n = n.replace("$CONSTANTEG$", constanteG.toString());

        m = m.replace("$CONSTANTEA$", constanteA.toString());
        m = m.replace("$CONSTANTEB$", constanteB.toString());
        m = m.replace("$CONSTANTEC$", constanteC.toString());
        m = m.replace("$CONSTANTED$", constanteD.toString());
        m = m.replace("$CONSTANTEE$", constanteE.toString());
        m = m.replace("$CONSTANTEF$", constanteF.toString());
        m = m.replace("$CONSTANTEH$", constanteH.toString());

        a = a.replace("$CONSTANTEA$", constanteA.toString());
        a = a.replace("$CONSTANTED$", constanteD.toString());

        solucion = solucionaSimbolico.productoPotenciasCadena(ux, n, vx, m);

        reactivo = reactivo.replace("$SOLUCION$", solucion);

        //Concatenando el separador de reactivos
        reactivo = reactivo.concat(SEPARADOR_REACTIVOS);
        return reactivo;
    }

    public static void main(String[] args) {
        EjecutadorGeneradorXML.generarReactivos(NOMBRE_ARCHIVO_SALIDA, NUMERO_DE_REACTIVOS, new GeneradorReactivo_Thomas_3_6ej30());
    }


}
