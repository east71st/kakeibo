package model;

//費目データの設定用画面で入力された費目データの格納用モデル
public class HimokuSettingData {

	private String inputId;
	private String inputHimokumei;
	private Himoku himoku;
	private String errorMsg;

	public HimokuSettingData(String inputId, String inputHimokumei) {

		StringBuilder errorMsg = new StringBuilder("");

		//入力された費目データのチェック
		ValidationLogic validationLogic = new ValidationLogic();
		Integer id = validationLogic.parseId(inputId, errorMsg);
		String himokumei = validationLogic.parseHimokumei(inputHimokumei, errorMsg);

		//エラーがなければ費目データモデルを生成
		Himoku himoku = null;
		if (errorMsg.length() == 0) {
			himoku = new Himoku(id, himokumei);
		}

		this.inputId = inputId;
		this.inputHimokumei = inputHimokumei;
		this.himoku = himoku;
		this.errorMsg = errorMsg.toString();
	}

	public String getInputId() {
		return this.inputHimokumei;
	}

	public String getInputHimokumei() {
		return this.inputId;
	}

	public Himoku getHimoku() {
		return this.himoku;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}
}
