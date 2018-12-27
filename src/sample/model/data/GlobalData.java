package sample.model.data;

import sample.model.Dealers;
import sample.model.Office;
import sample.model.factories.FactoryAccessorie;
import sample.model.factories.FactoryBody;
import sample.model.factories.FactoryCar;
import sample.model.factories.FactoryEngine;
import sample.model.factories.warehause.Warehouse;

public class GlobalData {
	// data
	public FactoryEngine factoryE;
	public FactoryBody factoryB;
	public FactoryAccessorie factoryA;
	
	public Warehouse warehouseBody;
	public Warehouse warehouseEngine;
	public Warehouse warehouseAccessories;
	
	public Warehouse warehouseCar;
	
	public FactoryCar factoryCar;
	public Office office;
	public Dealers dealers;
	
	// singleton
	private static GlobalData instance = new GlobalData();
	private GlobalData() {}
	public static GlobalData getInstance() { return instance; }
}


