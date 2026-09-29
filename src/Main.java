import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        // LECTURA DE LOS METROS

        double metros;

        System.out.println("INGRESE EL NUMERO DE METROS");
        metros = teclado.nextDouble();

        // CONVERSIONES

        double pies;
        double pulgadas;
        double centimetros;

        pies = metros * 3.28084;
        pulgadas = metros * 39.3701;
        centimetros = metros * 100;

        // IMPRESION DE RESULTADOS

        System.out.println(metros + " METROS EQUIVALEN A:");
        System.out.println(pies + " PIES");
        System.out.println(pulgadas + " PULGADAS");
        System.out.println(centimetros + " CENTIMETROS");

    }
}
