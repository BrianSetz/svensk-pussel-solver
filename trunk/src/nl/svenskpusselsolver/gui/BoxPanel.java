package nl.svenskpusselsolver.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class BoxPanel extends JPanel {
	public static final int TYPE_BOX = 0;
	public static final int TYPE_WORDBOX = 1;
	public static final int TYPE_LETTERBOX = 2;
	
	private int type = TYPE_BOX;
	
	public BoxPanel() {
		super();
		
		this.setPreferredSize(new Dimension(25, 25));
		this.updateType(type);		
		this.addMouseListener(new BoxPanelMouseListener(this));
	}

	private void updateType(int newType) {
		type = newType;
		
		switch(newType){ 
			case TYPE_BOX:
				this.setBackground(Color.gray);
				break;
				
			case TYPE_WORDBOX:
				this.setBackground(Color.lightGray);
				break;
				
			case TYPE_LETTERBOX:
				this.setBackground(Color.white);
				break;				
		}
		
		this.updateUI();
	}
	
	private class BoxPanelMouseListener implements MouseListener {
		private BoxPanel boxPanel;

		public BoxPanelMouseListener(BoxPanel boxPanel) {
			this.boxPanel = boxPanel;
		}

		@Override
		public void mouseClicked(MouseEvent e) {			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			switch(boxPanel.type){ 
				case TYPE_BOX:
					boxPanel.updateType(TYPE_WORDBOX);
					break;
					
				case TYPE_WORDBOX:
					boxPanel.updateType(TYPE_LETTERBOX);
					break;
					
				case TYPE_LETTERBOX:
					boxPanel.updateType(TYPE_BOX);
					break;				
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}
}
