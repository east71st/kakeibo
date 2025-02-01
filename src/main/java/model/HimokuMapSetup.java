package model;

import java.util.Map;

import dao.HimokuDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class HimokuMapSetup {

	public void setHimokuMap(HttpServletRequest request) {

		HimokuDAO himokuDAO = new HimokuDAO();
		Map<Integer, Himoku> himokuMap = (Map<Integer, Himoku>) himokuDAO.findAll();

		HttpSession session = request.getSession();
		session.setAttribute("himokuMap", himokuMap);

	}
}
