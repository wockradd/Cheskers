import java.util.ArrayList;

public class Pawn extends Piece{

	

	public Pawn(Board b, boolean mine, int x, int y) {
		super(b, mine, x, y);
		type = Piece.Type.Pawn;
	}



	@Override
	void generateMoveList() {
		mustMove = false;

		//check if we have to move
		try {
			if(!b.getSquares()[x+1][y-1].getPiece().getMine()) {
				mustMove = true;
			}
		}catch(ArrayIndexOutOfBoundsException aioobe) {}
		catch(NullPointerException npe) {}
		
		try {
			if(!b.getSquares()[x-1][y-1].getPiece().getMine()) {
				mustMove = true;
			}
		}catch(ArrayIndexOutOfBoundsException aioobe) {}
		catch(NullPointerException npe) {}
		
	
		//not capturing
		if(!mustMove) {
			try {
				if(b.getSquares()[x+1][y-1].getEmpty()) {
					moves.add(new Move(x,y,x+1,y-1,null));
				}
			}catch(ArrayIndexOutOfBoundsException aioobe) {}
			try {
				if(b.getSquares()[x-1][y-1].getEmpty()) {
					moves.add(new Move(x,y,x-1,y-1,null));
				}
			}catch(ArrayIndexOutOfBoundsException aioobe) {}
		}

		
//		//hoping and capturing
		if(mustMove) {
			System.out.println("hi");
		}
	}
}
