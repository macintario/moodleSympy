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
import static java.lang.Math.abs;

public class GeneradorReactivo_Thomas_3_4ej1 implements GeneradorReactivoCloze {
    /**
     * El número de dígitos para el número de reactivo que se pondrá como
     * comentario del reactivo. e.g. si el número de posiciones es 3 entonces el
     * comentario que tendrá el primer reactivo será
     * "<!--Reactivo Thomas_3_3x40_000-->"
     */
    private static final int POSICIONES_CONTADOR_REACTIVO = 3;
    /**
     * El nombre o ruta absoluta del archivo de salida.
     */
    private static final String NOMBRE_ARCHIVO_SALIDA = "reactivos_Thomas_3_4ej1.xml";

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
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; En este ejercicio aprenderás a aplicar la regla del producto</strong></span> <br><br>"
            + "<span style=\"color: #ff0000; font-size: xx-large;\"><strong>\n"
            + "PROBLEMA:\n"
            + "</strong></span>"
            + "<span style=\"color: #0000ff; font-size: x-large;\"><strong>"
            + "La ecuación da las posiciones $$y=f(x)$$ de un cuerpo que se mueve en una recta coordenada; " +
            " $$y$$ está en metros y $$x$$ en segundos: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>"
            + "<center>$$\\displaystyle y(x)=$EXPRESION$ $$, &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            +" $$ $INTERVALO_BAJO$ \\leq x \\leq $INTERVALO_ALTO$ $$</center><br/>"
            + "</strong><br/><br/></span><span style=\"color: #ff0000; font-size: x-large;\"><strong>"
            + "<script type=\"math/tex\">\\bullet</script> &nbsp;&nbsp;&nbsp;"
            +" Deberá responder las siguientes preguntas: </strong></span><br/><br/>"
            + "<span style=\"color: #000000; font-size: medium;\"><strong>"
            + "<ol type=\"a\">"
            +"<li>Determine el desplazamiento del cuerpo y la velocidad promedio para el intervalo indicado</li>"
            +"<li>Determine la rapidez y aceleación en los extremos del intervalo</li>"
            +"<li>¿Cuándo, si es que sucede, el cuerpo cambia de dirección durante el intervalo?</li>"
            +"</ol>"
            +"Usted deberá calcular las respuestas, indicando en papel todos los pasos. "
            + "<br/>Deberá escribir en las cajas correspondientes los números que obtuvo. \n"
            + "<br/></strong></span>"
            + "$RESPUESTAS$<br/>"
            + "<span style=\"color: #FF4000; font-size: medium;\"><strong>\n" +
            "¿ Revisión de su ejercicio ? Escribirás en papel el procedimiento detallado que muestre cómo obtuviste tus respuestas. \n" +
            "</strong></span>";

    private static final int[] COTA_CONSTANTE_A = {2, 9};
    private static final int[] COTA_CONSTANTE_B = {2, 8};
    private static final int[] COTA_CONSTANTE_C = {2, 9};
    private static final int[] COTA_CONSTANTE_D = {2, 9};
    private static final int[] COTA_CONSTANTE_E = {2, 9};
    private static final int[] COTA_CONSTANTE_F = {2, 9};
    private static final int[] COTA_CONSTANTE_G = {-10, 5};
    private static final int[] COTA_CONSTANTE_H = {0, 5};

    private static final String EXPRESION = "$CONSTANTEA$x^2-$CONSTANTEB$x+$CONSTANTEC$";
    private String RESPUESTA= "$$\\displaystyle y'(x)=\\frac{Ax^4+Bx^2+C}{Dx^E(x^2+F)^2}$$ <br/><br/>" +
            "$$\\displaystyle y''(x)=\\frac{Gx^6+Hx^4+Jx^2+K}{Lx^M(x^2+N)^3}$$ <br/>";
    private String CAJAS_RESPUESTA
            = "<ol type=\"a\")>" +
            "<li>$$\\Delta s=$${1:SHORTANSWER:=$RESPUESTA_A$} &nbsp $$v_{prom}=$${1:SHORTANSWER:=$RESPUESTA_B$} </li>"
            + "<li>$$Rapidez \\bigg\\rvert_{t=$INTERVALO_BAJO$} =$${1:SHORTANSWER:=$RESPUESTA_C$}"
            + "$$Aceleración \\bigg\\rvert_{t=$INTERVALO_BAJO$}=$${1:SHORTANSWER:=$RESPUESTA_D$}<br/>"
            + "$$Rapidez \\bigg\\rvert_{t=$INTERVALO_ALTO$} =$${1:SHORTANSWER:=$RESPUESTA_E$}"
            + "$$Aceleración \\bigg\\rvert_{t=$INTERVALO_ALTO$}=$${1:SHORTANSWER:=$RESPUESTA_F$}</li>"
            + "<li>$$t=$${1:SHORTANSWER:=$RESPUESTA_G$}</li>"
            + "</ol>"
            + "<span style=\"color: #ff0000; font-size: x-large;\"><strong>"
            + "<script type=\"math/tex\">\\bullet</script> &nbsp;&nbsp;&nbsp;"
            +" Los números $$A,B,C,D,E,F,G,H,J,K,L,M,N$$ en este orden "
            + "y que dan solución correcta al ejercicio son: </strong></span>"
            + " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            +" {20:SHORTANSWER:=$RESPUESTA_A$,$RESPUESTA_B$,$RESPUESTA_C$,$RESPUESTA_D$,$RESPUESTA_E$,$RESPUESTA_F$,$RESPUESTA_G$," +
            "$RESPUESTA_H$,$RESPUESTA_J$,$RESPUESTA_K$,$RESPUESTA_L$,$RESPUESTA_M$,$RESPUESTA_N$"
            + "}</center> <br>"
            + "</center>";
    /**
     * El comentario que se pondrá a cada reactivo para etiquetarlo, el sufijo
     * sera el número de reactivo. Éste se insertará como un comentario html
     * para que no sea visible para el usuario. El lugar de inserción de este
     * comentario dentro del texto del reactivo esta dado por la variable
     * $COMENTARIO$ en la plantilla del reactivo.
     *
     */
    private static final String COMENTARIO_REACTIVO_PREFIJO = "Reactivo Thomas_3.3_Ej_40_";
    private static final String SEPARADOR_REACTIVOS = "\r\n";

    @Override
    public String generarReactivoCloze(int numeroReactivo) {
        String solucion = "";
        //Generación de variables aleatorias con parámetros de ejecución

        Integer constanteA = Utilidades.obtenerImparAleatorio(COTA_CONSTANTE_A[0], COTA_CONSTANTE_A[1]);
        Integer constanteB = Utilidades.obtenerImparAleatorio(COTA_CONSTANTE_B[0], COTA_CONSTANTE_B[1]);
        Integer constanteC = Utilidades.obtenerEnteroAleatorio(COTA_CONSTANTE_C[0], COTA_CONSTANTE_C[1]);
        Integer constanteD = Utilidades.obtenerEnteroAleatorioDistintoDe(COTA_CONSTANTE_D[0], COTA_CONSTANTE_D[1],constanteC);
        Integer constanteE = Utilidades.obtenerEnteroAleatorio(COTA_CONSTANTE_E[0], COTA_CONSTANTE_E[1]);
        Integer constanteF = Utilidades.obtenerEnteroAleatorio(COTA_CONSTANTE_F[0], COTA_CONSTANTE_F[1]);
//limites
        Double vertice= constanteB.doubleValue()/constanteA.doubleValue()/2.;
        Integer cambio = vertice.intValue();
        Integer constanteG = Utilidades.obtenerEnteroAleatorio(COTA_CONSTANTE_G[0], cambio-1);
        Integer constanteH = Utilidades.obtenerImparAleatorio(cambio+1, COTA_CONSTANTE_H[1]);

        String comentarioReactivo
                = Utilidades.generaComentario(COMENTARIO_REACTIVO_PREFIJO, numeroReactivo, POSICIONES_CONTADOR_REACTIVO);
        Integer respuestaA = -2;
        Integer respuestaB = 6*(constanteB*constanteB-constanteA);
        Integer respuestaC = -6*constanteB*constanteB*constanteA;
        Integer respuestaD = 2;
        Integer respuestaE = 2;
        Integer respuestaF = 3*constanteB*constanteB;
        //Checar fracción reductible
        Integer divisor = maximoComunDivisor(maximoComunDivisor(respuestaA,respuestaB), maximoComunDivisor(respuestaC,respuestaD));
        respuestaA /= divisor;
        respuestaB /= divisor;
        respuestaC /= divisor;
        respuestaD /= divisor;


        Integer respuestaG = 8;
        Integer respuestaH = 8*(6*constanteA-9*constanteB*constanteB);
        Integer respuestaJ = 8*9*constanteB*constanteB*constanteA;
        Integer respuestaK = 8*9*constanteB*constanteB*constanteB*constanteB*constanteA;
        Integer respuestaL = 8;
        Integer respuestaM = 3;
        Integer respuestaN = 3*constanteB*constanteB;
        divisor = maximoComunDivisor(maximoComunDivisor(maximoComunDivisor(respuestaG,respuestaH),maximoComunDivisor(respuestaJ,respuestaK)),respuestaL);
        respuestaG /= divisor;
        respuestaH /= divisor;
        respuestaJ /= divisor;
        respuestaK /= divisor;
        respuestaL /= divisor;


        Integer respuestaP = constanteC;
        Double tolerancia =0.10;





        String parVariables = DatosReactivos.obtenerParesVariables();
        String variableIndependiente = parVariables.substring(0, 1);
        String variableDependiente = parVariables.substring(1, 2);

        //Sustitución de las variables por sus valores en el texto del reactivo
        String reactivo = XML_PREFIJO + PLANTILLA_REACTIVO + XML_SUFIJO;
        String expresion = EXPRESION;
// Incliur segunda derivada
        reactivo = reactivo.replace("\\frac{d}{dx}\\left(","\\frac{d}{dx}\\left(\\frac{d}{dx}\\left(");
        reactivo = reactivo.replace("\\right)","\\right)\\right)");
        reactivo = reactivo.replace("La derivada de la función $$f(","La segunda derivada de la función $$y(");

//
        expresion = expresion.replace("$CONSTANTEA$", constanteA.toString());
        expresion = expresion.replace("$CONSTANTEB$", constanteB.toString());
        expresion = expresion.replace("$CONSTANTEC$", constanteC.toString());
        expresion = expresion.replace("$CONSTANTED$", constanteD.toString());
        expresion = expresion.replace("$CONSTANTEE$", constanteE.toString());
        expresion = expresion.replace("$CONSTANTEF$", constanteF.toString());
        expresion = expresion.replace("$INTERVALO_BAJO$", constanteG.toString());
        expresion = expresion.replace("$INTERVALO_ALTO$", constanteH.toString());

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
        reactivo = reactivo.replace("$TOLERANCIA$" , tolerancia.toString());
        reactivo = reactivo.replace("$INTERVALO_BAJO$", constanteG.toString());
        reactivo = reactivo.replace("$INTERVALO_ALTO$", constanteH.toString());

        solucion = solucion+solucionaSimbolico.derivaSimbolicoSegunda(expresion);

        reactivo = reactivo.replace("$SOLUCION$", solucion);

        //Concatenando el separador de reactivos
        reactivo = reactivo.concat(SEPARADOR_REACTIVOS);
        return reactivo;
    }

    public static void main(String[] args) {
        EjecutadorGeneradorXML.generarReactivos(NOMBRE_ARCHIVO_SALIDA, NUMERO_DE_REACTIVOS, new GeneradorReactivo_Thomas_3_4ej1());
    }


}

