package shea.mancala;

import java.awt.*;

/**
 * Draw a Mancala board
 * @author Shea Bunge
 */
class Board {
	
	final int outerPadding = 15, innerPadding = 20;
	final int pitWidth = 75, pitHeight = 90;
	final int storeWidth = 80, storeHeight = 205;
	
	public Board() {}
	
	/**
	 * Get the size of the board as a Dimension object
	 * @return
	 */
	public Dimension getSize() {
		int height = 2 * (outerPadding + pitHeight) + innerPadding;
		int width = 6 * (pitWidth + innerPadding ) + 2 * (storeWidth + outerPadding);
		return new Dimension(width, height);
	}
	
	/**
	 * Draw a row of pits
	 * @param g Graphics object
	 * @param x Beginning X position of row
	 * @param y Beginning Y position of row
	 */
	protected void drawRow(Graphics g, int x, int y) {

		for (int i = 0; i < 6; ++i ) {
			g.drawOval(x, y, pitWidth, pitHeight);
			x += pitWidth + outerPadding;
		}
	}
	
	/**
	 * Draw the storage spaces
	 * @param g Graphics object
	 */
	protected void drawStores(Graphics g) {
		int round = 30;
		int resize = 20;
		
		// begin first mancala at padding position
		g.drawRoundRect(
			outerPadding, outerPadding + resize,
			storeWidth, storeHeight - resize*2,
			round, round
		);
		
		/* second mancala must be after all six boxes,
		 * plus the first mancala, plus padding */
		int x = outerPadding + storeWidth + 6 * ( innerPadding + pitWidth );
		
		g.drawRoundRect(
			x, outerPadding + resize,
			storeWidth, storeHeight - resize*2,
			round, round
		);
	}
	
	/**
	 * Draw the board pits and stores
	 * @param g Graphics object
	 */
	public void drawBoard(Graphics g) {
		drawStores(g);
		
		int rowX = storeWidth + innerPadding * 2;
		drawRow(g, rowX, outerPadding);
		drawRow(g, rowX, outerPadding + pitHeight + innerPadding );
		
	}

	/**
	 * Retrieve the X position of a pit
	 * @param pit
	 * @return
	 */
	public int getPitX(int pit) {
		int x;
		
		// check if pit is a store
		if ( pit == 6 || pit == 13 ) {
			x = outerPadding + storeWidth / 2;
			
			// subtract pit x from screen width
			x = (pit == 6) ? getSize().width - x : x;
		} else {
			
			// reverse the top row numbers
			if (pit > 6) pit = -pit + 12;
						
			// begin with outside padding + mancala
			x = outerPadding + storeWidth;
				
			// add padding for each box
			x += outerPadding * (pit+1);
			
			// add boxes
			x += pit * pitWidth;
		}
		
		return x;
	}
	
	/**
	 * Retrieve the Y position of a pit
	 * @param pit
	 * @return
	 */
	public int getPitY(int pit) {
		
		// check if a pit is a store or in the second row
		if ( pit <= 6 || pit == 13 ) {
			return outerPadding * 2 + pitHeight;
		}
		
		return outerPadding;
	}
	
	public int getPitCenterX(int pit) {
		int x = getPitX(pit);
		
		if (pit != 6 && pit != 13) {
			x += pitWidth/2;
		}
		
		return x;
	}
	
	public int getPitCenterY(int pit) {
		int y = getPitY(pit);
		
		if (pit != 6 && pit != 13) {
			y += pitHeight/2;
		}
		
		return y;
	}
}
