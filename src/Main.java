import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		Square square = new Square(4);
//		square.randomize(); // Do not use this, it creates impossible squares

//		square.shuffle(40*square.size);
		
		int [] tab = {5,3,2,1,4,6,0,7,8,9,10,11,12,13,14,15};
		square.custom(tab);
				System.out.println("New Square");
		square.draw(); // don't set to true, it does not work
		System.out.println("dist " + square.sumOfDistances()+ 
		" wrong " +square.wrongCases());
		
//		square.testSolution(square.randomSolution(12));
// 		this above just shows how to generate a set of admissible moves and test it.
// 		it uses correctList(): bool that uses verifyList():int  methods		

//		square.drawIter(); // shows square for every change
//		square.print(true);
		long t1 = System.currentTimeMillis();
		System.out.println("Astar");

		Solver solver = new Solver();
//		// either add the true argument to show all possible solutions, or use only the Square argument
//		// just don't use it, not worth it
//		

//		t1 = System.currentTimeMillis();
//		String str1 = ""+ solver.Astar(square, false, 1)+
//				"H1 took: "+ (System.currentTimeMillis()- t1)+"ms";
//		
//		t1 = System.currentTimeMillis();		
//		String str2 = ""+ solver.Astar(square, false, 2)+
//				"H2 took: "+ (System.currentTimeMillis()- t1)+"ms";
//
		t1 = System.currentTimeMillis();
		String str3 = ""+ solver.Astar(square, false, 3)+
				"H3 took: "+ (System.currentTimeMillis()- t1)+"ms" ;
//		System.out.println( str1);
//		System.out.println( str2);
		System.out.println( str3);
		

		Genetic population = new Genetic();
		population.geneticAlgorithm(square);

		
	System.out.println("End. took: "+ (System.currentTimeMillis()- t1)+"ms");
	}
	
}
