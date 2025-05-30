

# Tema 6: Resolución de Ecuaciones Diferenciales Ordinarias (EDOs)

Este tema abarca la resolución numérica de EDOs mediante tres métodos clásicos: **Euler**, **Runge-Kutta de orden 4**, y **Adams-Bashforth de orden 4**. Cada sección incluye una explicación, fórmula, seudocódigo, código Java y un ejemplo resuelto.

---

## 🔹 Método de Euler

### Definición

El método de Euler es una técnica de integración numérica de primer orden que permite aproximar soluciones de EDOs de la forma:

$$
\frac{dy}{dt} = f(t, y), \quad y(t_0) = y_0
$$

Dividiendo el intervalo en pasos $h$, Euler aproxima:

$$
y_{n+1} = y_n + h \cdot f(t_n, y_n)
$$

### Fórmula

$$
y_{n+1} = y_n + h \cdot f(t_n, y_n)
$$

### Seudocódigo

```
INICIO

    // Entrada de datos
    Imprimir "Ingrese el número de ecuaciones del sistema:"
    Leer n

    Imprimir "Ingrese los nombres de las variables (ejemplo: x y z):"
    Leer varNames (lista de n nombres)

    SI longitud(varNames) ≠ n ENTONCES
        Imprimir "Error: debe ingresar exactamente n nombres."
        TERMINAR PROGRAMA
    FIN SI

    // Entrada de ecuaciones
    Crear arreglo funciones de tamaño n
    Imprimir "Ingrese las ecuaciones (use t y las variables indicadas):"
    PARA i desde 0 hasta n-1 HACER
        Imprimir "d" + varNames[i] + "/dt = "
        Leer ecuacion_i

        Crear expresión funciones[i] con variables {"t", varNames}
    FIN PARA

    // Entrada de condiciones iniciales
    Crear arreglo condicionesIniciales de tamaño n
    Imprimir "Ingrese las condiciones iniciales:"
    PARA i desde 0 hasta n-1 HACER
        Imprimir varNames[i] + "(0) = "
        Leer condicionesIniciales[i]
    FIN PARA

    // Parámetros de simulación
    Imprimir "Ingrese el paso de tiempo (h):"
    Leer h

    Imprimir "Ingrese el tiempo final de simulación:"
    Leer tFinal

    pasos = redondear(tFinal / h) + 1

    Crear matriz solucion[n][pasos]
    Crear arreglo tiempo[pasos]

    // Inicializar solución y tiempos
    PARA i desde 0 hasta n-1 HACER
        solucion[i][0] = condicionesIniciales[i]
    FIN PARA

    PARA i desde 0 hasta pasos-1 HACER
        tiempo[i] = i * h
    FIN PARA

    // Método de Euler para cada paso
    PARA i desde 1 hasta pasos-1 HACER
        t = tiempo[i-1]

        Crear arreglo anteriores[n]
        PARA j desde 0 hasta n-1 HACER
            anteriores[j] = solucion[j][i-1]
        FIN PARA

        PARA j desde 0 hasta n-1 HACER
            expr = funciones[j]

            // Asignar valores a las variables en la expresión
            asignar variable "t" = t en expr
            PARA k desde 0 hasta n-1 HACER
                asignar variable varNames[k] = anteriores[k] en expr
            FIN PARA

            derivada = evaluar expr

            // Calcular nuevo valor usando método de Euler
            solucion[j][i] = anteriores[j] + h * derivada
        FIN PARA
    FIN PARA

    // Imprimir resultados
    Imprimir "Resultados numéricos:"
    Imprimir encabezados: "t", varNames...

    PARA i desde 0 hasta pasos-1 HACER
        Imprimir tiempo[i]
        PARA j desde 0 hasta n-1 HACER
            Imprimir solucion[j][i]
        FIN PARA
    FIN PARA

FIN

```

### Código Java

[EulerODESolver.java](https://github.com/paulina-hg/Tema-6---Soluci-n-de-ecuaciones-diferenciales/blob/main/EulerODESolver.java)

### Ejercicio resuelto

Enfriamiento de un objeto (Ley de enfriamiento de Newton)
Contexto:
Un objeto caliente se enfría en una habitación con temperatura constante. Según la ley de enfriamiento de Newton, la velocidad de enfriamiento es proporcional a la diferencia de temperatura entre el objeto y el ambiente.
EDO:
${dy/dx = -k(y - Tamb)}$

y: temperatura del objeto en °C
x: tiempo en minutos
k=0.1: constante de proporcionalidad
Tamb​=20: temperatura ambiente en °C

#### Salida de consola

```
Ingrese el número de ecuaciones del sistema: 1

Ingrese los nombres de las variables (ejemplo: x y z):
y

Ingrese las ecuaciones (use t y las variables indicadas):
dy/dt = -0.1*(y - 20)

Ingrese las condiciones iniciales:
y(0) = 90

Ingrese el paso de tiempo (h): 1
Ingrese el tiempo final de simulación: 10

Resultados numéricos:
t       y(t)
0.00    90.000000
1.00    83.000000
2.00    76.700000
3.00    71.030000
4.00    65.927000
5.00    61.334300
6.00    57.200870
7.00    53.480783
8.00    50.132705
9.00    47.119434
10.00   44.407491
```
Interpretación del resultado
La temperatura inicial era 90°C, y después de 10 minutos bajó a aproximadamente 44.4°C.

---

## 🔹 Método de Runge-Kutta (Orden 4)

### Definición

El método de Runge-Kutta de orden 4 (RK4) proporciona una solución más precisa que Euler. Utiliza cuatro evaluaciones por paso:

$$
\begin{aligned}
k_1 &= h f(x_n, y_n) \\
k_2 &= h f(x_n + h/2, y_n + k_1/2) \\
k_3 &= h f(x_n + h/2, y_n + k_2/2) \\
k_4 &= h f(x_n + h, y_n + k_3) \\
y_{n+1} &= y_n + \frac{1}{6}(k_1 + 2k_2 + 2k_3 + k_4)
\end{aligned}
$$

### Fórmula

$$
y_{n+1} = y_n + \frac{1}{6}(k_1 + 2k_2 + 2k_3 + k_4)
$$

### Seudocódigo

```
INICIO

    MOSTRAR "Método Runge-Kutta sin ScriptEngine, funciones básicas"
    PEDIR función f(x,y) como cadena de texto
    PEDIR x0
    PEDIR y0
    PEDIR paso h
    PEDIR número de pasos

    LLAMAR rungeKutta(funcion, x0, y0, h, pasos)
    MOSTRAR resultado final y(x)

FIN


FUNCIÓN rungeKutta(funcion, x0, y0, h, pasos)
    INICIAR x = x0
    INICIAR y = y0

    PARA i DESDE 0 HASTA pasos - 1 HACER
        k1 = h * evaluarFuncion(funcion, x, y)
        k2 = h * evaluarFuncion(funcion, x + h/2, y + k1/2)
        k3 = h * evaluarFuncion(funcion, x + h/2, y + k2/2)
        k4 = h * evaluarFuncion(funcion, x + h, y + k3)

        y = y + (k1 + 2*k2 + 2*k3 + k4) / 6
        x = x + h

        MOSTRAR "Paso i: x = ..., y = ..."
    FIN PARA

    RETORNAR y
FIN FUNCIÓN


FUNCIÓN evaluarFuncion(funcion, x, y)
    REEMPLAZAR "sin(x)", "cos(x)", "exp(x)", "log(x)" por sus valores con x
    REEMPLAZAR "sin(y)", "cos(y)", "exp(y)", "log(y)" por sus valores con y
    REEMPLAZAR "x" por valor de x
    REEMPLAZAR "y" por valor de y

    RETORNAR evaluarExpresionSimple(funcion)
FIN FUNCIÓN


FUNCIÓN evaluarExpresionSimple(expr)
    REEMPLAZAR "--" por "+"
    DIVIDIR expr en sumandos por los signos + y -

    resultado = 0
    PARA cada sumando EN sumandos
        resultado = resultado + evaluarProducto(sumando)
    FIN PARA

    RETORNAR resultado
FIN FUNCIÓN


FUNCIÓN evaluarProducto(expr)
    SI contiene "/"
        DIVIDIR expr por "/"
        resultado = evaluarFactor(primera parte)
        PARA cada parte restante
            resultado = resultado / evaluarFactor(parte)
    SINO SI contiene "*"
        DIVIDIR expr por "*"
        resultado = 1
        PARA cada parte
            resultado = resultado * evaluarFactor(parte)
    SINO
        resultado = evaluarFactor(expr)

    RETORNAR resultado
FIN FUNCIÓN


FUNCIÓN evaluarFactor(expr)
    QUITAR paréntesis externos
    QUITAR signos "+" al inicio

    SI expr está vacío, RETORNAR 0

    INTENTAR convertir expr a número
        SI éxito, RETORNAR el número
        SI error, MOSTRAR "Error al evaluar" y RETORNAR 0
FIN FUNCIÓN


```

### Código Java

[RungeKuttaBasic.java](https://github.com/paulina-hg/Tema-6---Soluci-n-de-ecuaciones-diferenciales/blob/main/RungeKuttaBasic.java)

### Ejercicio resuelto

María está investigando el crecimiento de algas en un estanque. La tasa de cambio de la cantidad de algas depende del tiempo x y de la cantidad de algas y. El modelo matemático que se le dio es:
${dy/dx = x + y^2 }$
María desea usar el método de Runge-Kutta de 4to orden para predecir la cantidad de algas en cierto momento.
Datos iniciales:
En el instante inicial x0=0, hay y0=1 unidades de algas.
Se usarán 5 pasos.
El tamaño del paso es h=0.1

#### Salida de consola

```
Metodo Runge-Kutta sin ScriptEngine, funciones basicas
Ingresa la funcion f(x,y): x + x*x
Ingresa x0: 0
Ingresa y0: 1
Ingresa paso h: 0.1
Numero de pasos: 5
Paso 1: x = 0.10000, y = 1.00533
Paso 2: x = 0.20000, y = 1.02267
Paso 3: x = 0.30000, y = 1.05400
Paso 4: x = 0.40000, y = 1.10133
Paso 5: x = 0.50000, y = 1.16667

```

Interpretación del resultado: María quería saber cuántas algas habrá en el tiempo x=0.5, usando pasos de h=0.1. Comenzó con 1 unidad de algas en x=0.
La solución aproximada muestra que al finalizar los 5 pasos:
La población de algas ha crecido hasta aproximadamente 1.16667 unidades.

---

## 🔹 Método de Adams-Bashforth (Orden 4)

### Definición

El método de Adams-Bashforth de orden 4 es explícito y requiere los valores de los cuatro pasos anteriores, por lo cual utiliza otro método (como Runge-Kutta) para calcular los primeros tres puntos.

$$
y_{n+1} = y_n + \frac{h}{24} \left( 55 f_n - 59 f_{n-1} + 37 f_{n-2} - 9 f_{n-3} \right)
$$

### Fórmula

$$
y_{n+1} = y_n + \frac{h}{24}(55f_n - 59f_{n-1} + 37f_{n-2} - 9f_{n-3})
$$

### Seudocódigo

```
Inicio

Mostrar "Método de Adams-Bashforth de orden 4"

Pedir al usuario:
    x0 ← valor inicial de x
    y0 ← valor inicial de y
    xf ← valor final de x
    h ← tamaño del paso

Llamar a procedimiento AdamsBashforth(x0, y0, xf, h)

Fin

Procedimiento AdamsBashforth(x0, y0, xf, h)

    n ← redondear hacia arriba ((xf - x0) / h)

    Crear arreglos:
        x[0..n], y[0..n], f[0..n]

    Inicializar primer punto:
        x[0] ← x0
        y[0] ← y0
        f[0] ← f(x0, y0)

    // Usar Runge-Kutta para calcular los primeros 3 puntos
    Para i desde 0 hasta 2 hacer:
        k1 ← f(x[i], y[i])
        k2 ← f(x[i] + h/2, y[i] + h*k1/2)
        k3 ← f(x[i] + h/2, y[i] + h*k2/2)
        k4 ← f(x[i] + h, y[i] + h*k3)

        x[i+1] ← x[i] + h
        y[i+1] ← y[i] + (h/6) * (k1 + 2*k2 + 2*k3 + k4)
        f[i+1] ← f(x[i+1], y[i+1])
    Fin Para

    Mostrar encabezado de tabla "x" y "y"
    Para i desde 0 hasta 3 hacer:
        Mostrar x[i] y y[i]
    Fin Para

    // Aplicar método Adams-Bashforth de orden 4
    Para i desde 3 hasta n-1 hacer:
        x[i+1] ← x[i] + h
        y[i+1] ← y[i] + (h/24) * (55*f[i] - 59*f[i-1] + 37*f[i-2] - 9*f[i-3])
        f[i+1] ← f(x[i+1], y[i+1])
        Mostrar x[i+1] y y[i+1]
    Fin Para

Fin Procedimiento

Función f(x, y)
    Retornar -2 * x * y
Fin Función

```

### Código Java

[AdamsBashforth.java](https://github.com/paulina-hg/Tema-6---Soluci-n-de-ecuaciones-diferenciales/blob/main/AdamsBashforth.java)

### Ejercicio resuelto

Lucía está analizando cómo disminuye la intensidad de una señal mientras se transmite por un cable. El modelo matemático está dado por la ecuación:
${dy/dx = -3xy^2 }$
Al inicio del tramo (x = 0), la intensidad es y = 1.
Lucía quiere saber cuál será la intensidad al final del tramo de 1.5 metros, si toma mediciones cada 0.1 metros.


#### Salida de consola
```
Método de Adams-Bashforth de orden 4
Ingresa el valor inicial de x (x0): 0
Ingresa el valor inicial de y (y0): 1
Ingresa el valor final de x (xf): 1.5
Ingresa el tamaño del paso (h): 0.1
         x          y
    0.0000     1.0000
    0.1000     0.9852
    0.2000     0.9434
    0.3000     0.8811
    0.4000     0.8073
    0.5000     0.7284
    0.6000     0.6506
    0.7000     0.5773
    0.8000     0.5109
    0.9000     0.4517
    1.0000     0.4001
    1.1000     0.3552
    1.2000     0.3164
    1.3000     0.2828
    1.4000     0.2537
    1.5000     0.2285

```

Interpretación del resultado
A lo largo del tramo de 1.5 metros, la señal pierde intensidad de manera progresiva, debido al efecto combinado de la posición en el cable (x) y el cuadrado de la intensidad (y²). Sin embargo, al final del tramo la intensidad no se ha anulado completamente: aún queda aproximadamente un 22.85% de la intensidad original, lo cual indica una atenuación moderada, no extrema.

---

## 📚 Referencias

* Burden, R. L., & Faires, J. D. (2011). *Numerical Analysis*. Cengage Learning.
* Chapra, S. C., & Canale, R. P. (2015). *Métodos numéricos para ingenieros*. McGraw-Hill.


