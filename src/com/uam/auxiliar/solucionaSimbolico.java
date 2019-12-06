package com.uam.auxiliar;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * solucionaSimbolico genera scripts en python para hacer derivación simbolica usando
 * la biblioteca SymPy (https://www.sympy.org/)
 * Se usa Python3 y al parecer puede funcionar con Python2
 * Para poder leer las expresiones en LaTex es necesario instalar (pip o conda) antlr4
 * $ pip3 install antlr4-python3-runtime
 * o
 * $ conda install --channel=conda-forge antlr-python-runtime
 * <p>
 * Habría que explorar si las bibliotecas necesarias se pueden usar con jython y evitar la salida a shell
 *
 * @author: Iván Gutierrez
 */

public class solucionaSimbolico {
    /**
     * ejecutaPython recibe un script en python, lo ejecuta y regresa la salida de la ejecución
     *
     * @param code Script en python para ejecutar.
     * @return cadena con el resultado de la ejecución.
     * @author: Iván Gutierrez
     */
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

    /**
     * Incremento hace derivación simbólica de la expresión usando el límite que tiende a cero del incremento
     *
     * @param expresion cadena en LaTex con expresión para derivar
     * @return cadena con el proceso de derivación usando el método del incremento que tiende a cero
     * @see "Cálculo de una Variable, Thomas 12a. Edición cap 3.2"
     */
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

    /**
     * solucionaSimbolicoConjugados deriva simbólicamente expresiones algebráicas en LaTex usando el método de limite que tiende a cero y
     * empeando además la multiplicación por el conjugado del incremento para eliminar la ideterminación
     *
     * @param expresion cadena en LaTex con expresión para derivar
     * @return cadena con el proceso de derivación usando el método del incremento que tiende a cero
     * @see "Cálculo de una Variable, Thomas 12a. Edición cap 3.2"
     */
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

    /**
     * reglaCadena deriva simbólicamente expresiones algebráicas en LaTex usando la regla de la cadena
     * se escoge una variable de sustitucion "u" y se ponen las expresiones en términos de f(u) y u(x)=g(x)
     *
     * @param fu función de la variable de sustitución  (u)
     * @param gx función de la variable original (x)
     * @return cadena con el proceso de derivación usando la regla de la cadena
     * @see "Cálculo de una Variable, Thomas 12a. Edición cap 3.6"
     */

    public static String reglaCadena(String fu, String gx) {
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

    /**
     * reglaCadenaNoCancel deriva simbólicamente expresiones algebráicas en LaTex usando la regla de la cadena
     * se escoge una variable de sustitucion "u" y se ponen las expresiones en términos de f(u) y u(x)=g(x).
     * A diferencia del método reglaCadena evita una simplificación al final
     *
     * @param fu función de la variable de sustitución  (u)
     * @param gx función de la variable original (x)
     * @return cadena con el proceso de derivación usando la regla de la cadena
     * @see class reglaCadena
     */

    public static String reglaCadenaNoCancel(String fu, String gx) {

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

    /**
     * reglaCadenaTrig deriva simbólicamente expresiones algebráicas en LaTex usando la regla de la cadena
     * se escoge una variable de sustitucion "u" y se ponen las expresiones en términos de f(u) y u(x)=g(x).
     * El método intenta hacer simplificación de expresiones trigonométricas
     *
     * @param fu función de la variable de sustitución  (u)
     * @param gx función de la variable original (x)
     * @return cadena con el proceso de derivación usando la regla de la cadena
     * @see class reglaCadena
     */
    public static String reglaCadenaTrig(String fu, String gx) {

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

    /**
     * sumaReglaCadena deriva simbólicamente expresiones algebráicas en LaTex usando la regla de la cadena para una suma
     * de dos sumandos.
     * Se escoge una variable de sustitucion "u" y se ponen las expresiones en términos de f(u) y u(x)=g(x) para el primer sumando.
     * Se escoge una variable de sustitucion "v" y se ponen las expresiones en términos de h(v) y v(x)=j(x) para el segundo sumando.
     * El método muestra los pasos de derivación con la regla de la cadena para cada sumando y efectúa la suma sibólica
     *
     * @param fu función de la variable de sustitución  (u)
     * @param gx función de la variable original (x)
     * @return cadena con el proceso de derivación usando la regla de la cadena
     * @see class reglaCadena
     */
    public static String sumaReglaCadena(String fu, String gx, String hv, String jx) {
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

    /**
     *  sumaProductosCadena deriva simbólicamente de la suma de dos productos
     *  de la forma y = uv + wz.
     * @param u primer factor del primer sumando como función de x
     * @param v
     * @param w
     * @param z
     * @return
     */
    public static String sumaProductosCadena(String u, String v, String w, String z){
        String PLANTILLA_SYMPY = "from sympy import *\n" +
                "from sympy.parsing.latex import parse_latex\n" +
                "#Solución a derivada de y=uv+wz\n" +
                "\n" +
                "salida = open(\"/tmp/solucion_$UUID$.txt\",\"w\")\n" +
                "init_printing()\n" +
                "x = var('x')\n" +
                "u = var('u')\n" +
                "v = var('v')\n" +
                "w = var('w')\n" +
                "z = var('z')\n" +
                "u = parse_latex(r\"$U$\")\n" +
                "v = parse_latex(r\"$V$\")\n" +
                "w = parse_latex(r\"$W$\")\n" +
                "z = parse_latex(r\"$Z$\")\n" +
                "du = diff(u)\n" +
                "dv = diff(v)\n" +
                "dw = diff(w)\n" +
                "dz = diff(z)\n" +
                "salida.write(\"$$y=%s$$<br/><br/>\\n\" % latex(u*v+w*z))\n" +
                "salida.write(\"$$u=%s$$<br/><br/>\\n\" % latex(u))\n" +
                "salida.write(\"$$v=%s$$<br/><br/>\\n\" % latex(v))\n" +
                "salida.write(\"$$w=%s$$<br/><br/>\\n\" % latex(w))\n" +
                "salida.write(\"$$z=%s$$<br/><br/><br/>\\n\" % latex(z))\n" +
                "salida.write(\"$$u'=%s$$<br/><br/>\\n\" % latex(du))\n" +
                "salida.write(\"$$v'=%s$$<br/><br/>\\n\" % latex(dv))\n" +
                "salida.write(\"$$w'=%s$$<br/><br/>\\n\" % latex(dw))\n" +
                "salida.write(\"$$z'=%s$$<br/><br/><br/>\\n\" % latex(dz))\n" +
                "result = du*v+dv*u+dw*z+dz*w\n" +
                "salida.write(\"$$y'=(u\\'v+v\\'u)+(w\\'z+z\\'w)$$<br/><br/>\\n\")\n" +
                "salida.write(\"$$y'=[(%s) (%s) +(%s) (%s)]+[(%s) (%s)]+[(%s)(%s)]$$<br/><br/>\\n\"% ( latex(du), latex(v),latex(dv), latex(u), latex(dw), latex(z),latex(dz), latex(w) ) )\n" +
                "salida.write(\"$$y'=%s$$<br/>\\n\" % latex(result))\n" +
                "salida.close()"
                ;
        String script = PLANTILLA_SYMPY;
        script = script.replace("$U$", u);
        script = script.replace("$V$", v);
        script = script.replace("$W$", w);
        script = script.replace("$Z$", z);
        String solucion = ejecutaPython(script);
        return solucion;
    }

    public static String sumaPotenciasCadena(String ux, String n, String vx, String m){
        String PLANTILLA_SYMPY = "from sympy import *\n" +
                "from sympy.parsing.latex import parse_latex\n" +
                "#Solución a derivada de y=uv+wz\n" +
                "\n" +
                "salida = open(\"/tmp/solucion_$UUID$.txt\",\"w\")\n" +
                "init_printing()\n" +
                "x = var('x')\n" +
                "u = var('u')\n" +
                "n = var('n')\n" +
                "v = var('v')\n" +
                "m = var('m')\n" +
                "ux = parse_latex(r\"$UX$\")\n" +
                "n = $N$\n" +
                "vx = parse_latex(r\"$VX$\")\n" +
                "m = $M$\n" +
                "f1 = u ** n\n" +
                "f2 = v ** m\n" +
                "fx = ux ** n + vx ** m\n" +
                "salida.write(\"$$f(x)=%s$$<br><br>\\n\" % latex(fx))\n" +
                "# u(x)\n" +
                "salida.write(\"$$u(x)=%s$$<br><br>\\n\" % latex(ux))\n" +
                "dfu = diff(u ** n)\n" +
                "salida.write(\"$$\\\\frac{d}{du}(%s)=%s$$<br><br>\\n\" % (latex(u ** n), latex(dfu)))\n" +
                "dux = diff(ux)\n" +
                "salida.write(\"$$\\\\frac{d}{dx}(%s)=%s$$<br><br>\\n\" % (latex(ux), latex(dux)))\n" +
                "df1 = dfu * dux\n" +
                "salida.write(\"$$\\\\frac{d}{du}\\\\frac{du}{dx}=%s$$<br><br>\\n\" % latex(df1))\n" +
                "df1 = df1.subs(u, ux)\n" +
                "salida.write(\"$$\\\\frac{d}{dx}(%s)=%s$$<br><br>\\n\" % (latex(f1.subs(u, ux)), latex(df1)))\n" +
                "# v(x)\n" +
                "salida.write(\"$$v(x)=%s$$$<br><br>\\n\" % latex(vx))\n" +
                "dfv = diff(v ** m)\n" +
                "salida.write(\"$$\\\\frac{d}{dv}(%s)=%s$$<br><br>\\n\" % (latex(v ** m), latex(dfv)))\n" +
                "dvx = diff(vx)\n" +
                "salida.write(\"$$\\\\frac{d}{dx}(%s)=%s$$<br><br>\\n\" % (latex(vx), latex(dvx)))\n" +
                "df2 = dfv * dvx\n" +
                "salida.write(\"$$\\\\frac{d}{du}\\\\frac{du}{dx}=%s$$<br><br>\\n\" % latex(df2))\n" +
                "df2 = df2.subs(v, vx)\n" +
                "salida.write(\"$$\\\\frac{d}{dx}(%s)=%s$$<br><br>\\n\" % (latex(f2.subs(v, vx)), latex(df2)))\n" +
                "\n" +
                "salida.write(\"$$\\\\frac{d}{dx}(%s)=%s$$<br><br>\\n\" % (latex(fx), latex(df1 + df2)))\n"+
                "salida.close()"
                ;
        String script = PLANTILLA_SYMPY;
        script = script.replace("$UX$", ux);
        script = script.replace("$VX$", vx);
        script = script.replace("$N$", n);
        script = script.replace("$M$", m);
        String solucion = ejecutaPython(script);
        return solucion;
    }

}
