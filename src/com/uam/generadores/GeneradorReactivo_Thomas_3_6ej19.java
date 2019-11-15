package com.uam.generadores;

import com.uam.auxiliar.GeneradorReactivoCloze;
import com.uam.executor.EjecutadorGeneradorXML;

public class GeneradorReactivo_Thomas_3_6ej19 implements GeneradorReactivoCloze {

     public static void main(String[] args) {
        EjecutadorGeneradorXML.generarReactivos(NOMBRE_ARCHIVO_SALIDA, NUMERO_DE_REACTIVOS, new GeneradorReactivo_Thomas_3_6ej19());
    }

}
