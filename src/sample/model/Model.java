package sample.model;

import sample.model.factories.FactoryCar;
import sample.model.factories.Factory;
import sample.model.factories.FactoryAccessorie;
import sample.model.factories.FactoryBody;
import sample.model.factories.FactoryEngine;

import java.util.ArrayList;
import java.util.List;

public class Model {
	
	public List<Factory> factory = new ArrayList<>();
	Dealers dealer;
	Office office;
	
	public Model() {
		factory.add(new FactoryEngine(0));
		factory.add(new FactoryBody(1));
		factory.add(new FactoryAccessorie(2));
		factory.add(new FactoryCar(3));
		
		office = new Office();
		dealer = new Dealers();
	}
}
