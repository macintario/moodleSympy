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
import static com.uam.utilidades.Utilidades.redondea;
import static com.uam.utilidades.Utilidades.obtenerEnteroAleatorio;

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
            + "<span style=\"color: #0000ff; font-size: x-large;\"><strong>"
            + "Una explosión de dinamita lanza una roca directamente hacia arriba con una velocidad de"
            + " $$ $CONSTANTEA$ \\frac{m}{seg}$$. Alcanza una altura de $$s=$CONSTANTEA$t-4.9t^2 $$ "
            + "al cabo de $$t$$ segundos. <br/><br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            + "<ol type=\"a\">"
            + "   <li>¿Qué altura alcanza la roca?<br/>{1:NUMERICAL:=$RESPUESTA_F$:$tolerancia$}$$m$$</li>"
            + "   <li>¿Cuáles es la velocidad  de la roca cuando la roca está $$ $CONSTANTEB$m $$"
            + " por arriba del del suelo durante el ascenso?<br/>{1:NUMERICAL:=$RESPUESTA_G$:$tolerancia$}" +
            "$$\\frac{m}{s}$$<br/> ¿Durante el descenso?" +
            "<br/>{1:NUMERICAL:=$RESPUESTA_H$:$tolerancia$}$$\\frac{m}{s}$$</li>"
            + "   <li>¿Cuál es la aceleración de la roca en cualquier instante t durante su trayecto (después de la explosión)?<br/>" +
            "{1:NUMERICAL:=$RESPUESTA_D$:$tolerancia$}$$\\frac{m}{s^2}$$</li>"
            + "   <li>¿Cuándo choca la roca contra el suelo?<br/>{1:NUMERICAL:=$RESPUESTA_E$:$tolerancia$}$$segundos$$</li>"
            + "</ol>"
            + "</strong><br/><br/></span><br/><br/>"
            + "<span style=\"color: #000000; font-size: medium;\"><strong>"
            + "Usted deberá calcular la derivada $$s(t)$$para calcular la velocidad como función del tiempo y derivar de nuevo para obtener la aceleración<br/>"
            + "<span style=\"color: #FF4000; font-size: medium;\"><strong>\n" +
            "¿ Revisión de su ejercicio ? Escribirás en papel el procedimiento detallado que muestre cómo obtuviste tus respuestas. \n" +
            "</strong></span>";

    private static final int[] COTA_CONSTANTE_A = {10, 50};
    private static final int[] COTA_CONSTANTE_B = {-5, 5};
    private static final int[] COTA_CONSTANTE_C = {-5, 5};
    private static final int[] COTA_CONSTANTE_D = {-9, 9};
    private static final int[] COTA_CONSTANTE_E = {2, 9};
    private static final int[] COTA_CONSTANTE_F = {2, 9};
    private static final int[] COTA_CONSTANTE_G = {3, 5};
    private static final int[] COTA_CONSTANTE_H = {2, 5};



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
        Integer constanteA = Utilidades.obtenerEnteroAleatorio(COTA_CONSTANTE_A[0], COTA_CONSTANTE_A[1]);
        Integer constanteB = constanteA;
        Double constanteC = 0.;
        Integer constanteD =  Utilidades.obtenerEnteroAleatorioDistintoDe(COTA_CONSTANTE_D[0], COTA_CONSTANTE_D[1],0);
        Integer constanteE = constanteA;
        Integer constanteF = constanteA;
        Integer constanteG = Utilidades.obtenerEnteroAleatorio(COTA_CONSTANTE_G[0], COTA_CONSTANTE_G[1]);
        Integer constanteH = Utilidades.obtenerImparAleatorioDistintoDe(COTA_CONSTANTE_H[0], COTA_CONSTANTE_H[1], constanteF);
        String comentarioReactivo = Utilidades.generaComentario(COMENTARIO_REACTIVO_PREFIJO, numeroReactivo, POSICIONES_CONTADOR_REACTIVO);
        Double v_0=constanteA.doubleValue();
        Double g=9.8;
        Double t_h_max = v_0/g;
        constanteC = redondea(t_h_max,2);
        Double s_max = v_0*t_h_max-g/2*t_h_max*t_h_max;
        constanteB = obtenerEnteroAleatorio(1,s_max.intValue());
        Double t_1 = (v_0-Math.sqrt(v_0*v_0-2*g*constanteB.doubleValue()))/g;
        Double v_t1 = v_0-g*t_1;
        Double t_2 = (v_0+Math.sqrt(v_0*v_0-2*g*constanteB.doubleValue()))/g;
        Double v_t2 = v_0-g*t_2;
        Double t_land = 2*v_0/g;
        Double respuestaA = redondea( t_h_max,2);
        Double respuestaB = redondea(t_1,2);
        Double respuestaC = redondea(t_2,2);
        Double respuestaD = -g;
        Double respuestaE = redondea(t_land,2);
        Double respuestaF = redondea(s_max,2);
        Double respuestaG = redondea(v_t1,2);
        Double respuestaH = redondea(v_t2,2);
        Integer respuestaJ = constanteA;
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
        solucion = "<ol type=\"a\">" +
                " <li> En el sistema de coordenadas elegido, $$s$$ mide la altura con respecto al suelo, así que la velocidad es positiva en el escenso y negativa durante el descenso. " +
                "El instante en el que la roca está en el punto más alto es aquél en el que la velocidad es cero. Para conocer la altura máxima, todo lo que necesitamos hacer " +
                "es determinar cuándo $$v=0$$ y evaluar $$s$$ en ese instante<br/>" +
                "En cualquier instante $$t$$ durante el trayecto de la roca, su velocidad es<br/>" +
                "<center>$$\\displaystyle v=\\frac{ds}{dt}=\\frac{d}{dt}($CONSTANTEA$t-4.9t^2)=$CONSTANTEA$-9.8t$$ " +
                "$$ \\frac{m}{s}$$</center>" +
                "La velocidad es cero  cuando" +
                "<center>$$\\displaystyle $CONSTANTEA$-9.8t=0$$ <br/>" +
                "o $$t=$CONSTANTEC$ $$ $$seg.$$</center>" +
                "La altura de la roca en $$t=$RESPUESTA_A$ $$ $$seg.$$ es<br/>" +
                "<center>$$\\displaystyle s_{máx}=s($RESPUESTA_A$)=" +
                "$CONSTANTEA$($RESPUESTA_A$)-4.9($RESPUESTA_A$)^2=$RESPUESTA_F$ $$  $$m.$$</center>" +
                " </li>" +
                "<li>Para determinar la velocidad de la roca a $$ $CONSTANTEB$ m$$, en el ascenso y " +
                "luego el descenso, primero determinamos los valores de de $$t$$ para los cuales:<br/>" +
                "<center>$$\\displaystyle s(t)=$CONSTANTEA$t-4.9t^2=$CONSTANTEB$ $$<br/>" +
                "$$4.9t^2-$CONSTANTEA$t+$CONSTANTEB$=0$$</center>" +
                "Resolviendo la ecuación<br/>" +
                "<center>$$\\displaystyle t=\\frac{2($CONSTANTEA$)\\pm\\sqrt{($CONSTANTEA$)^2-4(4.9)($CONSTANTEB$) }}{2(4.9)}$$" +
                "</center>" +
                "<center>$$t=$RESPUESTA_B$ seg, t=$RESPUESTA_C$ seg$$</center>" +
                "La roca está $$ $CONSTANTEB$ m$$ por encima del suelo $$ $RESPUESTA_B$ $$ segundos después" +
                " de la explosión y nuevamente a los $$ $RESPUESTA_C$ $$ segundos de la explosión, Las velocidades de la" +
                " roca en esos instantes son" +
                "<center>" +
                "$$v($RESPUESTA_B$)=$CONSTANTEA$-9.8($RESPUESTA_B$)=$RESPUESTA_G$ \\frac{m}{s}$$<br/>" +
                "$$v($RESPUESTA_C$)=$CONSTANTEA$-9.8($RESPUESTA_C$)=$RESPUESTA_H$ \\frac{m}{s}$$</center>" +
                "En ambos instantes, la rapidez de la roca es $$ $RESPUESTA_G$ \\frac{m}{s} $$." +
                " Como $$v($RESPUESTA_B$) > 0$$, la roca se desplaza hacia arriba (s aumenta) en t=$RESPUESTA_B$." +
                " En t = $RESPUESTA_C$ seg, se mueve hacia abajo (s disminuye) , ya que $$v($RESPUESTA_C$) < 0$$." +
                "</li>" +
                "<li>En cualquier instante durante el trayecto que sigue a la explosión, la aceleración de la roca es constante" +
                "<center>$$\\displaystyle a=\\frac{dv}{dt}=\\frac{d}{dt}({$CONSTANTEA$-9.8t})=-9.8 \\frac{m}{s^2}$$</center>" +
                "La aceleración siempre es hacia abajo. Conforme la roca sube, desacelera, y cuando cae acelera." +
                "</li>" +
                "<li>La roca choca con el suelo en el tiempo positivo $$t$$, para el cual s=0, por lo que se resuelve" +
                "<center>$$\\displaystyle $CONSTANTEA$t-4.9t^2=0$$<br/>" +
                "$$\\displaystyle t($CONSTANTEA$-4.9t)=0$$<br/>" +
                "$$\\displaystyle t=0, t=\\frac{$CONSTANTEA$}{4.9}=$RESPUESTA_E$ $$</center>" +
                "En $$t=0$$, la explosión ocurrió y la roca sale lanzada hacia arriba." +
                " Regresa al suelo al cabo de $$$RESPUESTA_E$ $$segundos." +
                "</li>" +
                "</ol>";

        reactivo = reactivo.replace("$SOLUCION$", solucion);

        reactivo = reactivo.replace("\\frac{d}{dx}\\left($EXPRESION$ \\right)"
                ,"</strong></span>Respuesta a)<br>" +
                        "<span style=\"color: #0000ff; font-size:x-large;\"><strong>" +
                        " \\frac{d}{dt}\\left($EXPRESION$ \\right)=0<br>" +
                        "\\s=$EXPRESION$, t=$RESPUESTA_A$ <br>" +
                        "</strong></span>Respuesta b)<br> <span style=\"color: #0000ff; font-size:x-large;\"><strong>" +
                        "  $EXPRESION$=$CONSTANTEB$<br>" +
                        "V_1=$CONSTANTEA$-9.8t, t=$RESPUESTA_B$ <br>" +
                        "V_2=$CONSTANTEA$-9.8t, t=$RESPUESTA_C$ <br>" +
                        "</strong></span>Respuesta c)<br> <span style=\"color: #0000ff; font-size:x-large;\"><strong>" +
                        "\\frac{d}{dt}\\left($CONSTANTEA$-9.8t\\right)<br>" +
                        "</strong></span>Respuesta d)<br> <span style=\"color: #0000ff; font-size:x-large;\"><strong>" +
                        "$EXPRESION$=0");
        reactivo = reactivo.replace("La derivada de la función $$f(x)$$ es","Para cada parte de la pregunta");
        reactivo = reactivo.replace("$EXPRESION$","$CONSTANTEA$t-4.9t^2");

        reactivo = reactivo.replace("$CONSTANTEA$", constanteA.toString());
        reactivo = reactivo.replace("$CONSTANTEB$", constanteB.toString());
        reactivo = reactivo.replace("$CONSTANTEC$", constanteC.toString());
        reactivo = reactivo.replace("$CONSTANTED$", constanteD.toString());
        reactivo = reactivo.replace("$CONSTANTEE$", constanteE.toString());
        reactivo = reactivo.replace("$CONSTANTEF$", constanteF.toString());
        reactivo = reactivo.replace("$CONSTANTEG$", constanteG.toString());
        reactivo = reactivo.replace("$CONSTANTEH$", constanteH.toString());
        reactivo = reactivo.replace("+-", "-");

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
        //Concatenando el separador de reactivos
        reactivo = reactivo.concat(SEPARADOR_REACTIVOS);
        return reactivo;
    }

    public static void main(String[] args) {
        EjecutadorGeneradorXML.generarReactivos(NOMBRE_ARCHIVO_SALIDA, NUMERO_DE_REACTIVOS, new GeneradorReactivo_MRUA_21feb2020());
    }


}
