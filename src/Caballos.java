import java.util.Scanner;

public class Caballos {

    private static final int TAMANO_TABLERO = 8;

    /**
     * Literal A: decide si un caballo en (filaCab, colCab) ataca a un rey en
     * (filaRey, colRey). Las filas y columnas van de 1 a 8.
     *
     * @return true si el caballo ataca al rey, false en caso contrario.
     * @throws IllegalArgumentException si alguna posición está fuera del tablero.
     */
    public static boolean caballoAtacaRey(int filaCab, int colCab, int filaRey, int colRey) {
        validarCasilla(filaCab, colCab, "caballo");
        validarCasilla(filaRey, colRey, "rey");

        int difFilas = Math.abs(filaCab - filaRey);
        int difColumnas = Math.abs(colCab - colRey);

        // Movimiento en "L": (1,2) o (2,1)
        return (difFilas == 1 && difColumnas == 2) || (difFilas == 2 && difColumnas == 1);
    }

    private static void validarCasilla(int fila, int columna, String pieza) {
        if (fila < 1 || fila > TAMANO_TABLERO || columna < 1 || columna > TAMANO_TABLERO) {
            throw new IllegalArgumentException("La posición del " + pieza + " (" + fila + ", " + columna
                    + ") está fuera del tablero (valores permitidos: 1 a " + TAMANO_TABLERO + ").");
        }
    }

    /**
     * Lee un entero dentro de un rango, repitiendo la pregunta hasta que el
     * usuario ingrese un valor válido. Devuelve null si se acaba la entrada.
     */
    private static Integer leerEntero(Scanner scanner, String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);
            if (!scanner.hasNextLine()) {
                return null;
            }
            String entrada = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor < minimo || valor > maximo) {
                    System.out.println("  Error: el valor debe estar entre " + minimo + " y " + maximo + ". Intenta de nuevo.");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("  Error: '" + entrada + "' no es un número entero. Ingresa un número, por ejemplo 4.");
            }
        }
    }

    /** Literal B: programa principal. */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== ¿EL CABALLO ATACA AL REY? ===");
        System.out.println("El tablero es de " + TAMANO_TABLERO + "x" + TAMANO_TABLERO
                + ". Filas y columnas se numeran de 1 a " + TAMANO_TABLERO + ".");
        System.out.println();

        System.out.println("Posición del CABALLO:");
        Integer filaCab = leerEntero(scanner, "  Fila (1-8): ", 1, TAMANO_TABLERO);
        Integer colCab = (filaCab == null) ? null : leerEntero(scanner, "  Columna (1-8): ", 1, TAMANO_TABLERO);

        Integer filaRey = null;
        Integer colRey = null;
        if (colCab != null) {
            System.out.println("Posición del REY:");
            boolean mismaCasilla;
            do {
                filaRey = leerEntero(scanner, "  Fila (1-8): ", 1, TAMANO_TABLERO);
                colRey = (filaRey == null) ? null : leerEntero(scanner, "  Columna (1-8): ", 1, TAMANO_TABLERO);
                mismaCasilla = colRey != null && filaRey.equals(filaCab) && colRey.equals(colCab);
                if (mismaCasilla) {
                    System.out.println("  Error: el rey no puede estar en la misma casilla del caballo. Ingresa otra posición.");
                }
            } while (mismaCasilla);
        }

        if (colCab == null || filaRey == null || colRey == null) {
            System.out.println("No se recibieron todos los datos. Programa finalizado.");
        } else {
            System.out.println();
            if (caballoAtacaRey(filaCab, colCab, filaRey, colRey)) {
                System.out.println("SÍ: el rey en (" + filaRey + ", " + colRey + ") es atacado por el caballo en ("
                        + filaCab + ", " + colCab + ").");
            } else {
                System.out.println("NO: el rey en (" + filaRey + ", " + colRey + ") NO es atacado por el caballo en ("
                        + filaCab + ", " + colCab + ").");
            }
        }
        scanner.close();
    }
}
