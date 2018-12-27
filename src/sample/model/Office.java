package sample.model;

import sample.model.data.GlobalData;
import sample.model.events.*;
import sample.model.events.observer.EventDispatcher;
import sample.model.factories.FactoryCar;

public class Office extends EventDispatcher {
	
	int tasks = 0;
	
	FactoryCar factoryCar;
	
	public Office() {
		GlobalData.getInstance().office = this;
		
		factoryCar = GlobalData.getInstance().factoryCar;
		factoryCar.addEventListener(this::factoryCarListener);
	}
	
	private void factoryCarListener(Custom_EventObject e) {
		switch ((CarFactory_Events) e.getType()) {
			case TASK_DONE:
				System.out.println("Office.factoryCarListener - TASK_DONE");
				taskDone();
				break;
		}
	}
	
	public void dealerRequest(int count) {
		setTasks(++tasks);
		factoryCar.taskOnCreation(count);
	}
	
	private void taskDone() {
		setTasks(--tasks);
		dispatchEvent(this, Events.OFFICE, Office_Events.ANSWER_DONE);
	}
	
	public void setTasks(int tasks) {
		this.tasks = tasks;
		FacadeModel.fireEvent(this, Events.OFFICE, Office_Events.UPDATE);
	}
	
	
}
