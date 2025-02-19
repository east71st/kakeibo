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
import model.HimokuMapSetup;
import model.InputData;
import model.Kakeibo;

//家計簿データの入力用のコントローラ
@WebServlet("/InputServlet")
public class InputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InputServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		//データベースから費目データを呼び出してマップに格納しセッションスコープに保存
		HimokuMapSetup himokuMapSetup = new HimokuMapSetup();
		himokuMapSetup.setHimokuMap(request);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/input.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		//入力された新規の家計簿データにエラーがなければ家計簿データモデルを生成
		String inputHiduke = request.getParameter("hiduke");
		String inputHimokuId = request.getParameter("himokuId");
		String inputMeisai = request.getParameter("meisai");
		String inputKingaku = request.getParameter("kingaku");

		InputData inputData = new InputData(inputHiduke, inputHimokuId, inputMeisai, inputKingaku);
		request.setAttribute("inputData", inputData);

		if (inputData.getErrorMsg().length() == 0) {

			//データベースに新規の家計簿データを追加
			KakeiboDAO kakeiboDAO = new KakeiboDAO();
			kakeiboDAO.create(inputData.getKakeibo());

			//入力した家計簿データを入力履歴のリストに追加してセッションスコープに保存
			HttpSession session = request.getSession();
			List<Kakeibo> inputDataList = (List<Kakeibo>) session.getAttribute("inputDataList");
			
			if (inputDataList == null) {
				inputDataList = new ArrayList<Kakeibo>();
			}
			
			inputDataList.add(0, inputData.getKakeibo());
			session.setAttribute("inputDataList", inputDataList);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/input.jsp");
		dispatcher.forward(request, response);
	}

}
