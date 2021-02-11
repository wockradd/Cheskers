import java.util.ArrayList;

public abstract class Piece {
	public enum Type{Pawn,Bishop,Knight,King};
	public Type type;
	
	protected Board b;
	protected boolean mine;
	protected int x, y;
	protected float value;
	protected float[][] valueMatrix;
	protected ArrayList<Move> moves;
	protected boolean  mustMove;
	
	abstract void generateMoveList();
	
	public Piece(Board b, boolean mine, int i, int j) {
		this.b = b;
		this.mine = mine;
		this.x = i;
		this.y = j;
		moves = new ArrayList<Move>();
		mustMove = false;
		
	}
	
	public ArrayList<Move> getMoveList() {
		return moves;
	}
	
	public boolean getMine() {
		return mine;
	}
	
	public boolean canMove() {
		return moves.size() > 0;
	}
	
	@Override
	public String toString() {
		switch(this.type) {
		case King:
			return "King at " + x + "," + y;
		case Bishop:
			return "Bishop at " + x + "," + y;
		case Knight:
			return "Knight at " + x + "," + y;
		case Pawn:
			return "Pawn at " + x + "," + y;
		default:
			return "Piece at " + x + "," + y;
		}
	}
}
