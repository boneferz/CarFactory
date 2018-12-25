package sample.model.suppliers.detail;

public class Car extends Detail {
	Engine engine;
	Body body;
	Accessories accessorie;
	
	public Car(int id, Engine engine, Body body, Accessories accessorie) {
		super(id);
		
		this.engine = engine;
		this.body = body;
		this.accessorie = accessorie;
	}
	
	public Car(int id) {
		super(id);
	}
}
