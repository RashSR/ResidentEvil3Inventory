package resInv.inventorys;

import javax.swing.ImageIcon;

public class MenuFile {
	private String name;
	private String text;
	private ImageIcon file;
	
	public MenuFile(String name, String text, String file) {
		if(name==null&&text==null&&file==null) {
			this.file=new ImageIcon("rsc/menuFile_default.png");
			this.name="Unknown";
			this.text="Unknown";
		}else {
			this.name=name;
			this.text=text;
			this.file=new ImageIcon(file);
		}
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
