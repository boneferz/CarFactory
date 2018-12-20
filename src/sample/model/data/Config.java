package sample.model.data;

public class Config {
	private static Config instance = new Config();
	
	public final int warehouseSize = 10;
	public final int factorysSpeed = 300;
	public final int salesSpeed = 100;
	public final int problemChance = 25;
	public final String logFile = "yes";
	
	// min\max values
	public final int warehouseSizeMin = 5;
	public final int warehouseSizeMax = 5;
	
	public static Config getInstance() {
		return instance;
	}
}
