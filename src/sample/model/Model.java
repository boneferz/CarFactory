package sample.model;

import sample.model.suppliers.EnginesSupplier;

public class Model {
	
	public EnginesSupplier enginesSupplier;
	public EnginesSupplier[] suppliers = new EnginesSupplier[4];
	
	public Model() {
		
		for (int i = 0; i < suppliers.length; i++) {
			enginesSupplier = new EnginesSupplier(i);
			suppliers[i] = enginesSupplier;
		}

	}
}
