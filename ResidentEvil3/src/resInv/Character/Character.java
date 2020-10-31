package resInv.Character;

import resInv.GUI.GUI;

//Enum welches die unterschiedlichen Charaktere verwaltet
public enum Character {
	JILL("JILL", 0, 0), NEMESIS("NEMESIS", 19, 1), LEON("LEON", 7, 0), CHRIS("CHRIS", 10, 0), CLAIRE("CLAIRE", 15, 0), REBECCA("REBECCA", 18, 2), CARLOS("CARLOS", 19, 0);
	public static int charState=0; //Gibt aktuellen Char an
	private String name;//Gibt Namen an
	private int xOffset;//Verschiebt das Name Tag �ber dem Charakterportr�t
	private int fontSizeOffset;//Setzt die Schriftgr��e fest
	//Konstruktor
	private Character(String name, int xOffset, int fontSizeOffset) {
		this.name = name;
		this.xOffset=xOffset;
		this.fontSizeOffset=fontSizeOffset;
	}
	public String getName() {
		return this.name;
	}
	public int getXOffset() {
		return this.xOffset;
	}
	public int getFontSizeOffset() {
		return this.fontSizeOffset;
	}
	//Ver�ndert das NameTag und Portr�t des Charakters
	public static void changeCharacter() {
		charState++;
		if(charState==7) {
			charState=0;
		}
		GUI.fillCharArrays(true);
	}
	
	public static void setCharacter(int index) {
		charState = index;
		GUI.fillCharArrays(true);
	}
}