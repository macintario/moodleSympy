package com.uam.auxiliar;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class solucionaSimbolico {
    private static final String PLANTILLA_SYMPY=
            "from sympy import *\n" +
                    "from sympy.parsing.latex import parse_latex\n" +
                    "salida = open(\"/tmp/solucion.txt\",\"w\")\n" +
                    "init_printing()\n" +
                    "x = var('x')\n" +
                    "h = var('h')\n" +
                    "f = parse_latex(r\" $EXPRESION$ \")\n" +
                    "fh=f.subs(x,x+h)\n" +
                    "drv=(fh-f)/h\n" +
                    "salida.write(\"$$\\\\displaystyle f(x)=%s$$<br/><br/>\\n\" %(latex(f)))\n" +
                    "salida.write(\"$$\\\\displaystyle f(x+h)=%s$$<br/><br/>\\n\" %latex(fh))\n" +
                    "salida.write(\"$$\\\\displaystyle \\\\frac{f(x+h)-f(x)}{h}=%s=%s $$ <br/><br/>\" % (latex(drv),latex(drv.simplify())))\n" +
                    "drv=limit(drv,h,0)\n" +
                    "salida.write(\"$$\\\\displaystyle f'(x)=\\\\lim_{h\\\\to 0} \\\\frac{f(x+h)-f(x)}{h}=%s=%s$$<br/><br/>\\n\" % (latex(drv),latex(drv.simplify())))\n";
    public static String Incremento(String expresion) {
        String script = PLANTILLA_SYMPY;
        String solucion="";
        script = script.replace("$EXPRESION$",expresion);
        try(FileWriter scriptFile = new FileWriter("/tmp/script.py");
            BufferedWriter bw = new BufferedWriter(scriptFile)){
            bw.write(script);
        }catch (IOException e){
            System.err.format("Escribiendo Script IOException: %s%n", e);
        }
        String cmd = "python3 /tmp/script.py";
        try{
            Process p = Runtime.getRuntime().exec(cmd);
            int exitVal = p.waitFor();
            System.out.println(exitVal);
        }catch(java.io.IOException e){
            System.err.format("Ejecutando Python IOException: %s%n", e);
        }catch (Throwable t)
        {
            t.printStackTrace();
        }
        try {
            solucion = new String(Files.readAllBytes(Paths.get("/tmp/solucion.txt")));
            //File f = new File("/tmp/solucion.txt");
            //f.delete();
        }catch(java.io.IOException e){
            System.err.format("Leyendo Solucion IOException: %s%n", e);
        }
        return solucion;
    }
}
