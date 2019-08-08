package ResInv;
//Enum für die Gesundheitszustände
public enum HealthState {
	FULLHEALTH(0), HARMED(1), WOUNDED(2);	
	private int healthState;
	
	private HealthState(int healthState){
		this.healthState=healthState;
	}
	public int getHealthState() {
		return this.healthState;
	}
}