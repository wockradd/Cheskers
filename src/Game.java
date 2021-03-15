
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;




public class Game {
	
	static Player player1;//black, goes first in cheskers
	static Player player2;//white
	static Scanner s = new Scanner(System.in);
	static Random r = new Random();

	public static void main(String[] args) {
		//initialise the players
		initPlayers();
		//initPlayersForTraining();
		
		Player currentPlayer = player1; 
		Player nextPlayer = player2;
		Player temp; // used for swapping current and next players
		
		
		
		FileWriter fw = null;
		int turn = 1;
		
		try {
			fw = new FileWriter("games.txt",true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		//main game loop
		while(true) {
			
			//check if the game is going on forever
			if(turn >= 200) {
				System.out.println("looping");
				break;
			}
			
			//win condition check
			if(currentPlayer.hasLost()) {
				if(currentPlayer.black) {
					System.out.println("white wins");
				}else {
					System.out.println("black wins");
				}
				break;
			}
			
			//print info to terminal
//			System.out.println("\n\n\n\n\n\n\n\nTurn num: " + turn);
//			if(currentPlayer.black) {
//				System.out.println("Blacks turn");
//			}else {
//				System.out.println("Whites turn");
//			}
//			currentPlayer.getBoard().printBoard();
			
			
			
			
			//work out possible moves
			currentPlayer.setMoves(currentPlayer.generateMoves(currentPlayer.getBoard()));
			
			//if you're human send the moves and board to the display
			if(currentPlayer.type == Player.Type.Human) {
				currentPlayer.display.setBoard(currentPlayer.getBoard());
				currentPlayer.display.setMoves(currentPlayer.getMoves());
			}
			
			
			//check for stalemate
			if(currentPlayer.getMoves().isEmpty()) {
				if(currentPlayer.black) {
					System.out.println("white wins");
				}else {
					System.out.println("black wins");
				}
				break;
			}
			
			
			
			
			
			//busy wait for human to enter move
			if(currentPlayer.type == Player.Type.Human) {
				while(currentPlayer.display.fromButton == -1 || currentPlayer.display.toButton == -1) {
				
				}
			}
			
			
			
			
			//pick a move
			Move m = currentPlayer.pickMove(currentPlayer.getBoard(),currentPlayer.getMoves());
			
			
			
			
			//update the board based on this move
			currentPlayer.setBoard(currentPlayer.makeMove(m,currentPlayer.getBoard()));
			
			
			//update the display if you're human
			if(currentPlayer.type == Player.Type.Human) {
				currentPlayer.display.setBoard(currentPlayer.getBoard());
				currentPlayer.display.fromButton = -1;
				currentPlayer.display.toButton = -1;
			}
			
			//print the new board to file
			try {
				fw.write(currentPlayer.getBoard().printBoardForNN() + "," + (m.fromI+(m.fromJ*8)) +"," + (m.toI+(m.toJ*8)));
				try {
					switch(m.promoteTo) {
					case Knight:
						fw.write(",1\n");
						break;
					case Bishop:
						fw.write(",2\n");
						break;
					case King:
						fw.write(",3\n");
						break;
					}
				}catch(NullPointerException npe) {
					fw.write(",0\n");
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			//print what move was picked and the board this created
//			m.printMove();
//			currentPlayer.getBoard().printBoard();
			
			
			//set the next players board to the new updated board
			nextPlayer.setBoard(currentPlayer.getBoard().flipBoard());
			
			
			//swap players
			temp = currentPlayer;
			currentPlayer = nextPlayer;
			nextPlayer = temp;
			
			turn++;
		}
		
		try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//used for training, sets the players as random minimax ais
	public static void initPlayersForTraining() {
		int p1 = r.nextInt(4);
		int p2 = r.nextInt(4);
		switch(p1) {
			case 0:
				System.out.println("black is mm1");
				player1 = new Player(Player.Type.MM1, true);
				break;
			case 1:
				System.out.println("black is mm2");
				player1 = new Player(Player.Type.MM2, true);
				break;
			case 2:
				System.out.println("black is mm3");
				player1 = new Player(Player.Type.MM3, true);
				break;
			case 3:
				System.out.println("black is mm4");
				player1 = new Player(Player.Type.MM4, true);
				break;
		}
		switch(p2) {
		case 0:
			System.out.println("white is mm1");
			player2 = new Player(Player.Type.MM1, false);
			break;
		case 1:
			System.out.println("white is mm2");
			player2 = new Player(Player.Type.MM2, false);
			break;
		case 2:
			System.out.println("white is mm3");
			player2 = new Player(Player.Type.MM3, false);
			break;
		case 3:
			System.out.println("white is mm4");
			player2 = new Player(Player.Type.MM4, false);
			break;
	}
	}
	
	
	//get user input to pick which player is using which ai
	public static void initPlayers(){
		System.out.println("MM1 - MM4 are minimax AIs using different depths and evaluation functions, higher the number the better the AI");
		
		System.out.println("Black player:\n1.Random\n2.Human\n3.MM1\n4.MM2\n5.MM3\n6.MM4\n7.NN");
		int in = s.nextInt();
		Player.Type p1Type=null, p2Type=null;
		switch(in) {
			case 1:
				p1Type = Player.Type.Random;
				
				break;
			case 2:
				p1Type = Player.Type.Human;
				break;
			case 3:
				p1Type = Player.Type.MM1;
				break;
			case 4:
				p1Type = Player.Type.MM2;
				break;
			case 5:
				p1Type = Player.Type.MM3;
				break;
			case 6:
				p1Type = Player.Type.MM4;
				break;
			case 7:
				p1Type = Player.Type.NN;
				break;
		}
		
		System.out.println("White player:\n1.Random\n2.Human\n3.MM1\n4.MM2\n5.MM3\n6.MM4\n7.NN");
		in = s.nextInt();
		switch(in) {
			case 1:
				p2Type = Player.Type.Random;
				break;
			case 2:
				p2Type = Player.Type.Human;
				break;
			case 3:
				p2Type = Player.Type.MM1;
				break;
			case 4:
				p2Type = Player.Type.MM2;
				break;
			case 5:
				p2Type = Player.Type.MM3;
				break;
			case 6:
				p2Type = Player.Type.MM4;
				break;
			case 7:
				p2Type = Player.Type.NN;
				break;
		}
		
		player1 = new Player(p1Type, true);
		player2 = new Player(p2Type, false);
	}
}
