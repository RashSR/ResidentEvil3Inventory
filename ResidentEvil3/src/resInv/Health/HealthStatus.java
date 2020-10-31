package resInv.Health;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import resInv.GUI.GUI;
import resInv.Inventorys.Inventory;

public class HealthStatus extends Thread{
	public static int healthState = 0; //Zeigt an in welchem Healtstatus der Spieler gerade ist
	public static JLabel[] healthBeatFULL=new JLabel[26]; //einzelne JLabels für die full healthbars
	public static JLabel[] healthBeatHARMED=new JLabel[32]; //einzelne JLabels für die harmed healthbars
	public static JLabel[] healthBeatWOUNDED=new JLabel[39]; //einzelne JLabels für die wounded healthbars
	private int heartSpeed=100; //Gibt die Geschwindigkeit der Herzschläge an
	//Konstruktor lädt die Dateien ins JLabel-Array
	public HealthStatus() {
		for(int i=0; i<healthBeatWOUNDED.length;i++) {
			if(i<healthBeatFULL.length) {
				healthBeatFULL[i]=new JLabel(new ImageIcon("rsc/fullH/fullhealth"+i+".png"));
				healthBeatFULL[i].setBounds(103, 42, 150, 100);
			}
			if(i<healthBeatHARMED.length) {
				healthBeatHARMED[i]=new JLabel(new ImageIcon("rsc/harM/harmed"+i+".png"));
				healthBeatHARMED[i].setBounds(103, 42, 150, 100);
			}
			if(i<healthBeatWOUNDED.length) {
				healthBeatWOUNDED[i]=new JLabel(new ImageIcon("rsc/wounD/wounded"+i+".png"));
				healthBeatWOUNDED[i].setBounds(103, 42, 150, 100);
			}
		}
	}
	
	public static void setHealthState(int health) {
		healthState = health;
	}
	
	//Setzt das Leben des Spielers zurück
	public static void decreaseHealthStatus() {
		healthState++;
		if(healthState == 3) {
			healthState--;
			return;
		}
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
	//Thread zeichnet den Herzschlag ins GUI. Bilder werden nacheinander angezeigt.
	@Override
	public void run() {
		int state = healthState;
		int zaehler=0;
		int speed=1;
		while(!Thread.interrupted()) {
			GUI.statusTexture[state].setVisible(false);
			if(state==HealthState.FULLHEALTH.getHealthState()) {
				speed=heartSpeed;
				if(zaehler==0) {
					GUI.statusTexture[HealthState.FULLHEALTH.getHealthState()] = healthBeatFULL[0];
					GUI.nemesisLabel.add(GUI.statusTexture[HealthState.FULLHEALTH.getHealthState()]);
				}else {
					GUI.statusTexture[HealthState.FULLHEALTH.getHealthState()] = healthBeatFULL[zaehler];
					GUI.nemesisLabel.add(GUI.statusTexture[HealthState.FULLHEALTH.getHealthState()]);
				}
				zaehler++;
				if(zaehler==healthBeatFULL.length) {
					zaehler=0;
				}
			}else if(state==HealthState.HARMED.getHealthState()) {
				speed=heartSpeed*3/4;
				if(zaehler==0) {
					GUI.statusTexture[HealthState.HARMED.getHealthState()] = healthBeatHARMED[0];
					GUI.nemesisLabel.add(GUI.statusTexture[HealthState.HARMED.getHealthState()]);
				}else {
					GUI.statusTexture[HealthState.HARMED.getHealthState()] = healthBeatHARMED[zaehler];
					GUI.nemesisLabel.add(GUI.statusTexture[HealthState.HARMED.getHealthState()]);
				}
				zaehler++;
				if(zaehler==healthBeatHARMED.length) {
					zaehler=0;
				}
			}else if(state==HealthState.WOUNDED.getHealthState()) {
				speed=heartSpeed*1/2;
				if(zaehler==0) {
					GUI.statusTexture[HealthState.WOUNDED.getHealthState()] = healthBeatWOUNDED[0];
					GUI.nemesisLabel.add(GUI.statusTexture[HealthState.WOUNDED.getHealthState()]);
				}else {
					GUI.statusTexture[HealthState.WOUNDED.getHealthState()] = healthBeatWOUNDED[zaehler];
					GUI.nemesisLabel.add(GUI.statusTexture[HealthState.WOUNDED.getHealthState()]);
				}
				zaehler++;
				if(zaehler==healthBeatWOUNDED.length) {
					zaehler=0;
				}
			}
			showHealthState();
			if(state!=healthState) {
				if(((state==HealthState.WOUNDED.getHealthState() || state==HealthState.HARMED.getHealthState())&&HealthState.FULLHEALTH.getHealthState()==healthState)||healthState==HealthState.WOUNDED.getHealthState()&&HealthState.HARMED.getHealthState()==state) {
					GUI.statusTexture[HealthState.HARMED.getHealthState()] = healthBeatHARMED[0];
					GUI.statusTexture[HealthState.HARMED.getHealthState()].setVisible(false);
				}else if(healthState==HealthState.HARMED.getHealthState()&&HealthState.FULLHEALTH.getHealthState()==state) {
					GUI.statusTexture[HealthState.FULLHEALTH.getHealthState()] = healthBeatFULL[0];
					GUI.statusTexture[HealthState.FULLHEALTH.getHealthState()].setVisible(false);
				}else if((healthState==HealthState.HARMED.getHealthState() || healthState==HealthState.FULLHEALTH.getHealthState())&&HealthState.WOUNDED.getHealthState()==state) {
					GUI.statusTexture[HealthState.WOUNDED.getHealthState()] = healthBeatWOUNDED[0];
					GUI.statusTexture[HealthState.WOUNDED.getHealthState()].setVisible(false);
				}
				state=healthState;
				zaehler=0;
			}
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				
			}
		}
	}
}