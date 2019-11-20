package ResInv;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class File {
	public static JLabel file; //JLabel von File
	private static final int fileAmount=15; //max 30
	public static MenuFile[] files = new MenuFile[fileAmount]; //Alle gefundenen MenuItems in einer Liste
	public static JLabel[] jMenuFiles = new JLabel[fileAmount]; //Alle MenuItems als JLabel
	public static JLabel menuFileFrame; //Auswahlsfeld
	public static int menuFilePosition; //Gibt die Position des Filesystems an
	//initialisiert Inventarpunkt File
	public static void initFile() {
		menuFilePosition=0;
		file=new JLabel(new ImageIcon("rsc/file_meu.png"));
		menuFileFrame=new JLabel(new ImageIcon("rsc/menuFile_frame.png"));
		GUI.nemesisLabel.add(menuFileFrame);
		GUI.nemesisLabel.add(file);
		menuFileFrame.setVisible(false);
		file.setVisible(false);
	}
	//Zeigt die Files an
	public static void showFileBackground() {
		if(!file.isVisible()) {
			file.setBounds(-106,7,GUI.width,GUI.height);
			file.setVisible(true);
			setFrameBounds();
			menuFileFrame.setVisible(true);
			showFiles();
		}else {
			file.setVisible(false);
			menuFileFrame.setVisible(false);
			hideFiles();
		}
		GUI.nemesisLabel.updateUI();
		
	}
	//initialisiert die Files -> evtl über FileLoader
	public static void initFileSection() {
		for(int i=0;i<fileAmount;i++) {
			files[i]= new MenuFile(null, null, null);
		}
		files[7]=new MenuFile("Game Instructions A", null, "rsc/menuFile_book_a.png");
		fillJMenuFiles();
	}
	//Verknüpft die JLabels mit den MenuFiles
	public static void fillJMenuFiles() {
		for(int i=0; i<fileAmount; i++) {
			if(files[i]!=null) {
				jMenuFiles[i]=new JLabel(files[i].getImageIcon());
				jMenuFiles[i].setBounds(calcX(i), calcY(i), 100, 100);
				jMenuFiles[i].setVisible(false);
				GUI.nemesisLabel.add(jMenuFiles[i]);
			}
		}
	}
	//Zeigt die Files an
	public static void showFiles() {
		for(int i=0; i<fileAmount;i++) {
			if(jMenuFiles[i]!=null) {
				jMenuFiles[i].setVisible(true);
			}
		}
	}
	//Verbirgt die Files
	public static void hideFiles() {
		menuFilePosition=0;
		for(int i=0; i<fileAmount; i++) {
			if(jMenuFiles[i]!=null) {
				jMenuFiles[i].setVisible(false);
			}
		}
	}
	//Berechnet die X-Position der JLabels
	private static int calcX(int i) {
		correctPos();
		if(i==0||i==5||i==10) {
			return 0;
		}else if(i==1||i==6||i==11) {
			return 57;
		}else if(i==2||i==7||i==12) {
			return 2*57;
		}else if(i==3||i==8||i==13) {
			return 3*57;
		}else {
			return 4*57;
		}
	}
	//Berechnet die Y-Position der JLabels
	private static int calcY(int i) {
		correctPos();
		if(i>=0&&i<=4) {
			return 143;
		}else if(i>=5&&i<=9) {
			return 200;
		}else {
			return 257;
		}
	}
	//schiebt das Auswahlframe auf die richtige Stelle
	public static void setFrameBounds(){
		if(menuFilePosition>=0 && menuFilePosition<=4) {
			menuFileFrame.setBounds(23+menuFilePosition*57, 166, 55, 55);
		}else if(menuFilePosition>=5 && menuFilePosition<=9) {
			menuFileFrame.setBounds(23+(menuFilePosition-5)*57, 166+57, 55, 55);
		}else if(menuFilePosition>=10 && menuFilePosition<=14) {
			menuFileFrame.setBounds(23+(menuFilePosition-10)*57, 166+2*57, 55, 55);
		}
	}
	//Verschiebt das Auswahlframe nach unten
	public static void down() {
		correctPos();
		if(menuFilePosition<10) {
			menuFilePosition+=5;
		}
		setFrameBounds();
	}
	//Verschiebt das Auswahlframe nach oben
	public static void up() {
		correctPos();
		if(menuFilePosition>4) {
			menuFilePosition-=5;
		}
		setFrameBounds();
	}
	//Verschiebt das Auswahlframe nach links
	public static void left() {
		correctPos();
		if((menuFilePosition!=0||menuFilePosition!=5||menuFilePosition!=10) && menuFilePosition>0) {
			menuFilePosition--;
		}
		setFrameBounds();
	}
	//Verschiebt das Auswahlframe nach rechts
	public static void right() {
		correctPos();
		if((menuFilePosition!=4||menuFilePosition!=9||menuFilePosition!=14) && menuFilePosition<14) {
			menuFilePosition++;
		}
		setFrameBounds();
	}
	//korrigiert die Position wenn sie größer als 14 ist
	private static void correctPos() {
		if(menuFilePosition>14) {
			menuFilePosition-=14;
		}
	}
}
