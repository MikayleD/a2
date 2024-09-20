import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.Comparator;

public class GA_Simulation {

  // Use the instructions to identify the class variables, constructors, and methods you need
  ArrayList<Individual> currentGeneration;
   int n; // num of individuals each gen
   int k;  // num of winners
   int r; // # of rounds
   int c_0; // initial chromosome soze
   int c_max; // the maximum chromosome size
   float m; //mutation chance
   int g; // # of gene states


   public GA_Simulation(int n, int k, int r, int c_0, int c_max, float m, int g){
    this.n = n;
    this.k = k;
    this.r = r;
    this.c_0 = c_0;
    this.c_max = c_max;
    this.m = m;
    this.g = g;
    this.currentGeneration = new ArrayList<>(n);
  }
  

  /** Sorts population by fitness score, best first 
   * @param pop: ArrayList of Individuals in the current generation
   * @return: ArrayList of Individuals sorted by fitness
  */
    public void rankPopulation(ArrayList<Individual> pop) {
        // sort population by fitness
        Comparator<Individual> ranker = new Comparator<>() {
          // this order will sort higher scores at the front
          public int compare(Individual c1, Individual c2) {
            return (int)Math.signum(c2.getFitness()-c1.getFitness());
          }
        };
        pop.sort(ranker); 
      }

  /** initializes a population of the desired size 
   * @param n takes in  the amount of times that individuals should be populated
   */
    public void init(int n){
      // array list runs n times and each time you will populate a new inividual 
      for (int i = 0; i < n; i ++){
        Individual newI = new Individual(c_0, g);
        this.currentGeneration.add(newI);
      }
      }

    /** selects each generation's winners, puts it into a list then evolves a new generation from two parents 
     * 
     */
    public void evolve(){
      this.rankPopulation(currentGeneration);

      ArrayList <Individual> firstGenWinners = new ArrayList<>();
      ArrayList <Individual> newGeneration = new ArrayList<>();
      for (int i = 0; i < k; i++ ){
        firstGenWinners.add(this.currentGeneration.get(i));
      }
      for(int i = 0; i < n; i++ ){
        int randnum1 = ThreadLocalRandom.current().nextInt(0, k);
        Individual parent1 = firstGenWinners.get(randnum1);

        int randnum2 = ThreadLocalRandom.current().nextInt(0, k);
        Individual parent2 = firstGenWinners.get(randnum2);

        Individual offspring = new Individual(parent1, parent2, c_max, m, g);
        newGeneration.add(offspring);

      }
      currentGeneration = newGeneration;
      System.out.println("The winners are " + firstGenWinners);
    }
    /** Prints out various statistics about the genrations fitness 
     * 
     */
    public void describeGeneration(){
      Individual mostFit = currentGeneration.getFirst();
      Individual kth = currentGeneration.get(k);
      Individual leastFit = currentGeneration.getLast();
      System.out.println("The most fit individual's score is " + mostFit.getFitness());
      System.out.println("The least fit individual's score is " + leastFit.getFitness());
      System.out.println("The k th fit individual's score is " + kth.getFitness());

      }
      /** run method that runs entire expirement by doing the initilizing, evolving,  ranking and describing. It then will do this for each round specified
       * 
       */
      public void run(){
        this.init(50);
        this.rankPopulation(currentGeneration);
        this.describeGeneration();

        for (int i = 0; i < r -1; i++){
          this.evolve();
          this.rankPopulation(currentGeneration);
          this.describeGeneration();
        }
      }
      public static void main(String[] args) {
        System.out.println("now running test ");
        GA_Simulation test = new GA_Simulation(10, 1, 10, 8, 20, 1, 5);
        test.run();
      }
}