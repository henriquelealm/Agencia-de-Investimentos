import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Validacoes {
    private static Scanner scanner = new Scanner(System.in);

    public static String verificarFormatarData() {
        String dataDistribuicao;
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
        Date dataAtual = new Date();
    
        do {
            System.out.print("Data prevista da distribuição (ddMMyyyy): ");
            dataDistribuicao = scanner.nextLine();
    
            if (dataDistribuicao.matches("\\d{8}")) {
                int dia = Integer.parseInt(dataDistribuicao.substring(0, 2));
                int mes = Integer.parseInt(dataDistribuicao.substring(2, 4));
                int ano = Integer.parseInt(dataDistribuicao.substring(4, 8));
    
                if (dia < 1 || dia > 31 || mes < 1 || mes > 12) {
                    System.out.println("[Data inserida é inválida. Insira uma data válida]");
                    continue;
                }
    
                // Verificar se o dia é válido para o mês
                if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) {
                    System.out.println("[Data inserida é inválida para o mês. Insira uma data válida]");
                    continue;
                }
    
                // Verificar ano bissexto
                boolean bissexto = (ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0);
                if (mes == 2 && ((bissexto && dia > 29) || (!bissexto && dia > 28))) {
                    System.out.println("[Data inserida é inválida para o mês. Insira uma data válida]");
                    continue;
                }
    
                String dataFormatada = String.format("%02d/%02d/%04d", dia, mes, ano);
                try {
                    Date dataInserida = df.parse(dataDistribuicao);
                    if (dataInserida.before(dataAtual)) {
                        System.out.println("[Data inserida é anterior à data atual. Insira uma data posterior]");
                        continue;
                    }
                    return dataFormatada;
                } catch (ParseException e) {
                    System.out.println("[Erro ao converter data]");
                }
            } else {
                System.out.println("[Formato de data inválido. Use o formato ddMMyyyy]");
            }
        } while (true);
    }
}

