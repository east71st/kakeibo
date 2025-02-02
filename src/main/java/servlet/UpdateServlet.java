package servlet;

import java.io.IOException;
import java.util.List;

import dao.KakeiboDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DisplayData;
import model.HimokuMapSetup;
import model.Kakeibo;
import model.UpdateKakeiboData;

@WebServlet("/UpdateServlet")
@MultipartConfig
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HimokuMapSetup himokuMapSetup = new HimokuMapSetup();
		himokuMapSetup.setHimokuMap(request);

		RequestDispatcher dispatcher = request.getRequestDispatcher("update.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String hidukeFirst = request.getParameter("hidukeFirst");
		String hidukeLast = request.getParameter("hidukeLast");
		String himokuSelectId = request.getParameter("himokuSelectId");
		String meisaiSelect = request.getParameter("meisaiSelect");
		String tableScrollTop = request.getParameter("tableScrollTop");

		DisplayData updateData = new DisplayData(hidukeFirst, hidukeLast, himokuSelectId, meisaiSelect, tableScrollTop);

		HttpSession session = request.getSession();
		session.setAttribute("updateData", updateData);

		KakeiboDAO kakeiboDAO = new KakeiboDAO();

		if (request.getParameter("option") != null) {

			String id = request.getParameter("id");
			String hiduke = request.getParameter("hiduke");
			String himokuId = request.getParameter("himokuId");
			String meisai = request.getParameter("meisai");
			String nyukingaku = request.getParameter("nyukingaku").replace(",", "");
			String shukingaku = request.getParameter("shukingaku").replace(",", "");

			UpdateKakeiboData updateKakeiboData = new UpdateKakeiboData(id, hiduke, himokuId, meisai, nyukingaku,
					shukingaku);

			if (updateKakeiboData.getErrorMsg().equals("")) {

				if (request.getParameter("option").equals("update")) {

					kakeiboDAO.update(updateKakeiboData.getKakeibo());
				} else if (request.getParameter("option").equals("delete")) {

					kakeiboDAO.delete(updateKakeiboData.getKakeibo());
				}
			}
		}

		List<Kakeibo> kakeiboList = (List<Kakeibo>) kakeiboDAO.findSelect(hidukeFirst, hidukeLast, himokuSelectId,
				meisaiSelect);

		request.setAttribute("kakeiboList", kakeiboList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("update.jsp");
		dispatcher.forward(request, response);
	}
}
