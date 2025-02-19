package model;

import java.io.Serializable;
import java.util.Date;

//家計簿データの修正用画面で入力された家計簿の修正データの格納用モデル
public class UpdateData implements Serializable {

	private String updateId;
	private String updateHiduke;
	private String updateHimokuId;
	private String updateNaiyou;
	private String updateNyukingaku;
	private String updateShukingaku;
	private Kakeibo kakeibo;
	private String errorMsg;

	public UpdateData(String updateId, String updateHiduke, String updateHimokuId, String updateMeisai,
			String updateNyukingaku, String updateShukingaku) {

		StringBuilder errorMsg = new StringBuilder("");

		//入力された家計簿の修正データをチェック
		ValidationLogic validationLogic = new ValidationLogic();

		Integer id = validationLogic.parseId(updateId, errorMsg);
		Date hiduke = validationLogic.parseHiduke(updateHiduke, errorMsg);
		Integer himokuId = validationLogic.parseHimokuId(updateHimokuId, "update", errorMsg);
		String meisai = validationLogic.parseMeisai(updateMeisai, errorMsg);
		Integer nyukingaku = validationLogic.parseKingaku(updateNyukingaku, "入", errorMsg);
		Integer shukingaku = validationLogic.parseKingaku(updateShukingaku, "出", errorMsg);

		if (himokuId == 1 & shukingaku != 0) {
			errorMsg = errorMsg.append(errorMsg.length() == 0 ? "" : " ").append("収入に出金があります");
		} else if (himokuId != 1 & nyukingaku != 0) {
			errorMsg = errorMsg.append(errorMsg.length() == 0 ? "" : " ").append("出金費目に入金があります");
		}

		//エラーがなければ家計簿データモデルを生成
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
		this.kakeibo = kakeibo;
		this.errorMsg = errorMsg.toString();
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

	public Kakeibo getKakeibo() {
		return this.kakeibo;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}
}
