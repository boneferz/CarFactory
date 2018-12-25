package sample.model.data;

import sample.model.suppliers.warehause.Warehouse;

public class GlobalData {
	// data
	public Warehouse warehouseBody;
	public Warehouse warehouseEngine;
	public Warehouse warehouseAccessories;
	
	// singleton
	private static GlobalData instance = new GlobalData();
	private GlobalData() {}
	public static GlobalData getInstance() { return instance; }
}


