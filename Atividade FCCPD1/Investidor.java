import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Investidor implements Runnable {
    private String topico;
    private static MulticastSocket socket;
    private static InetAddress grupoGeral;
    private static InetAddress grupoInvestimentos;
    private boolean encerrado = false;
    private static String nomeUsuario;

    @SuppressWarnings("static-access")
    public Investidor(String topico, MulticastSocket socket, String nomeUsuario) {
        this.topico = topico;
        this.socket = socket;
        this.nomeUsuario = nomeUsuario;
        try {
            grupoGeral = InetAddress.getByName("230.0.0.1");
            grupoInvestimentos = InetAddress.getByName("230.0.0.2");
        } catch (IOException e) {
            System.out.println("Erro ao definir grupo de multicast: " + e.getMessage());
        }

        
    }

    @SuppressWarnings("deprecation")
    @Override
    public void run() {
        try {
            InetAddress grupo = this.topico.equals("Dicas de Investimentos em Ações") ? grupoGeral : grupoInvestimentos;

            // Verifica se o socket já está associado ao grupo antes de tentar se juntar a ele novamente
            if (!socket.isBound() || socket.getInetAddress() == null || !socket.getInetAddress().equals(grupo)) {
                socket.joinGroup(grupo);
            }

            byte[] buffer = new byte[1024];
            System.out.println("[" + this.topico + "] Aguardando atualizações...");
            while (!encerrado) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String msg = new String(packet.getData(), 0, packet.getLength());
                System.out.println("[" + this.topico + "] Atualização recebida: " + msg);
            }
        } catch (IOException e) {
            if (encerrado) {
                System.out.println("[" + this.topico + "] Erro(Run):  " + e.getMessage());
            }
        } finally {
            if (!socket.isClosed()) {
                socket.close();
            }
        }
    }

    public static void main(String[] args) {
        iniciarInvestidor();
    }

    public static void iniciarInvestidor() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Digite o seu nome de usuário: ");
            String nomeUsuario = sc.nextLine();

            MulticastSocket socket = null;

            while (true) {
                System.out.println();
                System.out.println("Escolha o tópico de interesse:");
                System.out.println("1. Dicas de Investimentos em Ações");
                System.out.println("2. Relatórios de Dividendos");
                System.out.println("3. Encerrar");
                System.out.print("Opção: ");

                if (sc.hasNextInt()) {
                    int opcao = sc.nextInt();
                    sc.nextLine();

                    String topico;
                    switch (opcao) {
                        case 1:
                            topico = "Dicas de Investimentos em Ações";
                            System.out.println(nomeUsuario + ", você está acompanhando o tópico " + topico + ".");
                            if (socket == null) {
                                try {
                                    socket = new MulticastSocket(4321);
                                    grupoGeral = InetAddress.getByName("230.0.0.1");
                                } catch (IOException e) {
                                    System.out.println("Erro ao criar o socket: " + e.getMessage());
                                    return;
                                }
                            }
                            enviarMensagem(nomeUsuario, topico, socket, sc);
                            break;
                        case 2:
                            topico = "Relatórios de Dividendos";
                            System.out.println(nomeUsuario + ", você está acompanhando o tópico " + topico + ". Aguarde atualizações do servidor.");
                            if (socket == null) {
                                try {
                                    socket = new MulticastSocket(4321);
                                    grupoGeral = InetAddress.getByName("230.0.0.2");
                                } catch (IOException e) {
                                    System.out.println("Erro ao criar o socket: " + e.getMessage());
                                    return;
                                }
                            }

                            new Thread(new Investidor(topico, socket, nomeUsuario)).start();
                            if (topico.equals("Relatórios de Dividendos")) {
                                enviarMensagemRegistro("ENTRADA");
                            }

                            // Verifica se o usuário está conectado ao canal 2
                            if (topico.equals("Relatórios de Dividendos")) {
                                while (true) {
                                    System.out.println();
                                    System.out.println("Digite 'back' para escolher outro tópico ou 'quit' para sair:");
                                    System.out.println();
                                    String escolha = sc.nextLine().trim();
                                    if (escolha.equalsIgnoreCase("back")) {
                                        // Desconectar do canal ao sair
                                        new Investidor(topico, socket, nomeUsuario).desconectarDoGrupo();
                                        break;
                                    } else if (escolha.equalsIgnoreCase("quit")) {
                                        System.out.println("Encerrando investidor");
                                        if (socket != null && !socket.isClosed()) {
                                            // Desconectar do canal ao sair
                                            new Investidor(topico, socket, nomeUsuario).desconectarDoGrupo();
                                            socket.close();
                                        }
                                        System.exit(0);
                                    } else {
                                        System.out.println("Opção inválida.");
                                    }
                                }
                            }
                            break;

                        case 3:
                            System.out.println("Encerrando sessão");
                            // Desconectar do canal ao sair
                            if (socket != null && !socket.isClosed()) {
                                socket.close();
                            }
                            System.exit(0);
                            return;
                    }
                } else {
                    System.out.println("Por favor, insira um número inteiro válido.");
                    sc.nextLine();
                }
            }
        }
    }

    private static void enviarMensagem(String nomeUsuario, String topico, MulticastSocket socket, Scanner sc) {
        try {
            while (true) {
                if (socket == null || socket.isClosed()) {
                    System.out.println("Erro ao enviar mensagem: Socket closed");
                    return;
                }
                System.out.println();
                System.out.println("Escolha uma opção:");
                System.out.println("1. Enviar mensagem");
                System.out.println("2. Apenas ouvir mensagens");
                System.out.println("3. Voltar");
                System.out.print("Opção: ");

                if (sc.hasNextInt()) {
                    int escolha = sc.nextInt();
                    sc.nextLine();

                    if (escolha == 1) {
                        System.out.println("Digite a dica de investimento em ações:");
                        System.out.print("Nome da acao: ");
                        String acao = sc.nextLine().toUpperCase();

                        System.out.print("Recomendação (Compra/Venda): ");
                        String recomendacao = sc.nextLine().toUpperCase();

                        System.out.println("Informações adicionais sobre a tendência/oportunidade:");
                        String informacoes = sc.nextLine();

                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
                        String dataFormatada = df.format(new Date());
                        String mensagemFormatada = String.format("[%s] [%s] Dicas de Investimentos em Ações: %s - Recomendação: %s - %s.", dataFormatada, nomeUsuario, acao, recomendacao, informacoes);

                        byte[] envio = mensagemFormatada.getBytes();
                        DatagramPacket pacote = new DatagramPacket(envio, envio.length, grupoGeral, 4321);
                        socket.send(pacote);
                        System.out.println("Mensagem enviada com sucesso.");
                    } else if (escolha == 2) {
                        new Thread(new Investidor(topico, socket, nomeUsuario)).start();
                    } else if (escolha == 3) {
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
                        String dataFormatada = df.format(new Date());
                        String mensagemFormatada = String.format("[%s] %s saiu do grupo 1", dataFormatada, nomeUsuario);

                        byte[] envio = mensagemFormatada.getBytes();
                        DatagramPacket pacote = new DatagramPacket(envio, envio.length, grupoGeral, 4321);
                        socket.send(pacote);
                        return;
                    } else {
                        System.out.println("Opção inválida.");
                    }
                } else {
                    System.out.println("Por favor, insira um número inteiro válido.");
                    sc.nextLine(); 
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao enviar mensagem: " + e.getMessage());
        }
    }

    @SuppressWarnings("deprecation")
    public void desconectarDoGrupo() {
        try {
            InetAddress grupo = this.topico.equals("Dicas de Investimentos em Ações") ? grupoGeral : grupoInvestimentos;
            if (socket != null && !socket.isClosed() && socket.isBound()) {
                socket.leaveGroup(grupo);
                System.out.println("[" + this.topico + "] Desconectado do grupo " + grupo.getHostAddress());
                // Enviar mensagem de registro ao sair do canal 2
                if (topico.equals("Relatórios de Dividendos")) {
                    enviarMensagemRegistro("SAIDA");
                }
            }
        } catch (IOException e) {
            System.out.println("[" + this.topico + "] Erro ao desconectar do grupo: " + e.getMessage());
        }
    }

    private static void enviarMensagemRegistro(String status) {
        try {
            String mensagem = nomeUsuario + "|" + status;
            byte[] envio = mensagem.getBytes();
            DatagramPacket pacote = new DatagramPacket(envio, envio.length, grupoInvestimentos, 4321); 
            socket.send(pacote);
            System.out.println("Mensagem de registro enviada: " + mensagem);
        } catch (IOException e) {
            System.out.println("Erro ao enviar mensagem de registro: " + e.getMessage());
        }
    }
    
}
