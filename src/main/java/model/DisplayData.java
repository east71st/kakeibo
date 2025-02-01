package model;

public class DisplayData {

	private String hidukeFirst;
	private String hidukeLast;
	private String himokuSelectId;
	private String meisaiSelect;
	private Integer tableScrollTop;
	private String errorMsg;

	public DisplayData(String hidukeFirst, String hidukeLast, String himokuSelectId, String meisaiSelect,
			String tableScrollTop) {

		StringBuilder errorMsg = new StringBuilder("");

		InputLogic inputLogic = new InputLogic();
		inputLogic.parseHiduke(hidukeFirst, errorMsg);
		inputLogic.parseHiduke(hidukeLast, errorMsg);
		inputLogic.parseHimokuId(himokuSelectId, "display", errorMsg);

		this.hidukeFirst = hidukeFirst;
		this.hidukeLast = hidukeLast;
		this.himokuSelectId = himokuSelectId;
		this.meisaiSelect = meisaiSelect;
		this.tableScrollTop = inputLogic.parseInteger(tableScrollTop);
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
