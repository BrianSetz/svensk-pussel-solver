package nl.svenskpusselsolver.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class PuzzleFrame extends JFrame {
	private Container contentPane;

	public PuzzleFrame() {
		contentPane = this.getContentPane();

		this.initializePuzzle(10, 20);

		contentPane.setBackground(Color.black);
		this.setTitle("Svensk Pussel Solver");
		this.pack();
		this.setVisible(true);
	}

	private void initializePuzzle(int x, int y) {
		contentPane.setLayout(new GridLayout(y, x, 1, 1));

		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				contentPane.add(new BoxPanel());
			}
		}
	}
}
