import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Player {
	enum Type {Human,Random};
	Type type;
	Display display;
	private Board board;
	ArrayList<Move> moves;
	ArrayList<Board> allBoards;
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
		allBoards = new ArrayList<Board>();
		r = new Random();
	}
	
	
	public Board getBoard() {
		return board;
	}
	
	public void setBoard(Board newBoard) {
		board = newBoard;
	}
	
	public ArrayList<Move> getMoves(){
		return moves;
	}
	
	
	
	
	//return true if they've lost
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
	
	
	
	
	//populates this.moves with all the possible moves
	public void generateMoves() {
		moves.clear();
		
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

	}
	
	
	public Move pickMove() {
		//MINIMAX PICK
		float best = allBoards.get(0).evaluateBoard();
		int indexOfBest = 0;
		
		for(int i=1 ; i<allBoards.size() ; i++) {
			float currentEval = allBoards.get(i).evaluateBoard();
			if(currentEval > best) {
				best = currentEval;
				indexOfBest = i;
			}
		}
		Move m = moves.get(indexOfBest);
		
		
		//RANDOM PICK
		//Move m = moves.get(r.nextInt(moves.size()));
		
		
		
		
		System.out.println("picked move: \n");
		m.printMove();
		return m;
	}


	
	
	public Board makeMove(Move m) {
		
		//create a copy of the current board 
		//we then edit this copy instead of the global board
		Board newBoard = board.copyBoard();

		//remove any pieces the move got rid of
		if(m.taking != null) {
			for(int j=0 ; j<8 ; j++) {
				for(int i=0 ; i<8 ; i++) {
					try {
						if(m.taking.contains(board.getSquares()[i][j].getPiece())) {
							newBoard.getSquares()[i][j].removePiece();
						}
					}catch(NullPointerException npe) {}
				}
			}
		}

		//move the piece to its new position
		newBoard.getSquares()[m.toI][m.toJ].setPiece(newBoard.getSquares()[m.fromI][m.fromJ].getPiece());
		newBoard.getSquares()[m.fromI][m.fromJ].removePiece();

		

		//promote any pieces the move promoted
		if(m.promoteTo != null) {
			System.out.println("Promoting");
			newBoard.getSquares()[m.toI][m.toJ].removePiece();
			switch(m.promoteTo) {
			case King:
				newBoard.getSquares()[m.toI][m.toJ].setPiece(new King(newBoard, true, m.toI, m.toJ));
				break;
			case Knight:
				newBoard.getSquares()[m.toI][m.toJ].setPiece(new Knight(newBoard, true, m.toI, m.toJ));
				break;
			case Bishop:
				newBoard.getSquares()[m.toI][m.toJ].setPiece(new Bishop(newBoard, true, m.toI, m.toJ));
				break;
			default:
				System.err.println("opps");
				break;
			}
		}

		return newBoard;


	}


	public ArrayList<Board> getAllPossibleBoards(){
		allBoards.clear();
				for(Move m:moves) {
					allBoards.add(makeMove(m));
				}
		return allBoards;
	}
}
