package sample.model.events;

public enum Warehouse_Events {
	PULLED("pulled"),
	UPDATE("update"),
	RELEASED("released"),
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
