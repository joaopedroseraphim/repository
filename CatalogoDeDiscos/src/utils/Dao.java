package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dao {
private Connection con;
private String driver = "com.mysql.cj.jdbc.Driver";
private String url = "jdbc:mysql://localhost:3306/catalogo_discos";
private String user = "root";
private String password = "@senai508";

	public Connection conexao() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}