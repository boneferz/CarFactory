package sample.model.events;

public enum Warehouse_Events {
	UPDATE("update"),
	RELEASED("released"),
	
	EMPTY("empty"),
	OVERFLOW("overflow"),
	
	PUSHED("pushed");
	
	private String value;
	
	Warehouse_Events(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
