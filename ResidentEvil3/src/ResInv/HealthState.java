package ResInv;
//Enum f�r die Gesundheitszust�nde
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