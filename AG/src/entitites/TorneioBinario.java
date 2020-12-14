package entitites;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
    { Método de Torneio Binário }
*/
public class TorneioBinario {
    // Variável que contém o número da geração
    private int countGeracao = 1;
    
    // Vetor que recebe o fitness de cada cromossomo
    private int[] fitness = new int[10];
    
    // Matriz que recebe a população
    private int[][] population = new int[10][10];
    
    // Array que recebe o Primeiro Filho
    private int[] filho1 = new int[10];
    
    // Array que recebe o Segundo Filho
    private int[] filho2 = new int[10];
    
    // Array list que guarda as posições que já saíram na hora da seleção dos pais
    private List<Integer> guardaPosicao = new ArrayList<Integer>();
    
    // Instancia um objeto do tipo Random
    Random random = new Random();
    
    // Instancia um objeto do tipo População
    Populacao populacao = new Populacao();
    
    // Instancia um objeto do tipo PaiFilho que irá representar o primeiro pai e filho
    PaiFilho paiFilho1 = new PaiFilho();
    
    // Instancia um objeto do tipo paiFilho que irá representar o segundo pai e filho
    PaiFilho paiFilho2 = new PaiFilho();
    
    // Método construtor
    public TorneioBinario() {

    }
    
    // Método que ao ser chamado, chama métodos da classe população
    public void chamaPopulacao() {
        
        // Chama o método que irá preencher a população
        populacao.preenchePopulacao();
        
        /* Chama o método que irá calcular o Fitness e o peso de cada cromossomo
            e o Fitness de toda população
        */
        populacao.calculaFitKg();
        
        // Chama o método que printa em tela a população
        populacao.printPopulacao();
    }
    
    // Método que contém o laço das gerações
    public void geraTB(int geracoes) {
        
        // Array Fitness recebe o Fitness de cada cromossomo da população
        fitness = populacao.getFitness();
        
        // Cria e inicializa a variável que irá controlar quando ocorrerá mutação
        int countMutacao = 0;
        
        // Laço das gerações
        for (int i = 0; i < geracoes; i++) {
            
            // Chama o método que irá jogar o torneio binário para encontrar os pais mais aptos
            geraPai();
            
            // Chama o método que irá gerar os filhos desses pais       
            geraFilho();
        
            // A cada 20 gerações será executado o método de mutação
            if (countMutacao == 20) { 
                
                // Chama método de mutação
                mutacao();
                
                // Reseta a variável countMutacao
                countMutacao = 0;
            }
            
            // Chama o método que calcula o Fitness dos filhos
            calculaFitFilho();
            
            // Incrementa +1 na variável de mutação
            countMutacao++;
            
            // Reseta o fitness e peso dos filhos, para que o próximo comece zerado
            paiFilho1.resetaFitnessAndKg(0, 0);
            paiFilho2.resetaFitnessAndKg(0, 0);
        }
        
        // Chama o método para calcular o novo fitness e peso da população
        populacao.calculaFitKg();
        
        // Chama o método para printar em tela toda a população
        populacao.printPopulacao();
    }
    
    // Método que gera os pais mais adaptados ao ambiente
    public void geraPai() {
        
        // Sorteia 2 pais aleatórios
        paiFilho1.setPai(random.nextInt(10));
        paiFilho2.setPai(random.nextInt(10));
        
        // Guarda esses valores que já saíram no Arraylist
        guardaPosicao.add(paiFilho1.getPai());
        guardaPosicao.add(paiFilho2.getPai());
        
        // Laço que verifica qual dos dois pais sorteado é o mais apto
        for (int i = 0; i < 10; i++) {
            
            // Se o pai 1 tiver o fitness mais alto
            if (fitness[paiFilho1.getPai()] > fitness[paiFilho2.getPai()]) {
                
                // Sorteia um novo pai 2
                paiFilho2.setPai(random.nextInt(10));
                
                // Laço para verificar se o novo pai 2 é um pai que já foi escolhido anteriormente
                for (int j = 0; j < guardaPosicao.size() - 1; j++) {
                    
                    // Caso seja um pai 2 que já saiu, sorteia novamente, até que caia um novo
                    if (paiFilho2.getPai() == guardaPosicao.get(j)) {
                        paiFilho2.setPai(random.nextInt(10));
                        j = 0;
                    }
                }
                
                // Guarda o novo pai 2, pois ele já saiu
                guardaPosicao.add(paiFilho2.getPai());
                
            // Se o pai 2 tiver o fitness mais alto
            } else {
                
                // Sorteia um novo pai 1
                paiFilho1.setPai(random.nextInt(10));
                
                // Laço para verificar se o novo pai 1 é um pai que já foi escolhido anteriormente
                for (int j = 0; j < guardaPosicao.size() - 1; j++) {
                    
                    // Caso seja um pai 2 que já saiu, sorteia novamente, até que caia um novo
                    if (paiFilho1.getPai() == guardaPosicao.get(j)) {
                        paiFilho1.setPai(random.nextInt(10));
                        j = 0;
                    }
                }
                
                // Guarda o novo pai 2, pois ele já saiu
                guardaPosicao.add(paiFilho1.getPai());
            }
        }
        
        // Caso o pai 1 seja igual ao pai 2, sejam o mesmo pai
        if (paiFilho1.getPai() == paiFilho2.getPai()) {
            
            // Sorteia um novo valor para o pai 2
            paiFilho2.setPai(random.nextInt(10));
        }
        
        // limpa o ArrayList de posições para que na próxima geração comece zerado
        guardaPosicao.clear();
    }
    
    // Método que gera os filhos dos pais mais adaptados ao ambiente
    // { Método de corte no meio }
    public void geraFilho() {
        
        // Printa o método de seleção utilizado
        System.out.println("\n\n[Método de Torneio Binário]\n");
        
        // Recebe a matriz com a população
        population = populacao.getPopulacao();
        
        // Percorre toda a população procurando pelo pai 1 
        for (int i = 0; i < population.length; i++) {
            for (int j = 0; j < population[i].length; j++) {
                
                // Se encontrar o pai 1
                if (i == paiFilho1.getPai()) {
                    
                    // Printa em tela o pai 1 e sua posição
                    if (j == 0) System.out.print("Pai " + paiFilho1.getPai() + ": ");
                    System.out.print(population[i][j] + " ");
                    
                    // Filho1 recebe os 5 primeiros objetos do pai 1
                    // Esses valores são colocados no início do vetor
                    if (j < 5) filho1[j] = population[i][j];
                    
                    // Filho2 recebe os 5 últimos objetos do pai 1
                    // Esses valores são colocados no final do vetor
                    else if (j > 4) filho2[j] = population[i][j];
                }
            }
        }
        
        // Percorre toda a população procurando pelo pai 2
        for (int i = 0; i < population.length; i++) {
            for (int j = 0; j < population[i].length; j++) {
                
                // Se encontrar o pai 2
                if (i == paiFilho2.getPai()) {
                    
                    // Printa em tela o pai 2 e sua posição
                    if (j == 0) System.out.print("\nPai " + paiFilho2.getPai() + ": ");
                    System.out.print(population[i][j] + " ");
                    
                    // Filho2 recebe os 5 primeiros objetos do pai 2
                    // Esses valores são colocados no início do vetor
                    if (j < 5) filho2[j] = population[i][j];
                    
                    // Filh1 recebe os 5 últimos objetos do pai 2
                    // Esses valores são colocados no final do vetor
                    else if (j > 4) filho1[j] = population[i][j];
                }
            }
        }
        
        // Printa filho 1
        System.out.println();
        System.out.println();
        System.out.print("Filho 1: ");
        for (int i = 0; i < filho1.length; i++) {
            System.out.print(filho1[i]);
        }
        
        // Printa filho 2
        System.out.println();
        System.out.print("Filho 2: ");
        for (int i = 0; i < filho2.length; i++) {
            System.out.print(filho2[i]);
        }

        System.out.println();
    }
    
    // Método que faz a mutação nos filhos dos pais mais aptos
    public void mutacao() {
        
        System.out.println();
        System.out.print("ALERTA DE MUTAÇÃO!!");
        System.out.println();
        System.out.print("Filho 1 mutado: ");
        // Faz a mutação no filho 1, trocando onde tem 0 por 1 e o contrário
        for (int i = 0; i < filho1.length; i++) {
            if (filho1[i] == 0) filho1[i] = 1;
            else filho1[i] = 0;
            System.out.print(filho1[i]);
        }
        
        System.out.println();
        System.out.print("Filho 2 mutado: ");
        // Faz a mutação no filho 2, trocando onde tem 0 por 1 e o contrário
        for (int i = 0; i < filho2.length; i++) {
            if (filho2[i] == 0) filho2[i] = 1;
            else filho2[i] = 0;
            System.out.print(filho1[i]);
        }
    }
    
    // Método para calcular o Fitness do Filho
    public void calculaFitFilho() {
        
        // Array que recebe o valor dos objetos da população
        int[] valor = populacao.getValor();
        
        // Array que recebe o valor dos objetos da população
        int[] peso = populacao.getPeso();

        // Laço que calcula o fitness e peso do filho 1 e seta na variável SumFitness e SumKg
        for (int i = 0; i < filho1.length; i++) {
            if (filho1[i] == 1) {
                paiFilho1.setSumFitness(valor[i]);
                paiFilho1.setSumKg(peso[i]);
            }
        }
        
        // Array para armazenar o grau de Aptidão do filho
        String[] grauAptidao = new String[2];
        
        /*
            { Método de punição para os filhos, igual ao dos cromossomos da população }
        */
        
        // Se o peso do filho 1 ultrapassar o peso da mochila
        if (paiFilho1.getSumKg() > 25) {
            
            // Se for maior do que 15 e menor do que 20: punição 10
            if (paiFilho1.getSumFitness() > 15 && paiFilho1.getSumFitness() < 20) { 
                
                // Chama o método de punição passando o valor de 10
                paiFilho1.punicaoFitness(10);
                
                // Armazena o grau de aptidão desse filho e a penalidade
                grauAptidao[0] = "inapto | Penalizado com -10";
                paiFilho1.setGrauAptidao(grauAptidao);
                
                // Printa o grau de Aptidão do filho 1
                System.out.println();
                System.out.println();
                System.out.print("Filho 1 " + grauAptidao[0]);
            }
            
            // Se for maior do que 15 e menor do que 20: punição 15
            else if (paiFilho1.getSumFitness() > 20 && paiFilho1.getSumFitness() < 30) {
                
                // Chama o método de punição passando o valor de 15
                paiFilho1.punicaoFitness(15);
                
                // Armazena o grau de aptidão desse filho e a penalidade
                grauAptidao[0] = "inapto | Penalizado com -15";
                paiFilho1.setGrauAptidao(grauAptidao);
                
                // Printa o grau de Aptidão do filho 1
                System.out.println();
                System.out.println();
                System.out.print("Filho 1 " + grauAptidao[0]);
            }
            
            // Se for maior do que 15 e menor do que 20: punição 20
            else if (paiFilho1.getSumFitness() > 30) {
                
                // Chama o método de punição passando o valor de 20
                paiFilho1.punicaoFitness(20);
                
                // Armazena o grau de aptidão desse filho e a penalidade
                grauAptidao[0] = "inapto | Penalizado com -20";
                paiFilho1.setGrauAptidao(grauAptidao);
                
                // Printa o grau de Aptidão do filho 1
                System.out.println();
                System.out.println();
                System.out.print("Filho 1 " + grauAptidao[0]);
                
                // Então:
            } else {
                
                // Chama o método de punição passando o valor de 5
                paiFilho1.punicaoFitness(5);
                
                // Armazena o grau de aptidão desse filho e a penalidade
                grauAptidao[0] = "inapto | Penalizado com -5";
                paiFilho1.setGrauAptidao(grauAptidao);
                               
                // Printa o grau de Aptidão do filho 1
                System.out.println();
                System.out.println();
                System.out.print("Filho 1 " + grauAptidao[0]);
            }
            
            // Então o filho é apto
        } else {
            
            // Armazena o grau de aptidão desse filho
            grauAptidao[0] = "apto";
            paiFilho1.setGrauAptidao(grauAptidao);
                
            // Printa o grau de Aptidão do filho 1
            System.out.println();
            System.out.println();
            System.out.print("Filho 1 " + grauAptidao[0]);
        }

        
        // Laço que calcula o fitness e peso do filho 1 e seta na variável SumFitness e SumKg
        for (int i = 0; i < filho2.length; i++) {
            if (filho2[i] == 1) {
                paiFilho2.setSumFitness(valor[i]);
                paiFilho2.setSumKg(peso[i]);
            }
        }
        
        // Se o peso do filho 2 ultrapassar o peso da mochila
        if (paiFilho2.getSumKg() > 25) {
            
            // Se for maior do que 15 e menor do que 20: punição 10
            if (paiFilho2.getSumFitness() > 15 && paiFilho2.getSumFitness() < 20) { 
                
                // Chama o método de punição passando o valor de 10
                paiFilho2.punicaoFitness(10);
                
                // Armazena o grau de aptidão desse filho e a penalidade
                grauAptidao[1] = "inapto | Penalizado com -10";
                paiFilho2.setGrauAptidao(grauAptidao);
                
                // Printa o grau de Aptidão do filho 2
                System.out.println();
                System.out.println("Filho 2 " + grauAptidao[1]);
            }
            
            // Se for maior do que 15 e menor do que 20: punição 15
            else if (paiFilho2.getSumFitness() > 20 && paiFilho2.getSumFitness() < 30) {
                
                // Chama o método de punição passando o valor de 15
                paiFilho2.punicaoFitness(15);
                
                // Armazena o grau de aptidão desse filho e a penalidade
                grauAptidao[1] = "inapto | Penalizado com -15";
                paiFilho2.setGrauAptidao(grauAptidao);
                
                
                // Printa o grau de Aptidão do filho 2
                System.out.println();
                System.out.println("Filho 2 " + grauAptidao[1]);
            }
            
            // Se for maior do que 15 e menor do que 20: punição 20
            else if (paiFilho2.getSumFitness() > 30) { 
                
                // Chama o método de punição passando o valor de 20
                paiFilho2.punicaoFitness(20);
                
                // Armazena o grau de aptidão desse filho e a penalidade
                grauAptidao[1] = "inapto | Penalizado com -20";
                paiFilho2.setGrauAptidao(grauAptidao);
                                
                // Printa o grau de Aptidão do filho 2
                System.out.println();
                System.out.println("Filho 2 " + grauAptidao[1]);
                
                // Então: 
            } else {
                
                // Chama o método de punição passando o valor de 20
                paiFilho2.punicaoFitness(5);
                
                // Armazena o grau de aptidão desse filho e a penalidade
                grauAptidao[1] = "inapto | Penalizado com -5";
                paiFilho2.setGrauAptidao(grauAptidao);
                
                // Printa o grau de Aptidão do filho 2
                System.out.println();
                System.out.println("Filho 2 " + grauAptidao[1]);
            }
            
            // Então:
        } else {
            
            // Armazena o grau de aptidão desse filho
            grauAptidao[1] = "apto";
            paiFilho2.setGrauAptidao(grauAptidao);
            
            // Printa o grau de Aptidão do filho 1
            System.out.println();
            System.out.println("Filho 2 " + grauAptidao[1]);
        }

        
        // Printa o Fitness e o Kg dos dois filhos
        System.out.println();
        System.out.println("fitess filho 1: " + paiFilho1.getSumFitness());
        System.out.println("kg filho 1: " + paiFilho1.getSumKg());
        System.out.println();
        System.out.println("fitness filho 2: " + paiFilho2.getSumFitness());
        System.out.println("kg filho 2: " + paiFilho2.getSumKg());
        
        // Array de fitness recebe o fitness de cada cromossomo da população
        fitness = populacao.getFitness();
        
        int i;
        
        // Se o filho 1 for mais apto que o filho 2
        if (paiFilho1.getSumFitness() > paiFilho2.getSumFitness()) {      
            
            // Chama o método para substituir o filho 1 no lugrar do cromossomo menos adaptado ao ambiente
            populacao.substituiFilhoPai(encontraCromossomoMenosApto(), filho1, paiFilho1.getSumFitness(), paiFilho1.getSumKg());
            
            // Printa a geração e o cromossomo do filho mais apto ao ambiente
            System.out.println();
            System.out.println();
            System.out.print("Geração " + countGeracao + ": ");
            for (i = 0; i < filho1.length; i++) {
                System.out.print(filho1[i]);
            }
            
            // Printa o fitness, peso e grau de aptidão do filho mais apto
            System.out.print(" Fitness: " + paiFilho1.getSumFitness());
            System.out.print("| Kg: " + paiFilho1.getSumKg());
            System.out.print("| Grau de Aptidao: " + grauAptidao[0]);
            
            // Então significa que o filho 2 é o mais adaptado ao ambiente
        } else {
            
            // Chama o método para substituir o filho 1 no lugrar do cromossomo menos adaptado ao ambiente
            populacao.substituiFilhoPai(encontraCromossomoMenosApto(), filho2, paiFilho2.getSumFitness(), paiFilho2.getSumKg());
            
            // Printa a geração e o cromossomo do filho mais apto ao ambiente
            System.out.println();
            System.out.print("GERAÇÃO!!");
            System.out.println();
            System.out.print("Geração " + countGeracao + ": ");
            for (i = 0; i < filho2.length; i++) {
                System.out.print(" " + filho2[i]);
            }
            
            // Printa o fitness, peso e grau de aptidão do filho mais apto
            System.out.print(" Fitness: " + paiFilho2.getSumFitness());
            System.out.print("| KG: " + paiFilho2.getSumKg());
            System.out.print("| Grau de Aptidao: " + grauAptidao[1]);
        }
        
        // Incrementa a variável pra próxima geração
        countGeracao++;
    }
    
    // Método para encontrar o cromossomo menos apto na população, que este será substituido pelo filho mais apto
    public int encontraCromossomoMenosApto() {
        // Variáveis que serão utilizadas para achar o cromossomo de menor fitness
        int menor = 0;
        int aux = 0;
        int i;
        // Variável pra marcar a posição onde está o cromossomo de menor fitness
        int marcaPosition = 0;
        
        for (i = 0; i < fitness.length; i++) {
            aux = fitness[i];
            if (i == 0) menor = aux;
            if (aux < menor) {
                menor = aux;
                marcaPosition = i;
            }
        }
        
        return marcaPosition;
    }
}
