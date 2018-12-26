package sample.model;

import sample.model.factories.FactoryCar;
import sample.model.factories.Factory;
import sample.model.factories.FactoryAccessorie;
import sample.model.factories.FactoryBody;
import sample.model.factories.FactoryEngine;

import java.util.ArrayList;
import java.util.List;

public class Model {
	
	public List<Factory> supplier = new ArrayList<>();
	Dealers dealer;
	
	public Model() {
		supplier.add(new FactoryEngine(0));
		supplier.add(new FactoryBody(1));
		supplier.add(new FactoryAccessorie(2));
		supplier.add(new FactoryCar(3));
		
		// ofice
		// dealers
		dealer = new Dealers();
		
	}
}
