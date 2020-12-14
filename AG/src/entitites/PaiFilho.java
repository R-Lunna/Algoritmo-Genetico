package entitites;

// Classe pai e filho
public class PaiFilho {
    
    // Variável que contém a posição onde o pai está
    private int pai;
    
    // Variável que contém o Fitness do filho
    private  int sumFitness = 0;
    
    // Variável que contém o peso do filho
    private int sumKg = 0;
    
    // Array contendo o grau de Aptidão do filho
    private String[] grauAptidao = new String[1];
    
    // Método construtor
    public PaiFilho() {

    }
    
    // Gets e sets da classe
    public int getPai() {
        return pai;
    }

    public void setPai(int pai) {
        this.pai = pai;
    }

    public int getSumFitness() {
        return sumFitness;
    }
    
    // Faz o somatório dos valores para gerar o fitness do filho
    public void setSumFitness(int sumFitness) {
        this.sumFitness += sumFitness;
    }

    public int getSumKg() {
        return sumKg;
    }

    // Faz o somátório dos pesos para gerar o peso do filho
    public void setSumKg(int sumKg) {
        this.sumKg += sumKg;
    }
    
    public String[] getGrauAptidao() {
        return grauAptidao;
    }

    public void setGrauAptidao(String[] grauAptidao) {
        this.grauAptidao = grauAptidao;
    }

    // Método que decrementa do Fitness do filho como forma de punição
    public void punicaoFitness(int sumFitness) {
        this.sumFitness -= sumFitness;
    }
    
    // Método que reseta o Fitness e o Kg do filho para a próxima geração
    public void resetaFitnessAndKg(int sumFitness, int sumKg) {
        this.sumFitness = sumFitness;
        this.sumKg = sumKg;
    }
}
