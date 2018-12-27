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
	
	private void factoryCarListener(Custom_EventObject e) {
		switch ((CarFactory_Events) e.getType()) {
			case TASK_DONE:
				System.out.println(" TASK_DONE");
				taskDone();
				break;
		}
	}
	
	public void dealerRequest(int count) {
		setTasks(++tasks);
		
		factoryEngine.on();
		factoryBody.on();
		factoryAccessorie.on();
		
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
