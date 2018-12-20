package sample.model;

import sample.model.suppliers.EnginesSupplier;
//import sample.model.suppliers.warehauses.EngineWarehouse;

public class Model {
	
	public EnginesSupplier enginesSupplier;
	public EnginesSupplier[] suppliers = new EnginesSupplier[4];
	
//	public EngineWarehouse engineWarehouse;
//	public EngineWarehouse[] warehouses = new EngineWarehouse[4];
	
	public Model() {
		
		for (int i = 0; i < suppliers.length; i++) {
			enginesSupplier = new EnginesSupplier(i);
			suppliers[i] = enginesSupplier;
		}

//		for (int i = 0; i < warehouses.length; i++) {
//			engineWarehouse = new EngineWarehouse(i);
//			warehouses[i] = engineWarehouse;
//		}
		
	}
}
