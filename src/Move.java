import java.util.ArrayList;

public class Move {
	int fromI,fromJ,toI,toJ;
	ArrayList<Piece> taking;
	
	public Move(int fromI,int fromJ,int toI,int toJ, ArrayList<Piece> taking) {
		this.fromI = fromI;
		this.fromJ = fromJ;
		this.toI = toI;
		this.toJ = toJ;
		
		this.taking = new ArrayList<Piece>();
		this.taking = taking;
	}
	
	
	public void printMove() {
		System.out.println(fromI + "," + fromJ + " to " + toI + "," + toJ);
		System.out.println("Taking: " + taking + "\n");
	}

}
