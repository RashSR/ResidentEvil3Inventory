package resInv.Inventorys;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import resInv.GUI.GUI;
import resInv.GUI.KeyHandler;
import resInv.GUI.StringLabel;
import resInv.Health.HerbType;

public class Inventory {
	public static int inventoryState = 3;//Gibt an auf welchem InventarPlatz die Auswahl liegt
	public static boolean equipSlotOccupied=false;//Gibt an ob Equip-Slot ein Item beinhaltet
	public static int itemInEquipSlotId;//Gibt ItemID des ausgerüstetem Item an
	public static int itemNumberEquipSlotLink=-1;//GIBT AN IN WELCHEM SLOT DAS AUSGERÜSTETE ITEM LIEGT
	
	public static Item[] containedItems = new Item[8];//Beinhaltet die Items die im Inventar liegen
	//Wird zum Start aufgerufen um das Inventar mit Items zu befüllen
	public static void fillInventory() {
		//TODO Item über ENUM AUSWÄHLBAR
		addItem(Item.itemPool.get(4));
		addItem(Item.itemPool.get(0));
		addItem(new Item(Item.itemPool.get(6)));
		Item.itemPool.get(6).setAmount(30);
		addItem(Item.itemPool.get(5));
		addItem(Item.itemPool.get(8));
		addItem(Item.itemPool.get(6));
		addItem(Item.itemPool.get(0));
		addItem(Item.itemPool.get(1));
	}
	//Fügt Items an einem bestimmten Slot zum Inventar hinzu
	public static void addItem(Item item, int slot){
		if(containedItems[slot]==null) {
			containedItems[slot]=item;
			GUI.itemTextureWithInventoryPosition.put(hashMapSequence(slot), new JLabel(new ImageIcon(item.getFileName())));
			showItem(slot, hashMapSequence(slot));
			if(item.getMaxAmount()>1) {
				GUI.amountLable[slot] = new StringLabel(item.getAmount(),slot);
				GUI.nemesisLabel.add(GUI.amountLable[slot]);
			}
		}
	}
	//Fügt Items an chronologisch freie Stellen zum Inventar hinzu
	public static void addItem(Item item) {
		for(int i=0; i<8;i++) {
			int slot=inventorySequence(i);
			if(containedItems[slot] == null) {
				containedItems[slot]=item;
				GUI.itemTextureWithInventoryPosition.put(i, new JLabel(new ImageIcon(item.getFileName())));
				showItem(slot, i);
				if(item.getMaxAmount()>1) {
					GUI.amountLable[slot] = new StringLabel(item.getAmount(),slot);
					GUI.nemesisLabel.add(GUI.amountLable[slot]);
				}
				break;
			}
		}
	}
	//Visualisiert die Items im GUI
	private static void showItem(int slot, int i) {
		if(slot<4) {
			GUI.itemTextureWithInventoryPosition.get(i).setBounds(398, 120+((i)*72/2), 100, 100);
		}else {
			GUI.itemTextureWithInventoryPosition.get(i).setBounds(488, 120+((i-1)*72/2), 100, 100);
		}
		GUI.nemesisLabel.add(GUI.itemTextureWithInventoryPosition.get(i));
	}
	//Kombiniert Items im Inventar
	public static void combineItems(int slot_a, int slot_b) {
		//eingehende Items aufjedenfall kombinierbar
		int a=slot_a;
		int b=slot_b;
		if(containedItems[slot_a].getHerbType()==HerbType.GREEN_HERB&&containedItems[slot_b].getHerbType()==HerbType.GREEN_HERB) {
			combineBasic(slot_a, slot_b, 3);
		}else if((containedItems[slot_a].getHerbType()==HerbType.GREEN_HERB&&containedItems[slot_b].getHerbType()==HerbType.RED_HERB)||(containedItems[slot_a].getHerbType()==HerbType.RED_HERB&&containedItems[slot_b].getHerbType()==HerbType.GREEN_HERB)) {
			combineBasic(slot_a, slot_b, 2);
		}else if(containedItems[slot_a].getItemId()==4&&containedItems[slot_b].getItemId()==6 || containedItems[slot_a].getItemId()==6&&containedItems[slot_b].getItemId()==4) {
			if(containedItems[slot_b].isEquipable()) {
				slot_b=a;
				slot_a=b;
			}
			if(containedItems[slot_a].getAmount()<containedItems[slot_a].getMaxAmount()) {
				int am=refillAmount(containedItems[slot_a].getAmount(),containedItems[slot_a].getMaxAmount(),containedItems[slot_b].getAmount());
				changeAmount(am, slot_a);
				changeAmount(-1*am, slot_b);
			}else {
				//Pistol is full
			}
		}else if(containedItems[slot_a].getItemId()==containedItems[slot_b].getItemId()&&containedItems[slot_a].isCanStack()){
			int am=refillAmount(containedItems[slot_a].getAmount(), containedItems[slot_a].getMaxAmount(), containedItems[slot_b].getAmount());
			changeAmount(am, slot_a);
			changeAmount(-1*am, slot_b);
		}
		//Setzt die Kombinierslots wieder zurück
		KeyHandler.slot_a=-1;
		KeyHandler.slot_b=-1;
	}
	//Kombiniert Herbs miteinander
	private static void combineBasic(int slot_a, int slot_b, int item) {
		removeItem(slot_a);
		removeItem(slot_b);
		addItem(Item.itemPool.get(item), slot_a);
	}
	//Berechnet wie viel z.B. Bullets aufgefüllt werden können in eine Waffe
	private static int refillAmount(int amount_a, int max_a, int amount_b) {
		if(max_a-amount_a<=amount_b) {
			return max_a-amount_a;
		}else {
			return amount_b;
		}
	}
	//Zeigt alle Items im Inventar an
	public static void printItems() {
		for(int i=0; i<containedItems.length;i++) {
			if(containedItems[i]!=null) {
				System.out.println("An positon "+i+" ist Item "+containedItems[i].getItemName());
			}
		}
	}
	//Entfernt Item aus Inventar an Slot 
	public static void removeItem(int slot) {
		if(GUI.amountLable[slot]!=null) {
			GUI.amountLable[slot].setVisible(false);
		}
		containedItems[slot]=null;
		if(GUI.itemTextureWithInventoryPosition.get(hashMapSequence(slot))!=null) {
			GUI.itemTextureWithInventoryPosition.get(hashMapSequence(slot)).setVisible(false);
			GUI.itemTextureWithInventoryPosition.remove(hashMapSequence(slot));
		}
		GUI.fillItemDescriptionArray(true);
	}
	//Tauscht 2 Itempositionen im Inventory
	public static void swapItems (int slot_a, int slot_b) {
		if(inventoryState<8) {
			if((containedItems[slot_a]!=null&&containedItems[slot_b]!=null)) {
				Item item_a = containedItems[slot_a];
				Item item_b = containedItems[slot_b];
				if(slot_a!=slot_b) {
					removeItem(slot_a);
					removeItem(slot_b);
					addItem(item_a, slot_b);
					addItem(item_b, slot_a);
					checkIfEquiped(slot_a, slot_b);
				}
			}else if(containedItems[slot_a]==null&&containedItems[slot_b]!=null) {
				Item item_b = containedItems[slot_b];
				checkIfEquiped(slot_a, slot_b);
				removeItem(slot_b);
				addItem(item_b, slot_a);
			}else if(containedItems[slot_a]!=null&&containedItems[slot_b]==null) {
				Item item_a = containedItems[slot_a];
				checkIfEquiped(slot_a, slot_b);
				removeItem(slot_a);
				addItem(item_a, slot_b);
			}
		}
		GUI.fillItemDescriptionArray(true);
		KeyHandler.slot_a=-1;
		KeyHandler.slot_b=-1;
	}
	//Überprüft ob eines der beiden tauschenden Items ausgerüstet ist und verbirgt dieses dann
	private static void checkIfEquiped(int slot_a, int slot_b) {
		if(itemNumberEquipSlotLink==slot_a||itemNumberEquipSlotLink==slot_b) {
			//itemNumberEquipSlotLink=-1; Wird immer neu gesetzt bei Ausrüstung
			equipSlotOccupied=false;
			itemInEquipSlotId=-1;
			GUI.equipedE.setVisible(false);
			GUI.equipmentSlot.setVisible(false);
			GUI.equipedAmount.setVisible(false);
		}
	}
	//Getter für equipSlotOccupied
	public static boolean isEquipSlotOccupied() {
		return equipSlotOccupied;
	}
	//Verändert die Inventarauswahl nach links
	public static void changeInventoryStateLeft() {
		if(inventoryState==10) {
			return;
		}
		inventoryState-=4;
		if(inventoryState==5) {
			inventoryState=8;
		}else if(inventoryState<0 || inventoryState==4) {
			inventoryState+=4;
			return;
		}
		GUI.fillFrameArray(true);
		GUI.fillItemDescriptionArray(true);
	}
	//Verändert die Inventarauswahl nach rechts
	public static void changeInventoryStateRight() {
		inventoryState+=4;
		if(inventoryState==12) {
			inventoryState=9;
		}else if(inventoryState>7) {
			inventoryState-=4;
			return;
		}
		GUI.fillFrameArray(true);
		GUI.fillItemDescriptionArray(true);
	}
	//Verändert die Inventarauswahl nach oben
	public static void changeInventoryStateUp() {
		inventoryState++;
		if(inventoryState==9||inventoryState==10) {
			inventoryState=10;
		}else if(inventoryState==4||inventoryState==9) {
			inventoryState=8;
		}else if(inventoryState==8||inventoryState==10) {
			inventoryState = 9;
		}else if(inventoryState>10){
			inventoryState--;
		}
		GUI.fillFrameArray(true);
		GUI.fillItemDescriptionArray(true);
	}
	//Verändert die Inventarauswahl nach unten
	public static void changeInventoryStateDown() {
		inventoryState--;
		if(inventoryState==-1||inventoryState==7) {
			inventoryState=3;
		}else if(inventoryState==3||inventoryState==8) {
			inventoryState = 7;
		}
		GUI.fillFrameArray(true);
		GUI.fillItemDescriptionArray(true);
	}
	//Verändert die Anzahl eines Items im Inventar an der ausgewählten Stelle
	public static void changeAmount(int amount) {
		if(inventoryState<8) {
			if(containedItems[inventoryState]!=null) {
				if(containedItems[inventoryState].getAmount()>=0 && containedItems[inventoryState].getAmount()<=containedItems[inventoryState].getMaxAmount()) {
					if(!(containedItems[inventoryState].getAmount()+amount<0 || containedItems[inventoryState].getAmount()+amount>containedItems[inventoryState].getMaxAmount())) {
						amountChanger(amount);
					}
				}
			}
		}
	}
	//Verändert die Anzahl eines Items im Inventar an Slot
	public static void changeAmount(int amount, int slot) {
		if(slot<8) {
			if(containedItems[slot]!=null) {
				if(containedItems[slot].getAmount()>=0 && containedItems[slot].getAmount()<=containedItems[slot].getMaxAmount()) {
					if(!(containedItems[slot].getAmount()+amount<0 || containedItems[slot].getAmount()+amount>containedItems[slot].getMaxAmount())) {
						amountChanger(amount, slot);
					}
				}
			}
		}
	}
	//Setzt Anzahl eines Items hinab
	public static void decreaseAmount() {
		if(inventoryState<8) {
			if(containedItems[inventoryState]!=null) {
				if(containedItems[inventoryState].getAmount()>0) {
					amountChanger(-1);
				}
			}
		}
	}
	//Setzt Anzahl eines Items hinauf
	public static void increaseAmount() {
		if(inventoryState<8) {
			if(containedItems[inventoryState]!=null) {
				if(containedItems[inventoryState].getAmount()<containedItems[inventoryState].getMaxAmount()) {
					amountChanger(+1);
				}
			}
		}
	}
	//Verändert die Anzahl eines Items um amount in slot
	private static void amountChanger(int amount, int slot) {
		try {
			GUI.amountLable[slot].setVisible(false);
			containedItems[slot].setAmount(containedItems[slot].getAmount()+amount);
			GUI.amountLable[slot] = new StringLabel(containedItems[slot].getAmount(), slot);
			GUI.nemesisLabel.add(GUI.amountLable[slot]);	
			if(GUI.equipedAmount!=null) {
				if(containedItems[slot].getItemId()==itemInEquipSlotId) {
					GUI.equipedAmount.setVisible(false);
					GUI.equipedAmount = new StringLabel(containedItems[slot].getAmount(), 8);
					GUI.nemesisLabel.add(GUI.equipedAmount);
				}
			}
		} catch (NullPointerException e) {
		}
	}
	//Setzt Anzahl eines Items hinauf/ab an ausgewählter Stelle
	private static void amountChanger(int amount) {
		try {
			GUI.amountLable[inventoryState].setVisible(false);
			containedItems[inventoryState].setAmount(containedItems[inventoryState].getAmount()+amount);
			GUI.amountLable[inventoryState] = new StringLabel(containedItems[inventoryState].getAmount(), inventoryState);
			GUI.nemesisLabel.add(GUI.amountLable[inventoryState]);	
			if(GUI.equipedAmount!=null) {
				if(containedItems[inventoryState].getItemId()==itemInEquipSlotId) {
					GUI.equipedAmount.setVisible(false);
					GUI.equipedAmount = new StringLabel(containedItems[inventoryState].getAmount(), 8);
					GUI.nemesisLabel.add(GUI.equipedAmount);
				}
			}
		} catch (NullPointerException e) {
		}
	}
	//Getter und Setter für itemInEquipSlotId
	public static int getItemInEquipSlotId() {
		return itemInEquipSlotId;
	}
	public static void setItemInEquipSlotId(int input) {
		itemInEquipSlotId=input;
	}
	//Wandelt die Inventar slots (3, 2, 1, 0, 7, 6, 5, 4) in die HashMapReihenfolge um (0, 1, 2, ..., 7)
	private static int inventorySequence(int step) {
		if(step==0) {
			return 3;
		}else if(step==1) {
			return 7;
		}else if(step==2) {
			return 2;
		}else if(step==3) {
			return 6;
		}else if(step==4) {
			return 1;
		}else if(step==5) {
			return 5;
		}else if(step==6) {
			return 0;
		}else {
			return 4;
		}
	}
	//Wandelt die HashMapReihenfolge (0, 1, 2, ..., 7) in Inventarslots um (3, 2, 1, 0, 7, 6, 5, 4)
	public static int hashMapSequence(int step) {
		if(step==3) {
			return 0;
		}else if(step==7) {
			return 1;
		}else if(step==2) {
			return 2;
		}else if(step==6) {
			return 3;
		}else if(step==1) {
			return 4;
		}else if(step==5) {
			return 5;
		}else if(step==0) {
			return 6;
		}else {
			return 7;
		}
	}	
}