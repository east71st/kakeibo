package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.http.HttpSession;

public class InputLogic {

	public String parseHimokumei(String himokumei, StringBuilder msg) {

		if (himokumei == null) {
			msg = msg.append("費目名が未入力です");
			return "";
		} else if (himokumei.length() > 40) {
			msg = msg.append(msg.length() == 0 ? "" : " ").append("費目名は100文字以下で入力してください");
			return null;
		}

		return himokumei;
	}

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

	public String parseMeisai(String meisai, StringBuilder msg) {

		if (meisai == null) {
			return "";
		} else if (meisai.length() > 100) {
			msg = msg.append(msg.length() == 0 ? "" : " ").append("明細は100文字以下で入力してください");
			return null;
		}

		return meisai;
	}

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
