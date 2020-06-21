package com.uam.generadores;

import com.uam.auxiliar.GeneradorReactivoCloze;
import com.uam.auxiliar.ObjetoComprable;
import com.uam.data.DatosReactivos;
import com.uam.executor.EjecutadorGenerador;
import com.uam.utilidades.Utilidades;


/**
 *
 * Este programa genera reactivos moodle tipo cloze aleatorios del tema 16538
 * (Resolución de problemas de comparación de razones, con base en la equivalencia).
 * Los reactivos son vaciados en un archivo de texto y la salida puede ser
 * manipulada variando las variables que se encuentran documentadas.
 *
 * @author Eduardo Martinez Cruz
 */
public class GeneradorReactivo_16538 implements GeneradorReactivoCloze{
    
    /**
     * ************INICIA CONFIGURACIÓN DE EJECUCIÓN**************************
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
    private static final String NOMBRE_ARCHIVO_SALIDA = "reactivos_16538.txt";
    
    /**
     * El comentario que se pondrá a cada reactivo para etiquetarlo, el sufijo
     * sera el número de reactivo. Éste se insertará como un comentario html
     * para que no sea visible para el usuario. El lugar de inserción de este
     * comentario dentro del texto del reactivo esta dado por la variable
     * $COMENTARIO$ en la plantilla del reactivo.
     */
    private static final String COMENTARIO_REACTIVO_PREFIJO = "Reactivo 16538_"; 
    
    /**
     * El texto, símbolo o caracter que separará cada reactivo generado en el
     * archivo de salida.
     */
    private static final String SEPARADOR_REACTIVOS = "\r\n";

    /**
     * El texto del reactivo, las variables se encuentran en mayúsculas y
     * encerradas entre signos $. La tildes deben ser colocadas con código utf8.
     */
    private static final String PLANTILLA_REACTIVO
            = "$COMENTARIO$ $NOMBRE$ compr\u00f3 $CANTIDAD_INICIAL$ $OBJETO_INICIAL$ "
            + "y le cost\u00f3 $$COSTO_COMPRADO$. Si regresa inmediatamente a la misma "
            + "tienda a comprar $CANTIDAD_FINAL$ $OBJETO_FINAL$ ¿cu\u00e1nto le costar\u00eda?"
            + "<br/> ${1:NUMERICAL:=$RESPUESTA$:0.5}";

    /**
     * El porcentaje de probabilidad (0-100 enteros) de que se elija un nombre
     * femenino; el porcentaje para los masculinos será de 100 menos el
     * porcentaje de femeninos.
     */
    private static final int PORCENTAJE_NOMBRES_FEMENINOS = 50;
    
    /**
     * Es la cota (límites inclusivos) que se usará para producir aleatoriamente
     * el entero para la variable $CANTIDAD_INICIAL$ .
     */
    private static final int[] COTA_CANTIDAD_INICIAL = {1,10};

    /**
     * Es la cota (límites inclusivos) que se usará para producir aleatoriamente
     * el entero para la variable $CANTIDAD_FINAL$ .
     */
    private static final int[] COTA_CANTIDAD_FINAL = {1,10};
    
    /**
     * La cantidad de decimales que tendrá el producto (precio * comprado).
     */
    private static final int NUMERO_DECIMALES_COSTO_TOTAL = 1;
    
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
        String nombre = DatosReactivos.obtenerNombreAleatorio(PORCENTAJE_NOMBRES_FEMENINOS);
        int cantidadCompradaInicial = Utilidades.obtenerEnteroAleatorio(COTA_CANTIDAD_INICIAL[0], COTA_CANTIDAD_INICIAL[1]);
        //nos aseguramos de que la cantidad final no sea igual que la inicial
        int cantidadCompradaFinal = Utilidades.obtenerEnteroAleatorioDistintoDe(COTA_CANTIDAD_FINAL[0], COTA_CANTIDAD_FINAL[1],cantidadCompradaInicial);
        ObjetoComprable objeto = Utilidades.obtenerAleatorioDeArreglo(DatosReactivos.OBJETOS_COMPRABLES);
        objeto.generarPrecio();
        double costoComprado = objeto.getPrecio() * cantidadCompradaInicial;
        double resultado = cantidadCompradaFinal * objeto.getPrecio();
        String respuesta = Utilidades.aStringDecimal(resultado, NUMERO_DECIMALES_RESULTADO);
                
        //Sustitución de las variables por sus valores en el texto del reactivo
        String reactivo = PLANTILLA_REACTIVO.replace("$COMENTARIO$", comentarioReactivo);
        reactivo = reactivo.replace("$NOMBRE$", nombre);
        reactivo = reactivo.replace("$CANTIDAD_INICIAL$", String.valueOf(cantidadCompradaInicial));
        reactivo = reactivo.replace("$CANTIDAD_FINAL$", String.valueOf(cantidadCompradaFinal));
        reactivo = reactivo.replace("$OBJETO_INICIAL$", objeto.getNombreString(cantidadCompradaInicial > 1));
        reactivo = reactivo.replace("$OBJETO_FINAL$", objeto.getNombreString(cantidadCompradaFinal > 1));
        reactivo = reactivo.replace("$COSTO_COMPRADO$",Utilidades.aStringDecimal(costoComprado, NUMERO_DECIMALES_COSTO_TOTAL));
        reactivo = reactivo.replace("$RESPUESTA$", respuesta);
        //Concatenando el separador de reactivos
        reactivo = reactivo.concat(SEPARADOR_REACTIVOS);
        return reactivo;

    }

    public static void main(String[] args) {
        EjecutadorGenerador.generarReactivos(NOMBRE_ARCHIVO_SALIDA, NUMERO_DE_REACTIVOS, new GeneradorReactivo_16538());
    }
}
