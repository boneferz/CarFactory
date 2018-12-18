package sample.model;

public class Config {
	private static Config instance = new Config();
	
	public final int warehouseSize = 10;
	public final int factorysSpeed = 500;
	public final int salesSpeed = 100;
	public final int problemChance = 25;
	public final String logFile = "yes";
	
	// min\max values
	
	public static Config getInstance() {
		return instance;
	}
}
