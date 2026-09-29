import java.util.Scanner;

public class SumaPrimos {

    /** Límite superior para no agotar la memoria con la criba (boolean[n+1]). */
    private static final int LIMITE_MAXIMO = 100_000_000;

    /**
     * Calcula la suma de todos los números primos menores o iguales que n.
     *
     * @param n entero positivo
     * @return suma de los primos <= n (0 si n < 2)
     * @throws IllegalArgumentException si n no es positivo
     */
    public static long sumaDePrimos(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n debe ser un entero positivo.");
        }
        if (n < 2) {
            return 0; // no hay primos menores o iguales a 1
        }

        // esCompuesto[i] == true  ->  i NO es primo
        boolean[] esCompuesto = new boolean[n + 1];
        long suma = 0;

        for (int i = 2; i <= n; i++) {
            if (!esCompuesto[i]) {
                suma += i;
                // Marcar múltiplos de i empezando en i*i (los menores ya fueron marcados).
                // Se usa long para que i*i no desborde el tipo int.
                for (long multiplo = (long) i * i; multiplo <= n; multiplo += i) {
                    esCompuesto[(int) multiplo] = true;
                }
            }
        }
        return suma;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== SUMA DE NÚMEROS PRIMOS ===");

        while (true) {
            System.out.print("Ingresa un número entero positivo n: ");
            if (!scanner.hasNextLine()) {
                System.out.println("No se recibió ningún dato. Programa finalizado.");
                break;
            }
            String entrada = scanner.nextLine().trim();

            try {
                int n = Integer.parseInt(entrada);
                if (n < 1) {
                    System.out.println("  Error: n debe ser positivo (mayor o igual a 1). Intenta de nuevo.");
                } else if (n > LIMITE_MAXIMO) {
                    System.out.println("  Error: n es demasiado grande para este programa (máximo "
                            + LIMITE_MAXIMO + "). Intenta con un valor menor.");
                } else {
                    long resultado = sumaDePrimos(n);
                    System.out.println("La suma de todos los números primos menores o iguales que "
                            + n + " es: " + resultado);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("  Error: '" + entrada + "' no es un número entero válido. Ejemplo de entrada: 10");
            }
        }
        scanner.close();
    }
}
