package model;

//費目データの格納用モデル
public class Himoku {

	private int id;				//ID
	private String himokumei;	//費目名

	public Himoku() {
	}

	public Himoku(int id, String himokumei) {
		this.id = id;
		this.himokumei = himokumei;
	}

	public int getId() {
		return this.id;
	}

	public String getHimokumei() {
		return this.himokumei;
	}

}
