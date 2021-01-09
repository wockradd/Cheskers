import java.util.ArrayList;
import java.util.Random;

public class Player {
	Board board;
	boolean mandatoryMoveFound;
	Random r;
	
	public Player() {
		board = new Board(false);
		mandatoryMoveFound = false;
		r = new Random();
	}
	
	
	public Board getBoard() {
		return board;
	}
	
	public void setBoard(Board newBoard) {
		board = newBoard;
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
	
	
	
	
	
	public void generateMoves() {
		
		//look through pawn and king moves
		for(int j=0 ; j<8 ; j++) {
			for(int i=0 ; i<8 ; i++) {
				try {
					if(board.getSquares()[i][j].getPiece().getMine()) {
						if(board.getSquares()[i][j].getPiece().type == Piece.Type.Pawn || board.getSquares()[i][j].getPiece().type == Piece.Type.King) {
							board.getSquares()[i][j].getPiece().generateMoveList();
							if(board.getSquares()[i][j].getPiece().mustMove) {
								mandatoryMoveFound = true;
								
							}
						}
					}
				}catch(NullPointerException npe) {}
			}
		}
		
		//look through knight and bishop moves only if theres not a mandatory pawn or king move
		if(!mandatoryMoveFound) {
			for(int j=0 ; j<8 ; j++) {
				for(int i=0 ; i<8 ; i++) {
					try {
						if(board.getSquares()[i][j].getPiece().getMine()) {
							if(board.getSquares()[i][j].getPiece().type == Piece.Type.Knight || board.getSquares()[i][j].getPiece().type == Piece.Type.Bishop) {
								board.getSquares()[i][j].getPiece().generateMoveList();
							}
						}
					}catch(NullPointerException npe) {}
				}
			}
		}
	}
	
	
	
	
	
	public void makeMove() {
		ArrayList<Move> moves =  new ArrayList<Move>();

		//add all the possible moves to the move list
		for(int j=0 ; j<8 ; j++) {
			for(int i=0 ; i<8 ; i++) {
				try {
					if(board.getSquares()[i][j].getPiece().getMine()) {
						moves.addAll(board.getSquares()[i][j].getPiece().moves);
					}
				}catch(NullPointerException npe) {}
			}
		}

		//remove all moves that dont capture pieces
		if(mandatoryMoveFound) {
			System.out.println("mandatory move");
			for(int i=0 ; i<moves.size() ; i++) {
				if(moves.get(i).taking == null) {
					System.out.println("Removing");
					moves.get(i).printMove();
					moves.remove(i);
					i--;
				}else {
					System.out.println("all good");
					moves.get(i).printMove();
				}
			}
		}
		
		System.out.println("Possible moves:");
		for(int i=0 ; i<moves.size() ; i++) {
			moves.get(i).printMove();
		}
		System.out.println("\n\nPicked move:");
		
		//pick a random move
		Move m   = moves.get(r.nextInt(moves.size()));
		m.printMove();

		//make it
		board.getSquares()[m.toI][m.toJ].setPiece(board.getSquares()[m.fromI][m.fromJ].getPiece());
		board.getSquares()[m.fromI][m.fromJ].removePiece();
		if(m.taking != null) {
			for(int j=0 ; j<8 ; j++) {
				for(int i=0 ; i<8 ; i++) {
					try {
						if(m.taking.contains(board.getSquares()[i][j].getPiece())) {
							board.getSquares()[i][j].removePiece();
						}
					}catch(NullPointerException npe) {}
				}
			}
		}
		
		mandatoryMoveFound = false;
	}
}
