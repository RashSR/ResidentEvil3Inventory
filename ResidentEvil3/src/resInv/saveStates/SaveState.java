package resInv.saveStates;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import resInv.Character.Character;
import resInv.Health.HealthStatus;
import resInv.Inventorys.Inventory;

public class SaveState implements Serializable {
	private static final long serialVersionUID = 1L;

	private int character; //gibt an welcher Character gespeichert wird
	private int health; //gibt das Leben des Characters an
	private int[][] items; //ID and amount
	//Erstellt einene Speicherstand
	public SaveState(boolean isSaving) {
		if(isSaving) {
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

	public void save(){
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("saveState.ser")));
			out.writeObject(this);
			out.close();
		} catch (IOException e) {
			System.out.println("Could not save file!");
		}
		System.out.println("Saved file!");
	}


	public SaveState load(){
		System.out.println("try to load");
		ObjectInputStream in;
		SaveState st = null;
		try {
			in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("saveState.ser")));
			st = (SaveState) in.readObject();
			in.close();
		} catch (IOException e) {
			System.out.println("Can't load file.");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found!");
		}
		System.out.println("loaded file!");
		Character.setCharacter(st.getCharacter());
		HealthStatus.setHealthState(st.getHealth());
		return st;

	}
}
