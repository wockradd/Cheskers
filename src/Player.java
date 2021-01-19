import java.util.ArrayList;
import java.util.Random;

public class Player {
	Board board;
	ArrayList<Move> moves;
	Random r;
	
	public Player() {
		board = new Board(false);
		moves =  new ArrayList<Move>();
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
		System.out.println(moves.size());
		for(int i=0 ; i<moves.size() ; i++) {
			moves.get(i).printMove();
		}

	}


	
	
	public void makeMove() {
		
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
		if(m.promoteTo != null) {
			System.out.println("Proomoting");
			board.getSquares()[m.toI][m.toJ].removePiece();
			switch(m.promoteTo) {
				case King:
					board.getSquares()[m.toI][m.toJ].setPiece(new King(this.board, true, m.toI, m.toJ));
					break;
				case Knight:
					board.getSquares()[m.toI][m.toJ].setPiece(new Knight(this.board, true, m.toI, m.toJ));
					break;
				case Bishop:
					board.getSquares()[m.toI][m.toJ].setPiece(new Bishop(this.board, true, m.toI, m.toJ));
					break;
				default:
					System.err.println("opps");
					break;
			}
			
		}
		

		moves.clear();

	}
}
