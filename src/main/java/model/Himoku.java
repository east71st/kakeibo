package model;

public class Himoku {

	private int id;
	private String himokumei;

	public Himoku(int id, String himokumei) {
		this.id = id;
		this.himokumei = himokumei;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getHimokumei() {
		return this.himokumei;
	}

}
