package sample.model.events;

public enum Factory_Events {
	SWITCH_ON("switch_on"),
	SWITCH_OFF("switch_off"),
	
	CREATED("created"),
	/*WAIT("wait"),*/
	
	INIT_SPEED_SLIDER("init_speed_slider"),
	MOVE_SPEED_SLIDER("move_speed_slider"),
	
	PROBLEM("problem"),
	PROBLEM_FIXED("problem_fixed"),
	
	RESUME("resume"),
	PAUSE("PAUSE");
	
	private String value;
	
	Factory_Events(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
