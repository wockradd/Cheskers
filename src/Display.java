import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class Display {
	//display stuff
	private boolean black;
	private Board board;
	private ArrayList<Move> moves;
	
	private int SIZE = 500;
	private ImageIcon [][] images;
	
	private JFrame frame;
	private JPanel panel;
	private JButton [][] chessBoardButtons;
	
	//logic stuff
	public int fromButton=-1,toButton=-1;

	public Display(boolean black) {
		this.black = black;
		loadImages();
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
						chessBoardButtons[i][j].setIcon(images[index][0]);
						break;
					case Knight:
						chessBoardButtons[i][j].setIcon(images[index][1]);
						break;
					case Bishop:
						chessBoardButtons[i][j].setIcon(images[index][2]);
						break;
					case King:
						chessBoardButtons[i][j].setIcon(images[index][3]);
						break;
					}
					
				}catch(NullPointerException npe) {}
			}
		}
	}

	
	
	
	public void loadImages() {
		images  = new ImageIcon[2][4];
		
		images[0][0] = new ImageIcon(Game.class.getResource("/resources/whitePawn.png"));
		images[0][1] = new ImageIcon(Game.class.getResource("/resources/whiteKnight.png"));
		images[0][2] = new ImageIcon(Game.class.getResource("/resources/whiteBishop.png"));
		images[0][3] = new ImageIcon(Game.class.getResource("/resources/whiteKing.png"));
		
		images[1][0] = new ImageIcon(Game.class.getResource("/resources/blackPawn.png"));
		images[1][1] = new ImageIcon(Game.class.getResource("/resources/blackKnight.png"));
		images[1][2] = new ImageIcon(Game.class.getResource("/resources/blackBishop.png"));
		images[1][3] = new ImageIcon(Game.class.getResource("/resources/blackKing.png"));
		
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
