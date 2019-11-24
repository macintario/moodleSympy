package com.uam.generadores;

import com.uam.auxiliar.GeneradorReactivoCloze;
import com.uam.auxiliar.solucionaSimbolico;
import com.uam.data.DatosReactivos;
import com.uam.executor.EjecutadorGeneradorXML;
import com.uam.utilidades.Utilidades;

import static com.uam.constantes.Constantes.XML_PREFIJO;
import static com.uam.constantes.Constantes.XML_SUFIJO;

public class GeneradorReactivo_Thomas_3_6ej22 implements GeneradorReactivoCloze {
    /**
     * El número de dígitos para el número de reactivo que se pondrá como
     * comentario del reactivo. e.g. si el número de posiciones es 3 entonces el
     * comentario que tendrá el primer reactivo será
     * "<!--Reactivo Thomas_3_6x22_000-->"
     */
    private static final int POSICIONES_CONTADOR_REACTIVO = 3;
    /**
     * El nombre o ruta absoluta del archivo de salida.
     */
    private static final String NOMBRE_ARCHIVO_SALIDA = "reactivos_Thomas_3_6ej22.xml";

    /**
     * El número de reactivos que se generarán y vaciarán al archivo de texto.
     */
    private static final int NUMERO_DE_REACTIVOS = 3;

    private static final int[] COTA_CONSTANTE_A = {2, 9};
    private static final int[] COTA_CONSTANTE_B = {2, 9};
    private static final int[] COTA_CONSTANTE_C = {5, 8};
    private static final int[] COTA_CONSTANTE_D = {2, 9};


    /**
     * El texto del reactivo, las variables se encuentran en mayúsculas y
     * encerradas entre signos $. La tildes deben ser colocadas con código utf8.
     */
    private static final String PLANTILLA_REACTIVO
            = "<span style=\"color: #ff0000,2E8B57; font-size: large;\"><strong>\n" +
            "galoisenlinea&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; http://galois.azc.uam.mx </strong></span>\n" +
            "<span style=\"color: #E38E03; font-size: large;\"><strong>\n" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; En este ejercicio aprenderás a aplicar la regla de la cadena</strong></span> <br><br>"
            +"<span style=\"color: #ff0000; font-size: xx-large;\"><strong>\n"
            +"PROBLEMA:\n"
            +"</strong></span>"
            + "<center><span style=\"color: #0000ff; font-size: x-large;\"><strong>"
            +"Considere la función: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            +"$$\\displaystyle y=$EXPRESION$ $$<br/>"
            +"</strong><br/><br/></span><span style=\"color: #ff0000; font-size: x-large;\"><strong>"
            +"<script type=\"math/tex\">\\bullet</script> &nbsp;&nbsp;&nbsp; Calculando la derivada de la función $$f($VARIABLE_INDEPENDIENTE$)$$ obtenemos que: </strong></span><br/><br/>"
            +"$$\\displaystyle\\frac{df}{dx}=\\frac{A+Bx}{3(Cx+Dx^2)^E}$$ <br/>"
            +"</strong></span><br/>"
            +"<span style=\"color: #000000; font-size: medium;\"><strong>"
            +"Usted deberá calcular la derivada $$f(x)$$ utilizando la regla de la cadena, indicando en papel todos los pasos. "
            //+"Utilice la respuesta parcial que ofrecemos, cada letra representa un dígito en su respuesta."
            //+" Llene únicamente los cuadros apropiados:"
            +"<br/></strong></span>"
            +" $$A$$={1:SHORTANSWER:=$RESPUESTA_A$} <br/> $$B$$={4:SHORTANSWER:=$RESPUESTA_B$} <br/> " +
            "$$C$$={1:SHORTANSWER:=$RESPUESTA_C$} <br/> $$D$$={1:SHORTANSWER:=$RESPUESTA_D$}<br/> $$E$$={1:SHORTANSWER:=$RESPUESTA_E$}"
            +"<br/>"
            +"</center>"
            +"<span style=\"color: #FF4000; font-size: medium;\"><strong>\n" +
            "¿ Revisión de su ejercicio ? Escribirás en papel el procedimiento detallado que muestre cómo obtuviste tus respuestas. \n" +
            "</strong></span>"
            ;

    private static final String EXPRESION="\\sin{\\frac{$CONSTANTEA$}{$CONSTANTEB$}\\pi x}+" +
            "\\cos{\\frac{$CONSTANTEC$}{$CONSTANTED$}\\pi x}";
    private   String FU="\\sin{u}";
    private   String GX="\\frac{$CONSTANTEA$}{$CONSTANTEB$}\\pi x";
    private   String HV="\\cos{v}";
    private   String JX="\\frac{$CONSTANTEC$}{$CONSTANTED$}\\pi x";

    /**
     * El comentario que se pondrá a cada reactivo para etiquetarlo, el sufijo
     * sera el número de reactivo. Éste se insertará como un comentario html
     * para que no sea visible para el usuario. El lugar de inserción de este
     * comentario dentro del texto del reactivo esta dado por la variable
     * $COMENTARIO$ en la plantilla del reactivo.
     */
    private static final String COMENTARIO_REACTIVO_PREFIJO = "Reactivo Thomas_3.6_Ej_22_";
    private static final String SEPARADOR_REACTIVOS = "\r\n";

    @Override
    public String generarReactivoCloze(int numeroReactivo) {
        String solucion="";
        String f = FU;
        String g = GX;
        String h = HV;
        String j = JX;
        //Generación de variables aleatorias con parámetros de ejecución
        Integer constanteA = Utilidades.obtenerImparAleatorio(COTA_CONSTANTE_A[0],COTA_CONSTANTE_A[1]);
        Integer constanteB = Utilidades.obtenerImparAleatorioDistintoDe(COTA_CONSTANTE_C[0],COTA_CONSTANTE_C[1], constanteA);
        Integer constanteC = Utilidades.obtenerImparAleatorioDistintoDe(COTA_CONSTANTE_B[0],COTA_CONSTANTE_B[1], constanteA);
        Integer constanteD = Utilidades.obtenerEnteroAleatorioDistintoDe(COTA_CONSTANTE_D[0],COTA_CONSTANTE_D[1],constanteC);

        String comentarioReactivo
                = Utilidades.generaComentario(COMENTARIO_REACTIVO_PREFIJO, numeroReactivo, POSICIONES_CONTADOR_REACTIVO);
        Integer respuestaA = constanteA;
        Integer respuestaB = -2*constanteB;
        Integer respuestaC = constanteA;
        Integer respuestaD = -constanteB;
        String  respuestaE = "2/3";
        String  parVariables= DatosReactivos.obtenerParesVariables();
        String  variableIndependiente=parVariables.substring(0, 1);
        String  variableDependiente=parVariables.substring(1, 2);

        //Sustitución de las variables por sus valores en el texto del reactivo
        String reactivo = XML_PREFIJO + PLANTILLA_REACTIVO + XML_SUFIJO;
        String expresion = EXPRESION;
        expresion = expresion.replace("$CONSTANTEA$",constanteA.toString());
        expresion = expresion.replace("$CONSTANTEB$", constanteB.toString());
        expresion = expresion.replace("$CONSTANTEC$", constanteC.toString());
        expresion = expresion.replace("$CONSTANTED$", constanteD.toString());
        f = f.replace("$CONSTANTEA$", constanteA.toString());
        f = f.replace("$CONSTANTEB$", constanteB.toString());
        f = f.replace("$CONSTANTEC$", constanteC.toString());
        f = f.replace("$CONSTANTED$", constanteD.toString());

        g = g.replace("$CONSTANTEA$", constanteA.toString());
        g = g.replace("$CONSTANTEB$", constanteB.toString());
        g = g.replace("$CONSTANTEC$", constanteC.toString());
        g = g.replace("$CONSTANTED$", constanteD.toString());

        h = h.replace("$CONSTANTEA$", constanteA.toString());
        h = h.replace("$CONSTANTEB$", constanteB.toString());
        h = h.replace("$CONSTANTEC$", constanteC.toString());
        h = h.replace("$CONSTANTED$", constanteD.toString());

        j = j.replace("$CONSTANTEA$", constanteA.toString());
        j = j.replace("$CONSTANTEB$", constanteB.toString());
        j = j.replace("$CONSTANTEC$", constanteC.toString());
        j = j.replace("$CONSTANTED$", constanteD.toString());

        solucion = solucionaSimbolico.sumaReglaCadena(f,g,h,j);
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
        EjecutadorGeneradorXML.generarReactivos(NOMBRE_ARCHIVO_SALIDA, NUMERO_DE_REACTIVOS, new GeneradorReactivo_Thomas_3_6ej22());
    }


}
