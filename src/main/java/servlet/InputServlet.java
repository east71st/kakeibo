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

@WebServlet("/InputServlet")
public class InputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InputServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HimokuMapSetup himokuMapSetup = new HimokuMapSetup();
		himokuMapSetup.setHimokuMap(request);

		RequestDispatcher dispatcher = request.getRequestDispatcher("input.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String inputHiduke = request.getParameter("hiduke");
		String inputHimokuId = request.getParameter("himokuId");
		String inputMeisai = request.getParameter("meisai");
		String inputKingaku = request.getParameter("kingaku");

		InputData inputData = new InputData(inputHiduke, inputHimokuId, inputMeisai, inputKingaku);

		if (inputData.getErrorMsg().length() == 0) {

			KakeiboDAO kakeiboDAO = new KakeiboDAO();
			kakeiboDAO.create(inputData.getKakeibo());
			
			HttpSession session = request.getSession();
			List<Kakeibo> inputDataList = (List<Kakeibo>) session.getAttribute("inputDataList");
			
			if (inputDataList==null) {
				inputDataList= new ArrayList<Kakeibo>();
			}
			
			inputDataList.add(0,inputData.getKakeibo());
			session.setAttribute("inputDataList", inputDataList);			
		}

		request.setAttribute("inputData", inputData);

		RequestDispatcher dispatcher = request.getRequestDispatcher("input.jsp");
		dispatcher.forward(request, response);
	}

}
