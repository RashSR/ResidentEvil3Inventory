package ResInv;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class SubInventory {
	public static int subInventoryState=-1; //Gibt an welches Subinventory aufgerufen wird
	public static int subInventoryPosition=0; //Gibt an welches Subinventory aufgerufen wird//Gibt an in welchem State sich das Subinventory befindet maxwert=4 (0, 1, 2, 3, 4)
	public static boolean visible=false; //Gibt an ob das Subinventory grad angezeigt wird oder nicht
	public static JLabel subinv; //JLabel des Subinventorys
	public static JLabel subinv_frame; //JLabel des Auswahlfeldes
	
	//initialisiert das Subinventory
	public static void initSubInventory() {
		subinv=new JLabel(new ImageIcon("rsc/sub_inv.png"));
		subinv_frame=new JLabel(new ImageIcon("rsc/subinv_rahmen.png"));
		GUI.nemesisLabel.add(subinv_frame);
		GUI.nemesisLabel.add(subinv);
	}
	//Zeigt das Subinventory
	public static void show(int state) {
		if(!visible) {
			if(Inventory.containedItems[state]!=null) {
				setBounds(state);
				subinv_frame.setVisible(true);
				subinv.setVisible(true);
				visible=true;
			}
		}else {
			subinv.setVisible(false);
			subinv_frame.setVisible(false);
			visible=false;
		}
		subInventoryPosition=0;
		GUI.nemesisLabel.updateUI();
	}
	//Bewegt das Auswahlfeld für Subinventory nach unten
	public static void changeDown() {
		subInventoryPosition++;
		if(subInventoryPosition==5) {
			subInventoryPosition=4;
		}
		setBounds(Inventory.inventoryState);
	}
	//Bewegt das Auswahlfeld für Subinventory nach oben
		public static void changeUp() {
			subInventoryPosition--;
			if(subInventoryPosition==-1) {
				subInventoryPosition=0;
			}
			setBounds(Inventory.inventoryState);
		}
	//Legt die Größe des Subinventorys fest
	private static void setBounds(int state) {
		int i=0;
		if(subInventoryPosition==4) {
			 i = 1;
		}
		if(state==3) {
			subinv.setBounds(245, 75, 200, 200);
			subinv_frame.setBounds(295, 91+subInventoryPosition*17+i, 100, 100);
		}else if(state==2) {
			subinv.setBounds(245, 150, 200, 200);
			subinv_frame.setBounds(295, 166+subInventoryPosition*17+i, 100, 100);
		}else if(state==1) {
			subinv.setBounds(245, 220, 200, 200);
			subinv_frame.setBounds(295, 236+subInventoryPosition*17+i, 100, 100);
		}else if(state==0) {
			subinv.setBounds(245, 290, 200, 200);
			subinv_frame.setBounds(295, 306+subInventoryPosition*17+i, 100, 100);
		}else if(state==7) {
			subinv.setBounds(335, 75, 200, 200);
			subinv_frame.setBounds(385, 91+subInventoryPosition*17+i, 100, 100);
		}else if(state==6) {
			subinv.setBounds(335, 150, 200, 200);
			subinv_frame.setBounds(385, 166+subInventoryPosition*17+i, 100, 100);
		}else if(state==5) {
			subinv.setBounds(335, 220, 200, 200);
			subinv_frame.setBounds(385, 236+subInventoryPosition*17+i, 100, 100);
		}else if(state==4) {
			subinv.setBounds(335, 290, 200, 200);
			subinv_frame.setBounds(385, 306+subInventoryPosition*17+i, 100, 100);
		}
	}
}
