package sample.model.factories.detail;

public abstract class Detail {
	
	private String code;
	private int id;
	
	public Detail(int id) {
		this.id = id;
		
		code = createCode();
	}
	
	private String createCode() {
		return "EW12R";
	}
	
	public String getCode() {
		return code;
	}
	
	public int getId() {
		return id;
	}
}
