public abstract class ComponenteTerritorial {

    protected int id;
    protected String nome;
    protected String regiao;
    protected String status; //ativo ou inativo

    public ComponenteTerritorial(int id, String nome, String regiao) {
        this.id = id;
        this.nome = nome;
        this.regiao = regiao;
        this.status = "INATIVO";
    }

    //metodos concretos
    public void ativar() {
        this.status = "ATIVO";
        System.out.println(nome + " ativado.");
    }

    public void desativar() {
        this.status = "INATIVO";
        System.out.println(nome + " desativado.");
    }

    //metodo abstrato
    public abstract String verificarStatus();

    public int getId() { 
        return id; 
    }
    public String getNome() { 
        return nome; 
    }
    public String getRegiao() { 
        return regiao; 
    }
    public String getStatus() { 
        return status; 
    }
}