package sample.model.factories;

public enum FactoryState {
	OFF("off"),
	RUNNING("running"),
	WAIT("wait"),
	PAUSE("pause");
	
	private String value;
	
	FactoryState(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
