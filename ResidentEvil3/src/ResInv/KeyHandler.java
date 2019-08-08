package ResInv;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	//Notwendig für die Kombinierfunktion 
	public static int slot_a=-1; 
	public static int slot_b=-1;
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_D) {
			HealthStatus.decreaseHealthStatus();
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			Inventory.changeInventoryStateDown();
		}else if(e.getKeyCode() == KeyEvent.VK_UP) {
			Inventory.changeInventoryStateUp();
		}else if(e.getKeyCode()== KeyEvent.VK_RIGHT) {
			Inventory.changeInventoryStateRight();
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			Inventory.changeInventoryStateLeft();
		}else if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			if(Inventory.inventoryState==10) {
				System.exit(0);
			}else if(Inventory.inventoryState==9) {
				System.out.println("MAP NOT AVAILABLE YET");
			}else if(Inventory.inventoryState==8) {
				System.out.println("FILE NOT AVAILABLE YET");
			}else if(Inventory.inventoryState>=0 && Inventory.inventoryState <8) {
				if(Inventory.containedItems[Inventory.inventoryState]!=null) {
					Item i = Inventory.containedItems[Inventory.inventoryState];
					if(i.getHerbType()!=HerbType.NO_HERB) {
						HealthStatus.eatHerb(i.getHerbType());
					}else if(i.isEquipable()) {
						Item.equipItem(i);
					}
				}
			}
		}else if(e.getKeyCode()==KeyEvent.VK_C) {
			Character.changeCharacter();
		}else if(e.getKeyCode()==KeyEvent.VK_F1) {
			Inventory.decreaseAmount();
		}else if(e.getKeyCode()==KeyEvent.VK_F2) {
			Inventory.increaseAmount();
		}else if(e.getKeyCode()==KeyEvent.VK_F4) {
			Inventory.changeAmount(5);
		}else if(e.getKeyCode()==KeyEvent.VK_F3) {
			Inventory.changeAmount(-5);
		}else if(e.getKeyCode()==KeyEvent.VK_K) {
			if(Inventory.inventoryState<8) {
				if(slot_a==-1) {
					slot_a=Inventory.inventoryState;
					System.out.println("[A] Ich wähle aus "+Inventory.containedItems[slot_a].getItemName()+".");
					if(Inventory.containedItems[slot_a].isCanBeCombined()) {
						System.out.println("[A] Ich kann kombiniert werden!");
					}else {
						slot_a=-1;
					}
				}else{
					slot_b=Inventory.inventoryState;
					System.out.println("[B] Ich wähle aus "+Inventory.containedItems[slot_b].getItemName()+".");
					if(slot_a==slot_b) {
						System.out.println("Ich kombiniere nicht den selben slot.");
						slot_a=-1;
						slot_b=-1;
					}else if(Inventory.containedItems[slot_b].isCanBeCombined()) {
						System.out.println("[B] Ich kann kombiniert werden!");
						Inventory.combineItems(slot_a, slot_b);
					}else {
						slot_b=-1;
					}
				}
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {	
	}
}