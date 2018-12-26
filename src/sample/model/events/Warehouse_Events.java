package sample.model.events;

public enum Warehouse_Events {
	UPDATE("update"),
	RELEASED("released"),
	ADDED("added");
	
	
	private String value;
	
	Warehouse_Events(String value) {
		this.value = value;
	}
}
