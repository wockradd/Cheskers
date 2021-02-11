import java.io.IOException;
import java.util.ArrayList;


/* BITBOARDS

0 1 2 3 4 5 6 7
0 _ K _ k _ k _ b 
1 p _ _ _ p _ _ _ 
2 _ p _ _ _ _ _ p 
3 _ _ _ _ _ _ _ _ 
4 _ _ _ _ _ _ _ _ 
5 P _ _ _ _ _ P _ 
6 _ n _ _ _ P _ P 
7 B _ K _ K _ N _ 



=>

black pawns
00000000
00000000
00000000
00000000
00000000
10000010
00000101
00000000

black knights
00000000
00000000
00000000
00000000
00000000
00000000
00000000
00000010

black bishops
00000000
00000000
00000000
00000000
00000000
00000000
00000000
10000000

black kings
01000000
00000000
00000000
00000000
00000000
00000000
00000000
00101000

white pawns
00000000
10001000
01000001
00000000
00000000
00000000
00000000
00000000

white knights
00000000
00000000
00000000
00000000
00000000
00000000
01000000
00000000

white bishops
00000001
00000000
00000000
00000000
00000000
00000000
00000000
00000000

white kings
00010100
00000000
00000000
00000000
00000000
00000000
00000000
00000000


=>
0b00000000000000000000000000000000000000001000001000000101000000000000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000001000000001000000000000000000000000000000000000000000000000000000001010000000000010001000010000010000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010000000000000000000001000000000000000000000000000000000000000000000000000000000001010000000000000000000000000000000000000000000000000000000000


=>
0x8205000000000000000002000000000000008040000000000000280088410000000000000000000000400001000000000000001400000000000000

* */



public class Game {
	

	public static void main(String[] args) {
		//initialise the players
		Player player1 = new Player(Player.Type.Minimax, true);
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
//			try {
//				System.in.read();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
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
