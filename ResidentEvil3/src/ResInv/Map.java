package ResInv;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
//https://www.evilresource.com/resident-evil-3-nemesis/maps/police-station#policestation1f
public class Map {
	public static JLabel map; //JLabel der Map
	public static boolean visible=false; //Gibt an ob Map gezeigt wird
	public static JLabel[] player_arrow_active = new JLabel[4];
	public static int active_arrow_count=0; //Gibt an in welche Richtung der Pfeil zeigt
	public static int room = 0; //Aktuelle Raum-nummer
	public static int mapNr=0; //Gibt die aktuelle Karte an
	//initialisiert die Map
	public static void initMap() {
		if(mapNr==0) {
			map=new JLabel(new ImageIcon("maps/map_policestation_1.png"));
		}
		player_arrow_active[0]=new JLabel(new ImageIcon("maps/player_arrow_north.png"));
		player_arrow_active[1]=new JLabel(new ImageIcon("maps/player_arrow_east.png"));
		player_arrow_active[2]=new JLabel(new ImageIcon("maps/player_arrow_south.png"));
		player_arrow_active[3]=new JLabel(new ImageIcon("maps/player_arrow_west.png"));
		for(int i=0;i<4;i++) {
			GUI.nemesisLabel.add(player_arrow_active[i]);
		}
		GUI.nemesisLabel.add(map);
		PlayerArrow f = new PlayerArrow();
		f.start();
	}
	//Zeigt die Karte
	public static void showMap() {
		if(!visible) {
			map.setBounds(0,0,GUI.width,GUI.height);
			setPlayerLoc();
			map.setVisible(true);
			for(int i=0;i<4;i++) {
				player_arrow_active[i].setVisible(false);
			}
			player_arrow_active[active_arrow_count].setVisible(true);
			visible=true;
		}else {
			map.setVisible(false);
			player_arrow_active[active_arrow_count].setVisible(false);
			visible=false;
		}
		GUI.nemesisLabel.updateUI();
	}
	//Zeigt den SpielerPfeil an
	public static void showPlayerArrow() {
		if(visible) {
			setPlayerLoc();
		}
	}
	//Wechselt in den nächsten Raum
	public static void changeRoom() {
		room++;
		if(room>7) {
			room=0;
		}
		showPlayerArrow();
		PlayerArrow.resetOfsets();
	}
	//Wechselt in den angegeben Raum
	public static void changeRoom(int roomNr) {
		if(roomNr==-1) {
			System.out.println("falsche raum ID");
		}
		if(roomNr>=0 && roomNr<=7) {
			room=roomNr;
			showPlayerArrow();
			PlayerArrow.resetOfsets();
		}
	}
	//Legt die X und Y Komponente des SpielerPfeils fest
	public static void setPlayerLoc() {
		int x=PlayerArrow.xOfset;
		int y=PlayerArrow.yOfset;
		for(int i=0;i<4;i++) {
			if(room==0) {
				player_arrow_active[i].setBounds(253+x, 377+y, 20, 40);
			}else if(room==1){
				player_arrow_active[i].setBounds(257+x, 300+y, 20, 40);
			}else if(room==2){
				player_arrow_active[i].setBounds(150, 220, 20, 40);
			}else if(room==3){
				player_arrow_active[i].setBounds(120, 150, 20, 40);
			}else if(room==4){
				player_arrow_active[i].setBounds(170, 140, 20, 40);
			}else if(room==5){
				player_arrow_active[i].setBounds(120, 100, 20, 40);
			}else if(room==6){
				player_arrow_active[i].setBounds(57, 130, 20, 40);
			}else if(room==7){
				player_arrow_active[i].setBounds(200, 130, 20, 40);
			}else {
				player_arrow_active[i].setBounds(0, 0, 0, 0);
			}
		}
	}
	
	public static int canGoToNextRoom() {
		int x=PlayerArrow.xOfset;
		int y=PlayerArrow.yOfset;
		if(mapNr==0) {
			if(room==0) {
				if(y==-59&&x>=0&&x<=8) { //TODO: muss überprüfen ob Tür aufmachbar ist
					return 1;
				}
			}
		}
		return -1;
	}
}