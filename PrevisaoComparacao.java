import java.util.ArrayList;

public class PrevisaoComparacao extends EstrategiaPrevisao {

    private int tamanhoPeriodo; //quantas leituras formam cada período

    public PrevisaoComparacao(int tamanhoPeriodo) {
        //precisa de 2 períodos completos: o atual e o anterior
        super("Comparação de Períodos", tamanhoPeriodo * 2);
        this.tamanhoPeriodo = tamanhoPeriodo;
    }

    @Override
    public double prever(ArrayList<Double> leituras) {
        if (!temLeiturasSuficientes(leituras)) {
            return -1;
        }
        int fim = leituras.size();
        double mediaAtual = media(leituras, fim - tamanhoPeriodo, fim);
        double mediaAnterior = media(leituras, fim - 2 * tamanhoPeriodo, fim - tamanhoPeriodo);

        //se o período atual subiu em relação ao anterior, projeta que a tendência continua
        double previsto = mediaAtual + (mediaAtual - mediaAnterior);
        if (previsto < 0) {
            previsto = 0; //chuva não pode ser negativa
        }
        return previsto;
    }

    //média das leituras de "inicio" (inclusive) até "fim" (exclusive)
    private double media(ArrayList<Double> leituras, int inicio, int fim) {
        double soma = 0;
        for (int i = inicio; i < fim; i++) {
            soma += leituras.get(i);
        }
        return soma / (fim - inicio);
    }

    public int getTamanhoPeriodo() {
        return tamanhoPeriodo;
    }
}