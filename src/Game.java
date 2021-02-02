import java.io.IOException;

public class Game {

	public static void main(String[] args) {
		Player player1 = new Player(Player.Type.Human, true);
		Player player2 = new Player(Player.Type.Random,false);
		int round = 1;
		
		while(true) {
			if(player1.hasLost()) {
				System.out.println("player 1 loses");
				break;
			}
			System.out.println("\n\n\n\n\n\n\n\nTurn num: " + round);
			System.out.println("PLAYER 1");
			player1.getBoard().printBoard();
			player1.display.setBoard(player1.getBoard());
			player1.generateMoves();
			player1.display.setMoves(player1.getMoves());
			
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			player1.makeMove();
			player1.display.setBoard(player1.getBoard());
			player1.getBoard().printBoard();
			player2.setBoard(player1.board.flipBoard());
	
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
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
