# Territory Predictive Insights

**Global Solution 2º/2026 – Object-Oriented Programming – CitizenTech**

**Integrantes:**
* Natalia Heloisa Rocha de Alcântara Bezerra – RM 568570
* Raissa Marinho de Jesus Viana – RM 568301

---

## 1. Sobre o projeto
Sistema de monitoramento territorial desenvolvido em Java, com recorte em **clima local: chuva intensa e risco de alagamento**. O sistema lê medições simuladas de sensores ambientais, armazena as últimas leituras, processa previsões simples e emite alertas fundamentados no console, explicando o motivo de cada aviso.

## 2. Público beneficiado e decisão que o sistema apoia
* **Quem sofre o problema:** Moradores de áreas próximas a córregos e pontos vulneráveis a alagamentos no território.
* **Quem usa o sistema:** A equipe responsável pela gestão do território (associação de bairro, defesa civil, prefeitura).
* **Decisão que apoia:** Avaliar se é necessário emitir avisos preventivos aos moradores, mobilizar equipes de campo, realizar limpeza emergencial de bueiros ou preparar abrigos antes que a chuva prevista atinja o pico.

> **Nota sobre privacidade:** O território é descrito apenas por região, população estimada e equipamentos instalados. Nenhum dado pessoal de pessoas reais é utilizado.

## 3. Como compilar e rodar
**Requisito:** JDK 8 ou superior.

Abra o terminal na pasta raiz do projeto e execute:

```bash
javac *.java
java SistemaMonitoramento
```

*(Caso os acentos apareçam desconfigurados no console do Windows, execute: `java -Dfile.encoding=UTF-8 SistemaMonitoramento`)*

### Roteiro de teste rápido
1. **Menu 2 (Rodar previsões):** O sistema se cala e informa que ainda não há leituras suficientes.
2. **Menu 4 (Simular leituras):** Digite `6` (ou `8`) para gerar histórico.
3. **Menu 2 novamente:** As duas estratégias de previsão são exibidas, emitindo alertas explicativos caso ultrapassem os limiares.
4. **Menu 1:** Verifica o status de funcionamento dos sensores e exibe uma leitura atual de cada.
5. **Menu 3 (Opção 4):** Permite alterar os limiares de chuva utilizando o código de acesso inicial: `clima2026`.
6. **Validação de segurança:** Inserir um código incorreto ou limiares fora de ordem rejeita a alteração e mantém os valores anteriores.
7. **Menu 5:** Exibe o status completo do território, sensores e leituras armazenadas.

## 4. Exemplo de execução

**Sem leituras suficientes (Menu 2):**
```text
--- Previsões de chuva ---

Estratégia: Média Móvel
Previsão não emitida: são necessárias pelo menos 3 leituras e existem 0. Use a opção 4 para simular leituras.

Estratégia: Comparação de Períodos
Previsão não emitida: são necessárias pelo menos 6 leituras e existem 0. Use a opção 4 para simular leituras.
```

**Simulando 8 leituras (Menu 4):**
```text
Quantas leituras simular? (1 a 24)
8
Leitura 1: chuva=41.8 mm/h | rio=4.3 m | umidade=48.3%
Leitura 2: chuva=16.9 mm/h | rio=0.8 m | umidade=90.6%
Leitura 3: chuva=15.4 mm/h | rio=2.6 m | umidade=46.7%
Leitura 4: chuva=4.8 mm/h | rio=1.4 m | umidade=81.8%
Leitura 5: chuva=76.0 mm/h | rio=1.4 m | umidade=32.4%
Leitura 6: chuva=69.4 mm/h | rio=1.9 m | umidade=78.6%
Leitura 7: chuva=11.0 mm/h | rio=2.2 m | umidade=91.8%
Leitura 8: chuva=80.6 mm/h | rio=4.5 m | umidade=57.3%
Leituras de chuva guardadas: 8
```

**Previsões e alertas com explicação (Menu 2):**
```text
--- Previsões de chuva ---

Estratégia: Média Móvel
Valor previsto: 53.7 mm/h
[ALERTA] Risco de alagamento em Território Exemplo
Sensor: Chuva
Estratégia: Média Móvel
Valor previsto: 53.7 mm/h
Limite estourado: 50.0 mm/h (nível ALERTA)
População na região: 5000 pessoas
Este alerta apoia a decisão da equipe responsável; não decide por ela.

Estratégia: Comparação de Períodos
Valor previsto: 75.3 mm/h
[ALERTA] Risco de alagamento em Território Exemplo
Sensor: Chuva
Estratégia: Comparação de Períodos
Valor previsto: 75.3 mm/h
Limite estourado: 50.0 mm/h (nível ALERTA)
População na região: 5000 pessoas
Este alerta apoia a decisão da equipe responsável; não decide por ela.
```
*(Conferência matemática: as últimas 3 leituras [69.4, 11.0 e 80.6] resultam em média móvel de 53.7 mm/h. Na comparação, o período atual tem média 53.7 e o anterior [15.4, 4.8 e 76.0] tem média 32.1, projetando tendência de `53.7 + (53.7 - 32.1) = 75.3 mm/h`.)*

**Validações de encapsulamento (Menu 3):**
```text
Código de acesso:
grfsdmk
Alteração rejeitada: código de acesso incorreto.

Nova população:
-4
Alteração rejeitada: a população deve ser maior que zero. Mantido: 5000
```

## 5. Estrutura e conceitos de OOP aplicados

| Arquivo | Conceito de OOP aplicado |
| :--- | :--- |
| `ComponenteTerritorial.java` | **Classe Abstrata:** define atributos comuns (`id`, `nome`, `regiao`, `status`), métodos concretos (`ativar()`, `desativar()`) e o método abstrato `verificarStatus()`. |
| `EstacaoDeColeta.java` | **Herança:** classe concreta que estende `ComponenteTerritorial`. |
| `Sensor.java` | **Interface:** contrato com os métodos `lerValor()`, `verificarFuncionamento()` e `retornarTipo()`. |
| `SensorChuva.java`, `SensorNivelRio.java`, `SensorUmidade.java` | **Polimorfismo / Interface:** classes concretas que herdam de `ComponenteTerritorial` e implementam a interface `Sensor`. |
| `DadosTerritorio.java` | **Encapsulamento:** atributos privados, getters e setters com validação de regras de negócio e proteção de limiares por código de acesso. |
| `EstrategiaPrevisao.java` | **Classe Abstrata:** classe base que define a estrutura comum das estratégias de previsão. |
| `PrevisaoMediaMovel.java` | **Herança:** calcula a média das últimas $N$ leituras (atributo `janela`), utilizando `super()`. |
| `PrevisaoComparacao.java` | **Herança:** compara o período atual com o anterior (atributo `tamanhoPeriodo`), utilizando `super()`. |
| `SistemaMonitoramento.java` | **Classe Principal:** gerencia o loop do menu interativo, armazenamento em `ArrayList`, execução das previsões e emissão de alertas. |

## 6. Decisões da equipe
* **Simulação realista:** Valores gerados com `Math.random()` dentro de faixas coerentes para clima urbano: chuva de `0 a 100 mm/h`, nível do rio de `0 a 5 m` e umidade de `30 a 100%`.
* **Janela de histórico:** As leituras são armazenadas em um `ArrayList` limitado às últimas 24 medições.
* **Níveis de alerta:** Definidos inicialmente para chuva em **ATENÇÃO** (`20 mm/h`), **ALERTA** (`50 mm/h`) e **CRÍTICO** (`80 mm/h`), podendo ser ajustados mediante senha.
* **Trava de confiabilidade:** O sistema exige no mínimo 3 leituras para calcular a Média Móvel e 6 leituras para a Comparação de Períodos.
* **Escopo dos alertas:** Os limiares de alerta foram aplicados diretamente ao sensor de chuva; os sensores de nível do rio e umidade são monitorados e registrados no histórico para apoio visual.
* **Entrada de limiares:** Os limiares são lidos como números inteiros no menu para evitar erros de formatação regional (ponto vs. vírgula) no `Scanner`.

## 7. Reflexão crítica

**Quando o sistema deve se calar?**
O sistema deve permanecer em silêncio sempre que não houver volume mínimo de dados confiáveis ou quando um sensor apresentar falha. Com menos de 3 leituras, uma média móvel sofre distorções severas por valores isolados; com menos de 6 leituras, não é possível formar dois períodos completos para comparação. Nesses cenários, o sistema recusa a emissão da previsão e explica o motivo ao operador. Emitir alarmes sem base estatística mínima gera fadiga de alerta e faz a comunidade perder a confiança no sistema, enquanto uma falsa sensação de segurança pode atrasar ações preventivas críticas.

**O que os dados não representam?**
Sensores pontuais não capturam a complexidade espacial de um território inteiro: uma chuva intensa pode atingir uma encosta ou cabeceira de córrego enquanto outro ponto do mesmo bairro registra volume baixo. Além disso, os dados atuais são simulados e não contemplam variáveis físicas determinantes para um alagamento real, como impermeabilização do solo, entupimento de galerias pluviais, topografia ou histórico de obras na região.

**Por que o alerta apoia a decisão da pessoa, em vez de decidir por ela?**
Modelos baseados em médias e tendências lineares são ferramentas de triagem rápida, sujeitas a margens de erro diante de mudanças climáticas bruscas. A decisão de evacuar moradores, interditar vias ou mobilizar equipes envolve impacto social, logístico e humano que apenas gestores locais e lideranças comunitárias conseguem avaliar. Por isso, cada alerta detalha o sensor, a estratégia utilizada, o valor projetado e o limite ultrapassado — garantindo transparência para que o operador audite o motivo do aviso e tome a decisão final.

## 8. Limitações e próximos passos
* Expandir a lógica de alertas automáticos e limiares configuráveis também para os sensores de nível do rio e umidade.
* Implementar tratamento de exceções (`try-catch`) para entradas não numéricas no menu, evitando encerramento inesperado caso o usuário digite letras.

## 9. Uso de IA
Utilizamos inteligência artificial como ferramenta de apoio ao desenvolvimento e organização do projeto nos seguintes pontos:
* **Revisão e eficiência de código:** Validamos se a implementação das classes atendia aos conceitos de OOP exigidos, identificando pontos de melhoria e boas práticas de eficiência e legibilidade.
* **Versionamento (Git):** Utilizamos apoio para estruturar uma sequência lógica e profissional de commits no repositório.
* **Documentação:** Estruturamos e organizamos a formatação deste `README.md` para garantir clareza e cobertura de todos os tópicos solicitados.

**Como revisamos:** Todo o código foi lido, testado e validado na prática. Compilamos e executamos todos os cenários do menu (simulação de leituras, silêncio por falta de dados, disparo de alertas nos três níveis e bloqueio de alterações inválidas por código de acesso), garantindo domínio completo sobre o funcionamento de cada classe.

## 10. Divisão de tarefas
* **Raissa Marinho de Jesus Viana (RM 568301):** Desenvolvimento integral do código em Java (sensores, encapsulamento, estratégias de previsão, menu interativo e alertas), organização dos commits no repositório GitHub e elaboração do `README.md`.
* **Natalia Heloisa Rocha de Alcântara Bezerra (RM 568570):** Colaboração na elaboração e revisão do `README.md`, validação dos cenários de teste do sistema e conferência final da entrega.