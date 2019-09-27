package ResInv;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class SubInventory {
	public static int subInventoryState=-1; //Gibt an in welchem State sich das Subinventory befindet maxwert=4 (0, 1, 2, 3, 4)
	public static int subInventoryPosition=1; //Gibt an welches Subinventory aufgerufen wird
	public static boolean visible=false; //Gibt an ob das Subinventory grad angezeigt wird oder nicht
	public static JLabel subinv; //JLabel des Subinventorys
	//initialisiert das Subinventory
	public static void initSubInventory() {
		subinv=new JLabel(new ImageIcon("rsc/sub_inv.png"));
		GUI.nemesisLabel.add(SubInventory.subinv);
	}
	//Zeigt das Subinventory
	public static void show(int state) {
		if(!visible) {
			if(Inventory.containedItems[state]!=null) {
				setBounds(state);
				subinv.setVisible(true);
				visible=true;
			}
		}else {
			subinv.setVisible(false);
			visible=false;
		}
		GUI.nemesisLabel.updateUI();
	}
	//Legt die Größe des Subinventorys fest
	private static void setBounds(int state) {
		if(state==3) {
			subinv.setBounds(245, 75, 200, 200);
		}else if(state==2) {
			subinv.setBounds(245, 150, 200, 200);
		}else if(state==1) {
			subinv.setBounds(245, 220, 200, 200);
		}else if(state==0) {
			subinv.setBounds(245, 290, 200, 200);
		}else if(state==7) {
			subinv.setBounds(335, 75, 200, 200);
		}else if(state==6) {
			subinv.setBounds(335, 150, 200, 200);
		}else if(state==5) {
			subinv.setBounds(335, 220, 200, 200);
		}else if(state==4) {
			subinv.setBounds(335, 290, 200, 200);
		}
	}
}
