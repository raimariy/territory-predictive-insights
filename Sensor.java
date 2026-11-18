public interface Sensor {
    double lerValor(); //retorna a leitura atual (ou -1 se o sensor não estiver funcionando)

    boolean verificarFuncionamento(); //true se o sensor está ativo e funcionando

    String retornarTipo(); //tipo - "Chuva", "Nível do Rio", "Umidade"
}