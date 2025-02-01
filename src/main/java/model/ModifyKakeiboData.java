package model;

import java.util.Date;

public class ModifyKakeiboData {

	private String updateId;
	private String updateHiduke;
	private String updateHimokuId;
	private String updateNaiyou;
	private String updateNyukingaku;
	private String updateShukingaku;
	private String errorMsg;
	private Kakeibo kakeibo;

	public ModifyKakeiboData(String updateId, String updateHiduke, String updateHimokuId, String updateMeisai,
			String updateNyukingaku, String updateShukingaku) {

		StringBuilder errorMsg = new StringBuilder("");

		InputLogic inputLogic = new InputLogic();

		Integer id = inputLogic.parseId(updateId, errorMsg);
		Date hiduke = inputLogic.parseHiduke(updateHiduke, errorMsg);
		Integer himokuId = inputLogic.parseHimokuId(updateHimokuId, "update", errorMsg);
		String meisai = inputLogic.parseMeisai(updateMeisai, errorMsg);
		Integer nyukingaku = inputLogic.parseKingaku(updateNyukingaku, "入", errorMsg);
		Integer shukingaku = inputLogic.parseKingaku(updateShukingaku, "出", errorMsg);

		if (himokuId == 1 & shukingaku != 0) {
			errorMsg = errorMsg.append(errorMsg.length() == 0 ? "" : " ").append("収入に出金があります");
		} else if (himokuId != 1 & nyukingaku != 0) {
			errorMsg = errorMsg.append(errorMsg.length() == 0 ? "" : " ").append("出金の費目に入金があります");
		}

		Kakeibo kakeibo = null;
		if (errorMsg.length() == 0) {
			kakeibo = new Kakeibo(id, hiduke, himokuId, meisai, nyukingaku, shukingaku);
		}

		this.updateId = updateId;
		this.updateHiduke = updateHiduke;
		this.updateHimokuId = updateHimokuId;
		this.updateNaiyou = updateMeisai;
		this.updateNyukingaku = updateNyukingaku;
		this.updateShukingaku = updateShukingaku;
		this.errorMsg = errorMsg.toString();
		this.kakeibo = kakeibo;

	}

	public String getUpdateId() {
		return this.updateId;
	}

	public String getUpdateHiduke() {
		return this.updateHiduke;
	}

	public String getUpdateHimokuId() {
		return this.updateHimokuId;
	}

	public String getUpdateNaiyou() {
		return this.updateNaiyou;
	}

	public String getUpdateNyukingaku() {
		return this.updateNyukingaku;
	}

	public String getUpdateShukingaku() {
		return this.updateShukingaku;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public Kakeibo getKakeibo() {
		return this.kakeibo;
	}
}
