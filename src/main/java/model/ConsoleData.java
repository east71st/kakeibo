package model;

//家計簿データの表示用画面と修正用画面でコンソールに入力された表示条件データの格納用モデル
public class ConsoleData {

	private String hidukeFirst;
	private String hidukeLast;
	private String himokuSelectId;
	private String meisaiSelect;
	private Integer tableScrollTop;
	private String errorMsg;

	public ConsoleData(String hidukeFirst, String hidukeLast, String himokuSelectId, String meisaiSelect,
			String tableScrollTop) {

		StringBuilder errorMsg = new StringBuilder("");

//		入力された表示条件データのチェック
		ValidationLogic validationLogic = new ValidationLogic();
		validationLogic.parseHiduke(hidukeFirst, errorMsg);
		validationLogic.parseHiduke(hidukeLast, errorMsg);
		validationLogic.parseHimokuId(himokuSelectId, "display", errorMsg);

		this.hidukeFirst = hidukeFirst;
		this.hidukeLast = hidukeLast;
		this.himokuSelectId = himokuSelectId;
		this.meisaiSelect = meisaiSelect;
		this.tableScrollTop = validationLogic.parseInteger(tableScrollTop, errorMsg);
		this.errorMsg = errorMsg.toString();

	}

	public String getHidukeFirst() {
		return this.hidukeFirst;
	}

	public String getHidukeLast() {
		return this.hidukeLast;
	}

	public String getHimokuSelectId() {
		return this.himokuSelectId;
	}

	public String getMeisaiSelect() {
		return this.meisaiSelect;
	}

	public Integer getTableScrollTop() {
		return this.tableScrollTop;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}
}
