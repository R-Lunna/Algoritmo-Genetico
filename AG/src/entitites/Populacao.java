package entitites;

import java.util.Random;

// A quantidade de objetos e da população é de 10
// Espaço da mochila é de 25
// Classe que contém todas as informações da população
public class Populacao {
    
    // Array contendo os valores de cada objeto.
    private int[] valor = {1, 3, 8, 5, 4, 7, 12, 6, 3, 4};
    
    // Array contendo os pesos de cada objeto.
    private int[] peso = {4, 1, 9, 5, 1, 9, 10, 2, 3, 6};
    
    // Matriz binária contendo toda a população, todos os cromossomos
    private int[][] populacao = new int[10][10];
    
    // Array contendo o fitness de cada cromossomo
    private int[] fitness = new int[10];
    
    // Array contendo o peso de cada cromossomo
    private int[] kg = new int[10];
    
    // Array contendo o grau de aptidão de cada cromossomo
    private String[] grauAptidao = new String[10];
    
    // Variável contendo a soma dos fitness de toda a população
    private int fitnessPopulacao = 0;
    
    /* 
        Métodos gets e sets para cada uma das variáveis
    */
    public void setFitness(int[] fitness) {
        this.fitness = fitness;
    }

    public int[] getValor() {
        return valor;
    }

    public int[] getPeso() {
        return peso;
    }

    public int[][] getPopulacao() {
        return populacao;
    }

    public int[] getFitness() {
        return fitness;
    }

    public int[] getKg() {
        return kg;
    }
    
    // Método que preenche a matriz binária aleatoriamente
    public void preenchePopulacao() {
        
        // Instancia um objeto do tipo Random
        Random random = new Random();
        
        // Laço preenchendo a matriz com valores de 1 ou 0
        for (int i = 0; i < populacao.length; i++) {
            for (int j = 0; j < populacao[i].length; j++) {
                populacao[i][j] = random.nextInt(2);
            }
        }
    }
    
    /* 
        Método que substitui o filho mais apto, que tem o maior fitness depois de ser punido,
        pelo cromossomo menos apto da população
    
        { Variáveis }
        -- marcaPosition = posição em que está o cromossomo menos adaptado
        -- filho = cromossomo do filho mais adaptado
        -- fitnessFilho = fitness do filho mais adaptado
        -- kgFilho = peso do filho mais adaptado
    */
    public void substituiFilhoPai(int marcaPosition, int[] filho, int fitnessFilho, int kgFilho) {
        
        /* 
            Laço que procura pela posição que está o cromossomo menos adaptado
            Assim que encontra, substitui ele pelo filho mais adaptado
        */
        for (int i = 0; i < populacao.length; i++) {
            for (int j = 0; j < populacao[i].length; j++) {
                if (i == marcaPosition) {
                    populacao[i][j] = filho[j];
                }
            }
        }
        
        /*  
            Laço que substitui o fitness do cromossomo 
            menos adaptado pelo do filho mais adaptado
        */
        for (int i = 0; i < fitness.length; i++) {
            if (i == marcaPosition) fitness[i] = fitnessFilho;
        }
        
        /*  
            Laço que substitui o peso do cromossomo 
            menos adaptado pelo do filho mais adaptado
        */
        for (int i = 0; i < kg.length; i++) {
            if (i == marcaPosition) kg[i] = kgFilho;
        }
    }
    
    // Método que calcula o Fitness e o peso de cada cromossomo na população
    public void calculaFitKg() {
        
        // Variável que guarda o fitness de um cromossomo
        int sumFitness = 0;
        
        // Variável que guarda o peso de um cromossomo
        int sumKg = 0;
        
        /* 
            Laço que irá calcular e guardar o fitness e peso de cada cromossomo
            em seus respectivos arrays: fitness e kg
        */
        for (int i = 0; i < populacao.length; i++) {
            
            // Percorre todo o cromossomo somando o seu valor e seu peso
            for (int j = 0; j < populacao[i].length; j++) {
                
                /* 
                    Verifica se aquele objeto do cromossomo possui valor 1, 
                    caso tenha, guarda na variável somatória de fitness e kg
                */
                if (populacao[i][j] == 1) {
                    sumFitness += valor[j];
                    sumKg += peso[j];
                }
            }

            /*
                { Condição de punição }
                Caso o peso do cromossomo seja maior que o peso máximo da mochila
                entra no if, onde há diferentes formas de punições, dependendo 
                do fitness do cromossomo
            */
            if (sumKg > 25) {
                
                // Se for maior do que 15 e menor do que 20: punição 10
                if (sumFitness > 15 && sumFitness < 20) { 
                    
                    // Subtrai 10 do fitness do cromossomo
                    sumFitness -= 10;
                    
                    // Determinar o grau de Aptidão como "inapto"
                    grauAptidao[i] = "inapto | Penalizado com -10";
                }
                
                // Se for maior do que 20 e menor do que 30: punição 15
                else if (sumFitness > 20 && sumFitness < 30) {
                    
                    // Subtrai 15 do fitness do cromossomo
                    sumFitness -= 15;
                    
                    // Determina o grau de Aptidão como "inapto"
                    grauAptidao[i] = "inapto | Penalizado com -15";
                }
                
                // Se for maior do que 30: punição 20
                else if (sumFitness > 30) { 
                    
                    // Subtrai 20 do fitiness do cromossomo
                    sumFitness -= 20;
                    
                    // Determina o grau de Aptidão como "inapto"
                    grauAptidao[i] = "inapto | Penalizado com -20";
                
                // Então: punição 5
                } else {
                    sumFitness -= 5;
                    
                    // Determina o grau de Aptidão como "inapto"
                    grauAptidao[i] = "inapto | Penalizado com -5";
                }
                
                // Então determina o grau de Aptidão como "apto"
            } else {
                grauAptidao[i] = "apto";
            }
            
            // Insere no array que corresponde à posição do cromossomo o seu fitness
            fitness[i] = sumFitness;
            
            // Insere no array que corresponde à posição do cromossomo o seu peso
            kg[i] = sumKg;
            
            // Incrementa na variável do fitness da população o fitness desse cromossomo
            fitnessPopulacao += sumFitness;
            
            // Reseta as variáveis de fitness e peso para que seja usado no próximo cromossomo
            sumFitness = 0;
            sumKg = 0;
        }
    }
    
    // Método para printar em tela toda a população, valor e peso de cada objeto
    public void printPopulacao() {
        System.out.println();
        System.out.println();
        
        // Printa o peso, percorrendo todo o array de peso
        System.out.print("Peso: " + " ");
        for (int i = 0; i < peso.length; i++) {
                System.out.print(peso[i] + " " + " ");
        }

        // Printa o valor, percorrendo todo o array de valor
        System.out.println();
        System.out.print("Valor: ");
        for (int i = 0; i < valor.length; i++) {
                System.out.print(valor[i] + " " + " ");
        }
        
        // Printa a população, percorrendo toda a matriz
        System.out.println();
        System.out.println();
        for (int i = 0; i < populacao.length; i++) {
                System.out.print(" " + " " + " " + " " + " " + " " + " ");
                for (int j = 0; j < populacao[i].length; j++) {
                        System.out.print(populacao[i][j] + " " + " ");
                }
                
                // Printa o fitness, peso e grau de aptidão de cada cromossomo
                System.out.print("Fitness: " + fitness[i]);
                System.out.print("| Kg: " + kg[i]);
                System.out.print("| Grau de Aptidão: " + grauAptidao[i]);
                System.out.println();
        }
        
        // Printa o fitness da população
        System.out.println();
        System.out.println("Fitness da população: " + fitnessPopulacao);
        fitnessPopulacao = 0;
    }
	
}
