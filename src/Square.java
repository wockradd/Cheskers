
public class Square {
	private boolean black;
	private Piece piece;
	private boolean empty;
	
	
	public Square(boolean black){
		this.black = black;
		this.empty = true;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public void setPiece(Piece newPiece) {
		empty = false;
		piece = newPiece;
	}
	
	public void removePiece() {
		piece = null;
		empty = true;
	}
	
	public boolean getEmpty() {
		return empty;
	}

}
