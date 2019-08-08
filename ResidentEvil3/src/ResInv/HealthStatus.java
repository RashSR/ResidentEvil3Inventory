package ResInv;

public class HealthStatus {
	public static int healthState = 0; //Zeigt an in welchem Healtstatus der Spieler gerade ist
	//Setzt das Leben des Spielers zurück
	public static void decreaseHealthStatus() {
		healthState++;
		if(healthState == 3) {
			healthState--;
			return;
		}
		showHealthState();
	}
	//Erhöht das Leben des Spielers falls er ein heilendes Kraut isst
	public static void eatHerb(HerbType h) {
		if(h==HerbType.RED_HERB) {
			return;
		}
		if(healthState>0) {
			if(h == HerbType.GG_HERB || h == HerbType.GR_HERB) {
				healthState=0;
			}else if(h==HerbType.GREEN_HERB){
				healthState--;
			}
		}else {
			return;
		}
		removeHerb();
		showHealthState();
		GUI.fillItemDescriptionArray(true);
	}
	//Entfernt das Kraut aus dem Inventar und GUI
	private static void removeHerb() {
		Inventory.containedItems[Inventory.inventoryState]=null;
		GUI.itemTextureWithInventoryPosition.get(Inventory.hashMapSequence(Inventory.inventoryState)).setVisible(false);
		GUI.itemTextureWithInventoryPosition.remove(Inventory.hashMapSequence(Inventory.inventoryState));
	}
	//Zeigt den HealthStatus im Inventar
	public static void showHealthState() {
		GUI.fillStatusArrays(true);
	}
}