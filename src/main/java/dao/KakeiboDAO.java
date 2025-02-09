package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Kakeibo;

//家計簿データのDAO
public class KakeiboDAO {

	private final String JDBC_URL = "jdbc:mysql://localhost:3306/kakeibo";
	private final String DB_USER = "admin";
	private final String DB_PASS = "password";
	private final String FQCN = "com.mysql.cj.jdbc.Driver";
	
//	private final String JDBC_URL = "jdbc:h2:tcp://localhost/U:/My Documents/h2/kakeibo";
//	private final String DB_USER = "sa";
//	private final String DB_PASS = "";
//	private final String FQCN = "org.h2.Driver";
	
	public List<Kakeibo> findAll() {

		List<Kakeibo> kakeiboList = new ArrayList<Kakeibo>();

		try {
			Class.forName(FQCN);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			String sql = "SELECT * FROM KAKEIBO ORDER BY ID ASC";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("ID");
				Date hiduke = rs.getDate("HIDUKE");
				int himokuId = rs.getInt("HIMOKU_ID");
				String meisai = rs.getString("MEISAI");
				int nyukingaku = rs.getInt("NYUKINGAKU");
				int shukingaku = rs.getInt("SHUKINGAKU");
				Kakeibo kakeibo = new Kakeibo(id, hiduke, himokuId, meisai, nyukingaku, shukingaku);
				kakeiboList.add(kakeibo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return kakeiboList;
	}

	public List<Kakeibo> findSelect(String hidukeFirst, String hidukeLast, String himokuSelectId, String meisaiSelect) {

		List<Kakeibo> kakeiboList = new ArrayList<Kakeibo>();

		try {
			Class.forName(FQCN);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			String sqlHiduke = " HIDUKE >= '" + hidukeFirst + "' AND HIDUKE <= '" + hidukeLast + "'";
			String sqlHimokuId = "";
			if (!himokuSelectId.equals("0")) {
				sqlHimokuId = " AND HIMOKU_ID = '" + himokuSelectId + "'";
			}
			String sqlMeisai = "";
			if (!meisaiSelect.equals("")) {
				sqlMeisai = " AND MEISAI LIKE '%" + meisaiSelect + "%'";
			}

			String sqlWHERE = " WHERE" + sqlHiduke + sqlHimokuId + sqlMeisai;
			String sql = "SELECT * FROM KAKEIBO" + sqlWHERE + " ORDER BY HIDUKE ASC,ID ASC";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("ID");
				Date hiduke = rs.getDate("HIDUKE");
				int himokuId = rs.getInt("HIMOKU_ID");
				String meisai = rs.getString("MEISAI");
				int nyukingaku = rs.getInt("NYUKINGAKU");
				int shukingaku = rs.getInt("SHUKINGAKU");
				Kakeibo kakeibo = new Kakeibo(id, hiduke, himokuId, meisai, nyukingaku, shukingaku);
				kakeiboList.add(kakeibo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return kakeiboList;
	}

	public boolean create(Kakeibo kakeibo) {

		try {
			Class.forName(FQCN);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			String sql = "INSERT INTO KAKEIBO(HIDUKE,HIMOKU_ID,MEISAI,NYUKINGAKU,SHUKINGAKU) VALUES(?,?,?,?,?)";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setDate(1, new java.sql.Date(kakeibo.getHiduke().getTime()));
			pStmt.setInt(2, kakeibo.getHimokuId());
			pStmt.setString(3, kakeibo.getMeisai());
			pStmt.setInt(4, kakeibo.getNyukingaku());
			pStmt.setInt(5, kakeibo.getShukingaku());

			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean update(Kakeibo kakeibo) {

		try {
			Class.forName(FQCN);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			String sql = "UPDATE KAKEIBO SET HIDUKE=?,HIMOKU_ID=?,MEISAI=?,NYUKINGAKU=?,SHUKINGAKU=? WHERE ID=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setDate(1, new java.sql.Date(kakeibo.getHiduke().getTime()));
			pStmt.setInt(2, kakeibo.getHimokuId());
			pStmt.setString(3, kakeibo.getMeisai());
			pStmt.setInt(4, kakeibo.getNyukingaku());
			pStmt.setInt(5, kakeibo.getShukingaku());
			pStmt.setInt(6, kakeibo.getId());

			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delete(int id) {

		try {
			Class.forName(FQCN);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			String sql = "DELETE FROM KAKEIBO WHERE ID=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, id);

			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
