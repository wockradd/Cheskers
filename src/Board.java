
public class Board {
	private Square[][] squares;
	
	public Board() {
		squares = new Square[8][8];
		initSquares();
		setupPieces();
	}
	

	
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
	
	
	public void setupPieces() {
//		knight and bishop test set, empty, outofbounds, friendly piece, enemy piece moves all tested 
		addPiece(1, 3, 'b', true);
		addPiece(3, 5, 'n', true);
		addPiece(4, 2, 'p', true);
		addPiece(4, 2, 'p', true);
		addPiece(3, 1, 'p', false);
		addPiece(6, 4, 'p', false);
		
		
		
		//this players set up
//		addPiece(0, 5, 'p', true);
//		addPiece(1, 6, 'p', true);
//		addPiece(2, 5, 'p', true);
//		addPiece(3, 6, 'p', true);
//		addPiece(4, 5, 'p', true);
//		addPiece(5, 6, 'p', true);
//		addPiece(6, 5, 'p', true);
//		addPiece(7, 6, 'p', true);
//		addPiece(0, 7, 'b', true);
//		addPiece(2, 7, 'k', true);
//		addPiece(4, 7, 'k', true);
//		addPiece(6, 7, 'n', true);
	
	
		
		//other players set up
//		addPiece(0, 1, 'p', false);
//		addPiece(1, 2, 'p', false);
//		addPiece(2, 1, 'p', false);
//		addPiece(3, 2, 'p', false);
//		addPiece(4, 1, 'p', false);
//		addPiece(5, 2, 'p', false);
//		addPiece(6, 1, 'p', false);
//		addPiece(7, 2, 'p', false);
//		addPiece(7, 0, 'b', false);
//		addPiece(5, 0, 'k', false);
//		addPiece(3, 0, 'k', false);
//		addPiece(1, 0, 'n', false);
	}
	
	public void addPiece(int i, int j, char type, boolean mine) {
		switch(type) {
			case 'p':
				squares[i][j].setPiece(new Pawn(this,mine,i,j));
				break;
			case 'n':
				squares[i][j].setPiece(new Knight(this,mine,i,j));
				break;
			case 'b':
				squares[i][j].setPiece(new Bishop(this,mine,i,j));
				break;
			case 'k':
				squares[i][j].setPiece(new King(this,mine,i,j));
				break;
		}
	}
	
	public Square[][] getSquares() {
		return squares;
	}
	
	
	
	
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
		System.out.println();
		for(int j=0 ; j<8 ; j++) {
			for(int i=0 ; i<8 ; i++) {
				if(squares[i][j].getPiece() != null) {
					System.out.println(squares[i][j].getPiece() + " at " + i + "," + j);
				}
				
			}
		}
		System.out.println();
	}
}
