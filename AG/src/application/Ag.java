package application;

import java.util.Scanner;

import entitites.TorneioBinario;

/* 
    Integrantes: 
        -- Rafael dos Santos Luna da Silva
        -- William da Silva Santos Pinheiro
        -- Edcarlos
*/
public class Ag {
    
    // Método principal
    public static void main(String[] args) {
        
        // Instancia um objeto do tipo Scanner para a leitura de dados
        Scanner sc = new Scanner(System.in);
        
        // Instancia um objeto do tipo da classe Torneio Binário
        TorneioBinario torneio = new TorneioBinario();

        // Apresenta qual o peso máximo da mochila
        System.out.println("Peso máximo da mochila: 25kg");
        System.out.println();
        
        /* 
            Chama o método que irá chamar outros métodos da Classe População
            para poder gerar a população
        */
        torneio.chamaPopulacao();
        
        /*  
            Pede para o usuário digita o número de gerações que ele deseja,
            sendo este valor utilizado para o método de parada
        */
        System.out.print("Informe o número de gerações: ");
        int geracoes = sc.nextInt();
        
        // Utiliza o objeto torneio para chamar a função que irá gerar o torneio binário
        torneio.geraTB(geracoes);
        
        // Encerra o objeto Scanner
        sc.close();
    }
}