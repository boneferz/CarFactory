package sample.model;

import sample.model.suppliers.EnginesSupplier;

public class Model {
	
	public EnginesSupplier enginesSupplier;
	public EnginesSupplier[] suppliers;
	
	public Model() {
		suppliers = new EnginesSupplier[4];
		
		for (int i = 0; i < suppliers.length; i++) {
			enginesSupplier = new EnginesSupplier(i);
			suppliers[i] = enginesSupplier;
		}
	}
}
