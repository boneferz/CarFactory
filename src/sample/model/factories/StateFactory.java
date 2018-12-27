package sample.model.factories;

@Deprecated
public enum StateFactory {
	OFF("off"),
	RUNNING("running"),
	WAIT("wait"),
	PAUSE("pause");
	
	private String value;
	
	StateFactory(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
