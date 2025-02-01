package model;

public class HimokuSettingData {

	private String inputId;
	private String inputHimokumei;
	private Himoku himoku;
	private String errorMsg;
	
	public HimokuSettingData(String inputId,String inputHimokumei) {

		StringBuilder errorMsg = new StringBuilder("");

		InputLogic kLogic = new InputLogic();
		Integer id = kLogic.parseId(inputId, errorMsg);
		String himokumei = kLogic.parseMeisai(inputHimokumei, errorMsg);

		Himoku himoku = null;
		if (errorMsg.length() == 0) {
			himoku = new Himoku(id, himokumei);
		}

		this.inputId =inputId;
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
