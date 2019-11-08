package com.uam.generadores;

import com.uam.auxiliar.GeneradorReactivoCloze;
import com.uam.data.DatosReactivos;
import com.uam.executor.EjecutadorGenerador;
import com.uam.utilidades.Utilidades;

/**
 *
 * Este programa genera reactivos moodle tipo cloze aleatorios del tema 155381
 * (Relación del tanto por ciento con la expresión “n de cada 100”.
 * Relación de 50%, 25%, 20%, 10% con las fracciones 1/2, 1/4, 1/5, 1/10, respectivamente).
 * Los reactivos son vaciados en un archivo de texto y la salida puede ser
 * manipulada variando las variables que se encuentran documentadas.
 *
 * @author Eduardo Martinez Cruz
 */
public class GeneradorReactivo_155381 implements GeneradorReactivoCloze{
    
    
    /**
     * **************INICIA CONFIGURACIÓN DE EJECUCIÓN*************************
     */
    
    
    
    /**
     * El número de dígitos para el número de reactivo que se pondrá como
     * comentario del reactivo. e.g. si el número de posiciones es 3 entonces el
     * comentario que tendrá el primer reactivo será "<!--Reactivo
     * 16538_000-->"
     */
    private static final int POSICIONES_CONTADOR_REACTIVO = 3;

    /**
     * El número de reactivos que se generarán y vaciarán al archivo de texto.
     */
    private static final int NUMERO_DE_REACTIVOS = 50;

    /**
     * El nombre o ruta absoluta del archivo de salida.
     */
    private static final String NOMBRE_ARCHIVO_SALIDA = "reactivos_155381.txt";
    
    /**
     * El comentario que se pondrá a cada reactivo para etiquetarlo, el sufijo
     * sera el número de reactivo. Éste se insertará como un comentario html
     * para que no sea visible para el usuario. El lugar de inserción de este
     * comentario dentro del texto del reactivo esta dado por la variable
     * $COMENTARIO$ en la plantilla del reactivo.
     */
    private static final String COMENTARIO_REACTIVO_PREFIJO = "Reactivo 155381_"; 
    
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
            = "$COMENTARIO$ En la tienda \"$TIENDA$\" hay una rebaja del $DESCUENTO_GRAL$% en el "
            + "precio de todos sus productos; adem\u00e1s, en los productos marcados "
            + "con etiqueta azul hay un descuento del $DESCUENTO_AZUL$%. "
            + "¿Cu\u00e1l es el porcentaje que se paga del precio original en los "
            + "productos marcados con etiqueta azul? <br/> ${1:NUMERICAL:=$RESPUESTA$:0.5}";

    
    /**
     * Es la cota (límites inclusivos) que se usará para producir aleatoriamente
     * el entero para la variable $DESCUENTO_GRAL$ .
     */
    private static final int[] COTA_DESCUENTO_GRAL = {1,25};

    /**
     * Es la cota (límites inclusivos) que se usará para producir aleatoriamente
     * el entero para la variable $DESCUENTO_AZUL$.
     */
    private static final int[] COTA_DESCUENTO_AZUL = {5,50};
   
    /**
     * La cantidad de decimales que tendrá el resultado del reactivo. 
     */
    private static final int NUMERO_DECIMALES_RESULTADO = 3;

    /**
     * ***************TERMINA CONFIGURACIÓN DE EJECUCIÓN*********************
     */
    
    
    
    @Override
    public String generarReactivoCloze(int numeroReactivo) {
        //Generación de variables aleatorias con parámetros de ejecución
        String comentarioReactivo
                = Utilidades.generarEtiquetaReactivoHtml(COMENTARIO_REACTIVO_PREFIJO, numeroReactivo, POSICIONES_CONTADOR_REACTIVO);
        String tienda = DatosReactivos.obtenerNombreTiendaAleatorio();
        int descuentoGral = Utilidades.obtenerEnteroAleatorio(COTA_DESCUENTO_GRAL[0], COTA_DESCUENTO_GRAL[1]);
        int descuentoAzul = Utilidades.obtenerEnteroAleatorio(COTA_DESCUENTO_AZUL[0], COTA_DESCUENTO_AZUL[1]);
        String respuesta =
                Utilidades.calcularPorcentajeAPagarConDescuentosAnidados(NUMERO_DECIMALES_RESULTADO, descuentoGral,descuentoAzul);
                
        //Sustitución de las variables por sus valores en el texto del reactivo
        String reactivo = PLANTILLA_REACTIVO.replace("$COMENTARIO$", comentarioReactivo);
        reactivo = reactivo.replace("$TIENDA$", tienda);
        reactivo = reactivo.replace("$DESCUENTO_GRAL$", String.valueOf(descuentoGral));
        reactivo = reactivo.replace("$DESCUENTO_AZUL$", String.valueOf(descuentoAzul));
        reactivo = reactivo.replace("$RESPUESTA$", respuesta);
        //Concatenando el separador de reactivos
        reactivo = reactivo.concat(SEPARADOR_REACTIVOS);
        return reactivo;

    }

    public static void main(String[] args) {
        EjecutadorGenerador.generarReactivos(NOMBRE_ARCHIVO_SALIDA, NUMERO_DE_REACTIVOS, new GeneradorReactivo_155381());
    }
}
