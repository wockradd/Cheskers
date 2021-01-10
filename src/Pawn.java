import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends Piece{

	

	public Pawn(Board b, boolean mine, int x, int y) {
		super(b, mine, x, y);
		type = Piece.Type.Pawn;
	}



	@Override
	void generateMoveList() {
		moves.clear();
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

		
//		//hoping and capturing
		if(mustMove) {
			try {
				if(!b.getSquares()[x-1][y-1].getPiece().getMine() && b.getSquares()[x-2][y-2].getEmpty()) {
					if(y-2 == 0) {
						moves.add(new Move(x,y,x-2,y-2,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x-1][y-1].getPiece())),Piece.Type.Bishop));
						moves.add(new Move(x,y,x-2,y-2,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x-1][y-1].getPiece())),Piece.Type.King));
						moves.add(new Move(x,y,x-2,y-2,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x-1][y-1].getPiece())),Piece.Type.Knight));
					}else {
						moves.add(new Move(x,y,x-2,y-2,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x-1][y-1].getPiece()))));
					}
				}
			}catch(ArrayIndexOutOfBoundsException aioobe) {}
			catch(NullPointerException npe) {}
			
			try {
				if(!b.getSquares()[x+1][y-1].getPiece().getMine() && b.getSquares()[x+2][y-2].getEmpty()) {
					if(y-2 == 0) {
						moves.add(new Move(x,y,x+2,y-2,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x+1][y-1].getPiece())),Piece.Type.Bishop));
						moves.add(new Move(x,y,x+2,y-2,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x+1][y-1].getPiece())),Piece.Type.King));
						moves.add(new Move(x,y,x+2,y-2,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x+1][y-1].getPiece())),Piece.Type.Knight));
					}else {
						moves.add(new Move(x,y,x+2,y-2,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x+1][y-1].getPiece()))));		
					}
				}
			}catch(ArrayIndexOutOfBoundsException aioobe) {}
			catch(NullPointerException npe) {}
		}
	}
}
