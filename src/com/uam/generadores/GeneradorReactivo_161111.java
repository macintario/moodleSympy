package com.uam.generadores;

import com.uam.auxiliar.Fraccion;
import com.uam.auxiliar.GeneradorReactivoCloze;
import com.uam.auxiliar.Relacion;
import com.uam.executor.EjecutadorGenerador;
import com.uam.utilidades.Utilidades;

/**
 *
 * Este programa genera reactivos moodle tipo cloze aleatorios del tema 161111
 * (Lectura, escritura y comparación de números naturales, fraccionarios y
 * decimales. Explicitación de los criterios de comparación). Los reactivos son
 * vaciados en un archivo de texto y la salida puede ser manipulada variando las
 * variables que se encuentran documentadas.
 *
 * @author Eduardo Martinez Cruz
 */
public class GeneradorReactivo_161111 implements GeneradorReactivoCloze {

    
    /**
     * ****************************INICIA CONFIGURACIÓN DE EJECUCIÓN***************************************
     */
    
    
    
    /**
     * El número de dígitos para el número de reactivo que se pondrá como
     * comentario del reactivo. e.g. si el número de posiciones es 3 entonces el
     * comentario que tendrá el primer reactivo será "<!--Reactivo
     * 161111_000-->"
     */
    private static final int POSICIONES_CONTADOR_REACTIVO = 3;

    /**
     * El número de reactivos que se generarán y vaciarán al archivo de texto.
     */
    private static final int NUMERO_DE_REACTIVOS = 10;

    /**
     * El nombre o ruta absoluta del archivo de salida.
     */
    private static final String NOMBRE_ARCHIVO_SALIDA = "reactivos_161111.txt";
    
    /**
     * El texto, símbolo o caracter que separará cada reactivo generado en el
     * archivo de salida..
     */
    private static final String SEPARADOR_REACTIVOS = "\r\n";

    /**
     * El texto del reactivo, las variables se encuentran en mayúsculas y
     * encerradas entre signos $. La tildes deben ser colocadas con código utf8.
     */
    private static final String PLANTILLA_REACTIVO
            = "$COMENTARIO$ Pon el signo >, <, = seg\u00fan corresponda: $FRACCION_A$ {1:SHORTANSWER:= $RESPUESTA$} $FRACCION_B$";

    /**
     * El porcentaje de probabilidad (0-100 enteros) de que se genere
     * un par de fracciones equivalentes, i.e. con respuesta "=". 
     */
    private static final int PROBABILIDAD_FRACCIONES_EQUIVALENTES = 25;
    
    /**
     * Es la cota (límites inclusivos) que se usará para producir aleatoriamente
     * el entero multiplicador que producirá las fracciones equivalente. 
     * Cuando se produzca una fracción equivalente, se multiplicará su numerador
     * y denominador por el entero aleatorio generado con esta cota. 
     */
    private static final int[] COTA_MULTIPLICADOR_FRACCIONES_EQUIVALENTES = {2,6};
    
    /**
     * El comentario que se pondrá a cada reactivo para etiquetarlo, el sufijo
     * sera el número de reactivo. Éste se insertará como un comentario html
     * para que no sea visible para el usuario. El lugar de inserción de este
     * comentario dentro del texto del reactivo esta dado por la variable
     * $COMENTARIO$ en la plantilla del reactivo.
     */
    private static final String COMENTARIO_REACTIVO_PREFIJO = "Reactivo 161111_";

    /**
     * Los límites (inclusivos) inferior y superior que definen la cota del 
     * dominio de enteros del numerador de la variable $FRACCION_A$.
     * El primer dígito es el inferior y el segundo el superior.
     */
    private static final int[] COTA_NUMERADOR_FRACCION_A = {1, 20};

    /**
     * Los límites (inclusivos) inferior y superior que definen la cota del 
     * dominio de enteros del denominador de la variable $FRACCION_A$.
     * El primer dígito es el inferior y el segundo el superior.
     */
    private static final int[] COTA_DENOMINADOR_FRACCION_A = {1, 20};

    /**
     * Es la relación que se debe cumplir entre el numerador y denominador de la
     * variable $FRACCION_A$ al ser generados aleatoriamente utilzando las cotas
     * establecidas. Si con las cotas establecidas no es posible generar un par
     * numerador-denominador aleatorio que cumpla con esta relación el programa
     * lo notificará con una excepción al momento de la ejecución, la cual se
     * interrumpirá.
     */
    private static final Relacion RELACION_NUMERADOR_DENOMINADOR_FRACCION_A = Relacion.SIN_RELACION;
    
    /**
     * Los límites (inclusivos) inferior y superior que definen la cota del 
     * dominio de enteros del numerador de la variable $FRACCION_B$.
     * El primer dígito es el inferior y el segundo el superior.
     */
    private static final int[] COTA_NUMERADOR_FRACCION_B = {1, 20};

    /**
     * Los límites (inclusivos) inferior y superior que definen la cota del 
     * dominio de enteros del denominador de la variable $FRACCION_B$.
     * El primer dígito es el inferior y el segundo el superior.
     */
    private static final int[] COTA_DENOMINADOR_FRACCION_B = {1, 20};

    /**
     * Es la relación que se debe cumplir entre el numerador y denominador de la
     * variable $FRACCION_B$ al ser generados aleatoriamente utilzando las cotas
     * establecidas. Si con las cotas establecidas no es posible generar un par
     * numerador-denominador aleatorio que cumpla con esta relación el programa
     * lo notificará con una excepción al momento de la ejecución, la cual se
     * interrumpirá.
     */
    private static final Relacion RELACION_NUMERADOR_DENOMINADOR_FRACCION_B = Relacion.SIN_RELACION;

   
    /**
     * ***************TERMINA CONFIGURACIÓN DE EJECUCIÓN*********************
     */
    
    
    
    @Override
    public String generarReactivoCloze(int numeroReactivo) {
        //Generación de variables aleatorias con parámetros de ejecución
        String comentarioReactivo
                = Utilidades.generarEtiquetaReactivoHtml(COMENTARIO_REACTIVO_PREFIJO, numeroReactivo, POSICIONES_CONTADOR_REACTIVO);
        Fraccion fraccionA = 
                new Fraccion(COTA_NUMERADOR_FRACCION_A, COTA_DENOMINADOR_FRACCION_A, RELACION_NUMERADOR_DENOMINADOR_FRACCION_A);
        Fraccion fraccionB = null;
        if(Utilidades.eventoAleatorioDentroDeProbabilidad(PROBABILIDAD_FRACCIONES_EQUIVALENTES)){
            fraccionB = Utilidades.obtenerFraccionEquivalente(fraccionA, COTA_MULTIPLICADOR_FRACCIONES_EQUIVALENTES);
        }else{
            fraccionB = new Fraccion(COTA_NUMERADOR_FRACCION_B, COTA_DENOMINADOR_FRACCION_B, RELACION_NUMERADOR_DENOMINADOR_FRACCION_B);
        }
        
                
        String respuesta = Utilidades.obtenerSignoComparativo(fraccionA, fraccionB);
                
        //Sustitución de las variables por sus valores en el texto del reactivo
        String reactivo = PLANTILLA_REACTIVO.replace("$COMENTARIO$", comentarioReactivo);
        reactivo = reactivo.replace("$FRACCION_A$", fraccionA.enFormatoCloze());
        reactivo = reactivo.replace("$FRACCION_B$", fraccionB.enFormatoCloze());
        reactivo = reactivo.replace("$RESPUESTA$", respuesta);
        //Concatenando el separador de reactivos
        reactivo = reactivo.concat(SEPARADOR_REACTIVOS);
        return reactivo;

    }

    public static void main(String[] args) {
        EjecutadorGenerador.generarReactivos(NOMBRE_ARCHIVO_SALIDA, NUMERO_DE_REACTIVOS, new GeneradorReactivo_161111());
    }

}
