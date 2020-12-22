
public class Game {

	public static void main(String[] args) {
		Player player1 = new Player();
		Player player2 = new Player();
		
		//so we have seem kind of gui
		player1.getBoard().printBoard();
		
		//just generates the list of moves for now
		player1.makeMove();
		
		for(int j=0 ; j<8 ; j++) {
			for(int i=0 ; i<8 ; i++) {
				try {
					for(int k=0 ; k<player1.board.getSquares()[i][j].getPiece().getMoveList().size() ; k++) {
						System.out.println(player1.board.getSquares()[i][j].getPiece());
						player1.board.getSquares()[i][j].getPiece().getMoveList().get(k).printMove();
					}	
				}catch(NullPointerException npe) {}
			}
		}
	}
}
