package presentationLayer;

import javax.swing.JButton;

public class JRowButton extends JButton {
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	private int row;
}
