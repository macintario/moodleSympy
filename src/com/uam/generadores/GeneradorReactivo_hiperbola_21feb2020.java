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

public class GeneradorReactivo_hiperbola_21feb2020 implements GeneradorReactivoCloze {
    /**
     * El número de dígitos para el número de reactivo que se pondrá como
     * comentario del reactivo. e.g. si el número de posiciones es 3 entonces el
     * comentario que tendrá el primer reactivo será
     * "<!--Reactivo hiperbola_21feb2020_000-->"
     */
    private static final int POSICIONES_CONTADOR_REACTIVO = 3;
    /**
     * El nombre o ruta absoluta del archivo de salida.
     */
    private static final String NOMBRE_ARCHIVO_SALIDA = "reactivos_hiperbola_21feb2020.xml";

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
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; En este ejercicio encontrarás la recta tangente a una curva en un punto</strong></span> <br><br>"
            + "<span style=\"color: #ff0000; font-size: xx-large;\"><strong>\n"
            + "PROBLEMA:\n"
            + "</strong></span>"
            + "<center><span style=\"color: #0000ff; font-size: x-large;\"><strong>"
            + "Escribe la ecuación de le recta tangente a la curva $$y=f(x)$$ en el punto $$P(x_{0},f(x_{0}))$$:<br/><br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            + "$$\\displaystyle f(x)=$EXPRESION$ $$<br/> $$\\displaystyle x_{0}=$EQUIS0$ $$<br/>"
            + "</strong><br/><br/></span><span style=\"color: #ff0000; font-size: x-large;\"><strong>"
            + "<script type=\"math/tex\">\\bullet</script> &nbsp;&nbsp;&nbsp;"
            +" Calculando la derivada de la función $$f($VARIABLE_INDEPENDIENTE$)$$ y evaluando en el punto $$x_{0}$$, la ecuación de la recta tangente es: </strong></span><br/><br/>"
            + "$RESPUESTA$"
            + "</strong></span><br/>"
            + "<span style=\"color: #000000; font-size: medium;\"><strong>"
            + "Usted deberá calcular la derivada $$f(x)$$, evaluando la función y la derivada en el punto $$ x_{0} $$ y sustituyendo en la ecuación de la recta"
            + "<br/> $$ y-f(x_{0})=f'(x_{0})(x-x_{0})$$ \n"
            + "<br/>Utilizando el resultado calculado por el sistema, deberás escribir en las cajas correspondientes los números que tú obtuviste. \n"
            + "<br/></strong></span>"
            + "$RESPUESTAS$<br/>"
            + "<span style=\"color: #FF4000; font-size: medium;\"><strong>\n" +
            "¿ Revisión de su ejercicio ? Escribirás en papel el procedimiento detallado que muestre cómo obtuviste tus respuestas. \n" +
            "</strong></span>";

    private static final int[] COTA_CONSTANTE_A = {-4, 4};
    private static final int[] COTA_CONSTANTE_B = {-4, 4};
    private static final int[] COTA_CONSTANTE_C = {-9, 9};
    private static final int[] COTA_CONSTANTE_D = {3, 9};
    private static final int[] COTA_CONSTANTE_E = {2, 9};
    private static final int[] COTA_CONSTANTE_F = {2, 9};
    private static final int[] COTA_CONSTANTE_G = {3, 5};
    private static final int[] COTA_CONSTANTE_H = {2, 5};
    private static final int[] COTA_X_0 = {-3, 3};

    private static final String EXPRESION = "\\frac{1}{$CONSTANTEA$x+$CONSTANTEB$}";
    private String RESPUESTA= "$$\\displaystyle y=\\frac{A}{B}x+\\frac{C}{D}$$ <br/>";
    private String CAJAS_RESPUESTA = "$$A=$${1:SHORTANSWER:=$RESPUESTA_A$} <br/> $$B=$${1:SHORTANSWER:=$RESPUESTA_B$} <br/> "
            + "$$C=$${1:SHORTANSWER:=$RESPUESTA_C$} <br/> $$D=$${1:SHORTANSWER:=$RESPUESTA_D$}<br/> "
            + "<span style=\"color: #ff0000; font-size: x-large;\"><strong>"
            + "<script type=\"math/tex\">\\bullet</script> &nbsp;&nbsp;&nbsp; Los números $$A,B,C,D$$ en este orden "
            + "y que dan solución correcta al ejercicio son: </strong></span>"
            + " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            +" {20:SHORTANSWER:=$RESPUESTA_A$,$RESPUESTA_B$,$RESPUESTA_C$,$RESPUESTA_D$"
            + "}</center> <br>"
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
    private static final String COMENTARIO_REACTIVO_PREFIJO = "Reactivo hiperbola_21feb2020_";
    private static final String SEPARADOR_REACTIVOS = "\r\n";

    @Override
    public String generarReactivoCloze(int numeroReactivo) {
        String solucion = "";
        //Generación de variables aleatorias con parámetros de ejecución
        Integer constanteA = Utilidades.obtenerEnteroAleatorioDistintoDe(COTA_CONSTANTE_A[0], COTA_CONSTANTE_A[1],0);
        Integer constanteB = Utilidades.obtenerEnteroAleatorioDistintoDe(COTA_CONSTANTE_B[0], COTA_CONSTANTE_B[1],0);
        Integer constanteC = Utilidades.obtenerEnteroAleatorioDistintoDe(COTA_CONSTANTE_C[0], COTA_CONSTANTE_C[1],0);
        Integer constanteD =  Utilidades.obtenerEnteroAleatorio(COTA_CONSTANTE_D[0], COTA_CONSTANTE_D[1]);
        Integer constanteE = constanteA;
        Integer constanteF = constanteC;
        Integer constanteG = Utilidades.obtenerEnteroAleatorio(COTA_CONSTANTE_G[0], COTA_CONSTANTE_G[1]);
        Integer constanteH = Utilidades.obtenerImparAleatorioDistintoDe(COTA_CONSTANTE_H[0], COTA_CONSTANTE_H[1], constanteF);
        Integer x_0 = Utilidades.obtenerEnteroAleatorioDistintoDe(COTA_X_0[0], COTA_X_0[1],-constanteB/constanteA);
        String comentarioReactivo = Utilidades.generaComentario(COMENTARIO_REACTIVO_PREFIJO, numeroReactivo, POSICIONES_CONTADOR_REACTIVO);
        Integer y_0_d = constanteA*x_0+constanteB;
        Integer respuestaA = constanteA;
        Integer respuestaB = (constanteA*x_0+constanteB)*(constanteA*x_0+constanteB);
        Integer respuestaC = 2*constanteA*x_0+constanteB;
        Integer respuestaD = (constanteA*x_0+constanteB)*(constanteA*x_0+constanteB);
        Integer respuestaE = constanteA;
        Integer respuestaF = constanteC;
        Integer respuestaG = constanteA;
        Integer respuestaH = 2;
        Integer respuestaJ = constanteC;
        Integer respuestaK = constanteA;
        Integer respuestaL = 3*constanteA-1;
        Integer respuestaM = constanteA;
        Integer respuestaN = constanteB;
        Integer respuestaP = constanteA;
        //Checar fracción reductible respuestaE y respuestaJ
        Integer divisor = maximoComunDivisor(respuestaA, respuestaB);
        respuestaA /= divisor;
        respuestaB /= divisor;
        divisor = maximoComunDivisor(respuestaC, respuestaD);
        respuestaC /= divisor;
        respuestaD /= divisor;

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
        expresion = expresion.replace("+-", "-");

        reactivo = reactivo.replace("$RESPUESTAS$", CAJAS_RESPUESTA);
        reactivo = reactivo.replace("$RESPUESTA$", RESPUESTA);
        //cambiar el problema para punto  tangente
        reactivo = reactivo.replace("\\frac{d}{dx}\\left($EXPRESION$ \\right)",
                "tangente de f\\left(x\\right)=$EXPRESION$, at($EQUIS0$,\\frac{1}{$YE0$})");
        reactivo = reactivo.replace("<strong>La derivada de la función $$f(x)$$ es:</strong>",
                "<strong>La recta tangente a la función $$f(x)$$ en el punto $$x_0=$EQUIS0$ $$  es:</strong>");
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
        reactivo = reactivo.replace("$EQUIS0$", x_0.toString());
        reactivo = reactivo.replace("$YE0$", y_0_d.toString());

        solucion = solucionaSimbolico.rectaTangente(expresion,x_0);

        reactivo = reactivo.replace("$SOLUCION$", solucion);

        //Concatenando el separador de reactivos
        reactivo = reactivo.concat(SEPARADOR_REACTIVOS);
        return reactivo;
    }

    public static void main(String[] args) {
        EjecutadorGeneradorXML.generarReactivos(NOMBRE_ARCHIVO_SALIDA, NUMERO_DE_REACTIVOS, new GeneradorReactivo_hiperbola_21feb2020());
    }


}
