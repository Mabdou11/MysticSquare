import java.util.ArrayList;
import java.util.Comparator;

public class Solver {

	/* TODO: implement a method to show all possible solutions for a set.
	 * takes about 5 minutes without proper heuristic.
	 * implement it in the next TODO:
	 * */
	
	public void Astar(Square square) {
		// Surcharge, to simplify the implementation,
		// if false == show only one solution
		// the other one is really bad
		// so just leave it as false for now
		
		Astar(square, false);
	}
	
	public void Astar(Square square, boolean allSolutions) {
		// eh, don't want to explain, sorry 
		Astar(square, allSolutions, 1);
	}
	
	public ArrayList<String> Astar(Square square, boolean allSolutions, int method) {
		
		// boolean == true ==> show all possible solutions
		int i = 0;
		int solutions = 0;
		ArrayList<Square> Open = new ArrayList<Square>();
		ArrayList<Square> Closed = new ArrayList<Square>();
		ArrayList<ArrayList<String>> listOfSolutions = new ArrayList<ArrayList<String>>();
		long t1 = System.currentTimeMillis();
		Square exploreS = new Square(square);
		Open.add(exploreS);
				
		while(!Open.isEmpty()) {

			if(!exploreS.isSolved()) {
				
				/* Showing progress here */
				/*
				System.out.print("gen "+ i++ +": score:\t");
				exploreS.progressBar();
				System.out.print("Moved "+exploreS.moves.size()+
						" Distance: "+ exploreS.sumOfDistances()+
						": wrong cases: "+ exploreS.wrongCases());
				if(allSolutions) System.out.println(". found "+ solutions+" solutions");
				else System.out.println();
				*/
				generateStates(exploreS, Open, Closed);
				Closed.add(Open.get(0));
				Open.remove(0);
				
				if(method==2)
					Open.sort(compareCases); // ascendent order, and best (solution) == 0
				else 
					if (method==1)
						Open.sort(compareDistance);
					else
						Open.sort(compareBoth);
				//choose either compareCases  || compareDistance || compareBoth // (Both is better)
				if(!Open.isEmpty())
					exploreS = Open.get(0);

			}else {
				if(!allSolutions)
					break;
				else {
					listOfSolutions.add(exploreS.moves);

					// this is the next TODO:
					System.out.println("found a solution ! ("+ ++solutions +")" );
					square.draw();
					System.out.println(exploreS.moves);
					exploreS.draw();
					// continuing...
					generateStates(exploreS, Open, Closed);
					//this is wrong: Closed.add(Open.get(0)); if we add solution to closed, it will not show again
					listOfSolutions.add(exploreS.moves);
					Open.remove(0);
					Open.sort(compareDistance); // ascendent order, and best (solution) == 0
					if(!Open.isEmpty())
						exploreS = Open.get(0);
					System.out.println((System.currentTimeMillis()-t1));
				}
			}
		}
		
		if(exploreS.isSolved()) {
			System.out.println("found a solution !");
			square.draw();
			System.out.println(exploreS.moves+" = "+exploreS.moves.size());
			exploreS.draw();
			
			listOfSolutions.add(exploreS.moves);
			
		}else if(!allSolutions) {
			System.out.println("list of all squares:");
			for (Square alot : Closed) {
				alot.draw();
				System.out.println();
			}
		}else {
			System.out.println("list of all solutions:");
			for (ArrayList<String> arrayList : listOfSolutions) {
				System.out.println(arrayList);
			}
		}
		
		System.out.println("Open has "+Open.size());
		System.out.println("Closed has "+Closed.size());
		
		return listOfSolutions.get(0); // returns the First (should be the only)
	}
	
	public void generateStates( Square square,
								ArrayList<Square> Open,
								ArrayList<Square> Closed
								) {
		Square newS	 = null;
		Square origin = new Square(square);
		Square tempS = new Square(origin);

		if(tempS.moveUp()) {
			if(!contains(Closed, tempS))
				if(!contains(Open, tempS)) {
					tempS.moves.add("up");
					newS = new Square(tempS);
					Open.add(newS);
				}
			tempS = new Square(origin);
			newS = null;
		}
		
		if(tempS.moveDown()) {
			if(!contains(Closed, tempS))
				if(!contains(Open, tempS)) {
					tempS.moves.add("down");
					newS = new Square(tempS);
					Open.add(newS);
				}
			tempS = new Square(origin);
			newS = null;
		}
		
		if(tempS.moveLeft()) {
			if(!contains(Closed, tempS))
				if(!contains(Open, tempS)) {
					tempS.moves.add("left");
					newS = new Square(tempS);
					Open.add(newS);
				}
			tempS = new Square(origin);
			newS = null;
		}	
		
		if(tempS.moveRight()) {
			if(!contains(Closed, tempS))
				if(!contains(Open, tempS)) {
					tempS.moves.add("right");
					newS = new Square(tempS);
					Open.add(newS);
				}
			tempS = new Square(origin);
			newS = null;
		}
		
		tempS = null;
		newS = null;
	}
	
	public boolean contains(ArrayList<Square> squareList, Square square) {
			for (Square s : squareList) {
				if(s.equals(square)) {
					return true;
				}
			}
			return false;
	}
	
	
	
	public static Comparator<Square> compareDistance = new Comparator<Square>() {		
		public int compare(Square As1, Square As2) {
			float h1 = As1.sumOfDistances()+ As1.moves.size();
			float h2 = As2.sumOfDistances()+ As2.moves.size();
			if (h1 > h2) 		return 1 ;
			else if (h1 < h2) 	return -1;
			else 				return 0 ;
		}
	};

	public static Comparator<Square> compareCases = new Comparator<Square>() {		
		public int compare(Square As1, Square As2) {
			float h1 = As1.wrongCases()*(As1.size-1) + As1.moves.size();
			float h2 = As2.wrongCases()*(As2.size-1) + As2.moves.size();

			if (h1 > h2) 		return 1 ;
			else if (h1 < h2) 	return -1;
			else 				return 0 ;
		}
	};

	public static Comparator<Square> compareBoth = new Comparator<Square>() {		
		public int compare(Square As1, Square As2) {
			float h1 = As1.sumOfDistances()+  As1.wrongCases() +As1.moves.size() ;
			float h2 = As2.sumOfDistances()+  As2.wrongCases() +As2.moves.size() ;
			if (h1 > h2) 		return 1 ;
			else if (h1 < h2) 	return -1;
			else 				return 0 ;
		}
	};
	
	public void countDown(int sec) {
		System.out.print("will start in ");

		for (int i = sec; i > 0; i--) {		
			System.out.print(" "+i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
	}
}
