package sample.model.data;

public class Config {
	private static Config instance = new Config();
	
	public final int warehouseSize  = 10;    // 10-99
	public final int factorysSpeed  = 100;   // 100-1000 ms
	public final int salesSpeed     = 100;   // 100-1000 ms
	public final int problemChance  = 50;    // 0-100 %
	public final int problemDelay   = 2000;  // 100-1000 ms
	public final String logFile     = "yes"; // yes\no
	
	// min\max values
	public final int warehouseSizeMin = 5;
	public final int warehouseSizeMax = 5;
	public final int factorysSpeedMax  = 50;   // 100-1000 ms
	public final int factorysSpeedMin  = 1500;   // 100-1000 ms
	
	
	public static Config getInstance() {
		return instance;
	}
}
