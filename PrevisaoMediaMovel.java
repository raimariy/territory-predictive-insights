import java.util.ArrayList;

public class PrevisaoMediaMovel extends EstrategiaPrevisao {

    private int janela; //quantas das últimas leituras entram na média

    public PrevisaoMediaMovel(int janela) {
        super("Média Móvel", janela); //chama o construtor da classe mãe
        this.janela = janela;
    }

    @Override
    public double prever(ArrayList<Double> leituras) {
        if (!temLeiturasSuficientes(leituras)) {
            return -1;
        }
        //soma só as últimas "janela" leituras e divide pela janela
        double soma = 0;
        for (int i = leituras.size() - janela; i < leituras.size(); i++) {
            soma += leituras.get(i);
        }
        return soma / janela;
    }

    public int getJanela() {
        return janela;
    }
}