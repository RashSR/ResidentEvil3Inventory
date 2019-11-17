package ResInv;

import javax.swing.ImageIcon;

public class MenuFile {
	private String name;
	private String text;
	private ImageIcon file;
	
	public MenuFile(String name, String text, String file) {
		this.name=name;
		this.text=text;
		this.file=new ImageIcon(file);
	}
	
	public String getName() {
		return this.name;
	}
	public String getText() {
		return this.text;
	}
	public ImageIcon getImageIcon() {
		return this.file;
	}
}
