package ResInv;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
//https://www.evilresource.com/resident-evil-3-nemesis/maps/police-station#policestation1f
public class Map {
	public static JLabel map; //JLabel der Map
	public static boolean visible=false; //Gibt an ob Map gezeigt wird
	public static JLabel player_arrow; //JLabel des SpielerPfeils
	public static int room = 0; //Aktuelle Raum-nummer
	//initialisiert die Map
	public static void initMap() {
		map=new JLabel(new ImageIcon("maps/map_policestation_1.png"));
		player_arrow=new JLabel(new ImageIcon("maps/player_arrow.png"));
		GUI.nemesisLabel.add(player_arrow);
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
			player_arrow.setVisible(true);
			visible=true;
		}else {
			map.setVisible(false);
			player_arrow.setVisible(false);
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
	}
	//Wechselt in den angegeben Raum
	public static void changeRoom(int roomNr) {
		if(roomNr>=0 && roomNr<=7) {
			room=roomNr;
			showPlayerArrow();
		}
	}
	//Legt die X und Y Komponente des SpielerPfeils fest
	public static void setPlayerLoc() {
		if(room==0) {
			player_arrow.setBounds(270, 350, 20, 40);
		}else if(room==1){
			player_arrow.setBounds(260, 270, 20, 40);
		}else if(room==2){
			player_arrow.setBounds(150, 220, 20, 40);
		}else if(room==3){
			player_arrow.setBounds(120, 150, 20, 40);
		}else if(room==4){
			player_arrow.setBounds(170, 140, 20, 40);
		}else if(room==5){
			player_arrow.setBounds(120, 100, 20, 40);
		}else if(room==6){
			player_arrow.setBounds(57, 130, 20, 40);
		}else if(room==7){
			player_arrow.setBounds(200, 130, 20, 40);
		}else {
			player_arrow.setBounds(0, 0, 0, 0);
		}
	}		
}