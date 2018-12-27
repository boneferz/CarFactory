package sample.model.events;

public enum CarFactory_Events {
	TASK_DONE("task_done");
	
	private String value;
	
	CarFactory_Events(String value) {
		this.value = value;
	}
}
