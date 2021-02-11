import java.util.ArrayList;
import java.util.Arrays;

public class King extends Piece{
	ArrayList<Move> hops;
	

	public King(Board b, boolean mine, int x, int y) {
		super(b, mine, x, y);
		value = 8;
		valueMatrix = new float[][]{
			{5,5,5,5,5,5,5,5},
			{3,3,3,3,3,3,3,3},
			{4,4,4,4,4,4,4,4},
			{5,5,5,5,5,5,5,5},
			{5,5,5,5,5,5,5,5},
			{8,8,8,8,8,8,8,8},
			{9,9,9,9,9,9,9,9},
			{9,9,9,9,9,9,9,9}
			};
		type = Piece.Type.King;
		hops = new ArrayList<Move>(); 
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

		try {
			if(!b.getSquares()[x+1][y+1].getPiece().getMine() && b.getSquares()[x+2][y+2].getEmpty()) {
				mustMove = true;
			}
		}catch(ArrayIndexOutOfBoundsException aioobe) {}
		catch(NullPointerException npe) {}

		try {
			if(!b.getSquares()[x-1][y+1].getPiece().getMine() && b.getSquares()[x-2][y+2].getEmpty()) {
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
			try {
				if(b.getSquares()[x+1][y+1].getEmpty()) {
					moves.add(new Move(x,y,x+1,y+1,null));
				}
			}catch(ArrayIndexOutOfBoundsException aioobe) {}
			try {
				if(b.getSquares()[x-1][y+1].getEmpty()) {
					moves.add(new Move(x,y,x-1,y+1,null));
				}
			}catch(ArrayIndexOutOfBoundsException aioobe) {}
		}


//		//hoping and capturing
		if(mustMove) {
			try {
				if(!b.getSquares()[x-1][y-1].getPiece().getMine() && b.getSquares()[x-2][y-2].getEmpty()) {
					moves.add(new Move(x,y,x-2,y-2,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x-1][y-1].getPiece()))));
				}
			}catch(ArrayIndexOutOfBoundsException aioobe) {}
			catch(NullPointerException npe) {}

			try {
				if(!b.getSquares()[x+1][y-1].getPiece().getMine() && b.getSquares()[x+2][y-2].getEmpty()) {
					moves.add(new Move(x,y,x+2,y-2,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x+1][y-1].getPiece()))));
				}
			}catch(ArrayIndexOutOfBoundsException aioobe) {}
			catch(NullPointerException npe) {}

			try {
				if(!b.getSquares()[x-1][y+1].getPiece().getMine() && b.getSquares()[x-2][y+2].getEmpty()) {
					moves.add(new Move(x,y,x-2,y+2,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x-1][y+1].getPiece()))));
				}
			}catch(ArrayIndexOutOfBoundsException aioobe) {}
			catch(NullPointerException npe) {}

			try {
				if(!b.getSquares()[x+1][y+1].getPiece().getMine() && b.getSquares()[x+2][y+2].getEmpty()) {
					moves.add(new Move(x,y,x+2,y+2,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x+1][y+1].getPiece()))));
				}
			}catch(ArrayIndexOutOfBoundsException aioobe) {}
			catch(NullPointerException npe) {}
		}	
	}


}
