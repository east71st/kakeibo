package model;

import java.util.Date;

//家計簿データの入力用画面で入力された家計簿の新規データの格納用モデル
public class InputData {

	private String inputHiduke;
	private String inputHimokuId;
	private String inputMeisai;
	private String inputKingaku;
	private Kakeibo kakeibo;
	private String errorMsg;

	public InputData(String inputHiduke, String inputHimokuId, String inputMeisai, String inputKingaku) {

		StringBuilder errorMsg = new StringBuilder("");

		//入力された家計簿の新規データのチェックをして家計簿データモデルに格納
		ValidationLogic validationLogic = new ValidationLogic();
		Date hiduke = validationLogic.parseHiduke(inputHiduke, errorMsg);
		Integer himokuId = validationLogic.parseHimokuId(inputHimokuId, "input", errorMsg);
		String meisai = validationLogic.parseMeisai(inputMeisai, errorMsg);
		Integer kingaku = validationLogic.parseKingaku(inputKingaku, "", errorMsg);

		//エラーがなければ家計簿データモデルを生成
		Kakeibo kakeibo = null;
		if (errorMsg.length() == 0) {

			int nyukingaku = 0;
			int shukingaku = 0;
			if (himokuId == 1) {
				nyukingaku = kingaku;
			} else {
				shukingaku = kingaku;
			}
			kakeibo = new Kakeibo(0, hiduke, himokuId, meisai, nyukingaku, shukingaku);
		}

		this.inputHiduke = inputHiduke;
		this.inputHimokuId = inputHimokuId;
		this.inputMeisai = inputMeisai;
		this.inputKingaku = inputKingaku;
		this.errorMsg = errorMsg.toString();
		this.kakeibo = kakeibo;

	}

	public String getInputHiduke() {
		return this.inputHiduke;
	}

	public String getInputHimokuId() {
		return this.inputHimokuId;
	}

	public String getInputMeisai() {
		return this.inputMeisai;
	}

	public String getInputKingaku() {
		return this.inputKingaku;
	}

	public Kakeibo getKakeibo() {
		return this.kakeibo;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

}
