package sample.model.events;

public enum Office_Events {
	ANSWER_DONE("answer_done"),
	UPDATE("update");
	
	private String value;
	
	Office_Events(String value) {
		this.value = value;
	}
}
