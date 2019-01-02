package sample.model;

import sample.model.data.GlobalData;
import sample.model.events.*;
import sample.model.events.observer.EventDispatcher;
import sample.model.factories.FactoryAccessorie;
import sample.model.factories.FactoryBody;
import sample.model.factories.FactoryCar;
import sample.model.factories.FactoryEngine;

public class Office extends EventDispatcher {
	
	int tasks = 0;
	
	FactoryEngine factoryEngine;
	FactoryBody factoryBody;
	FactoryAccessorie factoryAccessorie;
	FactoryCar factoryCar;
	
	public Office() {
		GlobalData.getInstance().office = this;
		
		factoryEngine = GlobalData.getInstance().factoryE;
		factoryBody = GlobalData.getInstance().factoryB;
		factoryAccessorie = GlobalData.getInstance().factoryA;
		factoryCar = GlobalData.getInstance().factoryCar;
		
		factoryCar.addEventListener(this::factoryCarListener);
	}
	
	private void factoryCarListener(EventObject e) {
		switch ((Event_FactoryCar) e.getType()) {
			case TASK_DONE:
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
		dispatchEvent(this, Event.OFFICE, Event_Office.ANSWER_DONE);
	}
	
	public void setTasks(int tasks) {
		this.tasks = tasks;
		ModelFacade.fireEvent(this, Event.OFFICE, Event_Office.UPDATE);
	}
}
