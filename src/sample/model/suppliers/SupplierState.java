package sample.model.suppliers;

public enum SupplierState {
	OFF("off"),
	RUNNING("running"),
	WAIT("wait"),
	PAUSE("pause");
	
	private String value;
	
	SupplierState(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
