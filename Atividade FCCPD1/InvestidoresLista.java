import java.util.ArrayList;
import java.util.List;

public class InvestidoresLista {
    private static List<String> investidoresCanal2 = new ArrayList<>();

    public static synchronized void adicionarInvestidor(String nomeInvestidor) {
        investidoresCanal2.add(nomeInvestidor);
    }

    public static synchronized List<String> getInvestidoresCanal2() {
        return new ArrayList<>(investidoresCanal2);
    }
}
