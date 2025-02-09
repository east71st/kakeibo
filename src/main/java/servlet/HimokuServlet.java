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

//費目データの設定用のコントローラ
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

		//データベースから費目データを呼び出してマップに格納しセッションスコープに保存
		HimokuMapSetup himokuMapSetup = new HimokuMapSetup();
		himokuMapSetup.setHimokuMap(request);

		RequestDispatcher dispatcher = request.getRequestDispatcher("himokuSetting.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		//入力されたデータにエラーがなければ費目データモデルを生成
		String id = request.getParameter("id");
		String himokumei = request.getParameter("himokumei");

		HimokuSettingData himokuSettingData = new HimokuSettingData(id, himokumei);

		//入力データと費目データモデルをセッションスコープに保存
		HttpSession session = request.getSession();
		session.setAttribute("himokuSettingData", himokuSettingData);
		
		//データベースに費目データを追加もしくは更新
		HimokuDAO himokuDAO = new HimokuDAO();
		if (himokuSettingData.getErrorMsg().equals("")) {

			if (request.getParameter("option").equals("create")) {
				
				himokuDAO.create(himokuSettingData.getHimoku());
			} else if (request.getParameter("option").equals("update")) {

				himokuDAO.update(himokuSettingData.getHimoku());
			}
		}
		
		//データベースから費目データを呼び出してマップに格納しセッションスコープに保存
		HimokuMapSetup himokuMapSetup = new HimokuMapSetup();
		himokuMapSetup.setHimokuMap(request);

		RequestDispatcher dispatcher = request.getRequestDispatcher("himokuSetting.jsp");
		dispatcher.forward(request, response);
	}

}
