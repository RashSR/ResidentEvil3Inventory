package ResInv;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class HealthStatus extends Thread{
	public static int healthState = 0; //Zeigt an in welchem Healtstatus der Spieler gerade ist
	public static boolean running=false; //Gibt an ob Thread läuft
	public static JLabel[] healthBeatFULL=new JLabel[26]; //einzelne JLabels für die healtbars
	
	public HealthStatus() {
		running=true;
		
		for(int i=0; i<26;i++) {
			healthBeatFULL[i]=new JLabel(new ImageIcon("rsc/fullH/fullhealth"+i+".png"));
			healthBeatFULL[i].setBounds(103, 42, 150, 100);
		}
		
	}
	//Setzt das Leben des Spielers zurück
	public static void decreaseHealthStatus() {
		healthState++;
		if(healthState == 3) {
			healthState--;
			return;
		}
		showHealthState();
	}
	//Erhöht das Leben des Spielers falls er ein heilendes Kraut isst
	public static void eatHerb(HerbType h) {
		if(h==HerbType.RED_HERB) {
			return;
		}
		if(healthState>0) {
			if(h == HerbType.GG_HERB || h == HerbType.GR_HERB) {
				healthState=0;
			}else if(h==HerbType.GREEN_HERB){
				healthState--;
			}
		}else {
			return;
		}
		removeHerb();
		showHealthState();
		GUI.fillItemDescriptionArray(true);
	}
	//Entfernt das Kraut aus dem Inventar und GUI
	private static void removeHerb() {
		Inventory.containedItems[Inventory.inventoryState]=null;
		GUI.itemTextureWithInventoryPosition.get(Inventory.hashMapSequence(Inventory.inventoryState)).setVisible(false);
		GUI.itemTextureWithInventoryPosition.remove(Inventory.hashMapSequence(Inventory.inventoryState));
	}
	//Zeigt den HealthStatus im Inventar
	public static void showHealthState() {
		GUI.fillStatusArrays(true);
	}
	@Override
	public void run() {
		int state = healthState;
		int zaehler=0;
		while(running) {
			if(state==HealthState.FULLHEALTH.getHealthState()) {
				if(zaehler==0) {
					GUI.statusTexture[state].setVisible(false);
					GUI.statusTexture[HealthState.FULLHEALTH.getHealthState()] = healthBeatFULL[0];
					GUI.nemesisLabel.add(GUI.statusTexture[HealthState.FULLHEALTH.getHealthState()]);
					//System.out.println("ist jetzt wieder voll");
				}else {
					GUI.statusTexture[state].setVisible(false);
					GUI.statusTexture[HealthState.FULLHEALTH.getHealthState()] = healthBeatFULL[zaehler];
					GUI.nemesisLabel.add(GUI.statusTexture[HealthState.FULLHEALTH.getHealthState()]);
					//System.out.println("sollte was anderes machen");
				}
				zaehler++;
				if(zaehler==26) {
					zaehler=0;
				}
				showHealthState();
			}
			if(state!=healthState) {
				System.out.println("Es gab eine Veränderung.");
				state=healthState;
			}else {
				//System.out.println("der state ist ja der selbe");
			}
			//System.out.println("Mein Status = "+healthState+" der erste status: "+state);
			try {
				Thread.sleep(90);
			} catch (InterruptedException e) {
				System.out.println("Es gab ein Problem!");
			}
		}
	}
}