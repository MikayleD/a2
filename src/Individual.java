import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Individual {

    /**
     * Chromosome stores the individual's genetic data as an ArrayList of characters
     * Each character represents a gene
     */
    ArrayList<Character> chromosome;
    ArrayList<Character>  children;
    
    /**
     * Chooses a letter at random, in the range from A to the number of states indicated
     * @param num_letters The number of letters available to choose from (number of possible states)
     * @return The letter as a Character
     */
    private Character randomLetter(int num_letters) {
        return Character.valueOf((char)(65+ThreadLocalRandom.current().nextInt(num_letters)));
      }
    
    /** 
     * Method to determine whether a given gene will mutate based on the parameter ***m***
     * @param m the mutation rate
     * @return true if a number randomly chosen between 0 and 1 is less than ***m***, else false
    */
    private Boolean doesMutate(float m){
        float randomNum = ThreadLocalRandom.current().nextInt(0, 1);
        return randomNum < m;
    }

    /**
     * Expresses the individual's chromosome as a String, for display purposes
     * @return The chromosome as a String
     */
    public String toString() {
        StringBuilder builder = new StringBuilder(chromosome.size());
        for(Character ch: chromosome) {
          builder.append(ch);
        }
        return builder.toString();
      }

    /** 
     * Inital constructor to generate initial population members
     * @param c_0 The initial chromosome size
     * @param num_letters The number of letters available to choose from
     */
    public Individual(int c_0, int num_letters) {
      // initilizing chromosome array
      this.chromosome = new ArrayList<>();

      // loop that adds random characters to list and prints it out to check 
      for (int i = 0; i < c_0; i++){
        
        char randomizedLetter = randomLetter(num_letters);
        chromosome.add(randomizedLetter);
      }

      System.out.println("Chromosome's " + chromosome);
    }

     /**
      * Second constructor to create parents and offspring chromosomes
      * @param parent1 The first parent chromosome
      * @param parent2 The second parent chromosome
      * @param c_max The maximum chromosome size
      * @param m The chances per round of mutation in each gene
      */
    public Individual(Individual parent1, Individual parent2, int c_max, float m, int num_letters) {
      Random rand = new Random();
      int prefix = rand.nextInt(parent1.chromosome.size())+ 1;
      int suffix = rand.nextInt(parent2.chromosome.size()) + 1;

      
      this.chromosome = new ArrayList<>();

      for(int i = 0; i<prefix; i++){
        this.chromosome.add(parent1.chromosome.get(i));
      }
      for(int i = 0; i<suffix; i++){
        this.chromosome.add(parent2.chromosome.get(i));
      }
      // mutation for loop
      for(int i = 0; i < chromosome.size(); i++){
        if (this.doesMutate(m)){
          this.chromosome.set(i, this.randomLetter(num_letters));
        }
      }

      // truncate method 
      while (this.chromosome.size() > c_max){
        this.chromosome.remove(this.chromosome.size() - 1);
      }
      System.out.println("Child Chromosome's " + chromosome);
    }
    /**
     * Calculates the fitness score of each chromosome
     * @return The fitness score as an int
     */
    public int getFitness( ) {
      int score = 0;
      int n = this.chromosome.size();
        for (int i = 0; i < n/2 ; i++){
          if (this.chromosome.get(i) == this.chromosome.get(n-i-1)){
            score++;
          } else if 
            (this.chromosome.get(i) != this.chromosome.get(n-i-1)){
              score--;
            }
            
    } 
      for (int i = 0; i < n/2 ; i ++) {
          if (this.chromosome.get(i) == this.chromosome.get(i+1)){
            score--;
          } 
      }
   
    return score;
  }


    public static void main(String[] args) {
      Individual I2 = new Individual(8,5);
      Individual I3 = new Individual(8,5);
      Individual C4 = new Individual(I3, I2, 8, 0, 5);
      System.out.println(C4.toString());
      System.out.println(C4.getFitness());
    }
 
}
