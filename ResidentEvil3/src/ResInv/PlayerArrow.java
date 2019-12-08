package ResInv;

public class PlayerArrow extends Thread{
	public static int delay=200; //Gibt an wie viel Zeit zwischen Pfeil sehen und nicht sehen liegt
	public static int xOfset=0; //Gibt den Ofset für die x-Achse an
	public static int yOfset=0; //Gibt den Ofset für die y-Achse an
	public static boolean blinking=false; //falls true -> Pfeil kann sich nicht bewegen
	
	public static boolean isBlinking() {
		return blinking;
	}
	//Lässt den SpielerPfeil blinken
	@Override 
	public void run() {
		while(!Thread.interrupted()) {
			if(blinking) {
				if(Map.visible) {
					Map.player_arrow_active[Map.active_arrow_count].setVisible(false);
					try {
						Thread.sleep(delay*2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(Map.visible) {
						Map.player_arrow_active[Map.active_arrow_count].setVisible(true);
					}
					try {
						Thread.sleep(delay*3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}else {
					Map.player_arrow_active[Map.active_arrow_count].setVisible(false);
				}
			}
		}
	}
	//Bewegt den Spielerpfeil in die schauende Richtung
	public static void goForward() {
		if(!blinking) {
			System.out.println("Ich bin bei Position x:"+xOfset+" und y:"+yOfset);
			if(Map.active_arrow_count==0) {
				if(canGoUp()) {
					yOfset--;
				}
			}else if(Map.active_arrow_count==1) {
				if(canGoRight()) {
					xOfset++;
				}
			}else if(Map.active_arrow_count==2) {
				if(canGoDown()) {
					yOfset++;
				}
			}else if(Map.active_arrow_count==3) {
				if(canGoLeft()) {
					xOfset--;	
				}
			}
			Map.setPlayerLoc();
		}
	}
	//Checkt ob man nach oben gehen kann
	private static boolean canGoUp() {
		if(Map.mapNr==0) {
			if(yOfset>-48 || (yOfset>-59&&xOfset>-5&&xOfset<19)) {
				return true;
			}else{
				System.out.println("jetzt ist schluss, y:"+yOfset);
			}
		}
		return false;
	}
	//Checkt ob man nach unten gehen kann
	private static boolean canGoDown() {
		if(Map.mapNr==0) {
			if(yOfset<0) {
				return true;
			}else{
				System.out.println("jetzt ist schluss, y:"+yOfset);
			}
		}
		return false;
	}
	//Checkt ob man nach links gehen kann
	private static boolean canGoLeft() {
		if(Map.mapNr==0) {
			if(xOfset>-15) {
				if(xOfset>-5&&yOfset<-48) {
					return false;
				}
				return true;
			}else{
				System.out.println("jetzt ist schluss, x:"+xOfset);
			}
		}
		return false;
	}
	//Checkt ob man nach rechts gehen kann
	private static boolean canGoRight() {
		if(Map.mapNr==0) {
			if(xOfset<51) {
				if(xOfset>17&&yOfset<-48) {
					return false;
				}
				return true;
			}else{
				System.out.println("jetzt ist schluss, x:"+xOfset);
			}
		}
		return false;
	}
	//Setzt den Weg zurück
	public static void resetOfsets() {
		xOfset=0;
		yOfset=0;
	}
	//Ändert ob Spieler bewegbar oder nicht ist
	public static void changeMode() {
		if(blinking) {
			blinking=false;
		}else {
			blinking=true;
		}
	}
}
