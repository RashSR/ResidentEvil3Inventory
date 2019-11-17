package ResInv;

public class PlayerArrow extends Thread{
	public static int delay=200; //Gibt an wie viel Zeit zwischen Pfeil sehen und nicht sehen liegt
	//Lässt den SpielerPfeil blinken
	@Override 
	public void run() {
		while(!Thread.interrupted()) {
			if(Map.visible) {
				Map.player_arrow.setVisible(false);
				try {
					Thread.sleep(delay*2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(Map.visible) {
					Map.player_arrow.setVisible(true);
				}
				try {
					Thread.sleep(delay*3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else {
				Map.player_arrow.setVisible(false);
			}
		}
	}
}
