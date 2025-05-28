import java.util.Scanner;

public class RungeKuttaBasic {

    // Evaluador simple de la función f(x,y)
    public static double evaluarFuncion(String funcion, double x, double y) {
	    funcion = funcion.toLowerCase().replace(" ", "");
	
	    funcion = funcion.replaceAll("sin\\(x\\)", Double.toString(Math.sin(x)));
	    funcion = funcion.replaceAll("cos\\(x\\)", Double.toString(Math.cos(x)));
	    funcion = funcion.replaceAll("exp\\(x\\)", Double.toString(Math.exp(x)));
	    funcion = funcion.replaceAll("log\\(x\\)", Double.toString(Math.log(x)));
	
	    funcion = funcion.replaceAll("sin\\(y\\)", Double.toString(Math.sin(y)));
	    funcion = funcion.replaceAll("cos\\(y\\)", Double.toString(Math.cos(y)));
	    funcion = funcion.replaceAll("exp\\(y\\)", Double.toString(Math.exp(y)));
	    funcion = funcion.replaceAll("log\\(y\\)", Double.toString(Math.log(y)));
	
	    // Aquí reemplazamos sin paréntesis para evitar errores
	    funcion = funcion.replaceAll("x", Double.toString(x));
	    funcion = funcion.replaceAll("y", Double.toString(y));
	
	    return evaluarExpresionSimple(funcion);
	}

    // Evalúa expresiones simples con + y - (sin paréntesis)
    public static double evaluarExpresionSimple(String expr) {
        double resultado = 0;
        expr = expr.replaceAll("--", "+"); // convierte dobles negativos

        // Separar en sumandos
        String[] sumandos = expr.split("(?=[+-])"); // split pero manteniendo signo delante

        for (String sumando : sumandos) {
            resultado += evaluarProducto(sumando);
        }
        return resultado;
    }

    // Evalúa productos y divisiones dentro de un sumando
    public static double evaluarProducto(String expr) {
        String[] factores;
        double resultado = 1;

        // Si contiene división, la evaluamos con prioridad
        if (expr.contains("/")) {
            factores = expr.split("/");
            resultado = evaluarFactor(factores[0]);
            for (int i = 1; i < factores.length; i++) {
                resultado /= evaluarFactor(factores[i]);
            }
        } else if (expr.contains("*")) {
            factores = expr.split("\\*");
            for (String f : factores) {
                resultado *= evaluarFactor(f);
            }
        } else {
            resultado = evaluarFactor(expr);
        }
        return resultado;
    }

	public static double evaluarFactor(String expr) {
	    expr = expr.trim();
	
	    // Quitar paréntesis externos
	    while (expr.startsWith("(") && expr.endsWith(")")) {
	        expr = expr.substring(1, expr.length() - 1).trim();
	    }
	
	    // Quitar signos + redundantes al inicio
	    while (expr.startsWith("+")) {
	        expr = expr.substring(1).trim();
	    }
	
	    if (expr.isEmpty()) return 0;
	
	    try {
	        return Double.parseDouble(expr);
	    } catch (NumberFormatException e) {
	        System.out.println("Error al evaluar factor: " + expr);
	        return 0;
	    }
	}
	
    // Runge-Kutta 4to orden
    public static double rungeKutta(String funcion, double x0, double y0, double h, int pasos) {
        double x = x0;
        double y = y0;

        for (int i = 0; i < pasos; i++) {
            double k1 = h * evaluarFuncion(funcion, x, y);
            double k2 = h * evaluarFuncion(funcion, x + h / 2.0, y + k1 / 2.0);
            double k3 = h * evaluarFuncion(funcion, x + h / 2.0, y + k2 / 2.0);
            double k4 = h * evaluarFuncion(funcion, x + h, y + k3);

            y += (k1 + 2*k2 + 2*k3 + k4) / 6.0;
            x += h;

            System.out.printf("Paso %d: x = %.5f, y = %.5f\n", i + 1, x, y);
        }
        return y;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Método Runge-Kutta sin ScriptEngine, funciones básicas");
        System.out.print("Ingresa la función f(x,y) (ejemplo: x + y, sin(x) - y, x * y): ");
        String funcion = sc.nextLine();

        System.out.print("Ingresa x0: ");
        double x0 = sc.nextDouble();

        System.out.print("Ingresa y0: ");
        double y0 = sc.nextDouble();

        System.out.print("Ingresa paso h: ");
        double h = sc.nextDouble();

        System.out.print("Número de pasos: ");
        int pasos = sc.nextInt();

        double resultado = rungeKutta(funcion, x0, y0, h, pasos);
        System.out.printf("Resultado final: y(%.5f) = %.5f\n", x0 + pasos * h, resultado);
        sc.close();
    }
}

