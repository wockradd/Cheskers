import java.util.ArrayList;
import java.util.Arrays;

public class Bishop extends Piece{

	public Bishop(Board b, boolean mine, int x, int y) {
		super(b, mine, x, y);
		type = Piece.Type.Bishop;
	}


	@Override
	void generateMoveList() {
		moves.clear();
		int xOffset=1, yOffset=1;
	
		try {
			while(b.getSquares()[x+xOffset][y+yOffset].getEmpty()) {
				moves.add(new Move(x, y, x+xOffset, y+yOffset,null));
				xOffset++;
				yOffset++;
			}
			if(!b.getSquares()[x+xOffset][y+yOffset].getPiece().getMine()) {
				moves.add(new Move(x, y, x+xOffset, y+yOffset,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x+xOffset][y+yOffset].getPiece()))));
			}
		}catch(ArrayIndexOutOfBoundsException aioobe) {}
		
		xOffset = 1;
		yOffset = 1;
		try {
			while(b.getSquares()[x-xOffset][y+yOffset].getEmpty()) {
				moves.add(new Move(x, y, x-xOffset, y+yOffset,null));
				xOffset++;
				yOffset++;
			}
			if(!b.getSquares()[x-xOffset][y+yOffset].getPiece().getMine()) {
				moves.add(new Move(x, y, x-xOffset, y+yOffset,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x-xOffset][y+yOffset].getPiece()))));
			}
		}catch(ArrayIndexOutOfBoundsException aioobe) {}
		xOffset = 1;
		yOffset = 1;
		try {
			while(b.getSquares()[x-xOffset][y-yOffset].getEmpty()) {
				moves.add(new Move(x, y, x-xOffset, y-yOffset,null));
				xOffset++;
				yOffset++;
			}
			if(!b.getSquares()[x-xOffset][y-yOffset].getPiece().getMine()) {
				moves.add(new Move(x, y, x-xOffset, y-yOffset,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x-xOffset][y-yOffset].getPiece()))));
			}
		}catch(ArrayIndexOutOfBoundsException aioobe) {}
		
		xOffset = 1;
		yOffset = 1;
		try {
			while(b.getSquares()[x+xOffset][y-yOffset].getEmpty()) {
				moves.add(new Move(x, y, x+xOffset, y-yOffset,null));
				xOffset++;
				yOffset++;
			}
			if(!b.getSquares()[x+xOffset][y-yOffset].getPiece().getMine()) {
				moves.add(new Move(x, y, x+xOffset, y-yOffset,new ArrayList<Piece>(Arrays.asList(b.getSquares()[x+xOffset][y-yOffset].getPiece()))));
			}
		}catch(ArrayIndexOutOfBoundsException aioobe) {}
	}






}
