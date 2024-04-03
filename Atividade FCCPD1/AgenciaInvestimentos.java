import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AgenciaInvestimentos {
    private static Scanner scanner;
    private static MulticastSocket socket;
    private static InetAddress grupoGeral;
    private static InetAddress grupoInvestimentos;
    private static boolean servidorAtivo = true;
    private static List<String> investidoresCanal2 = new ArrayList<>();

    public static void inicializarServidor() throws IOException {
        scanner = new Scanner(System.in);
        socket = new MulticastSocket();
        grupoGeral = InetAddress.getByName("230.0.0.1");
        grupoInvestimentos = InetAddress.getByName("230.0.0.2");

        Thread ReceptorThread = new Thread(new Receptor());
        ReceptorThread.start();

        exibirMenu();
    }

    public static void main(String[] args) throws IOException {
        inicializarServidor();
    }
    
    private static void exibirMenu() throws IOException {
        while (servidorAtivo) { // Loop continua enquanto o servidor estiver ativo
            System.out.println();
            System.out.println("Escolha o tipo de mensagem:");
            System.out.println("1. Dicas de Investimentos em Ações");
            System.out.println("2. Relatórios do Próximo Ativo a Distribuir Dividendos");
            System.out.println("3. Listar investidores conectados no canal 2");
            System.out.println("0. Desconectar");
            System.out.println();
            System.out.print("Opção: ");
            int escolha = scanner.nextInt();
            scanner.nextLine(); 

            if (escolha == 0) {
                servidorAtivo = false; // Define o servidor como inativo ao desconectar
                break; 
            } else if (escolha == 1) {
                enviarDicaInvestimento();
            } else if (escolha == 2) {
                enviarOkParaCanal2();
                enviarRelatorioDividendos();
            } else if (escolha == 3) {
                imprimirInvestidoresCanal2();
            } else {
                System.out.println("Opção inválida.");
            }
        }

        socket.close();
        System.out.println("Servidor da Agência de Investimentos desconectado.");
    }

    private static void enviarDicaInvestimento() throws IOException {
        System.out.println("Digite a dica de investimento em ações:");
        System.out.print("Nome da ação: ");
        String acao = scanner.nextLine().toUpperCase();
    
        System.out.print("Recomendação (Compra/Venda): ");
        String recomendacao = scanner.nextLine().toUpperCase();
    
        System.out.println("Informações adicionais sobre a tendência/oportunidade:");
        String informacoes = scanner.nextLine();
    
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        String dataFormatada = df.format(new Date());
        String serverName = "Servidor";
        String mensagemFormatada = String.format("[%s] [%s] Dicas de Investimentos em Ações: %s - Recomendação: %s - %s.", 
        dataFormatada, serverName, acao, recomendacao, informacoes);
    
        byte[] envio = mensagemFormatada.getBytes();
        DatagramPacket pacote = new DatagramPacket(envio, envio.length, grupoGeral, 4321);
        socket.send(pacote);
    
        System.out.println("Dica de investimento em ações enviada.");
    }

    
    private static void enviarRelatorioDividendos() throws IOException {
        System.out.println("Digite o relatório do próximo ativo a distribuir dividendos:");
        System.out.print("Nome do ativo: ");
        String ativo = scanner.nextLine().toUpperCase();
    
        System.out.print("Valor do dividendo: ");
        String valorDividendo = scanner.nextLine();
    
        String dataDistribuicao = Validacoes.verificarFormatarData();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        String dataFormatada = df.format(new Date());
        String mensagemFormatada = String.format("[%s] Próximo Ativo a Distribuir Dividendos: %s - Valor do Dividendo: %s - Data prevista: %s",
        dataFormatada, ativo, valorDividendo, dataDistribuicao);
    
        byte[] envio = mensagemFormatada.getBytes();
        DatagramPacket pacote = new DatagramPacket(envio, envio.length, grupoInvestimentos, 4321);
        socket.send(pacote);
    
        System.out.println("Relatório de próximo ativo a distribuir dividendos enviado.");
    }   
    
    private static void enviarOkParaCanal2() throws IOException {
        String mensagemOk = "OK";
        byte[] envio = mensagemOk.getBytes();
        DatagramPacket pacote = new DatagramPacket(envio, envio.length, grupoInvestimentos, 4321);
        socket.send(pacote);
    }
    
    private static void imprimirInvestidoresCanal2() {
        System.out.println();
        System.out.println("Investidores conectados no Canal 2:");
        for (String investidor : investidoresCanal2) {
            System.out.println("- " + investidor);
        }
    }
    

    static class Receptor implements Runnable {
        @SuppressWarnings("deprecation")
        @Override
        public void run() {
            try {
                byte[] buffer = new byte[1024];
                MulticastSocket multicastSocket = new MulticastSocket(4321); // Associar à porta 4321
                multicastSocket.joinGroup(grupoInvestimentos); // Juntar-se ao grupo multicast
    
    
                while (servidorAtivo) {
                    DatagramPacket pacoteRecebido = new DatagramPacket(buffer, buffer.length);
                    multicastSocket.receive(pacoteRecebido);
                    String mensagemRecebida = new String(pacoteRecebido.getData(), 0, pacoteRecebido.getLength());
                    tratarMensagemRegistro(mensagemRecebida);
                }
    
                multicastSocket.leaveGroup(grupoInvestimentos); // Deixar o grupo multicast
                multicastSocket.close(); // Fechar o socket quando o servidor for desativado
            } catch (IOException e) {
                if (!servidorAtivo) {
                } else {
                }
            }
        }
    
        private void tratarMensagemRegistro(String mensagem) {
            String[] partes = mensagem.split("\\|");
            if (partes.length == 2) {
                String nomeUsuario = partes[0];
                String status = partes[1];
                if (status.equals("ENTRADA")) {
                    if (!investidoresCanal2.contains(nomeUsuario)) {
                        investidoresCanal2.add(nomeUsuario);
                        System.out.println("[Servidor] Novo investidor conectado ao Canal 2: " + nomeUsuario);
                    }
                } else if (status.equals("SAIDA")) {
                    if (investidoresCanal2.contains(nomeUsuario)) {
                        investidoresCanal2.remove(nomeUsuario);
                        System.out.println("[Servidor] Investidor desconectado do Canal 2: " + nomeUsuario);
                    }
                }
            }
        }
    }
    
}
