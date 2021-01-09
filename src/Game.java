
public class Game {

	public static void main(String[] args) {
		Player player1 = new Player();
		Player player2 = new Player();
		int round = 1;
		
		while(true) {
			if(player1.hasLost()) {
				System.out.println("player 1 loses");
				break;
			}
			System.out.println("\n\n\n\n\n\n\n\nTurn num: " + round);
			System.out.println("PLAYER 1");
			player1.getBoard().printBoard();
			player1.generateMoves();
			player1.makeMove();
			player1.getBoard().printBoard();
			player2.setBoard(player1.board.flipBoard());
			
			
			if(player2.hasLost()) {
				System.out.println("player 2 loses");
				break;
			}
			System.out.println("\nPLAYER 2");
			player2.getBoard().printBoard();
			player2.hasLost();
			player2.generateMoves();
			player2.makeMove();
			player2.getBoard().printBoard();
			player1.setBoard(player2.board.flipBoard());
			
			round++;
		}
	}
}
