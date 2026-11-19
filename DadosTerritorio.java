public class DadosTerritorio {
    private String nomeRegiao;
    private int populacao;
    private double limiarAtencao; //chuva em mm/h
    private double limiarAlerta;
    private double limiarCritico;
    private String codigoAcesso; //protege a alteração dos limiares

    public DadosTerritorio(String nomeRegiao, int populacao, String codigoAcesso) {
        this.nomeRegiao = "Não definida";
        this.populacao = 1;
        this.limiarAtencao = 20;
        this.limiarAlerta = 50;
        this.limiarCritico = 80;
        this.codigoAcesso = codigoAcesso;

        //usa os setters para validar os valores recebidos
        setNomeRegiao(nomeRegiao);
        setPopulacao(populacao);
    }

    public String getNomeRegiao() {
        return nomeRegiao;
    }
    public int getPopulacao() {
        return populacao;
    }
    public double getLimiarAtencao() {
        return limiarAtencao;
    }
    public double getLimiarAlerta() {
        return limiarAlerta;
    }
    public double getLimiarCritico() {
        return limiarCritico;
    }

    public void setNomeRegiao(String nomeRegiao) {
        if (nomeRegiao == null || nomeRegiao.trim().isEmpty()) {
            System.out.println("Alteração rejeitada: o nome da região não pode ser vazio. Mantido: " + this.nomeRegiao);
            return;
        }
        this.nomeRegiao = nomeRegiao;
    }

    public void setPopulacao(int populacao) {
        if (populacao <= 0) {
            System.out.println("Alteração rejeitada: a população deve ser maior que zero. Mantido: " + this.populacao);
            return;
        }
        this.populacao = populacao;
    }

    //altera os 3 limiares de uma vez (para garantir a ordem) e exige o código de acesso
    public boolean setLimiares(String codigo, double atencao, double alerta, double critico) {
        if (!codigoCorreto(codigo)) {
            System.out.println("Alteração rejeitada: código de acesso incorreto.");
            return false;
        }
        if (atencao <= 0 || critico > 100) {
            System.out.println("Alteração rejeitada: os limiares devem estar entre 0 e 100 mm/h.");
            return false;
        }
        if (!(atencao < alerta && alerta < critico)) {
            System.out.println("Alteração rejeitada: é preciso ATENÇÃO < ALERTA < CRÍTICO.");
            return false;
        }
        this.limiarAtencao = atencao;
        this.limiarAlerta = alerta;
        this.limiarCritico = critico;
        System.out.println("Limiares atualizados com sucesso.");
        return true;
    }

    //metodo privado: só a própria classe compara o código
    private boolean codigoCorreto(String codigo) {
        return codigoAcesso.equals(codigo);
    }
}