package resInv.GUI;

import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import resInv.Character.Character;
import resInv.Health.HealthState;
import resInv.Health.HealthStatus;
import resInv.Inventorys.File;
import resInv.Inventorys.Inventory;
import resInv.Inventorys.Item;
import resInv.Inventorys.MenuFile;
import resInv.Inventorys.SubInventory;
import resInv.Map.Map;

public class GUI{
	private static JFrame f = new JFrame("Resident Evil 3 - Nemesis");//Name des Fensters
	public static final int height = 485;//Höhe des Fensters
	public static final int width = 600;//Breite des Fensters
	public static JLabel[] inventoryChooseFrame = new JLabel[11];//Auswahlrechteck im Inventar
	public static JLabel nemesisLabel = new JLabel(new ImageIcon("rsc/NemesisInv.png"));//PNG-Datei welche das Inventar repräsentiert
	public static JLabel[] statusTexture = new JLabel[HealthState.values().length];//Zeigt Leben in unterschiedlichen Farben an
	public static JLabel[] statusText = new JLabel[HealthState.values().length];//Zeigt Lebenstext an
	public static StringLabel[] charNameLable = new StringLabel[Character.values().length];//Zeigt Charakternamen an
	public static JLabel[] charPicture = new JLabel[Character.values().length];//Zeigt Charakterporträt an
	public static HashMap<Integer, JLabel> itemTextureWithInventoryPosition = new HashMap<>();//Verbindet Inventoryslot mit Item *.png 
	public static StringLabel[] itemDescription;//Zeigt die Itembeschreibung an
	public static JLabel equipmentSlot;//Zeigt ausgerüstetes Item an
	public static StringLabel equipedE;//Zeigt ein rotes E bei ausgerüsteten Items an
	public static StringLabel equipedAmount;//Zeigt die Anzahl des ausgerüstetens Items an
	public static StringLabel[] amountLable=new StringLabel[Inventory.containedItems.length];//Zeigt die Anzahl des Items an
	public static JLabel SLarrowDown = new JLabel(new ImageIcon("rsc/StringLabel_arrowDown.png"));
	//Startet das GUI
	public GUI() {
		init();
	}
	//Initialisiert die Grenzen des GUI's und zeichnet die Ausgangsposition
	private void init() {
		initFrame();
		SLarrowDown.setBounds(160, 432, 30, 30);
		nemesisLabel.add(SLarrowDown);
		SLarrowDown.setVisible(false);
		File.initFileSection();
		File.initFile();
		Map.initMap();
		SubInventory.initSubInventory();
		fillFrameArray(false);
		fillStatusArrays(false);
		fillCharArrays(false);
		Item.makePool();
		itemDescription=new StringLabel[Item.itemPool.size()+3];
		Inventory.fillInventory();
		fillItemDescriptionArray(false);
		HealthStatus n = new HealthStatus();
		n.start();
		equipedE = new StringLabel("E");
		nemesisLabel.add(equipedE);
		
		f.getContentPane().add(nemesisLabel);
		f.setVisible(true);
		f.addKeyListener(new KeyHandler());
	}
	//Füllt die Itembeschreibungen und zeigt diese an
	public static void fillItemDescriptionArray(boolean add) {
		for(int i=0; i<itemDescription.length;i++) {
			if(!add) {
				if(i<itemDescription.length-3) {
					itemDescription[i]=new StringLabel(Item.itemPool.get(i).getExamineText());
				}else {
					if(i==itemDescription.length-3) {
						if(!File.file.isVisible()) {
							itemDescription[i]=new StringLabel("Look at all your collected Files. Maybe you will find something.");
						}else {
							MenuFile mF = File.files[File.menuFilePosition+15*File.menuFilePage];
							itemDescription[i].changeText(mF.getName());
							SLarrowDown.setVisible(false);
							if(!mF.getText().equals("Unknown")) {
								SLarrowDown.setVisible(true); //Zeigt den Pfeil nach unten an
							}
						}
					}else if(i==itemDescription.length-2) {
						itemDescription[i]=new StringLabel("GAME EXIT");
					}else if(i==itemDescription.length-1) {
						itemDescription[i]=new StringLabel("The Map shows your actual Location in Raccoon City.");
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
			}else if(Inventory.inventoryState==9){
				itemDescription[itemDescription.length-1].setVisible(true);
			}else if(Inventory.inventoryState==8) {
				itemDescription[itemDescription.length-3].setVisible(true);
			}
		}
	}
	//Füllt das Charakter-Array mit den Namen und teilt diese JLabels mit Porträt zu
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
	//Füllt Lebensanzeige-Array und teilt diese JLabels mit *.png zu
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
	//Füllt Frame-Array und teilt die Auswahlrahmen im Inventory zu
	public static void fillFrameArray(boolean add) {
		for(int i=0; i<inventoryChooseFrame.length; i++) {
			if(!add) {
				if(i<8) {
					inventoryChooseFrame[i]=new JLabel(new ImageIcon("rsc/rahmen.png"));
				}else if(i<10){
					if(i==8) {
						inventoryChooseFrame[i]=new JLabel(new ImageIcon("rsc/file_rahmen.png"));
					}
					if(i==9) {
						inventoryChooseFrame[i]=new JLabel(new ImageIcon("rsc/map_rahmen.png"));
					}
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
	//Verbirgt alle Auswahlrahmen
	public static void hideInventoryFrame() {
		for(int i = 0; i<inventoryChooseFrame.length;i++) {
			inventoryChooseFrame[i].setVisible(false);
		}
	}
	//Setzt die größe, resizeability, layout des JFrames fest
	private void initFrame() {
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(width, height);
		f.setResizable(false);
		f.setLayout(null);
		nemesisLabel.setBounds(0, 0, width, height-15);
	}
}