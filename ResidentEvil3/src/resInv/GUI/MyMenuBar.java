package resInv.GUI;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import resInv.saveStates.SaveState;

public class MyMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("serial")
	public MyMenuBar() {
		JMenu fileMenu = new JMenu("File");
		this.add(fileMenu);
		JMenuItem loadItem = new JMenuItem(new AbstractAction("Save") {
			@Override
			public void actionPerformed(ActionEvent e) {
				SaveState s = new SaveState(true);
				s.save();
			}
		});
		fileMenu.add(loadItem);
		JMenuItem saveItem = new JMenuItem(new AbstractAction("Load") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SaveState s = new SaveState(false);
				s.load();
			}
		});
		fileMenu.add(saveItem);
	}

}
