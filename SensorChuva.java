public class SensorChuva extends ComponenteTerritorial implements Sensor {

    public SensorChuva(int id, String nome, String regiao) {
        super(id, nome, regiao);
    }

    @Override
    public double lerValor() {
        if (!verificarFuncionamento()) {
            return -1;
        }
        return Math.random() * 100; //gera numero entre O e 1, chuva entre 0 e 100 mm/h
    }

    @Override
    public boolean verificarFuncionamento() {
        return status.equals("ATIVO"); //equals compara se dois textos são iguais
    }

    @Override
    public String retornarTipo() {
        return "Chuva";
    }

    @Override
    public String verificarStatus() {
        return "Sensor de Chuva " + nome + " (" + regiao + "): " + status;
    }
}