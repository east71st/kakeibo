package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.KakeiboDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ConsoleData;
import model.HimokuMapSetup;
import model.Kakeibo;

//家計簿データの表示用のコントローラ
@WebServlet("/DisplayServlet")
public class DisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DisplayServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		//データベースから費目データを呼び出してマップに格納しセッションスコープに保存
		HimokuMapSetup himokuMapSetup = new HimokuMapSetup();
		himokuMapSetup.setHimokuMap(request);

		RequestDispatcher dispatcher = request.getRequestDispatcher("display.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		//入力された家計簿データの表示条件をチェックしてセッションスコープに保存
		String hidukeFirst = request.getParameter("hidukeFirst");
		String hidukeLast = request.getParameter("hidukeLast");
		String himokuSelectId = request.getParameter("himokuSelectId");
		String meisaiSelect = request.getParameter("meisaiSelect");

		ConsoleData consoleData = new ConsoleData(hidukeFirst, hidukeLast, himokuSelectId, meisaiSelect, "0");

		HttpSession session = request.getSession();
		session.setAttribute("DisplayConsoleData", consoleData);

		//データベースから家計簿データを呼び出してリストに格納しリクエストスコープに保存
		KakeiboDAO kakeiboDAO = new KakeiboDAO();
		List<Kakeibo> kakeiboList = (List<Kakeibo>) kakeiboDAO.findSelect(hidukeFirst, hidukeLast, himokuSelectId,
				meisaiSelect);

		request.setAttribute("kakeiboList", kakeiboList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("display.jsp");
		dispatcher.forward(request, response);

	}

}
