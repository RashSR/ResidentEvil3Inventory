package resInv.GUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import resInv.character.Character;
import resInv.health.HealthStatus;
import resInv.health.HerbType;
import resInv.inventorys.ExamineItems;
import resInv.inventorys.File;
import resInv.inventorys.Inventory;
import resInv.inventorys.Item;
import resInv.inventorys.SubInventory;
import resInv.map.Map;
import resInv.map.PlayerArrow;
import resInv.saveStates.SaveState;
import resInv.sound.Sound;
import resInv.sound.SoundPlayer;

public class KeyHandler implements KeyListener{
	//Notwendig f�r die Kombinierfunktion 
	public static int slot_a=-1; 
	public static int slot_b=-1;
	public static boolean swap=false;
	private int holdSlot=-1;
	
	@SuppressWarnings("null")
	@Override
	public void keyPressed(KeyEvent e) {
		//Wenn der Men�punkt "Map" ausgew�hlt ist
		if(Map.visible) {
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				Map.showMap();
				PlayerArrow.resetOfsets();
			}else if(e.getKeyCode()==KeyEvent.VK_F6) {
				Map.changeRoom();
			}else if(e.getKeyCode()==KeyEvent.VK_UP&&!PlayerArrow.isBlinking()){
				PlayerArrow.goForward();
			}else if(e.getKeyCode()==KeyEvent.VK_RIGHT&&!PlayerArrow.isBlinking()){
				Map.player_arrow_active[Map.active_arrow_count].setVisible(false);
				Map.changeArrowCount(true);
				Map.player_arrow_active[Map.active_arrow_count].setVisible(true);
				
			}else if(e.getKeyCode()==KeyEvent.VK_LEFT&&!PlayerArrow.isBlinking()){
				Map.player_arrow_active[Map.active_arrow_count].setVisible(false);
				Map.changeArrowCount(false);
				Map.player_arrow_active[Map.active_arrow_count].setVisible(true);
				
			}else if(e.getKeyCode()==KeyEvent.VK_ENTER){
				Map.changeRoom(Map.goToNextRoom());
				return;
			}else {
				return;
			}
		}
		//Wenn der Men�punkt "File" ausgew�hlt ist
		if(File.file.isVisible()) {
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				File.hideFiles();
				File.showFileBackground();
				File.menuFilePage=0;
				GUI.itemDescription[GUI.itemDescription.length-3].changeText("Look at all your collected Files. Maybe you will find something.");
				GUI.fillItemDescriptionArray(true);
			}else if(e.getKeyCode() == KeyEvent.VK_UP) {
				File.up();
				return;
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				File.down();
				return;
			}else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				File.right();
				return;
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				File.left();
				return;
			}else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				if(GUI.SLarrowDown.isVisible()) {
					GUI.itemDescription[GUI.itemDescription.length-3].changeText(File.files[File.menuFilePosition+15*File.menuFilePage].getText());
					GUI.SLarrowDown.setVisible(false);
					GUI.fillItemDescriptionArray(true);
				}
				return;
			}
			else {
				return;
			}

		}
		//Normale Inventarslots
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
			if(ExamineItems.visible) {
				ExamineItems.hideExamineItem();
			}
			if(SubInventory.visible) {
				SubInventory.show(Inventory.inventoryState);
			}
			if(Inventory.combineFrame.isVisible()) {
				Inventory.combineFrame.setVisible(false);
				Inventory.inventoryState=holdSlot;
				swap=false;
				slot_a=-1;
			}
		} if(e.getKeyCode() == KeyEvent.VK_D) {
			HealthStatus.decreaseHealthStatus();
		}else if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			if(Inventory.inventoryState==10) {
				System.exit(0);
			}else if(Inventory.inventoryState==9) {
				Map.showMap();
				SoundPlayer.play(Sound.INVENTORY_ACKNOWLEDGE, 0);
			}else if(Inventory.inventoryState==8) {
				File.showFileBackground();
				SoundPlayer.play(Sound.INVENTORY_ACKNOWLEDGE, 0);
				GUI.fillItemDescriptionArray(false);
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
					if(ExamineItems.visible) {
						ExamineItems.hideExamineItem();
					}
					SubInventory.show(Inventory.inventoryState);
					SoundPlayer.play(Sound.INVENTORY_ACKNOWLEDGE, 0);
				}else if(Inventory.containedItems[Inventory.inventoryState]!=null&&SubInventory.visible) {
					Item i = Inventory.containedItems[Inventory.inventoryState];
					if(SubInventory.subInventoryPosition==0) {
						if(i.getHerbType()!=HerbType.NO_HERB) {
							HealthStatus.eatHerb(i.getHerbType());
							SubInventory.show(Inventory.inventoryState);
							ExamineItems.hideExamineItem();
						}else if(i.isEquipable()) {
							Item.equipItem(i);
							GUI.equipedE.setVisible(true);
							SubInventory.show(Inventory.inventoryState);
							ExamineItems.hideExamineItem();
						}
					}else if(SubInventory.subInventoryPosition==1) {
						ExamineItems.showExamineItem(i);
						SubInventory.show(Inventory.inventoryState);
					}else if(SubInventory.subInventoryPosition==2) {
						if(slot_a==-1) {
							slot_a=Inventory.inventoryState;
							if(Inventory.containedItems[slot_a].isCanBeCombined()) {
								SubInventory.show(Inventory.inventoryState);
								Inventory.showCombineFrame();
								holdSlot=Inventory.inventoryState;
								Inventory.setBoundsForCombineFrame();
							}else {
								slot_a=-1;
							}
						}
					}else if(SubInventory.subInventoryPosition==3) {
						if(slot_a==-1) {
							slot_a=Inventory.inventoryState;
							SubInventory.show(Inventory.inventoryState);
							swap=true;
							Inventory.showCombineFrame();
							holdSlot=Inventory.inventoryState;
							Inventory.setBoundsForCombineFrame();
						}
					}else if(SubInventory.subInventoryPosition==4) {
						if(Inventory.inventoryState!=Inventory.itemNumberEquipSlotLink) {
							//Item kann nur weggeworfen werden falls nicht ausger�stet
							Inventory.removeItem(Inventory.inventoryState);
							SubInventory.show(Inventory.inventoryState);
						}
					}
				}
			}
		}else if(e.getKeyCode() == KeyEvent.VK_C) {
			Character.changeCharacter();
		}else if(e.getKeyCode() == KeyEvent.VK_F1) {
			Inventory.decreaseAmount();
		}else if(e.getKeyCode() == KeyEvent.VK_F2) {
			Inventory.increaseAmount();
		}else if(e.getKeyCode() == KeyEvent.VK_F4) {
			Inventory.changeAmount(5);
		}else if(e.getKeyCode() == KeyEvent.VK_F3) {
			Inventory.changeAmount(-5);
		}else if(e.getKeyCode() == KeyEvent.VK_S) {
			SaveState s = new SaveState(true);
			s.save();
		}else if(e.getKeyCode() == KeyEvent.VK_L) {
			SaveState s = new SaveState(false);
			s = s.load();
		}else if(e.getKeyCode()==KeyEvent.VK_U) {
			PlayerArrow.changeMode();
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {	
	}
}