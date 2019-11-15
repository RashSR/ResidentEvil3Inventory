package ResInv;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import SaveStates.SaveState;

public class KeyHandler implements KeyListener{
	//Notwendig für die Kombinierfunktion 
	public static int slot_a=-1; 
	public static int slot_b=-1;
	private boolean swap=false;
	@Override
	public void keyPressed(KeyEvent e) {
		if(Map.visible) {
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				Map.showMap();
			}else if(e.getKeyCode()==KeyEvent.VK_W) {
				Map.changeRoom();
			}else {
				return;
			}
		} 
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(!SubInventory.visible) {
				Inventory.changeInventoryStateDown();
				if(ExamineItems.visible) {
					ExamineItems.hideExamineItem();
				}
			}else {
				SubInventory.changeDown();
			}
		}else if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(!SubInventory.visible) {
				Inventory.changeInventoryStateUp();
				if(ExamineItems.visible) {
					ExamineItems.hideExamineItem();
				}
			}else {
				SubInventory.changeUp();
			}
		}else if(e.getKeyCode()== KeyEvent.VK_RIGHT&&!SubInventory.visible) {
			Inventory.changeInventoryStateRight();
			if(ExamineItems.visible) {
				ExamineItems.hideExamineItem();
			}
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT&&!SubInventory.visible) {
			Inventory.changeInventoryStateLeft();
			if(ExamineItems.visible) {
				ExamineItems.hideExamineItem();
			}
		}else if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
			if(SubInventory.visible) {
				SubInventory.show(Inventory.inventoryState);
			}
		} if(e.getKeyCode() == KeyEvent.VK_D) {
			HealthStatus.decreaseHealthStatus();
		}else if(e.getKeyCode()==KeyEvent.VK_A) {
			if(Inventory.inventoryState<8) {
				SubInventory.show(Inventory.inventoryState);
			}
		}else if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			if(Inventory.inventoryState==10) {
				System.exit(0);
			}else if(Inventory.inventoryState==9) {
				Map.showMap();
			}else if(Inventory.inventoryState==8) {
				System.out.println("FILE NOT AVAILABLE YET");
			}else if(Inventory.inventoryState>=0 && Inventory.inventoryState <8) {
				if(slot_a!=-1) {
					if(slot_b==-1&&swap) {
						slot_b=Inventory.inventoryState;
						Inventory.swapItems(slot_b, slot_a);
						GUI.fillItemDescriptionArray(true);
						swap=false;
					}else if(slot_b==-1&&!swap) {
						slot_b=Inventory.inventoryState;
						if(slot_a==slot_b) {
							slot_a=-1;
							slot_b=-1;
						}else if(Inventory.containedItems[slot_b].isCanBeCombined()) {
							Inventory.combineItems(slot_b, slot_a);
							GUI.fillItemDescriptionArray(true);
						}else {
							slot_b=-1;
						}
					}
				}
				else if(Inventory.containedItems[Inventory.inventoryState]!=null&&!SubInventory.visible) {
					SubInventory.show(Inventory.inventoryState);
				}else if(Inventory.containedItems[Inventory.inventoryState]!=null&&SubInventory.visible) {
					Item i = Inventory.containedItems[Inventory.inventoryState];
					if(SubInventory.subInventoryPosition==0) {
						if(i.getHerbType()!=HerbType.NO_HERB) {
						HealthStatus.eatHerb(i.getHerbType());
						SubInventory.show(Inventory.inventoryState);
						}else if(i.isEquipable()) {
							Item.equipItem(i);
							GUI.equipedE.setVisible(true);
							SubInventory.show(Inventory.inventoryState);
						}
					}else if(SubInventory.subInventoryPosition==1) {
						ExamineItems.showExamieItem(i);
						SubInventory.show(Inventory.inventoryState);
					}else if(SubInventory.subInventoryPosition==2) {
						if(slot_a==-1) {
							slot_a=Inventory.inventoryState;
							if(Inventory.containedItems[slot_a].isCanBeCombined()) {
								SubInventory.show(Inventory.inventoryState);
							}else {
								slot_a=-1;
							}
						}
					}else if(SubInventory.subInventoryPosition==3) {
						if(slot_a==-1) {
							slot_a=Inventory.inventoryState;
							SubInventory.show(Inventory.inventoryState);
							swap=true;
						}
					}else if(SubInventory.subInventoryPosition==4) {
						if(Inventory.inventoryState!=Inventory.itemNumberEquipSlotLink) {
							//Item kann nur weggeworfen werden falls nicht ausgerüstet
							Inventory.removeItem(Inventory.inventoryState);
							SubInventory.show(Inventory.inventoryState);
						}
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
		}else if(e.getKeyCode()==KeyEvent.VK_S) {
			SaveState s = new SaveState();
			s.printSaveState();
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {	
	}
}