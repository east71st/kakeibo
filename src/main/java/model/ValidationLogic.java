package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.http.HttpSession;

//入力データのチェックと型変換処理
public class ValidationLogic {

	//数値
	public Integer parseInteger(String kakeiboTableScrollTop, StringBuilder msg) {

		if (kakeiboTableScrollTop == null || kakeiboTableScrollTop.length() == 0) {
			return 0;
		}

		try {
			return Integer.parseInt(kakeiboTableScrollTop);
		} catch (NumberFormatException e) {
			msg = msg.append(msg.length() == 0 ? "" : " ").append("数値を入力してください");
			return null;
		}
	}

	//費目名
	public String parseHimokumei(String himokumei, StringBuilder msg) {

		if (himokumei == null) {
			msg = msg.append("費目名が未入力です");
			return "";
		} else if (himokumei.length() > 40) {
			msg = msg.append(msg.length() == 0 ? "" : " ").append("費目名は40文字以下で入力してください");
			return null;
		}

		return himokumei;
	}

	//ID
	public Integer parseId(String id, StringBuilder msg) {

		if (id == null || id.length() == 0) {
			return 0;
		}

		try {
			return Integer.parseInt(id);
		} catch (NumberFormatException e) {
			msg = msg.append(msg.length() == 0 ? "" : " ").append("IDの入力に誤りがあります");
			return null;
		}
	}

	//日付
	public Date parseHiduke(String hiduke, StringBuilder msg) {

		if (hiduke == null || hiduke.length() == 0) {
			msg = msg.append("日付が未入力です");
			return null;
		}

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return dateFormat.parse(hiduke);
		} catch (ParseException e) {
			msg = msg.append(msg.length() == 0 ? "" : " ").append("日付の入力に誤りがあります");
			return null;
		}
	}

	//費目ID
	public Integer parseHimokuId(String himokuId, String mode, StringBuilder msg) {

		if (himokuId == null || himokuId.length() == 0) {
			msg = msg.append(msg.length() == 0 ? "" : " ").append("費目が未入力です");
			return null;
		}

		try {
			Integer num = Integer.parseInt(himokuId);

			if (mode.equals("input") && num == 0) {
				msg = msg.append(msg.length() == 0 ? "" : " ").append("費目の入力に誤りがあります");
				return null;
			}

			return num;

		} catch (NumberFormatException e) {
			msg = msg.append(msg.length() == 0 ? "" : " ").append("費目の入力に誤りがあります");
			return null;
		}
	}

	//明細
	public String parseMeisai(String meisai, StringBuilder msg) {

		if (meisai == null) {
			return "";
		} else if (meisai.length() > 100) {
			msg = msg.append(msg.length() == 0 ? "" : " ").append("明細は100文字以下で入力してください");
			return null;
		}

		return meisai;
	}

	//金額
	public Integer parseKingaku(String kingak, String str, StringBuilder msg) {

		if (kingak == null || kingak.length() == 0) {
			return 0;
		}

		try {
			Integer num = Integer.parseInt(kingak);

			if (num < 0) {
				msg = msg.append(msg.length() == 0 ? "" : " ").append(str + "金額がマイナスです");
				return null;
			}

			return num;
		} catch (NumberFormatException e) {
			msg = msg.append(msg.length() == 0 ? "" : " ").append(str + "金額の入力に誤りがあります");
			return null;
		}

	}

}
