package com.uam.auxiliar;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class solucionaSimbolico {
    
    private static String ejecutaPython(String code) {
        String uuid = String.valueOf(UUID.randomUUID());
        String solucion = "";
        code = code.replace("$UUID$", uuid);
        try (FileWriter scriptFile = new FileWriter("/tmp/script_" + uuid + ".py");
             BufferedWriter bw = new BufferedWriter(scriptFile)) {
            bw.write(code);
        } catch (IOException e) {
            System.err.format("Escribiendo Script IOException: %s%n", e);
        }
        String cmd = "python3 /tmp/script_" + uuid + ".py";
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            InputStream errorPython = p.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(errorPython, StandardCharsets.UTF_8));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    System.out.println("Python: " + line);
                }
            } catch (IOException e) {
                System.out.println("Exception in reading output" + e.toString());
            }
            int exitVal = p.waitFor();
            if (exitVal == 0) System.out.println("Ok");
        } catch (java.io.IOException e) {
            System.err.format("Ejecutando Python IOException: %s%n", e);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        try {
            solucion = new String(Files.readAllBytes(Paths.get("/tmp/solucion_" + uuid + ".txt")));
            File archivoSolucion = new File("/tmp/solucion_" + uuid + ".txt");
            archivoSolucion.delete();
            File archivoScript = new File("/tmp/script_" + uuid + ".py");
            archivoScript.delete();
        } catch (java.io.IOException e) {
            System.err.format("Leyendo Solucion IOException: %s%n", e);
        }
        return solucion;

    }

    public static String Incremento(String expresion) {
        String PLANTILLA_SYMPY =
                "from sympy import *\n" +
                        "from sympy.parsing.latex import parse_latex\n" +
                        "salida = open(\"/tmp/solucion_$UUID$.txt\",\"w\")\n" +
                        "init_printing()\n" +
                        "x = var('x')\n" +
                        "h = var('h')\n" +
                        "f = parse_latex(r\" $EXPRESION$ \")\n" +
                        "fh=f.subs(x,x+h)\n" +
                        "drv=(fh-f)/h\n" +
                        "salida.write(\"$$\\\\displaystyle f(x)=%s$$<br/><br/>\\n\" %(latex(f)))\n" +
                        "salida.write(\"$$\\\\displaystyle f(x+h)=%s$$<br/><br/>\\n\" %latex(fh))\n" +
                        "salida.write(\"$$\\\\displaystyle \\\\frac{f(x+h)-f(x)}{h}=%s=%s $$\\n <br/><br/>\" % (latex(drv),latex(drv.factor())))\n" +
                        "drv=limit(drv,h,0)\n" +
                        "resultado=drv.apart()\n" +
                        "salida.write(\"$$\\\\displaystyle f'(x)=\\\\lim_{h\\\\to 0} \\\\frac{f(x+h)-f(x)}{h}=%s=%s$$<br/><br/>\\n\" % (latex(drv),latex(resultado)))";

        String script = PLANTILLA_SYMPY;
        script = script.replace("$EXPRESION$", expresion);
        String solucion = ejecutaPython(script);
        return solucion;
    }

    public static String solucionaSimbolicoConjugados(String expresion) {
        String PLANTILLA_SYMPY =
                "from sympy import *\n" +
                        "from sympy.parsing.latex import parse_latex\n" +
                        "\n" +
                        "salida = open(\"/tmp/solucion_$UUID$.txt\", \"w\")\n" +
                        "init_printing()\n" +
                        "x = var('x')\n" +
                        "h = var('h')\n" +
                        "f = parse_latex(r\"$EXPRESION$ \")\n" +
                        "fh = f.subs(x, x + h)\n" +
                        "drv = (fh - f) / h\n" +
                        "salida.write(\"$$\\\\displaystyle f(x)=%s$$<br/><br/>\\n\" % (latex(f)))\n" +
                        "salida.write(\"$$\\\\displaystyle f(x+h)=%s$$<br/><br/>\\n\" % latex(fh))\n" +
                        "salida.write(\"$$\\\\displaystyle \\\\frac{f(x+h)-f(x)}{h}=%s=%s $$\\n <br/><br/>Multiplicando por el conjugado<br/><br/>\" % (latex(drv), latex(drv.simplify())))\n" +
                        "recipr = 1 / f\n" +
                        "reciprh = 1 / fh\n" +
                        "conjugado = recipr + reciprh\n" +
                        "numerador = ( fh - f ) * conjugado\n" +
                        "denominador = recipr * reciprh\n" +
                        "nuevoNumerador = numerador.simplify()\n" +
                        "salida.write(\"$$\\\\displaystyle = %s \\\\frac{%s}{%s}$$<br/><br/>\\n\" % (latex(drv), latex(conjugado), latex(conjugado)));\n" +
                        "salida.write(\n" +
                        "    \"$$\\\\displaystyle = \\\\frac{1}{%s({%s})} %s$$<br/><br/>\\n\" % (latex(h), latex(conjugado), latex((fh - f) * conjugado)));\n" +
                        "salida.write(\"$$\\\\displaystyle numerador: %s=%s$$<br/><br/>\\n\" % (latex(numerador), latex(numerador.simplify())));\n" +
                        "salida.write(\"$$\\\\displaystyle simplificando: \\\\frac{f(x+h)-f(x)}{h}=\\\\frac{%s}{h}*\\\\frac{1}{%s} $$<br/><br/>\\n\" % (\n" +
                        "latex(nuevoNumerador), latex(conjugado)));\n" +
                        "drv = nuevoNumerador / h / conjugado\n" +
                        "limite = limit(drv, h, 0)\n" +
                        "salida.write(\"$$\\\\displaystyle f'(x)=\\\\lim_{h\\\\to 0} \\\\frac{f(x+h)-f(x)}{h}=\\\\lim_{h\\\\to 0}%s=%s$$<br/><br/>\\n\" % (latex(drv), latex(limite.simplify())))\n";
        String script = PLANTILLA_SYMPY;
        script = script.replace("$EXPRESION$", expresion);
        String solucion = ejecutaPython(script);
        return solucion;
    }

    public static String reglaCadena(String fu, String gx) {
        String archivo = String.valueOf(UUID.randomUUID());
        String PLANTILLA_SYMPY = "from sympy import *\n" +
                "from sympy.parsing.latex import parse_latex\n" +
                "\n" +
                "\n" +
                "salida = open(\"/tmp/solucion_$UUID$.txt\",\"w\")\n" +
                "init_printing()\n" +
                "x = var('x')\n" +
                "u = var('u')\n" +
                "f = parse_latex(r\"$FU$\")\n" +
                "g = parse_latex(r\"$GX$\")\n" +
                "salida.write(\"$$\\\\displaystyle f(x)=%s$$<br/><br/>\\n\" % latex(f.subs(u,g)))\n" +
                "salida.write(\"$$\\\\displaystyle f(u)=%s$$<br/><br/>\\n\" % latex(f))\n" +
                "salida.write(\"$$\\\\displaystyle u(x)=%s$$<br/><br/>\\n\" % latex(g))\n" +
                "df = diff(f,u)\n" +
                "dg = diff(g,x)\n" +
//                "salida.write(\"$$\\\\displaystyle  \\\\frac{df}{du}=%s$$<br/><br/>\\n\" % latex(df))\n" +
//                "salida.write(\"$$ $$\\n\")\n" +
                "salida.write(\"$$\\\\displaystyle \\\\frac{df}{du}=%s$$<br/><br/>\\n\" % latex(df))\n" +
                "salida.write(\"$$\\\\displaystyle \\\\frac{du}{dx}=%s$$<br/><br/>\\n\" % latex(dg))\n" +
                "result = df*dg\n" +
                "salida.write(\"$$\\\\displaystyle \\\\frac{df}{du}\\\\frac{du}{dx} = %s$$<br/><br/>\\n\" % latex(result))\n" +
                "result = result.subs(u,g)\n" +
                "salida.write(\"$$\\\\displaystyle \\\\frac{df}{dx}=%s=%s$$<br/><br/>\\n\" % (latex(result), latex(result.cancel())))\n" +
                "salida.close()";
        String script = PLANTILLA_SYMPY;
        script = script.replace("$FU$", fu);
        script = script.replace("$GX$", gx);
        String solucion = ejecutaPython(script);
        return solucion;
    }

    public static String reglaCadenaNoCancel(String fu, String gx) {
        String archivo = String.valueOf(UUID.randomUUID());
        String PLANTILLA_SYMPY = "from sympy import *\n" +
                "from sympy.parsing.latex import parse_latex\n" +
                "\n" +
                "\n" +
                "salida = open(\"/tmp/solucion_$UUID$.txt\",\"w\")\n" +
                "init_printing()\n" +
                "x = var('x')\n" +
                "u = var('u')\n" +
                "f = parse_latex(r\"$FU$\")\n" +
                "g = parse_latex(r\"$GX$\")\n" +
                "salida.write(\"$$\\\\displaystyle f(x)=%s$$<br/><br/>\\n\" % latex(f.subs(u,g)))\n" +
                "salida.write(\"$$\\\\displaystyle f(u)=%s$$<br/><br/>\\n\" % latex(f))\n" +
                "salida.write(\"$$\\\\displaystyle u(x)=%s$$<br/><br/>\\n\" % latex(g))\n" +
                "df = diff(f,u)\n" +
                "dg = diff(g,x)\n" +
//                "salida.write(\"$$\\\\displaystyle  \\\\frac{df}{du}=%s$$<br/><br/>\\n\" % latex(df))\n" +
//                "salida.write(\"$$ $$\\n\")\n" +
                "salida.write(\"$$\\\\displaystyle \\\\frac{df}{du}=%s$$<br/><br/>\\n\" % latex(df))\n" +
                "salida.write(\"$$\\\\displaystyle \\\\frac{du}{dx}=%s$$<br/><br/>\\n\" % latex(dg))\n" +
                "result = df*dg\n" +
                "salida.write(\"$$\\\\displaystyle \\\\frac{df}{du}\\\\frac{du}{dx} = %s$$<br/><br/>\\n\" % latex(result))\n" +
                "result = result.subs(u,g)\n" +
                "salida.write(\"$$\\\\displaystyle \\\\frac{df}{dx}=%s$$<br/><br/>\\n\" % (latex(result)))\n" +
                "salida.close()";
        String script = PLANTILLA_SYMPY;
        script = script.replace("$FU$", fu);
        script = script.replace("$GX$", gx);
        String solucion = ejecutaPython(script);
        return solucion;
    }

    public static String reglaCadenaTrig(String fu, String gx) {
        String archivo = String.valueOf(UUID.randomUUID());
        String PLANTILLA_SYMPY = "from sympy import *\n" +
                "from sympy.parsing.latex import parse_latex\n" +
                "\n" +
                "\n" +
                "salida = open(\"/tmp/solucion_$UUID$.txt\",\"w\")\n" +
                "init_printing()\n" +
                "x = var('x')\n" +
                "u = var('u')\n" +
                "f = parse_latex(r\"$FU$\")\n" +
                "g = parse_latex(r\"$GX$\")\n" +
                "salida.write(\"$$\\\\displaystyle f(x)=%s$$<br/><br/>\\n\" % latex(f.subs(u,g)))\n" +
                "salida.write(\"$$\\\\displaystyle f(u)=%s$$<br/><br/>\\n\" % latex(f))\n" +
                "salida.write(\"$$\\\\displaystyle u(x)=%s$$<br/><br/>\\n\" % latex(g))\n" +
                "df = diff(f,u)\n" +
                "dg = diff(g,x)\n" +
//                "salida.write(\"$$\\\\displaystyle  \\\\frac{df}{du}=%s$$<br/><br/>\\n\" % latex(df))\n" +
//                "salida.write(\"$$ $$\\n\")\n" +
                "salida.write(\"$$\\\\displaystyle \\\\frac{df}{du}=%s$$<br/><br/>\\n\" % latex(df))\n" +
                "salida.write(\"$$\\\\displaystyle \\\\frac{du}{dx}=%s$$<br/><br/>\\n\" % latex(dg))\n" +
                "result = df*dg\n" +
                "salida.write(\"$$\\\\displaystyle \\\\frac{df}{du}\\\\frac{du}{dx} = %s$$<br/><br/>\\n\" % latex(result))\n" +
                "result = result.subs(u,g)\n" +
                "salida.write(\"$$\\\\displaystyle \\\\frac{df}{dx}=%s=%s$$<br/><br/>\\n\" % (latex(result), latex(trigsimp(result))))\n" +
                "salida.close()";
        String script = PLANTILLA_SYMPY;
        script = script.replace("$FU$", fu);
        script = script.replace("$GX$", gx);
        String solucion = ejecutaPython(script);
        return solucion;
    }

    public static String sumaReglaCadena(String fu, String gx, String hv, String jx) {
        String archivo = String.valueOf(UUID.randomUUID());
        String PLANTILLA_SYMPY = "from sympy import *\n" +
                "from sympy.parsing.latex import parse_latex\n" +
                "\n" +
                "\n" +
                "salida = open(\"/tmp/solucion_$UUID$.txt\",\"w\")\n" +
                "init_printing()\n" +
                "x = var('x')\n" +
                "u = var('u')\n" +
                "v = var('v')\n" +
                "f = parse_latex(r\"$FU$\")\n" +
                "g = parse_latex(r\"$GX$\")\n" +
                "h = parse_latex(r\"$HV$\")\n" +
                "j = parse_latex(r\"$JX$\")\n" +
                "salida.write(\"$$\\\\displaystyle f(x)=%s$$<br/><br/>\\n\" % latex(f.subs(u,g)+h.subs(v,j)))\n" +
                "salida.write(\"$$\\\\displaystyle f(u)=%s$$<br/><br/>\\n\" % latex(f))\n" +
                "salida.write(\"$$\\\\displaystyle u(x)=%s$$<br/><br/>\\n\" % latex(g))\n" +
                "salida.write(\"$$\\\\displaystyle g(v)=%s$$<br/><br/>\\n\" % latex(h))\n" +
                "salida.write(\"$$\\\\displaystyle v(x)=%s$$<br/><br/>\\n\" % latex(j))\n" +
                "df = diff(f,u)\n" +
                "dg = diff(g,x)\n" +
                "dh = diff(h,v)\n" +
                "dj = diff(j,x)\n" +
//                "salida.write(\"$$\\\\displaystyle  \\\\frac{df}{du}=%s$$<br/><br/>\\n\" % latex(df))\n" +
//                "salida.write(\"$$ $$\\n\")\n" +
                "salida.write(\"$$\\\\displaystyle \\\\frac{df}{du}=%s$$<br/><br/>\\n\" % latex(df))\n" +
                "salida.write(\"$$\\\\displaystyle \\\\frac{du}{dx}=%s$$<br/><br/>\\n\" % latex(dg))\n" +
                "salida.write(\"$$\\\\displaystyle \\\\frac{dg}{dv}=%s$$<br/><br/>\\n\" % latex(dh))\n" +
                "salida.write(\"$$\\\\displaystyle \\\\frac{dv}{dx}=%s$$<br/><br/>\\n\" % latex(dj))\n" +
                "result = df*dg+dh*dj\n" +
                "salida.write(\"$$\\\\displaystyle \\\\frac{df}{du}\\\\frac{du}{dx}+ \\\\frac{dg}{dv}\\\\frac{dv}{dx} = %s$$<br/><br/>\\n\" % latex(result))\n" +
                "result = result.subs(u,g)\n" +
                "result = result.subs(v,j)\n" +
                "salida.write(\"$$\\\\displaystyle \\\\frac{df}{dx}=%s=%s$$<br/><br/>\\n\" % (latex(result), latex(result.cancel())))\n" +
                "salida.close()";
        String script = PLANTILLA_SYMPY;

        script = script.replace("$FU$", fu);
        script = script.replace("$GX$", gx);
        script = script.replace("$HV$", hv);
        script = script.replace("$JX$", jx);
        String solucion = ejecutaPython(script);
        return solucion;
    }

}
