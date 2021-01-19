import java.util.ArrayList;

public class Move {
	int fromI,fromJ,toI,toJ;
	ArrayList<Piece> taking;
	Piece.Type promoteTo;
	
	public Move(int fromI,int fromJ,int toI,int toJ, ArrayList<Piece> taking) {
		this.fromI = fromI;
		this.fromJ = fromJ;
		this.toI = toI;
		this.toJ = toJ;
		this.promoteTo = null;
		
		this.taking = new ArrayList<Piece>();
		this.taking = taking;
	}
	
	public Move(int fromI,int fromJ,int toI,int toJ, ArrayList<Piece> taking, Piece.Type type) {
		this.fromI = fromI;
		this.fromJ = fromJ;
		this.toI = toI;
		this.toJ = toJ;
		this.promoteTo = type;
		
		this.taking = new ArrayList<Piece>();
		this.taking = taking;
	}
	
	public void addPieces(ArrayList<Piece> ps) {
		taking.addAll(ps);
	}
	
	
	public void printMove() {
		System.out.println(fromI + "," + fromJ + " to " + toI + "," + toJ);
		System.out.println("Taking: " + taking );
		if(promoteTo != null) {
			System.out.println("Promoting too: " + promoteTo + "\n");
		}else {
			System.out.println("\n");
		}
	}
	
	@Override
    public boolean equals(Object object)
    {
       Move m = (Move) object;
       if(m.toI == this.toI && m.toJ == this.toJ && m.fromI == this.fromI && m.fromJ == this.fromJ) {
    	   return true;
       }else {
    	   return false;
       }
    }

}
