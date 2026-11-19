import java.util.ArrayList;

public abstract class EstrategiaPrevisao {

    protected String nome;
    protected int minimoLeituras; //abaixo disso a previsão não é confiável

    public EstrategiaPrevisao(String nome, int minimoLeituras) {
        this.nome = nome;
        this.minimoLeituras = minimoLeituras;
    }

    //metodo concreto: igual para todas as estratégias
    public boolean temLeiturasSuficientes(ArrayList<Double> leituras) {
        return leituras.size() >= minimoLeituras;
    }

    //metodo abstrato: cada estratégia calcula do seu jeito.
    public abstract double prever(ArrayList<Double> leituras);  //retorna o valor previsto, ou -1 se não houver leituras suficientes.

    public String getNome() {
        return nome;
    }
    public int getMinimoLeituras() {
        return minimoLeituras;
    }
}