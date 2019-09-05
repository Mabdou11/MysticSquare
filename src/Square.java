import java.util.Random;
import java.util.ArrayList;
public class Square {

	int [][] tab;
	int size = 3;
	int zero = 0;
	int zeroX = 0;
	int zeroY = 0;
	boolean print = false;
	boolean printAll = false;
	boolean drawEverytime = false;
	ArrayList<String> moves = new ArrayList<String>();
	ArrayList<Float> realMoves = new ArrayList<Float>();
	
	Square(){
		int number = 0;
		this.size = 3;
		this.tab = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.tab[i][j] = number++;
			}
		}
	}
	
	Square(Square square){
		this.size = new Integer(square.size);
		this.zero = new Integer(square.zero);
		this.zeroX = new Integer(square.zeroX);
		this.zeroY = new Integer(square.zeroY);		
		this.print = new Boolean(square.print);		
		this.printAll = new Boolean(square.printAll);		
		this.drawEverytime= new Boolean(square.drawEverytime);		
		this.moves.addAll(square.moves);
		this.realMoves.addAll(square.realMoves);
		this.tab = new int[square.size][square.size];

		for (int i = 0; i < square.size; i++) {
			for (int j = 0; j < square.size; j++) {
				this.tab[i][j] = square.tab[i][j];
			}
		}	
	}
	

	
	
	Square(int size){
		int number = 0;
		this.size = size;
		this.tab = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				this.tab[i][j] = number++;
			}
		}
	}
	
	public void custom(int [] cases){
		int x = 0;
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if(cases[x] == this.zero) {
					this.zeroX = i;
					this.zeroY = j;
				}
				this.tab[i][j] = cases[x++];
			}
		}

	}
	
	public void shuffle(int times) {
		for (int i = 0; i < times; i++) {
			int rand = new Random().nextInt(4);
			if(rand==0) this.moveUp();
			else
			if(rand==1) this.moveDown();
			else
			if(rand==2) this.moveRight();
			else
			if(rand==3) this.moveLeft();
		}
	}
	
	public void randomize() { // creates impossible squares
		ArrayList<Integer> list = new ArrayList<>();
		
		for (int i = 0; i < this.size*this.size; i++) {
			list.add(i);
		}
		while(!list.isEmpty()) {
			for (int i = 0; i < this.size; i++) {
				for (int j = 0; j < this.size; j++) {
					
					int rand = new Random().nextInt(list.size());
					if(list.get(rand) == 0) {
						this.zeroX = i;
						this.zeroY = j;
						}
					this.tab[i][j] = list.get(rand);
					list.remove(rand);
				}
			}
		}
		
		System.out.println("pos of 0:\n x,y: ("+this.zeroX +"," + this.zeroY+")" );
	}
	
	public boolean isSolved() {
		int number = 0;
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (this.tab[i][j] != number) {
				//	if(printAll) System.out.println("Not Solved!");
					return false;
				}
				number++;
			}
		}
		if(printAll) System.out.println("Solved Square!");
		return true;
	}
	
	public boolean moveUp() {
		for (int i = 0; i < this.size; i++) {
			if(tab[0][i] == this.zero) {
				if(print) System.out.println("can't Up");
				return false;
			}
		}

		this.tab[this.zeroX][this.zeroY] = this.tab[this.zeroX-1][this.zeroY];
		this.tab[this.zeroX-1][this.zeroY] = this.zero;
		this.zeroX--;
		if(printAll) System.out.println("Up");
		if(drawEverytime) this.draw();
		return true;
	}
	
	public boolean moveDown() {
		for (int i = 0; i < this.size; i++) {
			if(tab[this.size-1][i] == this.zero) {
				if(print) System.out.println("can't Down");
				return false;
			}
		}

		this.tab[this.zeroX][this.zeroY] = this.tab[this.zeroX+1][this.zeroY];
		this.tab[this.zeroX+1][this.zeroY] = this.zero;
		this.zeroX++;
		if(printAll) System.out.println("Down");
		if(drawEverytime) this.draw();
		return true;
	}
	
	public boolean moveLeft() {
		for (int i = 0; i < this.size; i++) {
			if(tab[i][0] == this.zero) {
				if(print) System.out.println("can't Left");
				return false;
			}
		}		
		
		this.tab[this.zeroX][this.zeroY] = this.tab[this.zeroX][this.zeroY-1];
		this.tab[this.zeroX][this.zeroY-1] = this.zero;
		this.zeroY--;
		if(printAll) System.out.println("Left");
		if(drawEverytime) this.draw();
		return true;
	}

	public boolean moveRight() {
		for (int i = 0; i < this.size; i++) {
			if(tab[i][this.size-1] == this.zero) {
				if(print) System.out.println("can't Right");
				return false;
			}
		}

		this.tab[this.zeroX][this.zeroY] = this.tab[this.zeroX][this.zeroY+1];
		this.tab[this.zeroX][this.zeroY+1] = this.zero;
		this.zeroY++;
		if(printAll) System.out.println("Right");
		if(drawEverytime) this.draw();
		return true;
	}
	
	public int emptyMiddle3(int i, int j){
		switch (this.tab[i][j]) {
		
		case 0: return 1;
		case 1: return 2;
		case 2: return 3;
		case 3: return 8;
		case 4: this.zeroX= 1; this.zeroY= 1; this.zero = 4; return 0;
		case 5: return 4;
		case 6: return 7;
		case 7: return 6;
		case 8: return 5;

		}
		return -1;
	}
	

	public void draw(boolean emptyInMiddleForSize3only) {
		
		if(emptyInMiddleForSize3only && this.size!=3) {
			emptyInMiddleForSize3only = false;
			System.out.println("size is not 3, using default layout");
		}
		
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				int value;
				if(!emptyInMiddleForSize3only)
					value = this.tab[i][j];
				 else value = emptyMiddle3(i,j);	
				
				if(value>9||this.size<4) {
					if(value == this.zero)
						System.out.print(" [ ]");	
					else
						System.out.print(" ["+value+"]");
				}else {
					if(value == this.zero)
						System.out.print(" [  ]");
					else
						System.out.print(" [ "+value+"]");
				}

			}
			System.out.println();
		}
	}
	public void draw() {
		draw(false);
	}
	
	public void print(boolean all) {
		this.print = true;
		if (all) this.printAll = true;
	}
	
	public void print() {
		print(false);
	}
	
	public void drawIter() {
		this.drawEverytime = true;
	}
	
	public int wrongCases() {
		// the original heuristic (not the most efficient)
		int base  = this.size;
		// to get the real value, line *base(size) + remaining ,( converts base(size) to decimal )
		int number = 0;
		for (int i = 0; i < base; i++) {
			for (int j = 0; j < base; j++) {
				if (this.tab[i][j] != i*size+j) {
					number++;
				}
			}
		}
//		if(printAll) System.out.println(number+" wrong!");
		return number;
	}
	
	public void progressBar() {
		System.out.print("[");		
		for (int j = 0; j < this.wrongCases(); j++) {
			System.out.print("..");
		}
		System.out.print("<");
		for (int j = this.wrongCases(); j < this.size*this.size; j++) {
			System.out.print("==");
		}
		System.out.print("]");
	}
	
	public boolean equals(Square square) {
		
		for (int i = 0; i < square.size; i++) {
			for (int j = 0; j < square.size; j++) {
				if(this.tab[i][j] != square.tab[i][j]) return false;
			}
		}
		return true;
	}
	
	
	public void moveFromList(ArrayList<String> list) {
		// this method does not verify the rules, if it can not, then it passes to the next
		for (String string : list) {
			switch(string) {
			case "up":
				this.moveUp();
				break;
			case "down":
				this.moveDown();
				break;
			case "left":
				this.moveLeft();
				break;
			case "right":
				this.moveRight();
				break;
			default : 
				System.out.println("Please use the square moves list, or respect the format!");
				break;
			}	
		}
	}
	
	public int verifyList(ArrayList<String> list) {
		// checks if this square can make the move.
		// otherwise it returns the index of the impossible move
		// if == size of list, then the list is admissible
		
		// since the moveX methods return a boolean if possible, we use them here
		// but since we just verify, we create a new Square to not alter the original.
		
		// this does not check if the move is useless like an "up" "down" sequence
		Square temp = new Square(this);
		
		int index = 0;
		for (String string : list) {
			switch(string) {
			case "up":
				if(!temp.moveUp())
					return index;
				break;
			case "down":
				if(!temp.moveDown())
					return index;
				break;
			case "left":
				if(!temp.moveLeft())
					return index;
				break;
			case "right":
				if(!temp.moveRight())
					return index;
				break;
			default : 
				return -1;
			}
			index++;
		}
		temp = null;
		return index;
	}
	
	public boolean correctList(ArrayList<String> list) {
		// checks if the list is admissible
		if(verifyList(list)!=list.size())
			return false;
		return true;  //else, if verify == size, then all are correct
	}
	

	public ArrayList<Float> randomRealSolution(int length) {
		ArrayList<Float> list = new ArrayList<Float>();
		
		for (int i = 0; i < length; i++) {
			float rand = new Random().nextFloat();
			list.add(rand);
		}
		
		return list;
	}
	public ArrayList<String> randomSolution(int length) {

		/* generates a random admissible solution
		 * this should create a random sequence of possible moves
		 * it should not however, generate consecutively opposite moves like {..,"up","down",...}
		 * previous variable condition corrects this problem
		 * */
		
		ArrayList<String> list = new ArrayList<String>();
		// new Square to not alter the original
		Square temp = new Square(this);
		
		String previous = "";
		// TODO: use this previous for generating admissible solutions when moving the square (portion)
		while(list.size() < length) {
			
			if(!list.isEmpty())
				previous = list.get(list.size()-1); // last from the list

			int rand = new Random().nextInt(4);

			if (rand == 0 && previous != "down") {
				if(temp.moveUp())
					list.add("up");
			} else {
			if (rand == 1 && previous != "up") {
				if(temp.moveDown())
					list.add("down");
			} else {
			if (rand == 2 && previous != "left") {
				if(temp.moveRight())
					list.add("right");
			} else {
			if (rand == 3 && previous != "right") {
				if(temp.moveLeft())
					list.add("left");
			} } } }
			
		}
		return list;			
	}
	
	public void testSolution(ArrayList<String> list) {
		if(correctList(list))
			System.out.println(list+"\nis a correct list");
		else
		System.out.println("can't move with:"+verifyList(list)+" ("+list.get(verifyList(list))+")");
		
	}
public void moveSquare(String string) {
		
			if(!this.isSolved())
			switch(string) {
			case "up":
				this.moveUp();
				this.moves.add(string);
				break;
			case "down":
				this.moveDown();
				this.moves.add(string);
				break;
			case "left":
				this.moveLeft();
				this.moves.add(string);
				break;
			case "right":
				this.moveRight();
				this.moves.add(string);
				break;
			}
		}	
	
	
	public void moveSquare(ArrayList<String> list) {
		
		for (String string : list) {
			if(this.isSolved())
				break;
			switch(string) {
			case "up":
				this.moveUp();
				this.moves.add(string);
				break;
			case "down":
				this.moveDown();
				this.moves.add(string);
				break;
			case "left":
				this.moveLeft();
				this.moves.add(string);
				break;
			case "right":
				this.moveRight();
				this.moves.add(string);
				break;
			}
		}
	}
	
	public void moveSquareOnceByReal(float move) {
		Square temp = new Square(this);
		ArrayList<String> legalMoves = new ArrayList<String>();
		ArrayList<Float> portions = new ArrayList<Float>();
		
		if(temp.moveUp()) {
			legalMoves.add("up");
			temp = new Square(this);
			}
		if(temp.moveDown()) {
			legalMoves.add("down");
			temp = new Square(this);
			}
		if(temp.moveLeft()) {
			legalMoves.add("left");
			temp = new Square(this);
			}
		if(temp.moveRight()) {
			legalMoves.add("right");
			temp = new Square(this);
			}

		for (int j = 1; j < legalMoves.size(); j++) {
			portions.add((float)(((float)j)/(float)legalMoves.size()));
		}
		
		if(move<portions.get(0)) {			
			moveSquare(legalMoves.get(0));
		}	
		for (int j = 1; j < portions.size(); j++) {
			if(portions.get(j-1)<=move&&move<portions.get(j)) {
				moveSquare(legalMoves.get(j));
			}
		}
		if(portions.get(portions.size()-1)<=move) {
			moveSquare(legalMoves.get(portions.size()));
		}
		
	}
	
	
	public void moveSquareFromReal() {
		// solves the problem in genetic algorithm.
		// this is used first, then adds the real values of moves later
		// a bit redundant but I am way too tired to think properly
		
		int i = 0;

//		System.out.println("xxxxxxxxx "+  legalMoves +portions);

		String previous = "";
		
		while(i<this.realMoves.size()) {
			Square temp = new Square(this);
			ArrayList<String> legalMoves = new ArrayList<String>();
			ArrayList<Float> portions = new ArrayList<Float>();
			
			if(!this.moves.isEmpty())
				previous = moves.get(i-1); // previous according

			if(temp.moveUp() ) {
				legalMoves.add("up");
				temp = new Square(this);
				}
			if(temp.moveDown()) {
				legalMoves.add("down");
				temp = new Square(this);
				}
			if(temp.moveLeft() ) {
				legalMoves.add("left");
				temp = new Square(this);
				}
			if(temp.moveRight() ) {
				legalMoves.add("right");
				temp = new Square(this);
				}
			
//			if(!legalMoves.contains(this.moves.get(i))) {
				
			for (int j = 1; j < legalMoves.size(); j++) {
				portions.add((float)(((float)j)/(float)legalMoves.size()));
			}
			
				if(this.realMoves.get(i)<portions.get(0)) {			
					moveSquare(legalMoves.get(0));
				}	
				for (int j = 1; j < portions.size(); j++) {
					if(portions.get(j-1)<=this.realMoves.get(i)&&this.realMoves.get(i)<portions.get(j)) {
						moveSquare(legalMoves.get(j));
					}
				}
				if(portions.get(portions.size()-1)<=this.realMoves.get(i)) {
					moveSquare(legalMoves.get(portions.size()));
				}
			
			
//			}else {
//				if(!this.moves.isEmpty())
//				moveSquare(this.moves.get(i));
//			}
			
			i++;
		}
		
		
		
	}
	
	
	public void convertRealtoMoves() {
		// solves the problem in genetic algorithm.
		// this is used first, then adds the real values of moves later
		// a bit redundant but I am way too tired to think properly
		
		int i = 0;
		Square temp = new Square(this);
		ArrayList<String> legalMoves = new ArrayList<String>();
		ArrayList<Float> portions = new ArrayList<Float>();
		
		if(temp.moveUp())
			legalMoves.add("up");
		if(temp.moveDown())
			legalMoves.add("Down");
		if(temp.moveLeft())
			legalMoves.add("left");
		if(temp.moveRight())
			legalMoves.add("right");
		
		for (int j = 1; j < legalMoves.size(); j++) {
			portions.add((float)(((float)j)/(float)legalMoves.size()));
		}
		
		
		while(i<this.realMoves.size()) {
			
			for (int j = 0; j < portions.size(); j++) {
				if(this.realMoves.get(i)<portions.get(j))
						this.moves.add(legalMoves.get(j));
				
			}
			this.moves.add(legalMoves.get(portions.size()));
			
			
			i++;
		}
		
	}
	
	
	public int howFarIsThisCase(int i, int j) {
		// separated from sumOfDistances() just to have the option 
		int x = 0;
		int y = 0;
		int distance = 0;
		int base = this.size; // the base of the matrix 3*3: 00 01 02 10 11 ...
		// "xy" is the current value in the case, "ij" is the correct value

				// converts a decimal to its base(size) equivalent
		if(this.tab[i][j]!=0) {
				x = this.tab[i][j]/base ; 
				y = this.tab[i][j]%base ;
				distance = Math.abs(i-x) + Math.abs(j-y);
		}		
// we will use a little exception to the ZERO to not limit our model.
		
		return distance;
	}
	
	
	 public int sumOfDistances() {
		 // this is a better heuristic
		 int totalSum = 0;
		 for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				totalSum += this.howFarIsThisCase(i,j);
			}
		}
		 return totalSum;
	 }


	
	
}
