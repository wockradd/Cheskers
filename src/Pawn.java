import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends Piece{
	ArrayList<Move> hops;
	

	public Pawn(Board b, boolean mine, int x, int y) {
		super(b, mine, x, y);
		type = Piece.Type.Pawn;
		hops = new ArrayList<Move>(); 
	}



	@Override
	void generateMoveList() {
		moves.clear();
		hops.clear();
		mustMove = false;

		//check if we have to move
		try {
			if(!b.getSquares()[x+1][y-1].getPiece().getMine() && b.getSquares()[x+2][y-2].getEmpty()) {
				mustMove = true;
			}
		}catch(ArrayIndexOutOfBoundsException aioobe) {}
		catch(NullPointerException npe) {}
		
		try {
			if(!b.getSquares()[x-1][y-1].getPiece().getMine() && b.getSquares()[x-2][y-2].getEmpty()) {
				mustMove = true;
			}
		}catch(ArrayIndexOutOfBoundsException aioobe) {}
		catch(NullPointerException npe) {}
		
	
		//not capturing
		if(!mustMove) {
			try {
				if(b.getSquares()[x+1][y-1].getEmpty()) {
					if(y-1 == 0) {
						moves.add(new Move(x,y,x+1,y-1,null,Piece.Type.Bishop));
						moves.add(new Move(x,y,x+1,y-1,null,Piece.Type.King));
						moves.add(new Move(x,y,x+1,y-1,null,Piece.Type.Knight));
					}else {
						moves.add(new Move(x,y,x+1,y-1,null));
					}

				}
			}catch(ArrayIndexOutOfBoundsException aioobe) {}
			try {
				if(b.getSquares()[x-1][y-1].getEmpty()) {
					if(y-1 == 0) {
						moves.add(new Move(x,y,x-1,y-1,null,Piece.Type.Bishop));
						moves.add(new Move(x,y,x-1,y-1,null,Piece.Type.King));
						moves.add(new Move(x,y,x-1,y-1,null,Piece.Type.Knight));
					}else {
						moves.add(new Move(x,y,x-1,y-1,null));
					}

				}
			}catch(ArrayIndexOutOfBoundsException aioobe) {}
		}


		//hoping and capturing
		if(mustMove) {
			recursive(x,y);
			finalisePaths();
		}
	}
	
	
	public void recursive(int initialX, int initialY) {
		try {
			if(!b.getSquares()[initialX-1][initialY-1].getPiece().getMine() && b.getSquares()[initialX-2][initialY-2].getEmpty()) {
				hops.add(new Move(initialX, initialY, initialX-2, initialY-2, new ArrayList<Piece>(Arrays.asList(b.getSquares()[initialX-1][initialY-1].getPiece()))));
				recursive(initialX-2, initialY-2);
			}
		}catch(ArrayIndexOutOfBoundsException aioobe) {}
		catch(NullPointerException npe) {}
		
		try {
			if(!b.getSquares()[initialX+1][initialY-1].getPiece().getMine() && b.getSquares()[initialX+2][initialY-2].getEmpty()) {
				hops.add(new Move(initialX, initialY, initialX+2, initialY-2, new ArrayList<Piece>(Arrays.asList(b.getSquares()[initialX+1][initialY-1].getPiece()))));
				recursive(initialX+2, initialY-2);
			}
		}catch(ArrayIndexOutOfBoundsException aioobe) {}
		catch(NullPointerException npe) {}
	}
	
	
	
	public void finalisePaths() {
		ArrayList<Move> finalHops = (ArrayList<Move>) hops.clone();
		ArrayList<Move> result = new ArrayList<Move>();
		ArrayList<Move> used = new ArrayList<Move>();

		//set finalHops to contain the last hop
		for(int i=0 ; i<finalHops.size() ; i++) {
			for(int j=0 ; j<hops.size() ; j++) {
				//not a final hop
				if(finalHops.get(i).toI == hops.get(j).fromI && finalHops.get(i).toJ == hops.get(j).fromJ) {
					finalHops.remove(i);
					
				}
			}
		}
		
		
		used = (ArrayList<Move>) finalHops.clone();
		hops.removeAll(finalHops);
		result = finalHops;
		
		
		//work backwards from the final hops until you get to where the piece started, thats a full path
		for(Move m:result) {
			while(m.fromI != x || m.fromJ != y) {//not found the start of the path yet
				Move possibleHop = null;
				boolean addedHop = false;
				for(int i=0 ; i<hops.size() ; i++) {
					if(hops.get(i).toI == m.fromI && hops.get(i).toJ == m.fromJ) {
						if(used.contains(hops.get(i))) {
							possibleHop = hops.get(i);
						}else {
							//add this hop to a path and remove from the list of hops
							m.addPieces(hops.get(i).taking);
							m.fromI = hops.get(i).fromI;
							m.fromJ = hops.get(i).fromJ;
							used.add(hops.get(i));
							addedHop = true;
						}
						
					}
				}
				if(possibleHop != null && addedHop == false) {
					m.addPieces(possibleHop.taking);
					m.fromI = possibleHop.fromI;
					m.fromJ = possibleHop.fromJ;
					used.add(possibleHop);
				}
			}
		}
		
		
		
		//do promotion logic
		for(Move m:result) {
			if(m.toJ == 0) {
				moves.add(new Move(m.fromI,m.fromJ,m.toI,m.toJ,m.taking,Piece.Type.Bishop));
				moves.add(new Move(m.fromI,m.fromJ,m.toI,m.toJ,m.taking,Piece.Type.King));
				moves.add(new Move(m.fromI,m.fromJ,m.toI,m.toJ,m.taking,Piece.Type.Knight));
			}else {
				moves.add(m);
			}
		}
		
		
		
		
		
	}
}
