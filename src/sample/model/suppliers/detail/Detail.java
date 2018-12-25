package sample.model.suppliers.detail;

public abstract class Detail {
	public String code;
	public int id;
	
	public Detail(int id) {
		this.id = id;
		
		code = createCode();
	}
	
	private String createCode() {
		return "EW12R";
	}
}
