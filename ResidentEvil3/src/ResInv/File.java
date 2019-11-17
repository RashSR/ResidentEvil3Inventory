package ResInv;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class File {
	public static JLabel file; //JLabel von File
	public static MenuFile[] files = new MenuFile[15];
	//initialisiert Inventarpunkt File
	public static void initFile() {
		file=new JLabel(new ImageIcon("rsc/file_meu.png"));
		GUI.nemesisLabel.add(file);
		file.setVisible(false);
	}
	//Zeigt die Files an
	public static void showFileBackground() {
		if(!file.isVisible()) {
			file.setBounds(-106,7,GUI.width,GUI.height);
			file.setVisible(true);
		}else {
			file.setVisible(false);
		}
		GUI.nemesisLabel.updateUI();
		
	}
	
	public static void showFiles() {
		for(int i=0; i<files.length;i++) {
			//show MenuFiles
			if(files[i]==null) {
				//show deafault empty
			}
		}
	}
	
}
