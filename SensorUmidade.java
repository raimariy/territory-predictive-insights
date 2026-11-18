public class SensorUmidade extends ComponenteTerritorial implements Sensor {

    public SensorUmidade(int id, String nome, String regiao) {
        super(id, nome, regiao);
    }

    @Override
    public double lerValor() {
        if (!verificarFuncionamento()) {
            return -1;
        }
        return 30 + Math.random() * 70; //umidade entre 30% e 100%
    }

    @Override
    public boolean verificarFuncionamento() {
        return status.equals("ATIVO");
    }

    @Override
    public String retornarTipo() {
        return "Umidade";
    }

    @Override
    public String verificarStatus() {
        return "Sensor de Umidade " + nome + " (" + regiao + "): " + status;
    }
}