package shea.mancala;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings("serial")
class MancalaGame extends JPanel implements MouseListener {
	
	private Board board = new Board();
	private int[] pitStones = new int[] { 4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0 };
	private int currentPlayer;
	
	public MancalaGame() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(this);
	}
	
	/**
	 * Set the size of the window to the size of the board
	 */
	public Dimension getPreferredSize() {
		return board.getSize();
	}
	
	protected boolean moveStones(final int pit) {
		int pointer = pit;
		
		// return if pit has no stones
		if ( pitStones[pit] < 1 ) {
			return true;
		}
		
		// take stones out of pit
		int stones = pitStones[pit];
		pitStones[pit] = 0;
		
		while ( stones > 0 && pointer < 13 ) {
			pitStones[++pointer]++;
			stones--;
			repaint();
		}
		
		// set to point to the opposite pit
		int inversePointer = -pointer + 12;
		
		// Check for capture
		if (pointer != 6 && pitStones[pointer] == 1 && inversePointer > 0) {
	
			// Transfer this stone along with opposite pit's stones to store
			pitStones[6] = pitStones[inversePointer] + 1;
			
			// Clear the pits
			pitStones[pointer] = 0;
			pitStones[inversePointer] = 0;
		}
		
		// return true if the turn ended in storage pit
		return pointer == 6;
	}
	
	public void switchTurn() {
		currentPlayer++;
		currentPlayer %= 2;
				
		int[] newStones = new int[14];
		System.arraycopy(pitStones, 7, newStones, 0, 7);
		System.arraycopy(pitStones, 0, newStones, 7, 7);
		
		pitStones = newStones;
		repaint();
	}
	
	protected void drawStones(Graphics g) {
		int cx, cy; // extra centering correction

		for (int pit = 0; pit < pitStones.length; ++pit) {
			if (pit == 6 || pit == 13) {
				cx = -3;
				cy = 0;
			} else if (pit > 9) {
				cx = 3;
				cy = 6;
			} else {
				cx = 7;
				cy = 9;
			}
			
			g.drawString( Integer.toString(pitStones[pit]), board.getPitCenterX(pit) + cx, board.getPitCenterY(pit) + cy );
		}
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
		g.setColor(Color.black);
		board.drawBoard(g);
	
		g.setColor(Color.DARK_GRAY);
		drawStones(g);
		
		g.setColor(Color.black);
		g.drawString("Player " + (currentPlayer + 1) + "'s turn", 20, 20);
	}
	
	public void doEndGame() {
		
	}
	
	public void doPlayerTurn(int pit) {
		boolean hasStones = false;
		boolean	result = moveStones(pit);
		
		// Check if the entire row is empty
		for (int i = 0; i < 6; ++i) {
			if (pitStones[i] > 0) {
				hasStones = true;
				break;
			}
		}
		
		if ( ! hasStones ) {
			doEndGame();
			return;
		}
		
		// change the player if the current turn is ended
		if ( ! result ) {
			switchTurn();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x, y;
		int mx = e.getX();
		int my = e.getY();
				
		for (int pit = 0; pit < 6; ++pit) {
			x = board.getPitX(pit);
			y = board.getPitY(pit);

			if ( mx > x && mx < x + board.pitWidth && my > y && my < y + board.pitHeight )  {
				doPlayerTurn(pit);
			}
		}
	}

	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}

}
