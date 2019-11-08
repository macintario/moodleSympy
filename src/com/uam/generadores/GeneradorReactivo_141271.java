package com.uam.generadores;

import com.uam.auxiliar.GeneradorReactivoCloze;
import com.uam.data.DatosReactivos;
import com.uam.executor.EjecutadorGenerador;
import com.uam.utilidades.Utilidades;

/**
 *
 * Este programa genera reactivos moodle tipo cloze aleatorios del tema 141271
 * (Resolución de problemas vinculados al uso del reloj y del calendario). Los
 * reactivos son vaciados en un archivo de texto y la salida puede ser
 * manipulada variando las variables que se encuentran documentadas.
 *
 * @author Eduardo Martinez Cruz
 */
public class GeneradorReactivo_141271 implements GeneradorReactivoCloze {

    /**
     * ****************INICIA CONFIGURACIÓN DE EJECUCIÓN*********************
     */
    
    
    /**
     * El número de dígitos para el número de reactivo que se pondrá como
     * comentario del reactivo. e.g. si el número de posiciones es 3 entonces el
     * comentario que tendrá el primer reactivo será
     * "<!--Reactivo 141271_000-->"
     */
    private static final int POSICIONES_CONTADOR_REACTIVO = 3;

    /**
     * El número de reactivos que se generarán y vaciarán al archivo de texto.
     */
    private static final int NUMERO_DE_REACTIVOS = 10;

    /**
     * El nombre o ruta absoluta del archivo de salida.
     */
    private static final String NOMBRE_ARCHIVO_SALIDA = "reactivos_141271.txt";
    
    /**
     * El texto, símbolo o caracter que separará cada reactivo generado en el
     * archivo de salida.
     */
    private static final String SEPARADOR_REACTIVOS = "\r\n";

    /**
     * El porcentaje de probabilidad (0-100 enteros) de que se elija un nombre
     * femenino; el porcentaje para los masculinos será de 100 menos el
     * porcentaje de femeninos.
     */
    private static final int PORCENTAJE_NOMBRES_FEMENINOS = 50;

    /**
     * El texto del reactivo, las variables se encuentran en mayúsculas y
     * encerradas entre signos $. La tildes deben ser colocadas con código utf8.
     */
    private static final String PLANTILLA_REACTIVO
            = "$COMENTARIO$ $NOMBRE$ aborda el transporte p\u00fablico a las $HORA_PARTIDA$:$MINUTOS_PARTIDA$ para "
            + "llegar a la puerta de su escuela. ¿A qu\u00e9 hora llega si el "
            + "viaje dura $MINUTOS_VIAJE$ minutos? <br/> {1:SHORTANSWER:=$RESPUESTA$}";

    /**
     * El comentario que se pondrá a cada reactivo para etiquetarlo, el sufijo
     * sera el número de reactivo. Éste se insertará como un comentario html
     * para que no sea visible para el usuario. El lugar de inserción de este
     * comentario dentro del texto del reactivo esta dado por la variable
     * $COMENTARIO$ en la plantilla del reactivo.
     */
    private static final String COMENTARIO_REACTIVO_PREFIJO = "Reactivo 141271_";

    /**
     * Los límites (inclusivos) inferior y superior del dominio de la variable
     * $MINUTOS_PARTIDA$. El primer dígito es el inferior y el segundo el
     * superior.
     */
    private static final int[] COTA_MINUTOS_PARTIDA = {0, 59};

    /**
     * Los límites (inclusivos) inferior y superior del dominio de la variable
     * $HORA_PARTIDA$. El primer dígito es el inferior y el segundo el superior.
     */
    private static final int[] COTA_HORA_PARTIDA = {6, 6};

    /**
     * Los límites (inclusivos) inferior y superior del dominio de la variable
     * $MINUTOS_VIAJE$. El primer dígito es el inferior y el segundo el
     * superior.
     */
    private static final int[] COTA_MINUTOS_VIAJE = {5, 59};

    /**
     * ******************TERMINA CONFIGURACIÓN DE EJECUCIÓN*****************
     */
    
    
    @Override
    public String generarReactivoCloze(int numeroReactivo) {

        //Generación de variables aleatorias con parámetros de ejecución
        String nombre = DatosReactivos.obtenerNombreAleatorio(PORCENTAJE_NOMBRES_FEMENINOS);
        Integer horaPartida = Utilidades.obtenerEnteroAleatorio(COTA_HORA_PARTIDA[0], COTA_HORA_PARTIDA[1]);
        Integer minutosPartida = Utilidades.obtenerEnteroAleatorio(COTA_MINUTOS_PARTIDA[0], COTA_MINUTOS_PARTIDA[1]);
        Integer minutosViaje = Utilidades.obtenerEnteroAleatorio(COTA_MINUTOS_VIAJE[0], COTA_MINUTOS_VIAJE[1]);
        String comentarioReactivo
                = Utilidades.generarEtiquetaReactivoHtml(COMENTARIO_REACTIVO_PREFIJO, numeroReactivo, POSICIONES_CONTADOR_REACTIVO);
        String respuesta = Utilidades.sumarMinutos(horaPartida, minutosPartida, minutosViaje);

        //Sustitución de las variables por sus valores en el texto del reactivo
        String reactivo = PLANTILLA_REACTIVO.replace("$NOMBRE$", nombre);
        reactivo = reactivo.replace("$MINUTOS_PARTIDA$", Utilidades.digitoACadena(minutosPartida, 2));
        reactivo = reactivo.replace("$HORA_PARTIDA$", horaPartida.toString());
        reactivo = reactivo.replace("$MINUTOS_VIAJE$", minutosViaje.toString());
        reactivo = reactivo.replace("$COMENTARIO$", comentarioReactivo);
        reactivo = reactivo.replace("$RESPUESTA$", respuesta);
        //Concatenando el separador de reactivos
        reactivo = reactivo.concat(SEPARADOR_REACTIVOS);
        return reactivo;
    }

    public static void main(String[] args) {
        EjecutadorGenerador.generarReactivos(NOMBRE_ARCHIVO_SALIDA, NUMERO_DE_REACTIVOS, new GeneradorReactivo_141271());
    }

}
