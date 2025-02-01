package model;

import java.util.Date;

public class Kakeibo {

	private int id;
	private Date hiduke;
	private int himokuId;
	private String meisai;
	private int nyukingaku;
	private int shukingaku;

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
