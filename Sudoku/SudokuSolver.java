import java.util.ArrayList;


public class SudokuSolver implements ISudokuSolver {

	int[][] puzzle;
	int size;
	ArrayList<ArrayList<Integer>> D; //= new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> R;
	public int[][] getPuzzle() {
		return puzzle;
	}

	public void setValue(int col, int row, int value) {
		puzzle[col][row] = value;
	}

	public void setup(int size1) {
		size = size1;
		System.out.println(size1);
		puzzle = new int[size*size][size*size];
		D = new ArrayList<ArrayList<Integer>>();
		for (int j = 0; j < size*size;j++){
			System.out.println("WOWOWOWOWOW");
			ArrayList<Integer> tempList = new ArrayList<>();
			D.add(tempList);
		}
	

		for (ArrayList<Integer> i : D){

			for (int j = 0; j < size*size;j++){
				System.out.println("WOWOWOWOWOW");
				i.add(0);
			}

			System.out.println("owo" + i.size());
		}
		for (int i = 0; i<size*size; i++){
			for(int j = 0; j<size*size; j++){
				if (j == i){
					setValue(i, j, j+1);
				} else {
					setValue(i, j, 0);
				}
			}
		}

		
		
	}


	public boolean solve() {
		//D = GetAssignment(puzzle).;
		ArrayList<Integer> asn = GetAssignment(puzzle);
		
		//INITIAL_FC
		//INITIAL_FC(asn);
		//FC
		System.out.println("We start :3");
		
		int[][] uwu = GetPuzzles(FC(asn));
		for (int i = 0; i<size*size; i++){
			for(int j = 0; j<size*size; j++){
					setValue(i, j, uwu[i][j]);
			}
		}
		return true;
	}

	public void readInPuzzle(int[][] p) {
		puzzle = p;
	}
	
	
		//---------------------------------------------------------------------------------
		//YOUR TASK:  Implement FC(asn)
		//---------------------------------------------------------------------------------
		public ArrayList<Integer> FC(ArrayList<Integer> asn) {
			
			
			Boolean fin = true;
			int X = 0;

			ArrayList<ArrayList<Integer>> D_old = new ArrayList<>();

			for (ArrayList<Integer> Rows : D){
				
				ArrayList<Integer> tempList = new ArrayList<>(); 
				
				for (int i : Rows){
					int tempI = i;
					tempList.add(tempI);
				}

				D_old.add(tempList);
			}
			
			System.out.println("hahahahahaha");
			for (int i = 0; i < asn.size(); i++){
				System.out.println(asn.get(i)+"hihihihihiih");
				if(asn.get(i) == 0){
					X = asn.get(i);
					System.out.println("huhuhuhuhuhu");
					fin = false;
				}
				
				if (fin == true){
					return asn;
				}
				
			}
			
			if(fin == false){
				System.out.println("hahahahahaha");
				for (int i = 1; i<10; i++){
	
					if(AC_FC(X, i)){
					
						asn.add(X, i);
						R = FC(asn);
					System.out.println("brrrrr");

					if(R.size() != 9999){
					System.out.println("This works! :33333");
						return R;
					}
					
					asn.add(X, 0);
					D = D_old;
					}else{
					D = D_old;
					}
				}
		
			}

			ArrayList<Integer> fail = new ArrayList<>(9999);
			System.out.println("YOU HAVE FAILED ME JACK");
			return fail; //failure
		}

	

		
		//---------------------------------------------------------------------------------
		// CODE SUPPORT FOR IMPLEMENTING FC(asn)
		//
		// It is possible to implement FC(asn) by using only AC_FC function from below.
		// 
		// If you have time, I strongly reccomend that you implement AC_FC and REVISE from scratch
		// using only implementation of CONSISTENT algorithm and general utility functions. In my opinion
		// by doing this, you will gain much more from this exercise.
		//
		//---------------------------------------------------------------------------------
		
		
	
		//------------------------------------------------------------------
		//				AC_FC
		//
		// Implementation of acr-consistency for forward-checking AC-FC(cv).
		// This is a key component of FC algorithm, and the only function you need to 
		// use in your FC(asn) implementation
		//------------------------------------------------------------------
		public boolean AC_FC(Integer X, Integer V){
			//Reduce domain Dx
			D.get(X).clear();
			D.get(X).add(V);
			
			//Put in Q all relevant Y where Y>X
			ArrayList<Integer> Q = new ArrayList<Integer>(); //list of all relevant Y
			int col = GetColumn(X);
			int row = GetRow(X);
			int cell_x = row / size;
			int cell_y = col / size;
			
			//all variables in the same column
			for (int i=0; i<size*size; i++){
				if (GetVariable(i,col) > X) {
					Q.add(GetVariable(i,col));
				}
			}
			//all variables in the same row
			for (int j=0; j<size*size; j++){
				if (GetVariable(row,j) > X) {
					Q.add(GetVariable(row,j));
				}
			}
			//all variables in the same size*size box
			for (int i=cell_x*size; i<=cell_x*size + 2; i++) {
				for (int j=cell_y*size; j<=cell_y*size + 2; j++){
					if (GetVariable(i,j) > X) {
						Q.add(GetVariable(i,j));
					}
				}
			}
		
			//REVISE(Y,X)
			boolean consistent = true;
			while (!Q.isEmpty() && consistent){
				Integer Y = (Integer) Q.remove(0);
				if (REVISE(Y,X)) {
					consistent = !D.get(Y).isEmpty();
				}
			}
			return consistent;
		}	
		
		
		//------------------------------------------------------------------
		//				REVISE 
		//------------------------------------------------------------------
		public boolean REVISE(int Xi, int Xj){
			Integer zero = 0;
			
			if(Xi >= 0 && Xj >=0 && Xi < size*size*size*size && Xj <size*size*size*size && Xi != Xj){
			
			boolean DELETED = false;

			System.out.println(Xi);
			System.out.println(Xj);
			ArrayList<Integer> Di = D.get(Xi);
			ArrayList<Integer> Dj = D.get(Xj);	
			
			for (int i=0; i<Di.size(); i++){
				Integer vi = (Integer) Di.get(i);
				ArrayList<Integer> xiEqVal = new ArrayList<Integer>(size*size*size*size);	
				for (int var=0; var<size*size*size*size; var++){
					xiEqVal.add(var,zero);				
				}

				xiEqVal.set(Xi,vi);
				
				boolean hasSupport = false;	
				for (int j=0; j<Dj.size(); j++){
					Integer vj = (Integer) Dj.get(j);
					if (CONSISTENT(xiEqVal, Xj, vj)) {
						hasSupport = true;
						break;
					}
				}
				
				if (hasSupport == false) {
					Di.remove((Integer) vi);
					DELETED = true;
				}
				
			}
			
			return DELETED;
		}
			return false;
	}
		

		
		//------------------------------------------------------------------
		//CONSISTENT: 
		//
		//Given a partiall assignment "asn"  checks whether its extension with 
		//variable = val is consistent with Sudoku rules, i.e. whether it violates
		//any of constraints whose all variables in the scope have been assigned. 
		//This implicitly encodes all constraints describing Sudoku.
		//
		//Before it returns, it undoes the temporary assignment variable=val
		//It can be used as a building block for REVISE and AC-FC
		//
		//NOTE: the procedure assumes that all assigned values are in the range 
		// 		{0,..,9}. 
		//-------------------------------------------------------------------
		public boolean CONSISTENT(ArrayList<Integer> asn, Integer variable, Integer val) {
			Integer v1,v2;
			
			//variable to be assigned must be clear
			assert(asn.get(variable) == 0);
			asn.set(variable,val);

			//alldiff(col[i])
		 	for (int i=0; i<size*size; i++) {
		 		for (int j=0; j<size*size; j++) {
		 			for (int k=0; k<size*size; k++) {
			 			if (k != j) {
			 				v1 = (Integer) asn.get(GetVariable(i,j));
			 				v2 = (Integer) asn.get(GetVariable(i,k));
				 			if (v1 != 0 && v2 != 0 && v1.compareTo(v2) == 0) {
				 				asn.set(variable,0);
				 				return false;
				 			}
				 		}
		 			}
		 		}
		 	}
		

		 	
		 	//alldiff(row[j])
		 	for (int j=0; j<size*size; j++) {
		 		for (int i=0; i<size*size; i++) {
		 			for (int k=0; k<size*size; k++) {
			 			if (k != i) {
			 				v1 = (Integer) asn.get(GetVariable(i,j));
			 				v2 = (Integer) asn.get(GetVariable(k,j));
				 			if (v1 != 0 && v2 != 0 && v1.compareTo(v2) == 0) {
				 				asn.set(variable,0);			 				
				 				return false;
				 			}
			 			}
		 			}
		 		}
		 	}
		 	

		 	//alldiff(block[size*i,size*j])
		 	for (int i=0; i<size; i++) {
		 		for (int j=0; j<size; j++) {
		 			for (int i1 = 0; i1<size; i1++) {
		 				for (int j1=0; j1<size; j1++) {
		 					int var1 = GetVariable(size*i + i1, size*j + j1);
		 		 			for (int i2 = 0; i2<size; i2++) {
		 		 				for (int j2=0; j2<size; j2++) {
		 		 					int var2 = GetVariable(size*i+i2, size*j + j2);
		 		 					if (var1 != var2) {
		 				 				v1 = (Integer) asn.get(var1);
		 				 				v2 = (Integer) asn.get(var2);
		 		 			 			if (v1 != 0 && v2 != 0 && v1.compareTo(v2) == 0) {
		 					 				asn.set(variable,0);	 		 			 				
		 					 				return false;
		 					 			}
		 		 					}
		 		 				}
		 		 			}
	 
		 				}
		 			}
		 		}
		 	}

			asn.set(variable,0);
			return true;
		}	
		
		

	
		//------------------------------------------------------------------
		//						INITIAL_FC
		//------------------------------------------------------------------
		public boolean INITIAL_FC(ArrayList<Integer> anAssignment) {
			//Enforces consistency between unassigned variables and all 
			//initially assigned values; 
			for (int i=0; i<anAssignment.size(); i++){
				Integer V = anAssignment.get(i);
				if (V != 0){
					ArrayList<Integer> Q = GetRelevantVariables(i);
					boolean consistent = true;
					while (!Q.isEmpty() && consistent){
						Integer Y = Q.remove(0);
						if (REVISE(Y,i)) {
							consistent = !D.get(Y).isEmpty();
						}
					}	
					if (!consistent) return false;
				}
			}
			
			return true;
		}
		
		
	
		
		//------------------------------------------------------------------
		//						GetRelevantVariables
		//------------------------------------------------------------------
		public ArrayList<Integer> GetRelevantVariables(Integer X){
			//Returns all variables that are interdependent of X, i.e. 
			//all variables involved in a binary constraint with X
			ArrayList<Integer> Q = new ArrayList<Integer>(); //list of all relevant Y
			int col = GetColumn(X);
			int row = GetRow(X);
			int cell_x = row / size;
			int cell_y = col / size;
			
			//all variables in the same column
			for (int i=0; i<size*size; i++){
				if (GetVariable(i,col) != X) {
					Q.add(GetVariable(i,col));
				}
			}
			//all variables in the same row
			for (int j=0; j<size*size; j++){
				if (GetVariable(row,j) != X) {
					Q.add(GetVariable(row,j));
				}
			}
			//all variables in the same size*size cell
			for (int i=cell_x*size; i<=cell_x*size + 2; i++) {
				for (int j=cell_y*size; j<=cell_y*size + 2; j++){
					if (GetVariable(i,j) != X) {
						Q.add(GetVariable(i,j));
					}
				}
			}	
			
			return Q;
		}
		
		



		//------------------------------------------------------------------
		// Functions translating between the puzzle and an assignment
		//-------------------------------------------------------------------
		public ArrayList<Integer> GetAssignment(int[][] p) {
			ArrayList<Integer> asn = new ArrayList<Integer>();
			for (int i=0; i<size*size; i++) {
				for (int j=0; j<size*size; j++) {
					asn.add(GetVariable(i,j), p[i][j]);
					if (p[i][j] != 0){
							//restrict domain
							System.out.println("Size +" + D.size());
							System.out.println("Size +" + D.get(i).size());
							//D.get(i).clear();
							D.get(i).add(j, p[i][j]);
						}
				}
			}
			return asn;
		}	
		
	
		public int[][] GetPuzzles(ArrayList<Integer> asn) {
			int[][] p = new int[size*size][size*size];
			for (int i=0; i<size*size; i++) {
				for (int j=0; j<size*size; j++) {
					Integer val = (Integer) asn.get(GetVariable(i,j));
					p[i][j] = val.intValue();
				}
			}
			return p;
		}

	
		//------------------------------------------------------------------
		//Utility functions
		//-------------------------------------------------------------------
		public int GetVariable(int i, int j){
			if (i<size*size && j<size*size && i>=0 && j>=0 && size*size < 10);
			{	
			System.out.println(i*size*size + j);
			return (i*size*size + j);
			}	
		}	
		
		
		public int GetRow(int X){
			return (X / (size*size)); 	
		}	
		
		public int GetColumn(int X){
			return X - ((X / (size*size))*size*size);	
		}	
		
		
		
		
		
}
