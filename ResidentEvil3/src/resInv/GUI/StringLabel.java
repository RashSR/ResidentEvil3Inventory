package resInv.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;

import resInv.Character.Character;
import resInv.Inventorys.Inventory;

@SuppressWarnings("serial")
public class StringLabel extends JLabel{
	private Character character;//falls Charakter übergeben != null
	
	private String text="";//Text welcher im GUI angezeigt wird
	
	private int amount=-1;//falls kein amount übergeben wird
	private int slot;//Gibt slot zu welchem die Anzahl angezeigt werden soll
	//Konstruktor für Charakter-Namen
    public StringLabel(Character character) {
    	this.setBounds(0, 0, GUI.width, GUI.height);
    	this.character = character;
    }
  //Konstruktor für Item-Anzahl
    public StringLabel(int amount, int slot) {
    	this.setBounds(0, 0, GUI.width, GUI.height);
    	this.amount=amount;
    	this.slot=slot;
    }
  //Konstruktor für Stringausgabe
    public StringLabel(String text) {
    	this.setBounds(0, 0, GUI.width, GUI.height);
    	this.text=text;
    }
    //Zeichnet auf das GUI
    @Override
    public void paintComponent(Graphics g) {
    	if(amount>=0) {
    		g.setFont(new Font("default", Font.BOLD, 16));
    		drawAmountString(slot, g);
    	}else if(character!=null) {
    		g.setColor(new Color(131, 141, 106));
    		g.setFont(new Font("default", Font.BOLD, 16-character.getFontSizeOffset()));
    		g.drawString(character.getName(),40-character.getXOffset(),42);
    	}else if(text.equals("E")){
    		g.setFont(new Font("default", Font.BOLD, 15));
    		g.setColor(Color.RED);
    		setBoundsForE(g);
    	}else {
    		g.setFont(new Font("default", Font.BOLD, 22));
    		g.setColor(Color.WHITE);
    		String above="";
    		String below="";
    		if(text.length()<=33) {
    			above=text;
    		}else {
    		above = splitInHalf(text, 1);
    		below = splitInHalf(text, 2);
    		}
    		g.drawString(above, 25,390);
    		g.drawString(below, 25,420);
    	}
    }
    //Zeichnet Anzahl des Items in slot
    private void drawAmountString(int slot, Graphics g) {
    	if(slot==8) {
    		if(Inventory.containedItems[Inventory.itemNumberEquipSlotLink].getAmount()==0){
    			g.setColor(Color.RED);
    		}else if(Inventory.containedItems[Inventory.itemNumberEquipSlotLink].getAmount()==Inventory.containedItems[Inventory.itemNumberEquipSlotLink].getMaxAmount()) {
    			g.setColor(new Color(62, 184, 215));
    		}else {
    			g.setColor(Color.YELLOW);
    		}
    	}
    	else if(amount==0) {
    		g.setColor(Color.RED);
    		if(Inventory.containedItems[slot]!=null) {
    			if(!Inventory.containedItems[slot].isEquipable()) {
    				Inventory.removeItem(slot);
    				GUI.amountLable[slot].setVisible(false);;
    			}
    		}
    	}else if(amount==Inventory.containedItems[slot].getMaxAmount()){
    		g.setColor(new Color(62, 184, 215));
    	}else {
    		g.setColor(Color.YELLOW);
    	}
    	if(slot==0) {
    		g.drawString(Integer.toString(amount), 405, 420);
    	}else if(slot==1){
    		g.drawString(Integer.toString(amount), 405, 348);
    	}else if(slot==2){
    		g.drawString(Integer.toString(amount), 405, 275);
    	}else if(slot==3){
    		g.drawString(Integer.toString(amount), 405, 203);
    	}else if(slot==4){
    		g.drawString(Integer.toString(amount), 493, 420);
    	}else if(slot==5) {
    		g.drawString(Integer.toString(amount), 493, 348);
    	}else if(slot==6){
    		g.drawString(Integer.toString(amount), 493, 275);
    	}else if(slot==7) {
    		g.drawString(Integer.toString(amount), 493, 203);
    	}else if(slot==8) {
    		g.drawString(Integer.toString(amount), 283, 115);
    	}
    }
    //Verändert den Textinhalt
    public void changeText(String newText) {
    	this.text=newText;
    }
    //Teilt den String in die Hälfte und gibt diesen im ExamineText aus
    private String splitInHalf(String s, int part) {
    	String f = "";
    	String[] parts = s.split(" ");
    	if(part==1) {
    		for(int i=0; i<=parts.length/2; i++) {
    			f+=parts[i]+" ";
    		}
    	}else if(part==2) {
    		for(int j=parts.length/2+1;j<parts.length;j++) {
    			f+=parts[j]+" ";
    		}
    	}
    	return f;
    }
    //Zeichnet das E bei dem ausgerüstetem Item
    private void setBoundsForE(Graphics g) {
    	if(Inventory.isEquipSlotOccupied()) {
    		if(Inventory.itemNumberEquipSlotLink==3) {
    			g.drawString(text, 467, 203);
    		}else if(Inventory.itemNumberEquipSlotLink==2) {
    			g.drawString(text, 467, 275);
    		}else if(Inventory.itemNumberEquipSlotLink==1) {
    			g.drawString(text, 467, 347);
    		}else if(Inventory.itemNumberEquipSlotLink==0) {
    			g.drawString(text, 467, 420);
    		}else if(Inventory.itemNumberEquipSlotLink==7) {
    			g.drawString(text, 557, 203);
    		}else if(Inventory.itemNumberEquipSlotLink==6) {
    			g.drawString(text, 557, 275);
    		}else if(Inventory.itemNumberEquipSlotLink==5) {
    			g.drawString(text, 557, 347);
    		}else if(Inventory.itemNumberEquipSlotLink==4) {
    			g.drawString(text, 557, 420);
    		}
    	}
    }
}