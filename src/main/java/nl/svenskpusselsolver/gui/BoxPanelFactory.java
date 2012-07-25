package nl.svenskpusselsolver.gui;

import nl.svenskpusselsolver.dataobjects.Box;
import nl.svenskpusselsolver.dataobjects.LetterBox;
import nl.svenskpusselsolver.dataobjects.StaticBox;
import nl.svenskpusselsolver.dataobjects.WordBox;
import nl.svenskpusselsolver.gui.panel.BoxPanel;
import nl.svenskpusselsolver.gui.panel.LetterBoxPanel;
import nl.svenskpusselsolver.gui.panel.StaticBoxPanel;
import nl.svenskpusselsolver.gui.panel.WordBoxPanel;
import nl.svenskpusselsolver.gui.panel.exception.UnknownBoxException;

public class BoxPanelFactory {
	public static BoxPanel createBoxPanel(Box box) throws UnknownBoxException {
		if(box instanceof LetterBox)
			return new LetterBoxPanel((LetterBox) box);
		else if(box instanceof StaticBox)
			return new StaticBoxPanel((StaticBox) box);
		else if(box instanceof WordBox)
			return new WordBoxPanel((WordBox) box);
		
		throw new UnknownBoxException();
	}
}
