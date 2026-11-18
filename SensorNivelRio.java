public class SensorNivelRio extends ComponenteTerritorial implements Sensor {

    public SensorNivelRio(int id, String nome, String regiao) {
        super(id, nome, regiao);
    }

    @Override
    public double lerValor() {
        if (!verificarFuncionamento()) {
            return -1;
        }
        return Math.random() * 5; //nível entre 0 e 5 metros
    }

    @Override
    public boolean verificarFuncionamento() {
        return status.equals("ATIVO");
    }

    @Override
    public String retornarTipo() {
        return "Nível do Rio";
    }

    @Override
    public String verificarStatus() {
        return "Sensor de Nível do Rio " + nome + " (" + regiao + "): " + status;
    }
}