package model;

//費目データをマップに格納してセッションスコープに保存する処理
import java.util.Map;

import dao.HimokuDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class HimokuMapSetup {

	public void setHimokuMap(HttpServletRequest request) {

		//データベースから費目データを呼び出してマップに格納
		HimokuDAO himokuDAO = new HimokuDAO();
		Map<Integer, Himoku> himokuMap = (Map<Integer, Himoku>) himokuDAO.findAll();

		//セッションスコープに保存
		HttpSession session = request.getSession();
		session.setAttribute("himokuMap", himokuMap);

	}
}
