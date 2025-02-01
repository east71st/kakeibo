package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Himoku;

public class HimokuDAO {

	private final String JDBC_URL = "jdbc:h2:tcp://localhost/U:/My Documents/h2/kakeibo";
	private final String DB_USER = "sa";
	private final String DB_PASS = "";

	public Map<Integer, Himoku> findAll() {

		Map<Integer, Himoku> himokuMap = new HashMap<Integer, Himoku>();

		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			
			String sql = "SELECT * FROM HIMOKU ORDER BY ID ASC";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("ID");
				String himokumei = rs.getString("HIMOKUMEI");
				Himoku himoku = new Himoku(id, himokumei);
				himokuMap.put(himoku.getId(), himoku);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return himokuMap;
	}

	public boolean create(Himoku himoku) {

		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			
			String sql = "INSERT INTO HIMOKU(HIMOKUMEI) VALUES(?)";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, himoku.getHimokumei());

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

	public boolean update(Himoku himoku) {

		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			
			String sql = "UPDATE HIMOKU SET HIMOKUMEI=? WHERE ID=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, himoku.getHimokumei());
			pStmt.setInt(2, himoku.getId());

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
