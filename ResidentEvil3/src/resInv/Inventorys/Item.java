package resInv.Inventorys;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import resInv.GUI.GUI;
import resInv.GUI.StringLabel;
import resInv.Health.HerbType;
import resInv.IO.FileLoader;

public class Item {
	private int amount; //Anzahl des Items
	private boolean canStack;//Gibt an ob das Item stapelbar ist
	private String itemName; //Name des Items
	private boolean canBeCombined;//Gibt an ob das Item kombinierbar ist
	private boolean equipable;//Gibt an ob das Item ausrüstbar ist
	private String examineText;//Untersuchungstext des Items
	private HerbType herbType;//Gibt den HerbType des Items an
	private String fileName;//Gibt den Pfad an wo die *.png des Items liegt
	public static ArrayList<Item> itemPool;//Eine Liste, welches jedes Item beinhaltet
	private int itemId;//spezifische ItemID des items(aufsteigend)
	private int maxAmount;//Gibt die maximale Anzahl des Items an
	//Erstellt die Itemliste, indem es aus einer Datei ausliest und diese dem Konstruktor übergibt
	public static void makePool() {
		itemPool=new ArrayList<>();
		FileLoader fl = new FileLoader("data/itemlist.txt");
		ArrayList<String> s = fl.readFile();
		int j=0;
		if(s!=null){
			for(String sr : s) {
				String[] parts = sr.split(";");
				for(int i = 0; i<parts.length; i++) {
					if(i==parts.length-1) {
						itemPool.add(new Item(parts[0], Boolean.parseBoolean(parts[1]), Integer.parseInt(parts[2]), Boolean.parseBoolean(parts[3]), parts[4], HerbType.valueOf(parts[5]), parts[6], j, Boolean.parseBoolean(parts[7])));
						j++;
					}
				}
			}
		}
	}
	//Kopiert ein Item, Sinn: um 2 unterschiedliche Items mit der selben ID zu benutzen 
	public Item(Item item) {
		this.amount=item.getAmount();
		this.canStack=item.isCanStack();
		this.itemName=item.getItemName();
		this.canBeCombined=item.isCanBeCombined();
		this.equipable=item.isEquipable();
		this.examineText=item.getExamineText();
		this.herbType=item.getHerbType();
		this.fileName=item.getFileName();
		this.itemId=item.getItemId();
		this.maxAmount=item.getMaxAmount();
	}
	//Standardkonstruktro für Item
	public Item(String itemName, boolean canStack, int amount, boolean canBeCombined, String examineText, HerbType herbType, String fileName, int itemId, boolean equipable) {
		this.itemName=itemName;
		if(itemName.equals("Pistol")) {
			this.maxAmount=15;
		}else if(itemName.equals("9mm Bullets")) {
			this.maxAmount=30;
		}else if(itemName.equals("Ink Ribbon")){
			this.maxAmount=10;
		}else {
			this.maxAmount=-1;
		}
		this.canStack=canStack;
		this.canBeCombined=canBeCombined;
		this.examineText=examineText;
		this.fileName=fileName;
		this.itemId=itemId;
		this.equipable=equipable;
		if(canStack) {
			this.amount=amount;
		}else {
			this.amount=-1;
		}
		this.herbType=herbType;
	}
	//Rüstet ein Item aus und packt es in den Equip-Slot
	public static void equipItem(Item item) {
		Inventory.itemNumberEquipSlotLink=Inventory.inventoryState;
		GUI.fillItemDescriptionArray(true);
		if(Inventory.isEquipSlotOccupied()) {
			if(Inventory.getItemInEquipSlotId()==item.getItemId()) {
				Inventory.setItemInEquipSlotId(-1);
				GUI.equipmentSlot.setVisible(false);
				Inventory.equipSlotOccupied=false;
				Inventory.itemNumberEquipSlotLink=-1;
				GUI.equipedAmount.setVisible(false);
			}else {
				GUI.equipmentSlot.setVisible(false);
				Inventory.equipSlotOccupied=false;
				showEquipmentSlot(item);
				if(item.getMaxAmount()<2) {
					GUI.equipedAmount.setVisible(false);
				}else {
					GUI.equipedAmount=new StringLabel(item.getAmount(), 8);
					GUI.nemesisLabel.add(GUI.equipedAmount);
				}
			}
		}else {
			showEquipmentSlot(item);
			GUI.equipedAmount=new StringLabel(item.getAmount(), 8);
			GUI.nemesisLabel.add(GUI.equipedAmount);
		}
	}
	//Hilfsfunktion die das Item im GUI darstellt
	private static void showEquipmentSlot(Item item) {
		GUI.equipmentSlot = new JLabel(new ImageIcon(item.getFileName()));
		GUI.equipmentSlot.setBounds(280, 35, 100, 100);
		GUI.nemesisLabel.add(GUI.equipmentSlot);
		GUI.fillItemDescriptionArray(true);
		Inventory.equipSlotOccupied=true;
		Inventory.setItemInEquipSlotId(item.getItemId());
	}
	//Getter und Setter
	public void setAmount(int amount) {
		this.amount=amount;
	}
	public int getMaxAmount() {
		return this.maxAmount;
	}
	public int getItemId() {
		return this.itemId;
	}
	public void showExamineText() {
		
	}
	public int getAmount() {
		return amount;
	}
	public boolean isCanStack() {
		return canStack;
	}
	public String getItemName() {
		return itemName;
	}
	public boolean isCanBeCombined() {
		return canBeCombined;
	}
	public String getExamineText() {
		return examineText;
	}
	public HerbType getHerbType() {
		return this.herbType;
	}
	public String getFileName() {
		return this.fileName;
	}
	public boolean isEquipable() {
		return this.equipable;
	}
}