package resInv.saveStates;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import resInv.character.Character;
import resInv.health.HealthStatus;
import resInv.inventorys.Inventory;
import resInv.inventorys.Item;

public class SaveState implements Serializable {
	private static final long serialVersionUID = 1L;

	private int character; //gibt an welcher Character gespeichert wird
	private int health; //gibt das Leben des Characters an
	private Item[] items;

	//Erstellt einene Speicherstand
	public SaveState(boolean isSaving) {
		if(isSaving) {
			this.character = Character.charState;
			this.health = HealthStatus.healthState;
			this.items = Inventory.containedItems;
		}
	}

	public int getCharacter() {
		return this.character;
	}

	public int getHealth() {
		return this.health;
	}

	public Item[] getItems() {
		return this.items;
	}

	//Zeigt alle wichtigen Inhalte des Characters an
	private void printSaveState(){
		System.out.println("Charnummer = "+this.character);
		System.out.println("Health = "+this.health);
		for(Item i : items) {
			System.out.println("Das habe ich noch dabei: " + i);
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
		printSaveState();
	}


	public SaveState load(){
		System.out.println("try to load");
		Inventory.clearInvetory();
		ObjectInputStream in;
		SaveState st = null;
		try {
			in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("saveState.ser")));
			st = (SaveState) in.readObject();
			in.close();
		} catch (IOException e) {
			System.out.println("Can't load file. Try default file.");
			try {
			in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("default.ser")));
			st = (SaveState) in.readObject();
			in.close();
			} catch (Exception ee) {
				System.out.println("No default file found!");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found!");
		}
		if(st != null) {
			System.out.println("loaded file!");
			Character.setCharacter(st.getCharacter());
			HealthStatus.setHealthState(st.getHealth());
			items = st.getItems();
			for(int i = 0; i < items.length; i++) {
				if(items[i] != null) {
					Inventory.addItem(items[i], i);
				}
			}
		}
		return st;
	}

}
