import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Genetic extends Solver {
	/*
	 * What should be done is the implementation of Junk DNA
	 * 
	 * say the length is 30, but you find a solution in 14
	 * rest should be ignored, 
	 * so when generating moves, we stop if we get a solution.
	 * would be better to stop if we get worse result overtime
	 * 
	 * TODO: Eliminate clones, physics forbids exact replicas.
	 * since here, genes are float (infinitely small fractions), 
	 * they can be considered as fundamental particles, 
	 * and there are no identical copies of the same thing in the universe
	 * the quantum state at least, I think
	 * or else, teleportation would create a paradox
	 * */
	
	
	public void geneticAlgorithm(Square square) {
		
		int popSize = 300;
		int maxIter = 999;
		int crossRate = 20;
		int mutationRate = 5;
		int sizeOfSolution = square.sumOfDistances() + square.wrongCases(); //acceptable assumption
		// yes, I know, solutions are list of moves, but my squares contain those lists,
		// it's simpler this way
		int bestYet = sizeOfSolution;
		float stagnationLimit= 30; // every X % of maxIter
		float addGenesAfter = 100; // every X % of maxIter
		int stagnated = 0;
		int stagGene = 0;
		
		float stagnationImpact = 10; // the worst X % to new ones
		
		
		/* converting ratio to value */
		crossRate = popSize*crossRate/100;
		mutationRate = popSize*mutationRate/100;
		addGenesAfter = maxIter*addGenesAfter/100;
		stagnationLimit = maxIter*stagnationLimit/100;
		stagnationImpact = popSize*stagnationImpact/100;

		
		ArrayList<Square> population = new ArrayList<Square>();
		
		for (int i = 0; i < popSize; i++) {
			ArrayList<Float> solution = new ArrayList<Float>(square.randomRealSolution(sizeOfSolution));
			Square newSpecie = new Square(square);
			newSpecie.realMoves.addAll(solution);
			newSpecie.moveSquareFromReal();
			population.add(newSpecie);
		}
		
		rankPopulation(population);
		System.out.println("initial pop: best: "+ fitness(population.get(0)) + " worst "+ fitness(population.get(population.size()-1)));

		for (int i = 0; i < maxIter; i++) {	
			
			if(fitness(population.get(0))==0) {
				System.out.println("found a solution!");
				System.out.println(population.get(0).moves+ " = " + population.get(0).moves.size());
				break;
			}
			
			if(bestYet>fitness(population.get(0))) {
				bestYet = fitness(population.get(0));
		//		System.out.println("best: "+ (i+1) +" "+bestYet);
			}else {
				stagnated++;
				stagGene++;
				
			}
			System.out.println("score: "+ (i+1) +" "+fitness(population.get(0)));

			
			if(stagnated>=stagnationLimit) {
				// when you reach rockBottom, you change dramatically !!
				// change how many think the same (impact)
				System.out.println(stagnationImpact+" new ones");				
				for (int x = 1; x <= stagnationImpact; x++) {
					ArrayList<Float> solution = new ArrayList<Float>(square.randomRealSolution(sizeOfSolution));
					Square newLife = new Square(square);
					newLife.realMoves.addAll(solution);
					newLife.moveSquareFromReal();
					population.set(population.size()-1,newLife);
				}
	
				stagnated = 0;				
			}
			
			if(stagGene>= addGenesAfter) {
				System.out.println("added a gene !!!!!");
				for (Square one : population) {
					float gene= new Random().nextFloat();
					one.realMoves.add(gene);
					one.moveSquareOnceByReal(gene);
				}
				stagGene = 0;
			}
			
			mutation(square, population,mutationRate);	
			crossingGenomes(square, popSize, crossRate,population,sizeOfSolution );
			theRing(population, popSize); // is where the battle happens
	
		}
		if(fitness(population.get(0))!=0) {
			System.out.println("Initial Square");
			square.draw();
			System.out.println("Best "+popSize/20+" of "+population.get(0).moves.size()+" length solutions!");
			for (int i=0 ;i<popSize/20;i++) {
				population.get(i).draw();
				System.out.println(population.get(i).moves);				
			}
		}

	}
	
	public void theRing(ArrayList<Square> population, int popSize) {
		rankPopulation(population);
//		System.out.print("size was " + population.size());
//		System.out.print("best ("+ fitness(population.get(0))+
//				") worst ("+ fitness(population.get(population.size()-1)));
//		
		while(killTheClone(population,detectAClone(population)));
		
//		System.out.print(") after clones " + population.size());

		while(population.size()>popSize) {
			population.remove(population.size()-1);
		}
//		System.out.println(" is now " + population.size() +
//				"worst now ("+ fitness(population.get(population.size()-1))+")");

	}
	

	public int detectRealClone(ArrayList<Square> population) {
		for (int i = 0; i < population.size()-1; i++) {
			for (int j = i+1; j < population.size(); j++) {
				if(population.get(i).realMoves.equals(population.get(j).realMoves))
					return j;
			}
		}
		return -1;
	}
	
	
	public int detectAClone(ArrayList<Square> population) {
		for (int i = 0; i < population.size()-1; i++) {
			for (int j = i+1; j < population.size(); j++) {
				if(population.get(i).moves.equals(population.get(j).moves))
					return j;
			}
		}
		return -1;
	}
	
	public boolean killTheClone(ArrayList<Square> population, int clone) {
		// you shall not self replicate, or create a copy of someone existing
		if(clone<0) 
		{
			return false;
		}
		population.remove(clone);
		return true;
	}
	
	public void crossingGenomes(Square square, int popSize, int crossRate,ArrayList<Square> population,int sizeOfSolution ) {
		ArrayList<Integer> list = new ArrayList<>();
		int i1, i2;
		for (int i = 0; i < population.size() ; i++) {
			list.add(i);
		}
		for (int i = 0; i < crossRate; i++) {
			int rand = new Random().nextInt(list.size());
			i1 = list.get(rand);
			list.remove(rand);
			rand = new Random().nextInt(list.size());
			i2 = list.get(rand);
			list.remove(rand);		
			
			int crossPoint = new Random().nextInt(sizeOfSolution) + 1;	
			Square parent1  = new Square(population.get(i1));
			Square parent2  = new Square(population.get(i2));
			crossOver(square, population, crossPoint, parent1, parent2);	
			
			
		}
		
			
	}

	
	public void crossOver(Square square,ArrayList<Square> population, int crossPoint, Square parent1, Square parent2) {
		ArrayList<Float> child = new ArrayList<Float>();
		ArrayList<Float> anti_child = new ArrayList<Float>();
		
		for (int i = 0; i < crossPoint; i++) {
			child.add(new Float(parent1.realMoves.get(i)));
		}
		for (int i = crossPoint; i < parent2.realMoves.size(); i++) {
			child.add(new Float(parent2.realMoves.get(i)));
		}
		for (int i = 0; i < crossPoint; i++) {
			anti_child.add(new Float(parent2.realMoves.get(i)));
		}
		for (int i = crossPoint; i < parent1.realMoves.size(); i++) {
			anti_child.add(new Float(parent1.realMoves.get(i)));
		}
		
		// we create 2 then they battle between all the population.
		Square babyMike = new Square(square);
		Square babyIke = new Square(square);
		
		babyMike.realMoves.addAll(new ArrayList<Float>(child));
		babyIke.realMoves.addAll(new ArrayList<Float>(anti_child));
		
		babyMike.moveSquareFromReal(); // Moves and converts Real(float)Moves to legal moves (String), then rest is done by others.
		babyIke.moveSquareFromReal();
		
		population.add(babyMike);
		population.add(babyIke);
/*		
		rankPopulation(population);
		
		if(!killTheClone(population, detectAClone(population)))
			population.remove(population.size()-1);

		if(!killTheClone(population, detectAClone(population)))
			population.remove(population.size()-1);
*/		
		
	}
	
	public void mutation(Square square, ArrayList<Square> population, int mutationRate) {
		for (int i = 0; i < mutationRate; i++) {
		int chosen = new Random().nextInt(population.size());
		mutate(square, population.get(chosen));
		}
	}
	
	public void mutate(Square square, Square chosen) {
		float rand  = new Random().nextFloat();
		int gene = new Random().nextInt(chosen.realMoves.size());
		chosen.realMoves.set(gene,rand);
		ArrayList<Float> newGenes = new ArrayList<Float>(chosen.realMoves);
		Square newBody = new Square(square);
		newBody.realMoves.addAll(newGenes);
		newBody.moveSquareFromReal(); // new Square according to new genes
		chosen = newBody;
		
	}
	

	
	public void rankPopulation(ArrayList<Square> population) {
		population.sort(compareBoth);
	}
	
	public int fitness(Square square) {
		return (square.sumOfDistances()+square.wrongCases());
	}
	

	public static Comparator<Square> compareDistance = new Comparator<Square>() {		
		public int compare(Square As1, Square As2) {
			float h1 = As1.sumOfDistances()+As1.wrongCases();
			float h2 = As2.sumOfDistances()+As2.wrongCases();
			if (h1 > h2) 		return 1 ;
			else if (h1 < h2) 	return -1;
			else 				return 0 ;
		}
	};

	public static Comparator<Square> compareBoth = new Comparator<Square>() {		
		public int compare(Square As1, Square As2) {
			float h1 = As1.sumOfDistances();
			float h2 = As2.sumOfDistances();
			if (h1 > h2) 		return 1 ;
			else if (h1 < h2) 	return -1;
			else 				return 0 ;
		}
	};
	
}
