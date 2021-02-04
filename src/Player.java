import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Player {
	enum Type {Human,Random};
	Type type;
	Display display;
	private Board board;
	private ArrayList<Move> moves;
	boolean black;
	Random r;
	
	public Player(Type type, boolean black) {
		this.black = black;
		this.type = type;
		if(this.type == Type.Human) {
			display = new Display(this.black);
		}
		board = new Board(false);
		moves =  new ArrayList<Move>();
		r = new Random();
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
			System.out.println("mandatory move");
			System.out.println(moves.size());
			for(int i=0 ; i<moves.size() ; i++) {
				try {
					if(!mandatoryCaptures.contains(moves.get(i).taking.get(0))) {
						System.out.println("Removing");
						moves.get(i).printMove();
						moves.remove(i);
						i--;
					}else {
						System.out.println("all good");
						moves.get(i).printMove();
					}
				}catch(NullPointerException npe) {
					System.out.println("Removing");
					moves.get(i).printMove();
					moves.remove(i);
					i--;
				}
				
			}
		}
		
		
		System.out.println("Possible moves:");
		for(int i=0 ; i<moves.size() ; i++) {
			System.out.println(i);
			moves.get(i).printMove();
		}
		
		return moves;

	}
	
	/** give me a board and list of moves, i pick my favorite move
	 */
	public Move pickMove(Board board, ArrayList<Move> allMoves) {
		//MINIMAX PICK
		
		
		
//		ArrayList<Board> allBoards = getAllPossibleBoards(board,allMoves);
//		float best = allBoards.get(0).evaluateBoard();
//		int indexOfBest = 0;
//		
//		for(int i=1 ; i<allBoards.size() ; i++) {
//			float currentEval = allBoards.get(i).evaluateBoard();
//			if(currentEval > best) {
//				best = currentEval;
//				indexOfBest = i;
//			}
//		}
//		Move m = allMoves.get(indexOfBest);
		
		
		//RANDOM PICK
		Move m = allMoves.get(r.nextInt(allMoves.size()));
		
		
		
		
		System.out.println("picked move: \n");
		m.printMove();
		return m;
	}
	
	
	
	
	public void minimax() {
		
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
			System.out.println("Promoting");
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
		
		newBoard.setScore(newBoard.evaluateBoard());

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
}
