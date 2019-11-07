package SaveStates;

import ResInv.Character;
import ResInv.HealthStatus;
import ResInv.Inventory;

public class SaveState {
	private int character; //gibt an welcher Character gespeichert wird
	private int health; //gibt das Leben des Characters an
	private int[][] items; //ID and amount
	//Erstellt einene Speicherstand
	public SaveState() {
		this.character = Character.charState;
		this.health = HealthStatus.healthState;
		this.items = new int[8][2];
		for(int i=0; i<8; i++) {
			if(Inventory.containedItems[i]!=null) {
				items[i][0]=Inventory.containedItems[i].getItemId();
				if(Inventory.containedItems[i].isCanStack()) {
					items[i][1]=Inventory.containedItems[i].getAmount();
				}
			}else {
				items[i][0]=-1; //default wert für leere Items
			}
		}
	}
	
	public int getCharacter() {
		return this.character;
	}
	
	public int getHealth() {
		return this.health;
	}
	//Zeigt alle wichtigen Inhalte des Characters an
	public void printSaveState(){
		System.out.println("Charnummer = "+this.character);
		System.out.println("Health = "+this.health);
		for(int i=0; i<8; i++) {
			System.out.println("In Inventory Slot "+i+" befindet sich Item "+items[i][0]+" und ist "+items[i][1]+" mal vorhanden.");
		}
	}
}
