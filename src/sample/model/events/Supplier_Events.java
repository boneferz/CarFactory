package sample.model.events;

public enum Supplier_Events {
	SWITCH_ON("switch_on"),
	SWITCH_OFF("switch_off"),
	MADE("made"),
	PROBLEM("problem"),
	PROBLEM_FIXED("problem_fixed"),
	RESUME("resume"),
	PAUSE("PAUSE");
	
	private String value;
	
	Supplier_Events(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
