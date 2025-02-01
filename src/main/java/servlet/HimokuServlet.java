package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.HimokuDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Himoku;
import model.HimokuMapSetup;
import model.HimokuSettingData;

@WebServlet("/HimokuServlet")
@MultipartConfig
public class HimokuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public HimokuServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HimokuMapSetup himokuMapSetup = new HimokuMapSetup();
		himokuMapSetup.setHimokuMap(request);

		RequestDispatcher dispatcher = request.getRequestDispatcher("himokuSetting.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HimokuDAO himokuDAO = new HimokuDAO();

		String id = request.getParameter("id");
		String himokumei = request.getParameter("himokumei");

		HimokuSettingData himokuSettingData = new HimokuSettingData(id, himokumei);

		if (himokuSettingData.getErrorMsg().equals("")) {

			if (request.getParameter("option") == null) {
				himokuDAO.create(himokuSettingData.getHimoku());

			} else if (request.getParameter("option").equals("update")) {

				himokuDAO.update(himokuSettingData.getHimoku());
			}
		}

		Map<Integer, Himoku> himokuMap = (Map<Integer, Himoku>) himokuDAO.findAll();
		
		HttpSession session = request.getSession();
		
		session.setAttribute("himokuSettingData", himokuSettingData);
		session.setAttribute("himokuMap", himokuMap);

		RequestDispatcher dispatcher = request.getRequestDispatcher("himokuSetting.jsp");
		dispatcher.forward(request, response);
	}

}
