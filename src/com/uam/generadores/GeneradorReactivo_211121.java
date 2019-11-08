package com.uam.generadores;

import com.uam.auxiliar.Fraccion;
import com.uam.auxiliar.GeneradorReactivoCloze;
import com.uam.auxiliar.Relacion;
import com.uam.data.DatosReactivos;
import com.uam.excepcion.CondicionReactivoNoCumplidaException;
import com.uam.executor.EjecutadorGenerador;
import com.uam.utilidades.Utilidades;

/**
 * 
 * Este programa genera reactivos moodle tipo cloze aleatorios
 * del tema 211121 (Resolución y planteamiento de problemas
 * que impliquen más de una operación de suma y 
 * resta de fracciones).
 * Los reactivos son vaciados en un archivo de texto y la salida
 * puede ser manipulada variando las variables que se encuentran
 * documentadas.
 * 
 * @author Eduardo Martinez Cruz
 */
public class GeneradorReactivo_211121 implements GeneradorReactivoCloze {

    /**
     * ***********INICIA CONFIGURACIÓN DE EJECUCIÓN**************************
     */

    /**
     * El número de dígitos para el número de reactivo que se pondrá como
     * comentario del reactivo. e.g. si el número de posiciones es 3 entonces el
     * comentario que tendrá el primer reactivo será "<!--Reactivo
     * 211121_000-->"
     */
    private static final int POSICIONES_CONTADOR_REACTIVO = 3;

    /**
     * El número de reactivos que se generarán y vaciarán al archivo de texto.
     */
    private static final int NUMERO_DE_REACTIVOS = 100;
    
    
    /**
     * El nombre o ruta absoluta del archivo de salida.
     */
    private static final String NOMBRE_ARCHIVO_SALIDA = "reactivos_211121.txt";

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
            = "$COMENTARIO$ Cuando $NOMBRE$ fue a la tienda, compr\u00f3 $COMPRADO$ de kg de queso; "
            + "utiliz\u00f3 $UTILIZADO$ de su compra, guard\u00f3 el resto y luego utiliz\u00f3 "
            + "otra parte y le qued\u00f3 $RESTANTE$ kg. ¿Cu\u00e1ntos gramos us\u00f3 la \u00faltima vez?"
            + "<br/>{1:NUMERICAL:=$RESPUESTA$:0.5}";

    /**
     * El comentario que se pondrá a cada reactivo para etiquetarlo, el sufijo
     * sera el número de reactivo. Éste se insertará como un comentario html
     * para que no sea visible para el usuario. El lugar de inserción de este
     * comentario dentro del texto del reactivo esta dado por la variable
     * $COMENTARIO$ en la plantilla del reactivo.
     */
    private static final String COMENTARIO_REACTIVO_PREFIJO = "Reactivo 211121_";

    /**
     * Los límites (inclusivos) inferior y superior que definen la cota del 
     * dominio de enteros del numerador de la variable $COMPRADO$.
     * El primer dígito es el inferior y el segundo el superior.
     */
    private static final int[] COTA_NUMERADOR_COMPRADO = {10, 20};

    /**
     * Los límites (inclusivos) inferior y superior que definen la cota del 
     * dominio de enteros del denominador de la variable $COMPRADO$.
     * El primer dígito es el inferior y el segundo el superior.
     */
    private static final int[] COTA_DENOMINADOR_COMPRADO = {11,30};

    /**
     * Es la relación que se debe cumplir entre el numerador y denominador de la
     * variable $COMPRADO$ al ser generados aleatoriamente utilzando las cotas
     * establecidas. Si con las cotas establecidas no es posible generar un par
     * numerador-denominador aleatorio que cumpla con esta relación el programa
     * lo notificará con una excepción al momento de la ejecución, la cual se
     * interrumpirá.
     */
    private static final Relacion RELACION_NUMERADOR_DENOMINADOR_COMPRADO = Relacion.MENOR_O_IGUAL_QUE;

    /**
     * Los límites (inclusivos) inferior y superior que definen la cota del 
     * dominio de enteros del numerador de la variable $UTILIZADO$.
     * El primer dígito es el inferior y el segundo el superior.
     */
    private static final int[] COTA_NUMERADOR_UTILIZADO = {1, 20};

    /**
     * Los límites (inclusivos) inferior y superior que definen la cota del 
     * dominio de enteros del denominador de la variable $UTILIZADO$.
     * El primer dígito es el inferior y el segundo el superior.
     */
    private static final int[] COTA_DENOMINADOR_UTILIZADO = {1, 20};

    /**
     * Es la relación que se debe cumplir entre el numerador y denominador de la
     * variable $UTILIZADO$ al ser generados aleatoriamente utilzando las cotas
     * establecidas. Si con las cotas establecidas no es posible generar un par
     * numerador-denominador aleatorio que cumpla con esta relación el programa
     * lo notificará con una excepción al momento de la ejecución, la cual se
     * interrumpirá.
     */
    private static final Relacion RELACION_NUMERADOR_DENOMINADOR_UTILIZADO = Relacion.MENOR_QUE;

    /**
     * Los límites (inclusivos) inferior y superior que definen la cota del 
     * dominio de enteros del numerador de la variable $RESTANTE$.
     * El primer dígito es el inferior y el segundo el superior.
     */
    private static final int[] COTA_NUMERADOR_RESTANTE = {1, 1};

    /**
     * Los límites (inclusivos) inferior y superior que definen la cota del 
     * dominio de enteros del denominador de la variable $RESTANTE$.
     * El primer dígito es el inferior y el segundo el superior.
     */
    private static final int[] COTA_DENOMINADOR_RESTANTE = {10, 10};

    /**
     * Es la relación que se debe cumplir entre el numerador y denominador de la
     * variable $RESTANTE$ al ser generados aleatoriamente utilzando las cotas
     * establecidas. Si con las cotas establecidas no es posible generar un par
     * numerador-denominador aleatorio que cumpla con esta relación el programa
     * lo notificará con una excepción al momento de la ejecución, la cual se
     * interrumpirá.
     */
    private static final Relacion RELACION_NUMERADOR_DENOMINADOR_RESTANTE = Relacion.SIN_RELACION;


    /**
     * El número de decimales que tendrá la respuesta del reactivo cloze.
     */
    private static final int NUMERO_DECIMALES_RESULTADO = 3;

    /**
     * **********TERMINA CONFIGURACIÓN DE EJECUCIÓN************************
     */
    
    
    @Override
    public String generarReactivoCloze(int numeroReactivo) {
        Fraccion restante = null;
        Fraccion comprado = null;
        Fraccion compradoMenosRestante =null;
        Fraccion utilizado = null;
        Fraccion compradoMenosUtilizado = null;
        try {
            //Generación de variables aleatorias con parámetros de ejecución
            String nombre = DatosReactivos.obtenerNombreAleatorio(PORCENTAJE_NOMBRES_FEMENINOS);
            //lo restante debe ser definido primero, ya que de este valor dependen las demas variables para asegurar la no negatividad del resultado
            restante = new Fraccion(COTA_NUMERADOR_RESTANTE, COTA_DENOMINADOR_RESTANTE, RELACION_NUMERADOR_DENOMINADOR_RESTANTE);
            //lo comprado tiene que ser mayor que lo restante
            comprado 
                    = Utilidades.obtenerFraccionAleatoriaMayorQue(restante, COTA_NUMERADOR_COMPRADO, COTA_DENOMINADOR_COMPRADO, RELACION_NUMERADOR_DENOMINADOR_COMPRADO);
            //lo utilizado tiene que ser menor que la resta comprado - restante
            compradoMenosRestante = Fraccion.restar(comprado, restante, true);
            utilizado
                    = Utilidades.obtenerFraccionAleatoriaMenorQue(compradoMenosRestante, COTA_NUMERADOR_UTILIZADO, COTA_DENOMINADOR_UTILIZADO, RELACION_NUMERADOR_DENOMINADOR_UTILIZADO);
            compradoMenosUtilizado = Fraccion.restar(comprado, utilizado, true);
            //lo ultimo utilizado es lo (comprado - utilizado) - restante
            Fraccion respuestaFraccion = Fraccion.restar(compradoMenosUtilizado, restante, true);
            //se multiplica por 1000 porque el resultado se espera en gramos
            String respuesta = Utilidades.fraccionADecimal(Fraccion.multiplicar(respuestaFraccion, new Fraccion(1000, 1)), NUMERO_DECIMALES_RESULTADO);
            String comentarioReactivo
                    = Utilidades.generarEtiquetaReactivoHtml(COMENTARIO_REACTIVO_PREFIJO, numeroReactivo, POSICIONES_CONTADOR_REACTIVO);

            //Sustitución de las variables por sus valores en el texto del reactivo
            String reactivo = PLANTILLA_REACTIVO.replace("$NOMBRE$", nombre);
            reactivo = reactivo.replace("$COMENTARIO$", comentarioReactivo);
            reactivo = reactivo.replace("$COMPRADO$", comprado.enFormatoCloze());
            reactivo = reactivo.replace("$UTILIZADO$", utilizado.enFormatoCloze());
            reactivo = reactivo.replace("$RESTANTE$", restante.enFormatoCloze());
            reactivo = reactivo.replace("$RESPUESTA$", respuesta);
            //Concatenando el separador de reactivos
            reactivo = reactivo.concat(SEPARADOR_REACTIVOS);
            return reactivo;
        } catch (CondicionReactivoNoCumplidaException e) {
            throw new RuntimeException(e.getMessage() + "\n CONTEXTO DE EJECUCION:"
                    + "\nFracción COMPRADO generada: "+comprado
                    + "\nFracción UTILIZADO generada: "+utilizado
                    + "\nFracción RESTANTE generada: "+restante
                    + "\nFracción COMPRADO - RESTANTE: "+compradoMenosRestante
                    + "\nFracción COMPRADO - UTILIZADO: "+compradoMenosUtilizado);
        }
        
    }

    public static void main(String[] args) {
        EjecutadorGenerador.generarReactivos(NOMBRE_ARCHIVO_SALIDA, NUMERO_DE_REACTIVOS, new GeneradorReactivo_211121());
    }

}
