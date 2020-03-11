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

public class GeneradorReactivo_MRUA_21feb2020 implements GeneradorReactivoCloze {
    /**
     * El número de dígitos para el número de reactivo que se pondrá como
     * comentario del reactivo. e.g. si el número de posiciones es 3 entonces el
     * comentario que tendrá el primer reactivo será
     * "<!--Reactivo MRUA_21feb2020_000-->"
     */
    private static final int POSICIONES_CONTADOR_REACTIVO = 3;
    /**
     * El nombre o ruta absoluta del archivo de salida.
     */
    private static final String NOMBRE_ARCHIVO_SALIDA = "reactivos_MRUA_21feb2020.xml";

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
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; En este ejercicio calcularás la posicion y velocidad en Movimiento Rectilineo Uniformemente Acelerado (Thomas 12a ed. p. 128 ej 4) </strong></span> <br><br>"
            + "<span style=\"color: #ff0000; font-size: xx-large;\"><strong>\n"
            + "PROBLEMA:\n"
            + "</strong></span>"
            + "<center><span style=\"color: #0000ff; font-size: x-large;\"><strong>"
            + "Una explosión de dinamita lanza una roca directamente hacia arriba con una velocidad de"
            + " $$ $CONSTANTEA$ \\frac{m}{seg}$$. Alcanza una altura de $$s=$CONSTANTEB$t-4.9t^2 $$ "
            + "al cabo de $$t$$ segundos. <br/><br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            + "<ol type=\"a\">"
            + "   <li>¿Qué altura alcanza la roca?{1:NUMERICAL:=$RESPUESTA_A$:$tolerancia$}</li>"
            + "   <li>¿Cuáles son la velocidad y la rapidez de la roca cuando la roca está $$ $CONSTANTEC$ m"
            + " por arriba del del suelo durnate el ascenso?{1:NUMERICAL:=$RESPUESTA_B$:$tolerancia$} ¿Durante el descenso? {1:NUMERICAL:=$RESPUESTA_C$:$tolerancia$}</li>"
            + "   <li>¿Cuál es la aceleración de la roca en cualquier instante t durante su trayecto (después de la explosión)?{1:NUMERICAL:=$RESPUESTA_D$:$tolerancia$}</li>"
            + "   <li>¿Cuándo choca la roca contra el suelo?{1:NUMERICAL:=$RESPUESTA_E$:$tolerancia$}</li>"
            + "</ol>"
            + "</strong><br/><br/></span><span style=\"color: #ff0000; font-size: x-large;\"><strong>"
            + "<script type=\"math/tex\">\\bullet</script> &nbsp;&nbsp;&nbsp;"
            +"  </strong></span><br/><br/>"
            + "<span style=\"color: #000000; font-size: medium;\"><strong>"
            + "Usted deberá calcular la derivada $$f(x)$$, igualarla a cero y encontrar las raices, para luego escribir las coordenadas de cada punto<br/>"
            + "<span style=\"color: #FF4000; font-size: medium;\"><strong>\n" +
            "¿ Revisión de su ejercicio ? Escribirás en papel el procedimiento detallado que muestre cómo obtuviste tus respuestas. \n" +
            "</strong></span>";

    private static final int[] COTA_CONSTANTE_A = {-3, 3};
    private static final int[] COTA_CONSTANTE_B = {-5, 5};
    private static final int[] COTA_CONSTANTE_C = {-5, 5};
    private static final int[] COTA_CONSTANTE_D = {-9, 9};
    private static final int[] COTA_CONSTANTE_E = {2, 9};
    private static final int[] COTA_CONSTANTE_F = {2, 9};
    private static final int[] COTA_CONSTANTE_G = {3, 5};
    private static final int[] COTA_CONSTANTE_H = {2, 5};
    private static final int[] COTA_R = {-4, 4};

    private static final String EXPRESION = "$CONSTANTEA$x^3+$CONSTANTEB$x^2+$CONSTANTEC$x+$CONSTANTED$";
    private String RESPUESTA= "$$\\displaystyle P_1($${1:SHORTANSWER:=$RESPUESTA_A$}$$,$${1:SHORTANSWER:=$RESPUESTA_B$}$$)$$ <br/>"+
            "$$\\displaystyle P_2($${1:SHORTANSWER:=$RESPUESTA_C$}$$,$${1:SHORTANSWER:=$RESPUESTA_D$}$$)$$ <br/>";
    private String CAJAS_RESPUESTA = "$$A=$${1:SHORTANSWER:=$RESPUESTA_A$} <br/> $$B=$${1:SHORTANSWER:=$RESPUESTA_B$}) <br/> "
            + "<span style=\"color: #ff0000; font-size: x-large;\"><strong>"
            + "<script type=\"math/tex\">\\bullet</script> &nbsp;&nbsp;&nbsp; Los números $$A,B$$ en este orden "
            + "y que dan solución correcta al ejercicio son: </strong></span>"
            + " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            +" {20:SHORTANSWER:=$RESPUESTA_A$,$RESPUESTA_B$"
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
    private static final String COMENTARIO_REACTIVO_PREFIJO = "Reactivo MRUA_21feb2020_";
    private static final String SEPARADOR_REACTIVOS = "\r\n";

    @Override
    public String generarReactivoCloze(int numeroReactivo) {
        String solucion = "";
        //Generación de variables aleatorias con parámetros de ejecución
        Integer r1 = Utilidades.obtenerEnteroAleatorioDistintoDe(COTA_R[0], COTA_R[1],0);
        Integer r2;
        do {
            r2 = Utilidades.obtenerEnteroAleatorioDistintoDe(COTA_R[0], COTA_R[1], 0);
        }while(r2.equals(r1));
        if(r2>r1){
            Integer tmp = r2;
            r2=r1;
            r1=tmp;
        }
        Integer constanteA = 2;
        Integer constanteB = (r1+r2)*3;
        Integer constanteC = 6*r1*r2;
        Integer constanteD =  Utilidades.obtenerEnteroAleatorioDistintoDe(COTA_CONSTANTE_D[0], COTA_CONSTANTE_D[1],0);
        Integer divisor = maximoComunDivisor(maximoComunDivisor(maximoComunDivisor(constanteA,constanteB),constanteC),constanteD);
        if(divisor<0) divisor = -divisor;
        constanteA/=divisor;
        constanteB/=divisor;
        constanteC/=divisor;
        constanteD/=divisor;
        Integer constanteE = constanteA;
        Integer constanteF = constanteC;
        Integer constanteG = Utilidades.obtenerEnteroAleatorio(COTA_CONSTANTE_G[0], COTA_CONSTANTE_G[1]);
        Integer constanteH = Utilidades.obtenerImparAleatorioDistintoDe(COTA_CONSTANTE_H[0], COTA_CONSTANTE_H[1], constanteF);
        String comentarioReactivo = Utilidades.generaComentario(COMENTARIO_REACTIVO_PREFIJO, numeroReactivo, POSICIONES_CONTADOR_REACTIVO);
        Integer respuestaA = -r1;
        Integer respuestaB = -constanteA*r1*r1*r1+constanteB*r1*r1-constanteC*r1+constanteD;
        Integer respuestaC = -r2;
        Integer respuestaD = -constanteA*r2*r2*r2+constanteB*r2*r2-constanteC*r2+constanteD;
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

        String parVariables = DatosReactivos.obtenerParesVariables();
        String variableIndependiente = parVariables.substring(0, 1);
        String variableDependiente = parVariables.substring(1, 2);

        //Sustitución de las variables por sus valores en el texto del reactivo
        String reactivo = XML_PREFIJO + PLANTILLA_REACTIVO + XML_SUFIJO;
        String expresion = EXPRESION;

        reactivo = reactivo.replace("$CONSTANTEA$", constanteA.toString());
        reactivo = reactivo.replace("$CONSTANTEB$", constanteB.toString());
        reactivo = reactivo.replace("$CONSTANTEC$", constanteC.toString());
        reactivo = reactivo.replace("$CONSTANTED$", constanteD.toString());
        reactivo = reactivo.replace("$CONSTANTEE$", constanteE.toString());
        reactivo = reactivo.replace("$CONSTANTEF$", constanteF.toString());
        reactivo = reactivo.replace("$CONSTANTEG$", constanteG.toString());
        reactivo = reactivo.replace("$CONSTANTEH$", constanteH.toString());
        reactivo = reactivo.replace("+-", "-");

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
        reactivo = reactivo.replace("$tolerancia$", "0.1");
        reactivo = reactivo.replace("1x", "x");

        //solucion = solucionaSimbolico.tangentesHorizontales(expresion);

        reactivo = reactivo.replace("$SOLUCION$", solucion);

        //Concatenando el separador de reactivos
        reactivo = reactivo.concat(SEPARADOR_REACTIVOS);
        return reactivo;
    }

    public static void main(String[] args) {
        EjecutadorGeneradorXML.generarReactivos(NOMBRE_ARCHIVO_SALIDA, NUMERO_DE_REACTIVOS, new GeneradorReactivo_MRUA_21feb2020());
    }


}
