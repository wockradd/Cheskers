
import java.io.FileWriter;
import java.io.IOException;
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
			fw = new FileWriter("moves.txt",true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		//main game loop
		while(true) {
			
			//print info to terminal
//			System.out.println("\n\n\n\n\n\n\n\nTurn num: " + turn);
//			if(currentPlayer.black) {
//				System.out.println("Blacks turn");
//			}else {
//				System.out.println("Whites turn");
//			}
			
			//check if the game is going on forever
			if(turn >= 200) {
				System.out.println("looping");
				Board blackBoard = player1.getBoard().copyBoard();
				Board whiteBoard = player2.getBoard().copyBoard();
				
				float blackScore = blackBoard.evaluateBoard();
				float whiteScore = whiteBoard.evaluateBoard();
				
				if(blackScore > whiteScore) {
					System.out.println("Black is up material");
				}else if(blackScore == whiteScore) {
					System.out.println("Tied on material");
				}else {
					System.out.println("White is up material");
				}
				
				break;
			}
			
			
			//win condition check
			if(currentPlayer.hasLost()) {
				if(currentPlayer.black) {
					System.out.println("White wins");
				}else {
					System.out.println("Black wins");
				}
				break;
			}
			
			//print info to terminal
			//currentPlayer.getBoard().printBoard();
			
			
			
			
			//work out possible moves
			currentPlayer.setMoves(currentPlayer.generateMoves(currentPlayer.getBoard()));
			
//			//info to terminal
//			for(Move m: currentPlayer.getMoves()) {
//				m.printMove();
//			}
			
			
			//if you're human send the moves and board to the display
			if(currentPlayer.type == Player.Type.Human) {
				currentPlayer.display.setBoard(currentPlayer.getBoard());
				currentPlayer.display.setMoves(currentPlayer.getMoves());
			}
			
			
			//check for stalemate
			if(currentPlayer.getMoves().isEmpty()) {
				if(currentPlayer.black) {
					System.out.println("w");
				}else {
					System.out.println("b");
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
			if(currentPlayer.getRecordMoves()) {
				try {
					fw.write(currentPlayer.getBoard().stringBoardForNN() + "," + (m.fromI+(m.fromJ*8)) +"," + (m.toI+(m.toJ*8)));
					try {
						switch(m.promoteTo) {
						case Knight:
							fw.write(",0\n");
							break;
						case Bishop:
							fw.write(",1\n");
							break;
						case King:
							fw.write(",2\n");
							break;
						default:
							System.err.println("you're trying to promote a pawn to a pawn?");
							break;
						}
					}catch(NullPointerException npe) {
						fw.write(",x\n");
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}


			//info to terminal
//			System.out.println("picked move:");
//			m.printMove();


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
		int p1 = r.nextInt(6);
		int p2 = r.nextInt(6);
		
		//set black player type
		switch(p1) {
		case 0:
			System.out.println("b1");
			player1 = new Player(Player.Type.MM1, true);
			break;
		case 1:
			System.out.println("b2");
			player1 = new Player(Player.Type.MM2, true);
			break;
		case 2:
			System.out.println("b3");
			player1 = new Player(Player.Type.MM3, true);
			break;
		case 3:
			System.out.println("b4");
			player1 = new Player(Player.Type.MM4, true);
			break;
		case 4:
			System.out.println("b5");
			player1 = new Player(Player.Type.MM5, true);
			break;
		case 5:
			System.out.println("b6");
			player1 = new Player(Player.Type.MM6, true);
			break;
		}
		
		//set white player type
		switch(p2) {
		case 0:
			System.out.println("w1");
			player2 = new Player(Player.Type.MM1, false);
			break;
		case 1:
			System.out.println("w2");
			player2 = new Player(Player.Type.MM2, false);
			break;
		case 2:
			System.out.println("w3");
			player2 = new Player(Player.Type.MM3, false);
			break;
		case 3:
			System.out.println("w4");
			player2 = new Player(Player.Type.MM4, false);
			break;
		case 4:
			System.out.println("w5");
			player2 = new Player(Player.Type.MM5, false);
			break;
		case 5:
			System.out.println("w6");
			player2 = new Player(Player.Type.MM6, false);
			break;
		}
	}


	//get user input to pick which player is using which ai
	public static void initPlayers(){
		System.out.println("MM1 - MM4 are minimax AIs using different depths and evaluation functions, higher the number the better the AI");
		Player.Type p1Type=null, p2Type=null;
		
		//set black player type
		System.out.println("Black player:\n1.Random\n2.Human\n3.MM1\n4.MM2\n5.MM3\n6.MM4\n7.MM5\n8.MM6\n9.NN1\n10.NN2");
		int in = s.nextInt();
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
				p1Type = Player.Type.MM5;
				break;
			case 8:
				p1Type = Player.Type.MM6;
				break;
			case 9:
				p1Type = Player.Type.NN1;
				break;
			case 10:
				p1Type = Player.Type.NN2;
				break;
		}
		
		//set white player type
		System.out.println("White player:\n1.Random\n2.Human\n3.MM1\n4.MM2\n5.MM3\n6.MM4\n7.MM5\n8.MM6\n9.NN1\n10.NN2");
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
				p2Type = Player.Type.MM5;
				break;
			case 8:
				p2Type = Player.Type.MM6;
				break;
			case 9:
				p2Type = Player.Type.NN1;
				break;
			case 10:
				p2Type = Player.Type.NN2;
				break;
		}
		
		player1 = new Player(p1Type, true);
		player2 = new Player(p2Type, false);
	}
}
