package ResInv;

import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GUI{
	private static JFrame f = new JFrame("Resident Evil 3 - Nemesis");
	public static final int height = 485;
	public static final int width = 600;
	public static JLabel[] inventoryChooseFrame = new JLabel[11];
	public static JLabel nemesisLabel = new JLabel(new ImageIcon("rsc/NemesisInv.png"));
	public static JLabel[] statusTexture = new JLabel[3];
	public static JLabel[] statusText = new JLabel[3];
	public static StringLabel[] charNameLable = new StringLabel[Character.values().length];
	public static JLabel[] charPicture = new JLabel[Character.values().length];
	public static HashMap<Integer, JLabel> itemTextureWithInventoryPosition = new HashMap<>();
	public static StringLabel[] itemDescription;
	public static JLabel equipmentSlot;
	public static StringLabel equipedE;
	public static StringLabel equipedAmount;
	public static StringLabel[] amountLable=new StringLabel[8];
	public GUI() {
		init();
	}
	private void init() {
		initFrame();
		fillFrameArray(false);
		fillStatusArrays(false);
		fillCharArrays(false);
		Item.makePool();
		itemDescription=new StringLabel[Item.itemPool.size()+2];
		Inventory.fillInventory();
		fillItemDescriptionArray(false);
		
		equipedE = new StringLabel("E");
		nemesisLabel.add(equipedE);
		f.addKeyListener(new KeyHandler());
		f.getContentPane().add(nemesisLabel);
		f.setVisible(true);
		
	}
	
	public static void fillItemDescriptionArray(boolean add) {
		for(int i=0; i<itemDescription.length;i++) {
			if(!add) {
				if(i<itemDescription.length-2) {
				itemDescription[i]=new StringLabel(Item.itemPool.get(i).getExamineText());
				}else {
					if(i==itemDescription.length-1) {
						itemDescription[i]=new StringLabel("NOT AVAILABLE");
					}else {
						itemDescription[i]=new StringLabel("GAME EXIT");
					}	
				}
				nemesisLabel.add(itemDescription[i]);
			}
			itemDescription[i].setVisible(false);
		}
		if(Inventory.inventoryState<8) {
			if(Inventory.containedItems[Inventory.inventoryState]!=null) {
				itemDescription[Inventory.containedItems[Inventory.inventoryState].getItemId()].setVisible(true);
			}
		}else {
			if(Inventory.inventoryState==10) {
				itemDescription[itemDescription.length-2].setVisible(true);
			}else {
				itemDescription[itemDescription.length-1].setVisible(true);
			}
		}
		
	}
	public static void fillCharArrays(boolean add) {
		if(!add) {
			int i=0;
			for(Character c : Character.values()) {
				charNameLable[i]=new StringLabel(c);
				charPicture[i]=new JLabel(new ImageIcon("rsc/"+c.getName()+".png"));
				charPicture[i].setBounds(5, 45, 100, 100);
				nemesisLabel.add(charNameLable[i]);
				nemesisLabel.add(charPicture[i]);
				i++;
			}
		}
		for(int j=0;j<charNameLable.length;j++) {
			charNameLable[j].setVisible(false);
			charPicture[j].setVisible(false);
		}
		charNameLable[Character.charState].setVisible(true);
		charPicture[Character.charState].setVisible(true);
	}
	public static void fillStatusArrays(boolean add) {
		if(!add) {
			statusTexture[HealthState.FULLHEALTH.getHealthState()] = new JLabel(new ImageIcon("rsc/fullhealth.png"));
			statusTexture[HealthState.HARMED.getHealthState()] = new JLabel(new ImageIcon("rsc/harmed.png"));
			statusTexture[HealthState.WOUNDED.getHealthState()] = new JLabel(new ImageIcon("rsc/wounded.png"));
			statusText[HealthState.FULLHEALTH.getHealthState()] = new JLabel(new ImageIcon("rsc/statustext_fine.png"));
			statusText[HealthState.HARMED.getHealthState()] = new JLabel(new ImageIcon("rsc/statustext_caution.png"));
			statusText[HealthState.WOUNDED.getHealthState()] = new JLabel(new ImageIcon("rsc/statustext_danger.png"));
		}
		for(int i=0; i<statusTexture.length; i++) {
			statusTexture[i].setVisible(false);
			statusText[i].setVisible(false);
			if(!add) {
				statusTexture[i].setBounds(103, 42, 150, 100);
				statusText[i].setBounds(142, 24, 150, 50);
				nemesisLabel.add(statusText[i]);
				nemesisLabel.add(statusTexture[i]);
			}
		}
		statusTexture[HealthStatus.healthState].setVisible(true);
		statusText[HealthStatus.healthState].setVisible(true);
	}
	public static void fillFrameArray(boolean add) {
		for(int i=0; i<inventoryChooseFrame.length; i++) {
			if(!add) {
				if(i<8) {
					inventoryChooseFrame[i]=new JLabel(new ImageIcon("rsc/rahmen.png"));
				}else if(i<10){
					inventoryChooseFrame[i]=new JLabel(new ImageIcon("rsc/file_map_rahmen.png"));
				}else {
					inventoryChooseFrame[i]=new JLabel(new ImageIcon("rsc/exit_rahmen.png"));
				}
				if(i<8) {
					if(i<4) {
						inventoryChooseFrame[i].setBounds(240, 192-i*72, 400, 400);
					}else {
						inventoryChooseFrame[i].setBounds(329, 192-(i-4)*72, 400, 400);
					}
				}
				else if(i<10){
					inventoryChooseFrame[i].setBounds(388+(i-8)*95, 56, 100, 100);
				}else {
					inventoryChooseFrame[i].setBounds(385, 19, 200, 70);
				}
			}
			inventoryChooseFrame[i].setVisible(false);
			if(!add) {
				nemesisLabel.add(inventoryChooseFrame[i]);
			}
		}
		inventoryChooseFrame[Inventory.inventoryState].setVisible(true);
	}
	private void initFrame() {
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(width, height);
		f.setResizable(false);
		f.setLayout(null);
		nemesisLabel.setBounds(0, 0, width, height-15);
	}
}