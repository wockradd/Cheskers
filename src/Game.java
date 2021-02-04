import java.io.IOException;
import java.util.ArrayList;

public class Game {
	

	public static void main(String[] args) {
		//initialise the players
		Player player1 = new Player(Player.Type.Human, true);
		Player player2 = new Player(Player.Type.Random,false);
		
		Player currentPlayer = player1; 
		Player nextPlayer = player2;
		Player temp; // used for swapping current and next players
		
		int turn = 1;
		
		
		//main game loop
		while(true) {
			
			//win condition check
			if(currentPlayer.hasLost()) {
				if(currentPlayer.black) {
					System.out.println("black loses");
				}else {
					System.out.println("white loses");
				}
				break;
			}
			
			
			//print stuff to terminal
			System.out.println("\n\n\n\n\n\n\n\nTurn num: " + turn);
			if(currentPlayer.black) {
				System.out.println("Blacks turn");
			}else {
				System.out.println("Whites turn");
			}
			currentPlayer.getBoard().printBoard();
			
			
			//work out moves and if you're human send the moves and board to the display
			currentPlayer.setMoves(currentPlayer.generateMoves(currentPlayer.getBoard()));
			
			if(currentPlayer.type == Player.Type.Human) {
				currentPlayer.display.setBoard(currentPlayer.getBoard());
				currentPlayer.display.setMoves(currentPlayer.getMoves());
			}
			
			
			//wait until i hit enter to make the move
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//pick a move
			Move m = currentPlayer.pickMove(currentPlayer.getBoard(),currentPlayer.getMoves());
			
			//update the board based on this move and the display if you're human
			currentPlayer.setBoard(currentPlayer.makeMove(m,currentPlayer.getBoard()));
			if(currentPlayer.type == Player.Type.Human) {
				currentPlayer.display.setBoard(currentPlayer.getBoard());
			}
			
			//print more stuff
			currentPlayer.getBoard().printBoard();
			
			
			//set the next players board to the new updated board
			nextPlayer.setBoard(currentPlayer.getBoard().flipBoard());
			
			
			//swap players
			temp = currentPlayer;
			currentPlayer = nextPlayer;
			nextPlayer = temp;
			
			turn++;
		}
	}
}
