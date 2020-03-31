package resInv.Inventorys;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import resInv.GUI.GUI;
import resInv.IO.FileLoader;

public class File {
	public static JLabel file; //JLabel von File
	private static final int fileAmount=30; //max 30
	public static MenuFile[] files = new MenuFile[fileAmount]; //Alle gefundenen MenuItems in einer Liste
	public static JLabel[] jMenuFiles = new JLabel[fileAmount]; //Alle MenuItems als JLabel
	public static JLabel menuFileArrowRight; //AuswahlPfeilRechts
	public static JLabel menuFileArrowLeft; //AuswahlPfeilLinks
	private static final int menuFileArrowX=273; //Gibt die X-Koordinate des AuwahlsArrow an
	private static final int MAX_ARROW_OFFSET = 2; //Gibt an wie weit der AuswahlsArrow nach rechts und links wackelt
	private static final int MAX_SPEED = 150; //Gibt an wie schnell der AuswahlsArrow nach rechts und links wackelt
	public static JLabel menuFileFrame; //Auswahlsfeld
	public static int menuFilePosition; //Gibt die Position des Filesystems an
	public static int menuFilePage; //Gibt an auf welcher Seite man ist
	//initialisiert Inventarpunkt File
	public static void initFile() {
		menuFilePosition=0;
		menuFilePage=0;
		file=new JLabel(new ImageIcon("rsc/file_meu.png"));
		menuFileFrame=new JLabel(new ImageIcon("rsc/menuFile_frame.png"));
		menuFileArrowRight=new JLabel(new ImageIcon("rsc/menuFile_arrowRight.png"));
		menuFileArrowLeft=new JLabel(new ImageIcon("rsc/menuFile_arrowLeft.png"));
		GUI.nemesisLabel.add(menuFileArrowRight);
		GUI.nemesisLabel.add(menuFileArrowLeft);
		GUI.nemesisLabel.add(menuFileFrame);
		GUI.nemesisLabel.add(file);
		menuFileFrame.setVisible(false);
		file.setVisible(false);
		menuFileArrowRight.setVisible(false);
		menuFileArrowLeft.setVisible(false);
	}
	//Zeigt den grünen FileBackground und Pfeil an
	public static void showFileBackground() {
		if(!file.isVisible()) {
			file.setBounds(-106,7,GUI.width,GUI.height);
			menuFileArrowRight.setBounds(menuFileArrowX, 195, 120, 120);
			menuFileArrowLeft.setBounds(menuFileArrowX, 195, 120, 120);
			menuFileArrowRight.setVisible(true);
			file.setVisible(true);
			setFrameBounds();
			menuFileFrame.setVisible(true);
			showFiles();
			MenuFileArrowThread f = new MenuFileArrowThread();
			f.start();
		}else {
			file.setVisible(false);
			menuFileFrame.setVisible(false);
			menuFileArrowRight.setVisible(false);
			menuFileArrowLeft.setVisible(false);
			hideFiles();
		}
		GUI.nemesisLabel.updateUI();
		
	}
	//initialisiert die Files
	public static void initFileSection() {
		FileLoader fl = new FileLoader("data/files.txt");
		ArrayList<String> s = fl.readFile();
		int j=0;
		if(s!=null){
			for(String sr : s) {
				String[] parts = sr.split(";");
				for(int i = 0; i<parts.length; i++) {
					if(i==parts.length-1) {
						files[j]=new MenuFile(checkIfNull(parts[0]), checkIfNull(parts[1]), checkIfNull(parts[2]));
						j++;
					}
				}
			}
		}
		fillJMenuFiles();
	}
	//Überprüft ob im String null steht
	public static String checkIfNull(String s) {
		if(s.equals("null")) {
			return null;
		}else {
			return s;
		}
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
			if(menuFilePage==0 && i<15) {
				if(jMenuFiles[i]!=null) {
					jMenuFiles[i].setVisible(true);
				}
			}else if(menuFilePage==1 && i>=15) {
				if(jMenuFiles[i]!=null) {
					jMenuFiles[i].setVisible(true);
				}
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
		GUI.SLarrowDown.setVisible(false);
	}
	//Berechnet die X-Position der JLabels
	private static int calcX(int i) {
		if(i>14) {
			i-=15;
		}
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
		if(i>14) {
			i-=15;
		}
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
			GUI.fillItemDescriptionArray(false);
		}
		setFrameBounds();
	}
	//Verschiebt das Auswahlframe nach oben
	public static void up() {
		correctPos();
		if(menuFilePosition>4) {
			menuFilePosition-=5;
			GUI.fillItemDescriptionArray(false);
		}
		setFrameBounds();
	}
	//Verschiebt das Auswahlframe nach links
	public static void left() {
		correctPos();
		if(menuFilePosition==0||menuFilePosition==5||menuFilePosition==10) {
			if(menuFilePage>0) {
				menuFilePage--;
				menuFileArrowRight.setVisible(true);
				menuFileArrowLeft.setVisible(false);
			}
			else {
				return;
			}
			int startMenuFilePosition=menuFilePosition;
			hideFiles();
			if(startMenuFilePosition==0) {
				menuFilePosition=4;
			}else if(startMenuFilePosition==5) {
				menuFilePosition=9;
			}else if(startMenuFilePosition==10) {
				menuFilePosition=14;
			}
			showFiles();
			GUI.fillItemDescriptionArray(false);
		} else if((menuFilePosition!=0||menuFilePosition!=5||menuFilePosition!=10) && menuFilePosition>0) {
			menuFilePosition--;
			GUI.fillItemDescriptionArray(false);
		}
		setFrameBounds();
	}
	//Verschiebt das Auswahlframe nach rechts
	public static void right() {
		correctPos();
		if(menuFilePosition==14||menuFilePosition==4||menuFilePosition==9) {
			if(menuFilePage<1) {
				menuFilePage++;
				menuFileArrowRight.setVisible(false);
				menuFileArrowLeft.setVisible(true);
			}else {
				return;
			}
			//menuFilePosition++;
			int startMenuFilePosition=menuFilePosition;
			hideFiles();
			if(startMenuFilePosition==4) {
				menuFilePosition=0;
			}else if(startMenuFilePosition==9) {
				menuFilePosition=5;
			}else if(startMenuFilePosition==14) {
				menuFilePosition=10;
			}
			showFiles();
			correctPos();
			GUI.fillItemDescriptionArray(false);
		}else if((menuFilePosition!=4||menuFilePosition!=9||menuFilePosition!=14) && menuFilePosition<14) {
			menuFilePosition++;
			GUI.fillItemDescriptionArray(false);
		}
		setFrameBounds();
	}
	//korrigiert die Position wenn sie größer als 14 ist
	private static void correctPos() {
		if(menuFilePosition>14) {
			menuFilePosition-=15;
		}
	}
	
	public static void hideMenuFileArrow() {
		menuFileArrowLeft.setVisible(false);
		menuFileArrowRight.setVisible(false);
	}
	
	public static int getMenuFileArrowX() {
		return menuFileArrowX;
	}
	
	public static boolean isRightVisible() {
		return menuFileArrowRight.isVisible();
	}
	
	static class MenuFileArrowThread extends Thread{
		public MenuFileArrowThread() {
			
		}
		public void run(){
			int zaehler = -MAX_ARROW_OFFSET;
			boolean posDir=true;
			while(!Thread.interrupted()&&menuFileFrame.isVisible()) {
				if(zaehler <= MAX_ARROW_OFFSET) {
					boolean isRight = isRightVisible();
					hideMenuFileArrow();
					menuFileArrowRight.setBounds(menuFileArrowX+zaehler, 195, 120, 120);
					menuFileArrowLeft.setBounds(menuFileArrowX+zaehler, 195, 120, 120);
					menuFileArrowRight.setVisible(isRight);
					menuFileArrowLeft.setVisible(!isRight);
				}
				try {
					Thread.sleep(MAX_SPEED);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(posDir) {
					zaehler++;
					if(zaehler==MAX_ARROW_OFFSET) {
						posDir=false;
					}
				}else {
					zaehler--;
					if(zaehler==-MAX_ARROW_OFFSET) {
						posDir=true;
					}
				}
			}
		}
	}

}