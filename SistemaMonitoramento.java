import java.util.ArrayList;
import java.util.Scanner;

public class SistemaMonitoramento {

    private Scanner entrada;
    private DadosTerritorio dados;

    private EstacaoDeColeta estacao;
    private SensorChuva sensorChuva;
    private SensorNivelRio sensorRio;
    private SensorUmidade sensorUmidade;

    private Sensor[] sensores;                   //todos implementam a interface Sensor
    private ComponenteTerritorial[] componentes; //todos herdam de ComponenteTerritorial
    private EstrategiaPrevisao[] estrategias;    //as duas formas de previsão

    private ArrayList<Double> leiturasChuva;
    private ArrayList<Double> leiturasRio;
    private ArrayList<Double> leiturasUmidade;

    private int maxLeituras = 24; //guarda só as últimas 24 leituras

    public SistemaMonitoramento() {
        entrada = new Scanner(System.in);

        String regiao = "Território Exemplo";
        dados = new DadosTerritorio(regiao, 5000, "clima2026");

        estacao = new EstacaoDeColeta(1, "Estação Central", regiao, "Córrego do bairro");
        sensorChuva = new SensorChuva(2, "Pluviômetro", regiao);
        sensorRio = new SensorNivelRio(3, "Régua do Córrego", regiao);
        sensorUmidade = new SensorUmidade(4, "Higrômetro", regiao);

        estacao.ativar();
        sensorChuva.ativar();
        sensorRio.ativar();
        sensorUmidade.ativar();

        sensores = new Sensor[3];
        sensores[0] = sensorChuva;
        sensores[1] = sensorRio;
        sensores[2] = sensorUmidade;

        componentes = new ComponenteTerritorial[4];
        componentes[0] = estacao;
        componentes[1] = sensorChuva;
        componentes[2] = sensorRio;
        componentes[3] = sensorUmidade;

        estrategias = new EstrategiaPrevisao[2];
        estrategias[0] = new PrevisaoMediaMovel(3);  //média das últimas 3 leituras
        estrategias[1] = new PrevisaoComparacao(3);  //últimas 3 vs 3 anteriores

        leiturasChuva = new ArrayList<Double>();
        leiturasRio = new ArrayList<Double>();
        leiturasUmidade = new ArrayList<Double>();
    }

    public static void main(String[] args) {
        SistemaMonitoramento sistema = new SistemaMonitoramento();
        sistema.iniciar();
    }

    public void iniciar() {
        int op;
        do {
            System.out.println("\n---- SISTEMA DE MONITORAMENTO TERRITORIAL ----");
            System.out.println("1-Verificar sensores");
            System.out.println("2-Rodar previsões");
            System.out.println("3-Gerenciar dados do território");
            System.out.println("4-Simular próximas leituras");
            System.out.println("5-Exibir status completo");
            System.out.println("0-Sair");
            op = entrada.nextInt();
            entrada.nextLine();

            switch (op) {
                case 1:
                    verificarSensores();
                    break;
                case 2:
                    rodarPrevisoes();
                    break;
                case 3:
                    gerenciarDados();
                    break;
                case 4:
                    simularLeituras();
                    break;
                case 5:
                    exibirStatusCompleto();
                    break;
                case 0:
                    System.out.println("Finalizando o programa");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        } while (op != 0);

        entrada.close();
    }

    //sensores
    private void verificarSensores() {
        System.out.println("\n--- Verificação dos sensores ---");
        for (Sensor s : sensores) {
            if (s.verificarFuncionamento()) {
                double valor = s.lerValor();
                System.out.println(s.retornarTipo() + ": funcionando | leitura = " + arredondar(valor));
                if (s.retornarTipo().equals("Chuva") && valor >= dados.getLimiarAtencao()) { //detecta quando o valor de chuva passa do limite
                    System.out.println("   -> Valor acima do limiar de ATENÇÃO (" + dados.getLimiarAtencao() + " mm/h)");
                }
            } else {
                System.out.println(s.retornarTipo() + ": FORA DE FUNCIONAMENTO");
            }
        }
    }

    //previsões
    private void rodarPrevisoes() {
        System.out.println("\n--- Previsões de chuva ---");
        for (EstrategiaPrevisao e : estrategias) {
            System.out.println("\nEstratégia: " + e.getNome());
            if (!e.temLeiturasSuficientes(leiturasChuva)) {
                System.out.println("Previsão não emitida: são necessárias pelo menos " + e.getMinimoLeituras() + " leituras e existem " + leiturasChuva.size() + ". Use a opção 4 para simular leituras.");
            } else {
                double previsto = e.prever(leiturasChuva);
                System.out.println("Valor previsto: " + arredondar(previsto) + " mm/h");
                emitirAlerta("Chuva", e.getNome(), previsto);
            }
        }
    }

    //alertas
    private void emitirAlerta(String sensor, String estrategia, double previsto) {
        String nivel;
        double limite;

        if (previsto >= dados.getLimiarCritico()) {
            nivel = "CRÍTICO";
            limite = dados.getLimiarCritico();
        } else if (previsto >= dados.getLimiarAlerta()) {
            nivel = "ALERTA";
            limite = dados.getLimiarAlerta();
        } else if (previsto >= dados.getLimiarAtencao()) {
            nivel = "ATENÇÃO";
            limite = dados.getLimiarAtencao();
        } else {
            System.out.println("Sem alerta: o valor previsto está abaixo do limiar de ATENÇÃO ("
                    + dados.getLimiarAtencao() + " mm/h).");
            return;
        }

        //System.out.println("*********************************************");
        System.out.println("[" + nivel + "] Risco de alagamento em " + dados.getNomeRegiao());
        System.out.println("Sensor: " + sensor);
        System.out.println("Estratégia: " + estrategia);
        System.out.println("Valor previsto: " + arredondar(previsto) + " mm/h");
        System.out.println("Limite estourado: " + limite + " mm/h (nível " + nivel + ")");
        System.out.println("População na região: " + dados.getPopulacao() + " pessoas");
        System.out.println("Este alerta apoia a decisão da equipe responsável; não decide por ela.");
        //System.out.println("*********************************************");
    }

    //dados do território
    private void gerenciarDados() {
        int op;
        do {
            System.out.println("\n--- Dados do território ---");
            System.out.println("1-Ver dados");
            System.out.println("2-Alterar nome da região");
            System.out.println("3-Alterar população");
            System.out.println("4-Alterar limiares de alerta (exige código de acesso)");
            System.out.println("0-Voltar");
            op = entrada.nextInt();
            entrada.nextLine();

            switch (op) {
                case 1:
                    exibirDados();
                    break;
                case 2:
                    System.out.println("Novo nome da região:");
                    dados.setNomeRegiao(entrada.nextLine());
                    break;
                case 3:
                    System.out.println("Nova população:");
                    dados.setPopulacao(entrada.nextInt());
                    entrada.nextLine();
                    break;
                case 4:
                    System.out.println("Código de acesso:");
                    String codigo = entrada.nextLine();
                    System.out.println("Novo limiar de ATENÇÃO (mm/h, número inteiro):");
                    int atencao = entrada.nextInt();
                    System.out.println("Novo limiar de ALERTA (mm/h, número inteiro):");
                    int alerta = entrada.nextInt();
                    System.out.println("Novo limiar CRÍTICO (mm/h, número inteiro):");
                    int critico = entrada.nextInt();
                    entrada.nextLine();
                    dados.setLimiares(codigo, atencao, alerta, critico);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        } while (op != 0);
    }

    private void exibirDados() {
        System.out.println("Região: " + dados.getNomeRegiao());
        System.out.println("População: " + dados.getPopulacao());
        System.out.println("Limiares de chuva (mm/h): ATENÇÃO=" + dados.getLimiarAtencao()
                + " | ALERTA=" + dados.getLimiarAlerta()
                + " | CRÍTICO=" + dados.getLimiarCritico());
    }

    //simulação de leituras
    private void simularLeituras() {
        System.out.println("Quantas leituras simular? (1 a " + maxLeituras + ")");
        int qtd = entrada.nextInt();
        entrada.nextLine();

        if (qtd < 1 || qtd > maxLeituras) {
            System.out.println("Quantidade inválida.");
            return;
        }

        for (int i = 1; i <= qtd; i++) {
            double chuva = sensorChuva.lerValor();
            double rio = sensorRio.lerValor();
            double umidade = sensorUmidade.lerValor();

            guardarLeitura(leiturasChuva, chuva);
            guardarLeitura(leiturasRio, rio);
            guardarLeitura(leiturasUmidade, umidade);

            System.out.println("Leitura " + i + ": chuva=" + arredondar(chuva) + " mm/h"
                    + " | rio=" + arredondar(rio) + " m"
                    + " | umidade=" + arredondar(umidade) + "%");
        }
        System.out.println("Leituras de chuva guardadas: " + leiturasChuva.size());
    }

    //guarda só as últimas leituras, o valor negativo (-1) significa sensor desligado
    private void guardarLeitura(ArrayList<Double> lista, double valor) {
        if (valor < 0) {
            return;
        }
        lista.add(valor);
        if (lista.size() > maxLeituras) {
            lista.remove(0); //descarta a mais antiga
        }
    }

    //status completo
    private void exibirStatusCompleto() {
        System.out.println("\n---- STATUS COMPLETO ----");
        for (ComponenteTerritorial c : componentes) {
            System.out.println(c.verificarStatus());
        }
        exibirDados();
        System.out.println("Leituras guardadas: chuva=" + leiturasChuva.size()
                + " | rio=" + leiturasRio.size()
                + " | umidade=" + leiturasUmidade.size());
        if (!leiturasChuva.isEmpty()) {
            System.out.println("Última leitura de chuva: "
                    + arredondar(leiturasChuva.get(leiturasChuva.size() - 1)) + " mm/h");
        }
    }

    //deixa o número com uma casa decimal para exibir
    private double arredondar(double valor) {
        return Math.round(valor * 10.0) / 10.0;
    }
}