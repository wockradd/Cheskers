import java.util.ArrayList;
import java.util.Arrays;

public class Knight extends Piece{

	

	

	public Knight(Board b, boolean mine, int x, int y) {
		super(b, mine, x, y);
		value = 3;
		type = Piece.Type.Knight;
	}



	//gross but it works
	//check each of the 8 squares its possible for a knight to move to in turn
	@Override
	void generateMoveList() {
		moves.clear();
		try {
			if(b.getSquares()[x-1][y-3].getEmpty()) {
				moves.add(new Move(x,y,x-1,y-3,null));
			}else if(b.getSquares()[x-1][y-3].getPiece().mine == false) {
				moves.add(new Move(x,y,x-1,y-3,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x-1][y-3].getPiece()))));
			}
		}catch(ArrayIndexOutOfBoundsException aioobe) {}
		
		try {
			if(b.getSquares()[x+1][y-3].getEmpty()) {
				moves.add(new Move(x,y,x+1,y-3,null));
			}else if(b.getSquares()[x+1][y-3].getPiece().mine == false) {
				moves.add(new Move(x,y,x+1,y-3,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x+1][y-3].getPiece()))));
			}
		}catch(ArrayIndexOutOfBoundsException aioobe) {}
		
		try {
			if(b.getSquares()[x-3][y-1].getEmpty()) {
				moves.add(new Move(x,y,x-3,y-1,null));
			}else if(b.getSquares()[x-3][y-1].getPiece().mine == false) {
				moves.add(new Move(x,y,x-3,y-1,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x-3][y-1].getPiece()))));
			}
		}catch(ArrayIndexOutOfBoundsException aioobe) {}
			
		try {
			if(b.getSquares()[x+3][y-1].getEmpty()) {
				moves.add(new Move(x,y,x+3,y-1,null));
			}else if( b.getSquares()[x+3][y-1].getPiece().mine == false) {
				moves.add(new Move(x,y,x+3,y-1,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x+3][y-1].getPiece()))));
			}
		}catch(ArrayIndexOutOfBoundsException aioobe) {}
		
		try {
			if(b.getSquares()[x-1][y+3].getEmpty()) {
				moves.add(new Move(x,y,x-1,y+3,null));
			}else if(b.getSquares()[x-1][y+3].getPiece().mine == false) {
				moves.add(new Move(x,y,x-1,y+3,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x-1][y+3].getPiece()))));
			}
		}catch(ArrayIndexOutOfBoundsException aioobe) {}
		
		try {
			if(b.getSquares()[x+1][y+3].getEmpty()) {
				moves.add(new Move(x,y,x+1,y+3,null));
			}else if(b.getSquares()[x+1][y+3].getPiece().mine == false) {
				moves.add(new Move(x,y,x+1,y+3,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x+1][y+3].getPiece()))));
			}
		}catch(ArrayIndexOutOfBoundsException aioobe) {}
		
		try {
			if(b.getSquares()[x-3][y+1].getEmpty()) {
				moves.add(new Move(x,y,x-3,y+1,null));
			}else if(b.getSquares()[x-3][y+1].getPiece().mine == false) {
				moves.add(new Move(x,y,x-3,y+1,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x-3][y+1].getPiece()))));
			}
		}catch(ArrayIndexOutOfBoundsException aioobe) {}
		
		try {
			if(b.getSquares()[x+3][y+1].getEmpty()) {
				moves.add(new Move(x,y,x+3,y+1,null));
			}else if(b.getSquares()[x+3][y+1].getPiece().mine == false) {
				moves.add(new Move(x,y,x+3,y+1,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x+3][y+1].getPiece()))));
			}
		}catch(ArrayIndexOutOfBoundsException aioobe) {}
	}
}
