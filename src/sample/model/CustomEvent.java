package sample.model;

import java.util.EventObject;

public class CustomEvent extends EventObject {
	private SupplierEvents type;
	
	public CustomEvent(Object source, SupplierEvents type) {
		super(source);
		this.type = type;
	}
	
	public SupplierEvents getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return super.toString() + ", [" + type.toString() + "]";
	}
}
