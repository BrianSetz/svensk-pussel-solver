package nl.svenskpusselsolver.gui.panel;

import java.awt.Color;

import nl.svenskpusselsolver.dataobjects.StaticBox;

public class StaticBoxPanel extends BoxPanel {
	private static final long serialVersionUID = 3465280331617203784L;

	public StaticBoxPanel(StaticBox staticBox) {
		super(staticBox);
	
		// Change appearance
		this.setBackground(Color.gray);
	}

}
