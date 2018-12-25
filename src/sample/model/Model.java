package sample.model;

import sample.model.suppliers.FactoryCar;
import sample.model.suppliers.Factory;
import sample.model.suppliers.FactoryAccessorie;
import sample.model.suppliers.FactoryBody;
import sample.model.suppliers.FactoryEngine;

import java.util.ArrayList;
import java.util.List;

public class Model {
	
	public List<Factory> supplier = new ArrayList<>();
	
	public Model() {
		supplier.add(new FactoryEngine(0));
		supplier.add(new FactoryBody(1));
		supplier.add(new FactoryAccessorie(2));
		supplier.add(new FactoryCar(3));
		
		// ofice
		
		// dealers
	}
}
