
public class Player {
	Board board;
	
	public Player() {
		board = new Board();
	}
	
	
	public Board getBoard() {
		return board;
	}
	
	public void makeMove() {
		boolean mandatoryMove = false;
		
		//look through pawn and king moves
		for(int j=0 ; j<8 ; j++) {
			for(int i=0 ; i<8 ; i++) {
				try {
					if(board.getSquares()[i][j].getPiece().getMine()) {
						if(board.getSquares()[i][j].getPiece().type == Piece.Type.Pawn || board.getSquares()[i][j].getPiece().type == Piece.Type.King) {
							board.getSquares()[i][j].getPiece().generateMoveList();
							if(board.getSquares()[i][j].getPiece().mustMove) {
								mandatoryMove = true;
							}
						}
					}
				}catch(NullPointerException npe) {}
			}
		}
		
		//look through knight and bishop moves
		if(!mandatoryMove) {
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

}
