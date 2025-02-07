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
import model.ConsoleData;
import model.HimokuMapSetup;
import model.ValidationLogic;
import model.Kakeibo;
import model.UpdateData;

//家計簿データの修正用画面のためのコントロール
@WebServlet("/UpdateServlet")
@MultipartConfig
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		//データベースから費目データを呼び出してマップに格納しセッションスコープに保存
		HimokuMapSetup himokuMapSetup = new HimokuMapSetup();
		himokuMapSetup.setHimokuMap(request);

		RequestDispatcher dispatcher = request.getRequestDispatcher("update.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		//家計簿データの修正用画面で入力した表示条件データをチェックしてセッションスコープに保存
		String hidukeFirst = request.getParameter("hidukeFirst");
		String hidukeLast = request.getParameter("hidukeLast");
		String himokuSelectId = request.getParameter("himokuSelectId");
		String meisaiSelect = request.getParameter("meisaiSelect");
		String tableScrollTop = request.getParameter("tableScrollTop");

		ConsoleData consoleData = new ConsoleData(hidukeFirst, hidukeLast, himokuSelectId, meisaiSelect,
				tableScrollTop);

		HttpSession session = request.getSession();
		session.setAttribute("updateConsoleData", consoleData);
		
		KakeiboDAO kakeiboDAO = new KakeiboDAO();

		if (request.getParameter("option").equals("update")) {
			
			//入力された家計簿の修正データにエラーがなければデータベースを更新
			String id = request.getParameter("id");
			String hiduke = request.getParameter("hiduke");
			String himokuId = request.getParameter("himokuId");
			String meisai = request.getParameter("meisai");
			String nyukingaku = request.getParameter("nyukingaku").replace(",", "");
			String shukingaku = request.getParameter("shukingaku").replace(",", "");

			UpdateData updateData = new UpdateData(id, hiduke, himokuId, meisai, nyukingaku,
					shukingaku);

			if (updateData.getErrorMsg().equals("")) {
				kakeiboDAO.update(updateData.getKakeibo());
			}

		} else if (request.getParameter("option").equals("delete")) {

			//指定されたIDの家計簿データをデータベースから削除
			ValidationLogic validationLogic = new ValidationLogic();
			int id = validationLogic.parseId(request.getParameter("id"), null);
			kakeiboDAO.delete(id);

		}

		//データベースから家計簿データを呼び出してリストに格納しリクエストスコープに保存
		List<Kakeibo> kakeiboList = (List<Kakeibo>) kakeiboDAO.findSelect(hidukeFirst, hidukeLast, himokuSelectId,
				meisaiSelect);
		
		request.setAttribute("kakeiboList", kakeiboList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("update.jsp");
		dispatcher.forward(request, response);
	}
}
