package sample.model;

public enum SupplierEvents {
	SWITCH_ON("switch_on"),
	SWITCH_OFF("switch_off"),
	MADE("made"),
	PROBLEM("problem"),
	STACK_OVERFLOW("stack_overflow");
	
	private String value;
	
	SupplierEvents(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
