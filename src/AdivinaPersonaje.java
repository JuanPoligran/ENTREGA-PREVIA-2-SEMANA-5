import java.util.Scanner;

public class AdivinaPersonaje {

    /** Nodo del árbol: pregunta (nodo interno) o personaje (hoja). */
    private static class Nodo {
        final String texto;
        final Nodo si;
        final Nodo no;

        Nodo(String pregunta, Nodo si, Nodo no) {
            this.texto = pregunta;
            this.si = si;
            this.no = no;
        }

        Nodo(String personaje) {
            this(personaje, null, null);
        }

        boolean esHoja() {
            return si == null && no == null;
        }
    }

    private static final int MAX_PREGUNTAS = 5;

    private static final String[] PERSONAJES = {
            "Radamel Falcao García", "Goku", "Michael Jordan", "Eminem",
            "Darth Vader", "Adam Sandler", "Bruce Wayne", "Tin Tin",
            "Ayudante de Santa", "Joe Biden", "José Saramago",
            "Günter Grass", "Kim Jong Un"
    };

    /** Construye el árbol de decisión completo (de las hojas hacia la raíz). */
    private static Nodo construirArbol() {
        // ---------- Rama de personas reales ----------
        Nodo deportista = new Nodo("¿Juega baloncesto?",
                new Nodo("Michael Jordan"),
                new Nodo("Radamel Falcao García"));

        Nodo politico = new Nodo("¿Es de Estados Unidos?",
                new Nodo("Joe Biden"),
                new Nodo("Kim Jong Un"));

        Nodo escritor = new Nodo("¿Es de nacionalidad portuguesa?",
                new Nodo("José Saramago"),
                new Nodo("Günter Grass"));

        Nodo artista = new Nodo("¿Es rapero?",
                new Nodo("Eminem"),
                new Nodo("Adam Sandler"));

        Nodo noPolitico = new Nodo("¿Es escritor?", escritor, artista);
        Nodo noDeportista = new Nodo("¿Es (o fue) político o gobernante?", politico, noPolitico);
        Nodo real = new Nodo("¿Es deportista?", deportista, noDeportista);

        // ---------- Rama de personajes ficticios ----------
        Nodo conPoderes = new Nodo("¿Es un villano?",
                new Nodo("Darth Vader"),
                new Nodo("Goku"));

        Nodo sinPoderes = new Nodo("¿Es periodista o reportero?",
                new Nodo("Tin Tin"),
                new Nodo("Bruce Wayne"));

        Nodo noNavidad = new Nodo("¿Tiene poderes o habilidades sobrenaturales (la Fuerza, el Ki, etc.)?",
                conPoderes, sinPoderes);

        Nodo ficticio = new Nodo("¿Está asociado con la Navidad?",
                new Nodo("Ayudante de Santa"),
                noNavidad);

        // ---------- Raíz ----------
        return new Nodo("¿Es una persona real (existe o existió)?", real, ficticio);
    }

    /**
     * Lee una respuesta Sí/No validando la entrada.
     *
     * @return true si es Sí, false si es No, null si ya no hay más entrada.
     */
    private static Boolean leerRespuesta(Scanner scanner) {
        while (true) {
            System.out.print("   Responde (s/n): ");
            if (!scanner.hasNextLine()) {
                return null; // no hay más entrada disponible
            }
            String entrada = scanner.nextLine().trim().toLowerCase();
            switch (entrada) {
                case "s":
                case "si":
                case "sí":
                    return true;
                case "n":
                case "no":
                    return false;
                default:
                    System.out.println("   Entrada no válida. Escribe 's' para Sí o 'n' para No.");
            }
        }
    }

    private static void mostrarLista() {
        System.out.println("Piensa en UNO de estos personajes (no me digas cuál):");
        for (int i = 0; i < PERSONAJES.length; i++) {
            System.out.printf("  %2d. %s%n", i + 1, PERSONAJES[i]);
        }
        System.out.println();
    }

    /** Juega una partida. Devuelve false si se quedó sin entrada. */
    private static boolean jugar(Scanner scanner, Nodo raiz) {
        mostrarLista();
        System.out.println("Cuando estés listo, presiona ENTER para comenzar...");
        if (!scanner.hasNextLine()) {
            return false;
        }
        scanner.nextLine();

        Nodo actual = raiz;
        int preguntasHechas = 0;

        while (!actual.esHoja()) {
            preguntasHechas++;
            System.out.printf("Pregunta %d de máximo %d: %s%n", preguntasHechas, MAX_PREGUNTAS, actual.texto);
            Boolean respuesta = leerRespuesta(scanner);
            if (respuesta == null) {
                return false;
            }
            actual = respuesta ? actual.si : actual.no;
        }

        System.out.println();
        System.out.println("¡Ya lo sé! Tu personaje es: " + actual.texto);
        System.out.println("(Lo descubrí con " + preguntasHechas + " pregunta(s).)");
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Nodo raiz = construirArbol();

        System.out.println("=== ADIVINA EL PERSONAJE ===");
        boolean continuar = true;
        while (continuar) {
            if (!jugar(scanner, raiz)) {
                break;
            }
            System.out.println();
            System.out.println("¿Quieres jugar otra vez?");
            Boolean otra = leerRespuesta(scanner);
            continuar = otra != null && otra;
            System.out.println();
        }
        System.out.println("¡Gracias por jugar!");
        scanner.close();
    }
}
