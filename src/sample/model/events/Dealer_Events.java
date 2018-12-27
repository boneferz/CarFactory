package sample.model.events;

public enum Dealer_Events {
	UPDATE("update"),
	WORK("work"),
	WAIT("wait");
	
	
	private String value;
	
	Dealer_Events(String value) {
		this.value = value;
	}
}
