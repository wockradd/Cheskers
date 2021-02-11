import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Player {
	private static final int MINIMAX_DEPTH = 0;
	
	enum Type {Human,Random,Minimax};
	Type type;
	Display display;
	private Board board;
	private ArrayList<Move> moves;
	boolean black;
	Random r;
	Scanner s;
	
	public Player(Type type, boolean black) {
		this.black = black;
		this.type = type;
		if(this.type == Type.Human) {
			display = new Display(this.black);
		}
		board = new Board(false);
		moves =  new ArrayList<Move>();
		r = new Random();
		s = new Scanner(System.in);
	}
	
	/** getter
	 */
	public Board getBoard() {
		return board;
	}
	
	/** setter
	 */
	public void setBoard(Board newBoard) {
		board = newBoard;
	}
	
	/** getter
	 */
	public ArrayList<Move> getMoves(){
		return moves;
	}
	
	/** setter
	 */
	public void setMoves(ArrayList<Move> newMoves) {
		moves = newMoves;
	}
	
	
	
	//TODO update this to handle stalemates too
	/** return true if the player has no kings left
	 */
	public boolean hasLost() {
		int numOfKings = 0;
		for(int j=0 ; j<8 ; j++) {
			for(int i=0 ; i<8 ; i++) {
				try {
					if(board.getSquares()[i][j].getPiece().getMine() && board.getSquares()[i][j].getPiece().type == Piece.Type.King) {
						numOfKings++;
					}
				}catch(NullPointerException npe) {}
			}
		}
		return numOfKings == 0;
	}
	
	
	
	
	/**give me a board, i return all valid moves for that board
	 */
	public ArrayList<Move> generateMoves(Board board) {
		ArrayList<Move> moves = new ArrayList<Move>();
		
		
		ArrayList<Piece> mandatoryCaptures = new ArrayList<Piece>();
		boolean mandatoryMoveFound = false;
		
		//check each piece, if it has moves add these moves to the players move list
		//also sets the mandatoryMove flag if it finds one
		for(int j=0 ; j<8 ; j++) {
			for(int i=0 ; i<8 ; i++) {
				try {
					if(board.getSquares()[i][j].getPiece().getMine()) {
						board.getSquares()[i][j].getPiece().generateMoveList();
						moves.addAll(board.getSquares()[i][j].getPiece().moves);
						
						if(board.getSquares()[i][j].getPiece().mustMove) {
							for(Move m: board.getSquares()[i][j].getPiece().moves) {
								mandatoryCaptures.add(m.taking.get(0));
							}
							mandatoryMoveFound = true;
						}

					}
				}catch(NullPointerException npe) {}
			}
		}


		//remove all moves that dont capture pieces if you found a mandatory move
		if(mandatoryMoveFound) {
			//System.out.println("mandatory move");
			//System.out.println(moves.size());
			for(int i=0 ; i<moves.size() ; i++) {
				try {
					if(!mandatoryCaptures.contains(moves.get(i).taking.get(0))) {
						//System.out.println("Removing");
						//moves.get(i).printMove();
						moves.remove(i);
						i--;
					}else {
						//System.out.println("all good");
						//moves.get(i).printMove();
					}
				}catch(NullPointerException npe) {
					//System.out.println("Removing");
					//moves.get(i).printMove();
					moves.remove(i);
					i--;
				}
				
			}
		}
		
		
//		System.out.println("Possible moves for the board:");
//		board.printBoard();
//		for(int i=0 ; i<moves.size() ; i++) {
//			System.out.println(i);
//			moves.get(i).printMove();
//		}
		
		return moves;

	}
	
	
	/** give me a board and list of moves, i pick my favorite move
	 */
	public Move pickMove(Board board, ArrayList<Move> allMoves) {
		Move m = null;
		long startTime = System.nanoTime();
		
		if(type ==  Type.Human) {
			
			//print the options
			for(int i=0  ;i<allMoves.size() ; i++) {
				System.out.println(i);
				allMoves.get(i).printMove();
			}
		     System.out.print("Pick a number: ");

		     //get the user input
		     int num = s.nextInt();
		     
		     //pick the move
		     m = allMoves.get(num);
			
		}else if(type == Type.Random) {
			m = allMoves.get(r.nextInt(allMoves.size()));
			
		}else if(type == Type.Minimax) {
			
			//MINIMAX
			//get all the possible boards
			ArrayList<Board> allBoards = getAllPossibleBoards(board,allMoves);
			float best = -Float.MAX_VALUE;
			int indexOfBest = 0;
			
			
			for(int i=1 ; i<allBoards.size() ; i++) {
				//do the actual calculations
				allBoards.get(i).setScore(minimax2(8, allBoards.get(i),false,-Float.MAX_VALUE, Float.MAX_VALUE));
				
				//update best score index
				if(allBoards.get(i).getScore() > best) {
					best = allBoards.get(i).getScore();
					indexOfBest = i;
				}else if(allBoards.get(i).getScore() > best && r.nextBoolean()) {
					best = allBoards.get(i).getScore();
					indexOfBest = i;
				}
			}
			
			//print hat you found
//			for(Board b:allBoards) {
//				System.out.println(b.getScore());
//				b.printBoard();
//			}
			 
		
			
			
			//pick the move that leads to this board
			m = allMoves.get(indexOfBest);
	
	
		}

		long endTime = System.nanoTime();
		System.out.println("Time: "+(endTime-startTime)/1000000 + "ms");
		
		return m;
	}
	
	
	
//	/**recursive alg that returns the minimax score for the given board
//	 */
//	//adapted from the code here https://gamedev.stackexchange.com/questions/31166/how-to-utilize-minimax-algorithm-in-checkers-game
//	public float minimax(int depth, Board currentBoard, boolean max) {
//		
//		//base case
//		if(depth == 0) {
//			return currentBoard.evaluateBoard();
//		}else {//step case
//			Board flipped = currentBoard.flipBoard();
//			ArrayList<Board> allBoards = getAllPossibleBoards(flipped, generateMoves(flipped));
//			//used to store the best score we find
//			
//			float score = 0;
//			if(max) {
//				score = -Float.MAX_VALUE;
//			}else {
//				score = Float.MAX_VALUE;
//			}
//			
//			for(Board b:allBoards) {
//				float newScore = minimax(depth-1, b,!max);
//				
//				if(max) {
//					if(newScore > score) {
//						score = newScore;
//					}
//				}else {
//					if(newScore < score) {
//						score = newScore;
//					}
//				}
//			    
//			}
//		
//			return score;
//		}
//	}
//	
//	
//
//	//THIS IS MINIMAX CODE - JUST ADHOC TO TEST MINIMAX VS MINIMAX
//	//MINIMAX
//	//get all the possible boards
//	ArrayList<Board> allBoards = getAllPossibleBoards(board,allMoves);
//	
//	//do the actual calculations
//	for(Board b:allBoards) {
//		b.setScore(minimax(4, b,false));
//	}
//	
//	//print hat you found
////	for(Board b:allBoards) {
////		System.out.println(b.getScore());
////		b.printBoard();
////	}
//	
//	
//	//allBoards now contains the scores for the 
//	
//	//find the best score
//	float best = allBoards.get(0).getScore();
//	int indexOfBest = 0;
//	for(int i=1 ; i<allBoards.size() ; i++) {
//		float currentEval = allBoards.get(i).getScore();
//		if(currentEval > best) {
//			best = currentEval;
//			indexOfBest = i;
//		}
//	}
//	
//	//pick the move that leads to this board
//	m = allMoves.get(indexOfBest);
	
	public float minimax2(int depth, Board currentBoard, boolean max, float alpha, float beta) {
		
		//base case
		if(depth == 0) {
			return currentBoard.evaluateBoardBetter();
			//return currentBoard.evaluateBoard();
		}else {//step case
			
			//flip board and get all children
			Board flipped = currentBoard.flipBoard();
			ArrayList<Board> allBoards = getAllPossibleBoards(flipped, generateMoves(flipped));
			
			if(max) {
				float value = -Float.MAX_VALUE;
				for(Board b : allBoards) {
					value = max(value,minimax2(depth-1, b, !max, alpha, beta));
					alpha = max(alpha,value);
					if(alpha >= beta) {
						break;
					}
				}
				return value;
			}else {
				float value = Float.MAX_VALUE;
				for(Board b : allBoards) {
					value = min(value,minimax2(depth-1, b, !max, alpha, beta));
					beta = min(beta,value);
					if(beta <= alpha) {
						break;
					}
				}
				return value;
			}
		}
	}

	
	
	


	
	/** give me a move and a board, i return the board that results from this move
	 */
	public Board makeMove(Move move, Board board) {
		Board newBoard = board.copyBoard();

		//remove any pieces the move got rid of
		if(move.taking != null) {
			for(int j=0 ; j<8 ; j++) {
				for(int i=0 ; i<8 ; i++) {
					try {
						if(move.taking.contains(board.getSquares()[i][j].getPiece())) {
							newBoard.getSquares()[i][j].removePiece();
						}
					}catch(NullPointerException npe) {}
				}
			}
		}

		//move the piece to its new position
		newBoard.getSquares()[move.toI][move.toJ].setPiece(newBoard.getSquares()[move.fromI][move.fromJ].getPiece());
		newBoard.getSquares()[move.fromI][move.fromJ].removePiece();

		

		//promote any pieces the move promoted
		if(move.promoteTo != null) {
			//System.out.println("Promoting");
			newBoard.getSquares()[move.toI][move.toJ].removePiece();
			switch(move.promoteTo) {
			case King:
				newBoard.getSquares()[move.toI][move.toJ].setPiece(new King(newBoard, true, move.toI, move.toJ));
				break;
			case Knight:
				newBoard.getSquares()[move.toI][move.toJ].setPiece(new Knight(newBoard, true, move.toI, move.toJ));
				break;
			case Bishop:
				newBoard.getSquares()[move.toI][move.toJ].setPiece(new Bishop(newBoard, true, move.toI, move.toJ));
				break;
			default:
				System.err.println("opps");
				break;
			}
		}

		return newBoard;


	}
	
	
	
	

	
	/** Give me a board and the list of moves, i return all the boards this can lead to 
	*/
	public ArrayList<Board> getAllPossibleBoards(Board board, ArrayList<Move> allMoves){
		ArrayList<Board> allBoards = new ArrayList<Board>();
				for(Move m:allMoves) {
					allBoards.add(makeMove(m, board));
				}
		return allBoards;
	}
	
	
	public float max(float f1, float f2) {
		if(f1 >= f2) {
			return f1;
		}else {
			return f2;
		}
	}
	
	public float min(float f1, float f2) {
		if(f1 <= f2) {
			return f1;
		}else {
			return f2;
		}
	}
}
