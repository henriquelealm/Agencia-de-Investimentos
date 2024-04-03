import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Bem-vindo ao sistema de comunicação da Agência de Investimentos.");
            System.out.println("Escolha uma opção:");
            System.out.println("1. Ser Agência de Investimentos (Servidor)");
            System.out.println("2. Ser Investidor");

            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            if (opcao == 1) {
                try {
                    AgenciaInvestimentos.inicializarServidor();
                } catch (IOException e) {
                    System.out.println("Erro ao iniciar o servidor da Agência de Investimentos: " + e.getMessage());
                }
            } else if (opcao == 2) {
                Investidor.iniciarInvestidor();
            } else {
                System.out.println("Opção inválida.");
            }
        }
    }
}
