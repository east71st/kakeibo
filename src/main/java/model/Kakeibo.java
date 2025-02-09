package model;

import java.util.Date;

//家計簿データの格納用モデル
public class Kakeibo {

	private int id;			//ID
	private Date hiduke;	//日付
	private int himokuId;	//費目ID
	private String meisai;	//明細
	private int nyukingaku;	//入金額
	private int shukingaku;	//出金額

	public Kakeibo() {
	}

	public Kakeibo(int id, Date hiduke, int himokuId, String meisai, int nyukingaku, int shukingaku) {
		this.id = id;
		this.hiduke = hiduke;
		this.himokuId = himokuId;
		this.meisai = meisai;
		this.nyukingaku = nyukingaku;
		this.shukingaku = shukingaku;
	}

	public int getId() {
		return this.id;
	}

	public Date getHiduke() {
		return this.hiduke;
	}

	public int getHimokuId() {
		return this.himokuId;
	}

	public String getMeisai() {
		return this.meisai;
	}

	public int getNyukingaku() {
		return this.nyukingaku;
	}

	public int getShukingaku() {
		return this.shukingaku;
	}

}
