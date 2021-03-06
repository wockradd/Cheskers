import java.util.ArrayList;
import java.util.Arrays;

public class Bishop extends Piece{

	public Bishop(Board b, boolean mine, int x, int y) {
		super(b, mine, x, y);
		value = 3;
		valueMatrix = new float[][]{
			{-1,0,0,0,0,0,0,-1},
			{0,1,1,1,1,1,1,0},
			{0,1,2,2.5f,2.5f,2,1,0},
			{0,2,2.5f,3,3,2.5f,2,0},
			{0,2,2.5f,3,3,2.5f,2,0},
			{0,1,2,2.5f,2.5f,2,1,0},
			{0,1,1,1,1,1,1,0},
			{-1,0,0,0,0,0,0,-1}
			};
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
