package com.uam.generadores;

import com.uam.auxiliar.GeneradorReactivoCloze;
import com.uam.auxiliar.solucionaSimbolico;
import com.uam.data.DatosReactivos;
import com.uam.executor.EjecutadorGeneradorXML;
import com.uam.utilidades.Utilidades;

import static com.uam.constantes.Constantes.XML_PREFIJO;
import static com.uam.constantes.Constantes.XML_SUFIJO;

public class GeneradorReactivo_Thomas_3_2x10 implements GeneradorReactivoCloze {
    /**
     * El número de dígitos para el número de reactivo que se pondrá como
     * comentario del reactivo. e.g. si el número de posiciones es 3 entonces el
     * comentario que tendrá el primer reactivo será
     * "<!--Reactivo Thomas_3_2x10_000-->"
     */
    private static final int POSICIONES_CONTADOR_REACTIVO = 3;
    /**
     * El número de reactivos que se generarán y vaciarán al archivo de texto.
     */
    private static final int NUMERO_DE_REACTIVOS = 3;

    /**
     * El nombre o ruta absoluta del archivo de salida.
     */
    private static final String NOMBRE_ARCHIVO_SALIDA = "reactivos_Thomas_3_2x10.xml";

    /**
     * El texto, símbolo o caracter que separará cada reactivo generado en el
     * archivo de salida.
     */
    private static final String SEPARADOR_REACTIVOS = "\r\n";


//    private static final String EXPRESION="\\frac{$NUMERADOR$}{\\sqrt{$COEFICIENTE$x-$INDEPENDIENTE$}}";
    private static final String EXPRESION="$INDEPENDIENTE$x-\\frac{$NUMERADOR$}{$COEFICIENTE$x-$INDEPENDIENTE$}";

    /**
     * El texto del reactivo, las variables se encuentran en mayúsculas y
     * encerradas entre signos $. La tildes deben ser colocadas con código utf8.
     */
    private static final String PLANTILLA_REACTIVO
            = "<span style=\"color: #ff0000,2E8B57; font-size: large;\"><strong>\n" +
            "galoisenlinea&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; http://galois.azc.uam.mx </strong></span>\n" +
            "<span style=\"color: #E38E03; font-size: large;\"><strong>\n" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Calculará la derivada de una función racional $$f(x)$$ </span> <br><br>"
            +"<span style=\"color: #ff0000; font-size: xx-large;\"><strong>\n"
            +"PROBLEMA:\n"
            +"</strong></span>"
            + "<center><span style=\"color: #0000ff; font-size: x-large;\"><strong>"
            +"Considere la función: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            //+"$$\\displaystyle $VARIABLE_DEPENDIENTE$=\\frac{$NUMERADOR$}{\\sqrt{$COEFICIENTE$$VARIABLE_INDEPENDIENTE$-$INDEPENDIENTE$}}$$ <br/>"
            +"$$\\displaystyle y=$EXPRESION$ $$<br/>"
            +"</strong><br/><br/></span><span style=\"color: #ff0000; font-size: x-large;\"><strong>"
            +"<script type=\"math/tex\">\\bullet</script> &nbsp;&nbsp;&nbsp; Calculando la derivada de la función $$f($VARIABLE_INDEPENDIENTE$)$$ obtenemos que: </strong></span><br/><br/>"
            +"$$\\displaystyle\\frac{d$VARIABLE_DEPENDIENTE$}{d$VARIABLE_INDEPENDIENTE$}=\\frac{A}{2(B$VARIABLE_INDEPENDIENTE$-C)^{\\frac{D}{E}}}$$ <br/>"
            +"</strong></span><br/>"
            +"<span style=\"color: #000000; font-size: medium;\"\"><strong>"
            +"Usted deberá calcular la derivada $$f(x)$$ indicando en papel todos los pasos. "
            +"Utilice la respuesta parcial que ofrecemos, cada letra representa un dígito"
            +" en su respuesta. Llene únicamente los cuadros apropiados:<br/></strong></span>"
            +" A={:SHORTANSWER:=$RESPUESTA_A$} <br/> B={:NUMERICAL:=$RESPUESTA_B$} <br/> C={:NUMERICAL:=$RESPUESTA_C$} <br/> D={:NUMERICAL:=$RESPUESTA_D$} <br/> E={:NUMERICAL:=$RESPUESTA_E$}"
            +"<br/>"
            +"</center>"
            +"<span style=\"color: #FF4000; font-size: medium;\"><strong>\n" +
            "¿ Revisión de su ejercicio ? Escribirás en papel el procedimiento detallado que muestre cómo obtuviste tus respuestas. \n" +
            "</strong></span>"
            ;


    /**
     * El comentario que se pondrá a cada reactivo para etiquetarlo, el sufijo
     * sera el número de reactivo. Éste se insertará como un comentario html
     * para que no sea visible para el usuario. El lugar de inserción de este
     * comentario dentro del texto del reactivo esta dado por la variable
     * $COMENTARIO$ en la plantilla del reactivo.
     */
    private static final String COMENTARIO_REACTIVO_PREFIJO = "Reactivo Thomas_3.2_Ej_10_";

    /**
     * Los límites (inclusivos) inferior y superior del dominio de la variable
     * $MINUTOS_PARTIDA$. El primer dígito es el inferior y el segundo el
     * superior.
     */
    private static final int[] COTA_NUMERADOR = {1, 5};

    /**
     * Los límites (inclusivos) inferior y superior del dominio de la variable
     * $HORA_PARTIDA$. El primer dígito es el inferior y el segundo el superior.
     */
    private static final int[] COTA_COEFICIENTE = {2, 9};

    /**
     * Los límites (inclusivos) inferior y superior del dominio de la variable
     * $MINUTOS_VIAJE$. El primer dígito es el inferior y el segundo el
     * superior.
     */
    private static final int[] COTA_INDEPENDIENTE = {1, 20};

    /**
     * ******************TERMINA CONFIGURACIÓN DE EJECUCIÓN*****************
     * @param numeroReactivo
     * @return
     */


    @Override
    public String generarReactivoCloze(int numeroReactivo) {

        //Generación de variables aleatorias con parámetros de ejecución
        Integer numerador = Utilidades.obtenerImparAleatorio(COTA_NUMERADOR[0],COTA_NUMERADOR[1]);
        Integer coeficiente = Utilidades.obtenerImparAleatorio(COTA_COEFICIENTE[0],COTA_COEFICIENTE[1]);
        Integer independiente = Utilidades.obtenerParAleatorio(COTA_INDEPENDIENTE[0],COTA_INDEPENDIENTE[1]);
        String comentarioReactivo
                = Utilidades.generaComentario(COMENTARIO_REACTIVO_PREFIJO, numeroReactivo, POSICIONES_CONTADOR_REACTIVO);
        Integer respuestaA = -numerador*coeficiente;
        Integer respuestaB = coeficiente;
        Integer respuestaC = independiente;
        Integer respuestaD = 3;
        Integer respuestaE = 2;
        String  parVariables= DatosReactivos.obtenerParesVariables();
        String  variableIndependiente=parVariables.substring(0, 1);
        String  variableDependiente=parVariables.substring(1, 2);

        //Sustitución de las variables por sus valores en el texto del reactivo
//        String reactivo = PLANTILLA_REACTIVO.replace("$NUMERADOR$",numerador.toString());
        String reactivo = XML_PREFIJO + PLANTILLA_REACTIVO + XML_SUFIJO;
        String expresion = EXPRESION;
        expresion = expresion.replace("$NUMERADOR$",numerador.toString());
        expresion = expresion.replace("$COEFICIENTE$", coeficiente.toString());
        expresion = expresion.replace("$INDEPENDIENTE$", independiente.toString());
        String solucion = solucionaSimbolico.Incremento(expresion);
        reactivo = reactivo.replace("$SOLUCION$", solucion);
        reactivo = reactivo.replace("$EXPRESION$",expresion);
        reactivo = reactivo.replace("$COMENTARIO$", comentarioReactivo);
        reactivo = reactivo.replace("$VARIABLE_INDEPENDIENTE$", variableIndependiente);
        reactivo = reactivo.replace("$VARIABLE_DEPENDIENTE$", variableDependiente);
        reactivo = reactivo.replace("$RESPUESTA_A$", respuestaA.toString());
        reactivo = reactivo.replace("$RESPUESTA_B$", respuestaB.toString());
        reactivo = reactivo.replace("$RESPUESTA_C$", respuestaC.toString());
        reactivo = reactivo.replace("$RESPUESTA_D$", respuestaD.toString());
        reactivo = reactivo.replace("$RESPUESTA_E$", respuestaE.toString());

        //Concatenando el separador de reactivos
        reactivo = reactivo.concat(SEPARADOR_REACTIVOS);
        return reactivo;
    }

    public static void main(String[] args) {
        EjecutadorGeneradorXML.generarReactivos(NOMBRE_ARCHIVO_SALIDA, NUMERO_DE_REACTIVOS, new GeneradorReactivo_Thomas_3_2x10());
    }

}