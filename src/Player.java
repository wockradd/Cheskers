import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import org.deeplearning4j.nn.api.Layer;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.LossLayer;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import java.io.File;
import java.io.IOException;

public class Player {
	private static final int MINIMAX_DEPTH = 0;
	
	enum Type {Human,Random,MM1,MM2,MM3,MM4,MM5,NN};
	Type type;
	Display display;
	private Board board;
	private ArrayList<Move> moves;
	boolean black;
	Random r;
	Scanner s;
	public ArrayList<Board> scoredBoards = new ArrayList<Board>();
	MultiLayerNetwork toModel,fromModel,promoteModel;
	
	
	public Player(Type type, boolean black) {
		this.black = black;
		this.type = type;
		if(this.type == Type.Human) {
			display = new Display(this.black);
		}
		board = new Board(false);
		moves =  new ArrayList<Move>();
		r = new Random();
		s = new Scanner(System.in);
		
		if(this.type == Type.NN) {
			final String toModelPath = new File("src/resources/toModel.h5").getAbsolutePath();
			final String fromModelPath = new File("src/resources/fromModel.h5").getAbsolutePath();
			final String promoteModelPath = new File("src/resources/promoteModel.h5").getAbsolutePath();
			try {
				toModel = KerasModelImport.importKerasSequentialModelAndWeights(toModelPath, true);
				fromModel = KerasModelImport.importKerasSequentialModelAndWeights(fromModelPath, true);
				promoteModel = KerasModelImport.importKerasSequentialModelAndWeights(promoteModelPath, true);
			} catch (IOException | InvalidKerasConfigurationException | UnsupportedKerasConfigurationException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/** getter
	 */
	public Board getBoard() {
		return board;
	}
	
	/** setter
	 */
	public void setBoard(Board newBoard) {
		board = newBoard;
	}
	
	/** getter
	 */
	public ArrayList<Move> getMoves(){
		return moves;
	}
	
	/** setter
	 */
	public void setMoves(ArrayList<Move> newMoves) {
		moves = newMoves;
	}
	
	//converts a string bitboard into an indarray for the nns
	public INDArray StringToINDArray(String s) {
		char[] chars = s.toCharArray();
		if(chars.length != 512) {
			System.err.println("somethings wrong");
			return null;
		}
		float[][] result = new float[1][512];
		for(int i=0  ;i<chars.length ; i++) {
			result[0][i] = Character.getNumericValue(chars[i]);
		}
		return Nd4j.createFromArray(result);
	}
	
	
	

	/** return true if the player has no kings left
	 */
	public boolean hasLost() {
		int numOfKings = 0;
		for(int j=0 ; j<8 ; j++) {
			for(int i=0 ; i<8 ; i++) {
				try {
					if(board.getSquares()[i][j].getPiece().getMine() && board.getSquares()[i][j].getPiece().type == Piece.Type.King) {
						numOfKings++;
					}
				}catch(NullPointerException npe) {}
			}
		}
		return numOfKings == 0;
	}
	
	
	
	
	/**give me a board, i return all valid moves for that board
	 */
	public ArrayList<Move> generateMoves(Board board) {
		ArrayList<Move> moves = new ArrayList<Move>();
		
		
		ArrayList<Piece> mandatoryCaptures = new ArrayList<Piece>();
		boolean mandatoryMoveFound = false;
		
		//check each piece, if it has moves add these moves to the players move list
		//also sets the mandatoryMove flag if it finds one
		for(int j=0 ; j<8 ; j++) {
			for(int i=0 ; i<8 ; i++) {
				try {
					if(board.getSquares()[i][j].getPiece().getMine()) {
						board.getSquares()[i][j].getPiece().generateMoveList();
						moves.addAll(board.getSquares()[i][j].getPiece().moves);
						
						if(board.getSquares()[i][j].getPiece().mustMove) {
							for(Move m: board.getSquares()[i][j].getPiece().moves) {
								mandatoryCaptures.add(m.taking.get(0));
							}
							mandatoryMoveFound = true;
						}

					}
				}catch(NullPointerException npe) {}
			}
		}


		//remove all moves that dont capture pieces if you found a mandatory move
		if(mandatoryMoveFound) {
			//System.out.println("mandatory move");
			//System.out.println(moves.size());
			for(int i=0 ; i<moves.size() ; i++) {
				try {
					if(!mandatoryCaptures.contains(moves.get(i).taking.get(0))) {
						//System.out.println("Removing");
						//moves.get(i).printMove();
						moves.remove(i);
						i--;
					}else {
						//System.out.println("all good");
						//moves.get(i).printMove();
					}
				}catch(NullPointerException npe) {
					//System.out.println("Removing");
					//moves.get(i).printMove();
					moves.remove(i);
					i--;
				}
				
			}
		}
		
		
//		System.out.println("Possible moves for the board:");
//		board.printBoard();
//		for(int i=0 ; i<moves.size() ; i++) {
//			System.out.println(i);
//			moves.get(i).printMove();
//		}
		
		return moves;

	}
	
	
	/** give me a board and list of moves, i pick my favorite move
	 */
	public Move pickMove(Board board, ArrayList<Move> allMoves) {
		Move m = null;
		long startTime = System.nanoTime();
		
		if(type ==  Type.Human) {
			
			
			

			for(int i=0  ;i<allMoves.size() ; i++) {
				if(allMoves.get(i).fromI + (allMoves.get(i).fromJ*8) == display.fromButton && allMoves.get(i).toI + (allMoves.get(i).toJ*8) == display.toButton) {
					
					m = allMoves.get(i);
				}
			}

			
		}else if(type == Type.Random) {
			m = allMoves.get(r.nextInt(allMoves.size()));
			
		}else if(type == Type.MM1 || type == Type.MM2 || type == Type.MM3 || type == Type.MM4) {
			scoredBoards.clear();
			
			//do the minimaxing
			if(type == Type.MM1) {
				board.setScore(minimax(4, 4,board,true,false,-Float.MAX_VALUE, Float.MAX_VALUE));
			}else if(type == Type.MM2) {
				board.setScore(minimax(4, 4,board,true,true,-Float.MAX_VALUE, Float.MAX_VALUE));
			}else if(type == Type.MM3) {
				board.setScore(minimax(6,6, board,true,false,-Float.MAX_VALUE, Float.MAX_VALUE));
			}else if(type == Type.MM4) {
				board.setScore(minimax(6,6, board,true,true,-Float.MAX_VALUE, Float.MAX_VALUE));
			}
			
			
			float best = -Float.MAX_VALUE;
			int indexOfBest = 0;
			
			//we have all the boards scored, just gotta find the best
			for(int i=0  ;i<scoredBoards.size() ; i++) {
				//update best score index
				if(scoredBoards.get(i).getScore() > best) {
					best = scoredBoards.get(i).getScore();
					indexOfBest = i;
					
				//if the scores equal to the best theres a 10% chance this move will be picked instead
				}else if(scoredBoards.get(i).getScore() == best && r.nextFloat() < 0.1) {
					best = scoredBoards.get(i).getScore();
					indexOfBest = i;
				}
			}
			
			Board bestBoard = scoredBoards.get(indexOfBest);
			
			
			//now we've got the best board, find which move lead to this board
			for(Move move: allMoves) {
				
				Board b = makeMove(move, board).flipBoard();
				if(b.equals(bestBoard)) {
					m = move;
					break;
				}
			}
		}else if(type == Type.NN) {
			//TODO
			//give board to toModel and fromModel
			//create a float array the same size as allMoves
			//find the from and to scores at the index specified
			//each moves score is from+to
			//find the best score in the float array, this is the index of the best move
			//other stuff for promotions
//			INDArray test = StringToINDArray("00000000000000000000000000000000000000001010101001010101000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000000000000000000000001000000000000000000000000000000000000000000000000000000000000000001010000000000010101010010101010000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000000000001000000000000000000000000000000000000000000000000000000000001010000000000000000000000000000000000000000000000000000000000");
//			INDArray output = toModel.output(test);
//			
//			for(int i=0  ; i<64 ; i++) {
//				System.out.println(output.getFloat(0,i));
//			}
		}

		long endTime = System.nanoTime();
		//System.out.println("Time: "+(endTime-startTime)/1000000 + "ms");
		
		return m;
	}
	
	
	

	
	public float minimax(int currentDepth,int maxDepth, Board currentBoard, boolean max, boolean posEval, float alpha, float beta) {
		
		//base case
		if(currentDepth == 0) {
			if(posEval) {
				return currentBoard.evaluateBoardBetter();
			}else {
				return currentBoard.evaluateBoard();
			}
			
		}else {//step case
			
			//get all children
			ArrayList<Board> allBoards = getAllPossibleBoards(currentBoard, generateMoves(currentBoard));
			
			if(max) {
				float value = -Float.MAX_VALUE;
				for(Board b : allBoards) {
					value = max(value,minimax(currentDepth-1,maxDepth, b.flipBoard(), !max,posEval, alpha, beta));
					alpha = max(alpha,value);
					if(alpha >= beta) {
						break;
					}
				}
				if(currentDepth == maxDepth-1) {
					Board copy = currentBoard.copyBoard();
					copy.setScore(value);
					scoredBoards.add(copy);
				}
				return value;
			}else {
				float value = Float.MAX_VALUE;
				for(Board b : allBoards) {
					value = min(value,minimax(currentDepth-1,maxDepth, b.flipBoard(), !max,posEval, alpha, beta));
					beta = min(beta,value);
					if(beta <= alpha) {
						break;
					}
				}
				if(currentDepth == maxDepth-1) {
					Board copy = currentBoard.copyBoard();
					copy.setScore(value);
					scoredBoards.add(copy);
				}
				return value;
			}
		}
	}

	
	
	


	
	/** give me a move and a board, i return the board that results from this move
	 */
	public Board makeMove(Move move, Board board) {
		Board newBoard = board.copyBoard();

		//remove any pieces the move got rid of
		if(move.taking != null) {
			for(int j=0 ; j<8 ; j++) {
				for(int i=0 ; i<8 ; i++) {
					try {
						if(move.taking.contains(board.getSquares()[i][j].getPiece())) {
							newBoard.getSquares()[i][j].removePiece();
						}
					}catch(NullPointerException npe) {}
				}
			}
		}

		//move the piece to its new position
		newBoard.getSquares()[move.toI][move.toJ].setPiece(newBoard.getSquares()[move.fromI][move.fromJ].getPiece());
		newBoard.getSquares()[move.fromI][move.fromJ].removePiece();

		

		//promote any pieces the move promoted
		if(move.promoteTo != null) {
			//System.out.println("Promoting");
			newBoard.getSquares()[move.toI][move.toJ].removePiece();
			switch(move.promoteTo) {
			case King:
				newBoard.getSquares()[move.toI][move.toJ].setPiece(new King(newBoard, true, move.toI, move.toJ));
				break;
			case Knight:
				newBoard.getSquares()[move.toI][move.toJ].setPiece(new Knight(newBoard, true, move.toI, move.toJ));
				break;
			case Bishop:
				newBoard.getSquares()[move.toI][move.toJ].setPiece(new Bishop(newBoard, true, move.toI, move.toJ));
				break;
			default:
				System.err.println("opps");
				break;
			}
		}

		return newBoard;


	}
	
	
	
	

	
	/** Give me a board and the list of moves, i return all the boards this can lead to 
	*/
	public ArrayList<Board> getAllPossibleBoards(Board board, ArrayList<Move> allMoves){
		ArrayList<Board> allBoards = new ArrayList<Board>();
				for(Move m:allMoves) {
					allBoards.add(makeMove(m, board));
				}
		return allBoards;
	}
	
	
	public float max(float f1, float f2) {
		if(f1 >= f2) {
			return f1;
		}else {
			return f2;
		}
	}
	
	public float min(float f1, float f2) {
		if(f1 <= f2) {
			return f1;
		}else {
			return f2;
		}
	}
}
