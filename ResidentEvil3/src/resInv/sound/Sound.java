package resInv.sound;

public enum Sound {
	INVENTORY_CHANGE, INVENTORY_ACKNOWLEDGE;
	
	public String getFilePath() {
		String s = "";
		switch (this) {
		case INVENTORY_CHANGE:
			s = "changeInventory";
			break;
		case INVENTORY_ACKNOWLEDGE:
			s = "acknowledgeInventory";
			break;
		default:
			break;
		}
		
		return "rsc/sounds/" + s + ".wav";
	}
}
