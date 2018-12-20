package sample.model.suppliers.details;

public class Engine {
	String code;
	int id;
	
	public Engine(int id) {
		this.id = id;
		
		code = createCode();
	}
	
	private String createCode() {
		return "EW12R";
	}
}
