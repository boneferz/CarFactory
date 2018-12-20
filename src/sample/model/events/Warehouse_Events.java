package sample.model.events;

public enum Warehouse_Events {
	PULL("pull"),
	UPDATE("update"),
	RELEASED("released"),
	OVERFLOW("overflow");
	
	private String value;
	
	Warehouse_Events(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
