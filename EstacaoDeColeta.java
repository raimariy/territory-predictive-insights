public class EstacaoDeColeta extends ComponenteTerritorial {

    private String pontoVulneravel;

    public EstacaoDeColeta(int id, String nome, String regiao, String pontoVulneravel) {
        super(id, nome, regiao);
        this.pontoVulneravel = pontoVulneravel;
    }

    @Override //é uma boa prática: garante ao compilador que estou sobrescrevendo um método existente e evita erros de digitação.
    public String verificarStatus() {
        return "Estação " + nome + " (" + regiao + ") monitorando "
                + pontoVulneravel + ": " + status;
    }

    public String getPontoVulneravel() { return pontoVulneravel; }
}