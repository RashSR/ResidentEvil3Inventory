package resInv.map;

public class PlayerArrow extends Thread{
	public static int delay=200; //Gibt an wie viel Zeit zwischen Pfeil sehen und nicht sehen liegt
	public static int xOfset=0; //Gibt den Ofset für die x-Achse an
	public static int yOfset=0; //Gibt den Ofset für die y-Achse an
	public static boolean blinking=false; //falls true -> Pfeil kann sich nicht bewegen
	public static int walkSpeed=1;
	
	public static boolean isBlinking() {
		return blinking;
	}
	//Setzt den x-Offset vom Spielerpfeil
	public static void setX(int x) {
		xOfset=x;
	}
	//Setzt den y-Offset vom Spielerpfeil
	public static void setY(int y) {
		yOfset=y;
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
					yOfset-=walkSpeed;
				}
			}else if(Map.active_arrow_count==1) {
				if(canGoRight()) {
					xOfset+=walkSpeed;
				}
			}else if(Map.active_arrow_count==2) {
				if(canGoDown()) {
					yOfset+=walkSpeed;
				}
			}else if(Map.active_arrow_count==3) {
				if(canGoLeft()) {
					xOfset-=walkSpeed;	
				}
			}
			Map.setPlayerLoc();
		}
	}
	//Checkt ob man nach oben gehen kann
	private static boolean canGoUp() {
		if(Map.mapNr==0) {
			if(Map.room==0) {
				if(yOfset>-48 || (yOfset>-59&&xOfset>-5&&xOfset<19)) {
					return true;
				}
			}else if(Map.room==1) {
				if(xOfset>36&&yOfset<-6) {
					return false;
				}
				if(yOfset>-193&&xOfset>=-32) {
					return true;
				}
			}else if(Map.room==2) {
				if(yOfset>=0) {
					return true;
				}
			}else if(Map.room==3) {
				
			}else if(Map.room==4) {
				if(yOfset>-107) {
					return true;
				}
			}else if(Map.room==5) {
				
			}else if(Map.room==6) {
				
			}else if(Map.room==7) {
				if(yOfset>0) {
					return true;
				}
			}
		}
		return false;
	}
	//Checkt ob man nach unten gehen kann
	private static boolean canGoDown() {
		if(Map.mapNr==0) {
			if(Map.room==0) {
				if(yOfset<0) {
					return true;
				}
			}else if(Map.room==1) {
				if(yOfset<0&&xOfset>=-32) {
					return true;
				}
			}else if(Map.room==2) {
				
			}else if(Map.room==3) {
				
			}else if(Map.room==4) {
				if((yOfset<0&&xOfset<=4)||(xOfset==39&&yOfset<-100)) {
					return true;
				}
			}else if(Map.room==5) {
				
			}else if(Map.room==6) {
				
			}else if(Map.room==7) {
				if(yOfset<81) {
					return true;
				}
			}
		}
		return false;
	}
	//Checkt ob man nach links gehen kann
	private static boolean canGoLeft() {
		if(Map.mapNr==0) {
			if(Map.room==0) {
				if(xOfset>-15) {
					if(xOfset<-2&&yOfset<-48) {
						return false;
					}
					return true;
				}
			}else if(Map.room==1) {
				if((yOfset<=-120&&yOfset>=-122&&xOfset>=-40)||xOfset>=-30) {
						return true;
				}else if(yOfset>=-30&&yOfset<=-26&&xOfset>=-37) {
					return true;
				}
			}else if(Map.room==2) {
				if(xOfset>=-106) {
					return true;
				}
			}else if(Map.room==3) {
				
			}else if(Map.room==4) {
				if(xOfset==39&&yOfset>=-106) {
					return false;
				}
				if(xOfset>0) {
					return true;
				}
			}else if(Map.room==5) {
				
			}else if(Map.room==6) {
				
			}else if(Map.room==7) {
				if(xOfset>-17) {
					return true;
				}
			}
		}
		return false;
	}
	//Checkt ob man nach rechts gehen kann
	private static boolean canGoRight() {
		if(Map.mapNr==0) {
			if(Map.room==0) {
				if(xOfset<51) {
					if(xOfset>17&&yOfset<-48) {
						return false;
					}
					return true;
				}
			}else if(Map.room==1) {
				if(xOfset<47&&yOfset>=-8) {
					return true;
				}else if(xOfset<36) {
					return true;
				}
			}else if(Map.room==2) {
				
			}else if(Map.room==3) {
				
			}else if(Map.room==4) {
				if(xOfset<4||(yOfset==-107&&xOfset<39)) {
					return true;
				}
			}else if(Map.room==5) {
				
			}else if(Map.room==6) {
				
			}else if(Map.room==7) {
				if(xOfset<0) {
					return true;
				}
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
