package sample.model.events;

public enum Dealer_Events {
	UPDATE("update"),
	PAUSE("pause"),
	RESUME("resume");
	
	private String value;
	
	Dealer_Events(String value) {
		this.value = value;
	}
}
