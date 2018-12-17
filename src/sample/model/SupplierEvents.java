package sample.model;

public enum SupplierEvents {
	SWITCH_ON("switch_on"),
	SWITCH_OFF("switch_off"),
	PROBLEM("problem"),
	STACK_OVERFLOW("stack_overflow");
	
	private final String value;
	
	SupplierEvents(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
