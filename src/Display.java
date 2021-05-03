import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

//using iamges from https://commons.wikimedia.org/wiki/Category:SVG_chess_pieces

public class Display {
	//display stuff
	private boolean black;
	private Board board;
	private ArrayList<Move> moves;
	
	private int SIZE = 500;
	private ImageIcon [][] images;
	private boolean iconsFailed = false;
	
	private JFrame frame;
	private JPanel panel;
	private JButton [][] chessBoardButtons;
	
	//logic stuff
	public int fromButton=-1,toButton=-1;

	public Display(boolean black) {
		this.black = black;
		try {
			loadImages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initGui();
	}
	
	public void setMoves(ArrayList<Move> moves) {
		this.moves = moves;
		
		//update display
		for(int j=0 ; j<8 ; j++) {
			for(int i=0 ; i<8 ; i++) {
				if((i+j)%2 == 0) {
					chessBoardButtons[i][j].setBackground(Color.WHITE);
				}else {
					chessBoardButtons[i][j].setBackground(Color.LIGHT_GRAY);
				}
				for(Move m: moves) {
					if(m.fromI == i && m.fromJ ==j) {
						chessBoardButtons[i][j].setBackground(Color.DARK_GRAY);
					}
				}
			}
		}
	}
	
	public void setBoard(Board board) {
		for(int j=0 ; j<8 ; j++) {
			for(int i=0 ; i<8 ; i++) {
				chessBoardButtons[i][j].setIcon(null);
				chessBoardButtons[i][j].setText("");
			}
		}
		this.board = board;
		int index;
		
		for(int j=0 ; j<8 ; j++) {
			for(int i=0 ; i<8 ; i++) {
				try {
					if(board.getSquares()[i][j].getPiece().getMine()) {
						if(black) {
							index = 1;
						}else {
							index = 0;
						}
					}else {
						if(black) {
							index = 0;
						}else {
							index = 1;
						}
					}

					switch(board.getSquares()[i][j].getPiece().type) {
					case Pawn:
						if(iconsFailed) {
							if(board.getSquares()[i][j].getPiece().getMine()) {
								chessBoardButtons[i][j].setText("P");
							}else {
								chessBoardButtons[i][j].setText("p");
							}
						}else {
							chessBoardButtons[i][j].setIcon(images[index][0]);
						}

						break;
					case Knight:
						if(iconsFailed) {
							if(board.getSquares()[i][j].getPiece().getMine()) {
								chessBoardButtons[i][j].setText("N");
							}else {
								chessBoardButtons[i][j].setText("n");
							}
						}else {
							chessBoardButtons[i][j].setIcon(images[index][1]);
						}

						break;
					case Bishop:
						if(iconsFailed) {
							if(board.getSquares()[i][j].getPiece().getMine()) {
								chessBoardButtons[i][j].setText("B");
							}else {
								chessBoardButtons[i][j].setText("b");
							}
						}else {
							chessBoardButtons[i][j].setIcon(images[index][2]);
						}

						break;
					case King:
						if(iconsFailed) {
							if(board.getSquares()[i][j].getPiece().getMine()) {
								chessBoardButtons[i][j].setText("K");
							}else {
								chessBoardButtons[i][j].setText("k");
							}
						}else {
							chessBoardButtons[i][j].setIcon(images[index][3]);
						}

						break;
					}

				}catch(NullPointerException npe) {}
			}
		}
	}

	
	
	
	public void loadImages() throws IOException {	
		images  = new ImageIcon[2][4];
		try {
			images[0][0] = new ImageIcon(Game.class.getResource("resources/whitePawn.png"));
			images[0][1] = new ImageIcon(Game.class.getResource("resources/whiteKnight.png"));
			images[0][2] = new ImageIcon(Game.class.getResource("resources/whiteBishop.png"));
			images[0][3] = new ImageIcon(Game.class.getResource("resources/whiteKing.png"));
			
			images[1][0] = new ImageIcon(Game.class.getResource("resources/blackPawn.png"));
			images[1][1] = new ImageIcon(Game.class.getResource("resources/blackKnight.png"));
			images[1][2] = new ImageIcon(Game.class.getResource("resources/blackBishop.png"));
			images[1][3] = new ImageIcon(Game.class.getResource("resources/blackKing.png"));
		
		}catch(NullPointerException npe) {
			iconsFailed = true;
		}
			
	}
	

	
	public void initGui() {
		frame = new JFrame("Cheskers");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chessBoardButtons = new JButton[8][8];
		
		for(int j=0  ;j<8 ; j++) {
			for(int i=0 ; i<8 ; i++) {
				chessBoardButtons[i][j] = new JButton();
				chessBoardButtons[i][j].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						JButton clicked = (JButton)arg0.getSource();
						
						if(clicked.getBackground() == Color.DARK_GRAY) {
							//figure out which button was clicked
							int clickedI=-1,clickedJ=-1;
							for(int j=0  ;j<8 ; j++) {
								for(int i=0 ; i<8 ; i++) {
									if(chessBoardButtons[i][j] == arg0.getSource()) {
										clickedI = i;
										clickedJ=j;
									}
								}
							}
							
							//havent picked a peice yet
							if(fromButton == -1) {
								fromButton = clickedI + (8*clickedJ);
								
								//update display to show where you can move this piece
								for(int j=0  ;j<8 ; j++) {
									for(int i=0 ; i<8 ; i++) {
										if((i+j)%2 == 0) {
											chessBoardButtons[i][j].setBackground(Color.WHITE);
										}else {
											chessBoardButtons[i][j].setBackground(Color.LIGHT_GRAY);
										}
										for(Move m: moves) {
											if(m.toI == i && m.toJ ==j && m.fromI+(m.fromJ*8) == fromButton) {
												chessBoardButtons[i][j].setBackground(Color.DARK_GRAY);
											}
										}
									}
								}
							//now im picking where to move it
							}else {
								toButton = clickedI + (8*clickedJ);
							}
						}
						
						
					}
				});
				//chessBoardSquares[i][j].setEnabled(false);
				if((i+j)%2 == 0) {
					chessBoardButtons[i][j].setBackground(Color.WHITE);
				}else {
					chessBoardButtons[i][j].setBackground(Color.LIGHT_GRAY);
				}
			}
		}
		
		panel = new JPanel(new GridLayout(8, 8));
		for(int j=0  ;j<8 ; j++) {
			for(int i=0 ; i<8 ; i++) {
				panel.add(chessBoardButtons[i][j]);
			}
		}
		
		frame.add(panel);
		frame.setSize(SIZE, SIZE);
	}
}
