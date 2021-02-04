
public class Board{
	private Square[][] squares;
	
	public Board(boolean empty) {
		squares = new Square[8][8];
		initSquares();
		if(!empty)	setupPieces();
	}
	

	//sets up the board (without pieces)
	public void initSquares() {
		for(int j=0 ; j<8 ; j++) {
			for(int i=0 ; i<8 ; i++) {
				if((i+j)%2==0) {
					squares[i][j] = new Square(false);
				}else {
					squares[i][j] = new Square(true);
				}
			}
		}
	}
	

	//add all the pieces to the board
	public void setupPieces() {
		addPiece(7, 7, Piece.Type.King, true);
		addPiece(0, 0, Piece.Type.King, false);
		addPiece(4, 1, Piece.Type.Knight, true);
		addPiece(6, 1, Piece.Type.Knight, false);
		addPiece(2, 3, Piece.Type.Bishop, true);
		addPiece(3, 2, Piece.Type.Bishop, false);
		addPiece(2, 5, Piece.Type.Knight, true);
		addPiece(4, 5, Piece.Type.Knight, false);
		addPiece(6, 5, Piece.Type.Bishop, true);


		
		
		
//		//this players set up
//		addPiece(0, 5, Piece.Type.Pawn, true);
//		addPiece(1, 6, Piece.Type.Pawn, true);
//		addPiece(2, 5, Piece.Type.Pawn, true);
//		addPiece(3, 6, Piece.Type.Pawn, true);
//		addPiece(4, 5, Piece.Type.Pawn, true);
//		addPiece(5, 6, Piece.Type.Pawn, true);
//		addPiece(6, 5, Piece.Type.Pawn, true);
//		addPiece(7, 6, Piece.Type.Pawn, true);
//		addPiece(0, 7, Piece.Type.Bishop, true);
//		addPiece(2, 7, Piece.Type.King, true);
//		addPiece(4, 7, Piece.Type.King, true);
//		addPiece(6, 7, Piece.Type.Knight, true);
//	
//	
//		
//		//other players set up
//		addPiece(0, 1, Piece.Type.Pawn, false);
//		addPiece(1, 2, Piece.Type.Pawn, false);
//		addPiece(2, 1, Piece.Type.Pawn, false);
//		addPiece(3, 2, Piece.Type.Pawn, false);
//		addPiece(4, 1, Piece.Type.Pawn, false);
//		addPiece(5, 2, Piece.Type.Pawn, false);
//		addPiece(6, 1, Piece.Type.Pawn, false);
//		addPiece(7, 2, Piece.Type.Pawn, false);
//		addPiece(7, 0, Piece.Type.Bishop, false);
//		addPiece(5, 0, Piece.Type.King, false);
//		addPiece(3, 0, Piece.Type.King, false);
//		addPiece(1, 0, Piece.Type.Knight, false);
	}
	
	
	//add an individual piece to the board 
	public void addPiece(int i, int j, Piece.Type type, boolean mine) {
		switch(type) {
			case Pawn:
				squares[i][j].setPiece(new Pawn(this,mine,i,j));
				break;
			case Knight:
				squares[i][j].setPiece(new Knight(this,mine,i,j));
				break;
			case Bishop:
				squares[i][j].setPiece(new Bishop(this,mine,i,j));
				break;
			case King:
				squares[i][j].setPiece(new King(this,mine,i,j));
				break;
		}
	}


	//getter
	public Square[][] getSquares() {
		return squares;
	}
	
	//rotates the board for the opponent player
	public Board flipBoard() {
		Board flippedBoard = new Board(true);
		for(int j=0 ; j<8 ; j++) {
			for(int i=0 ; i<8 ; i++) {
				if(!squares[i][j].getEmpty()) {
					Piece p = squares[i][j].getPiece();
					flippedBoard.addPiece(7-i, 7-j, p.type, !p.getMine());  
				}
			}
		}
		return flippedBoard;
	}
	
	public Board copyBoard() {
		Board newBoard = new Board(true);
		for(int j=0 ; j<8 ; j++) {
			for(int i=0 ; i<8 ; i++) {
				if(!squares[i][j].getEmpty()) {
					Piece p = squares[i][j].getPiece();
					newBoard.addPiece(i, j, p.type, p.getMine());  
				}
			}
		}
		return newBoard;
	}
	
	public float evaluateBoard() {
		float score = 0;
		
		for(int j=0 ; j<8 ; j++) {
			for(int i=0 ; i<8 ; i++) {
				if(!squares[i][j].getEmpty()) {
					Piece p = squares[i][j].getPiece();
					if(p.getMine()) {
						score += p.value;
					}else {
						score -= p.value;
					}
				}
			}
			}
		return score;
	}
	
	
	
	//so we have some form of ui
	public void printBoard() {
		System.out.println("  0 1 2 3 4 5 6 7");
		for(int j=0 ; j<8 ; j++) {
			System.out.print(j + " ");
			for(int i=0 ; i<8 ; i++) {
				try {
					if(squares[i][j].getPiece().getClass() == Pawn.class) {
						if(squares[i][j].getPiece().mine) {
							System.out.print("P ");
						}else {
							System.out.print("p ");
						}
					}
					if(squares[i][j].getPiece().getClass() == Knight.class) {
						if(squares[i][j].getPiece().mine) {
							System.out.print("N ");
						}else {
							System.out.print("n ");
						}
					}
					if(squares[i][j].getPiece().getClass() == Bishop.class) {
						if(squares[i][j].getPiece().mine) {
							System.out.print("B ");
						}else {
							System.out.print("b ");
						}
					}
					if(squares[i][j].getPiece().getClass() == King.class) {
						if(squares[i][j].getPiece().mine) {
							System.out.print("K ");
						}else {
							System.out.print("k ");
						}
					}
				}catch(NullPointerException npe) {
					System.out.print("_ ");
				}
			}
			System.out.println();
		}
		
//		//print the names and positions of each piece, useful for debugging
//		System.out.println();
//		for(int j=0 ; j<8 ; j++) {
//			for(int i=0 ; i<8 ; i++) {
//				if(squares[i][j].getPiece() != null) {
//					System.out.println(squares[i][j].getPiece() + " at " + i + "," + j);
//				}
//				
//			}
//		}
		System.out.println();
	}
	

}
