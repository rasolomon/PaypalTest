package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {
	// JDBC driver name and database URL
	private static String JDBC_DRIVER = "org.h2.Driver";
	private static String DB_URL = "jdbc:h2:~/test";

	// Database credentials
	private static String USER = "sa";
	private static String PASS = "";

	private static Connection conn = null;
	private static Statement stmt = null;

	@Override
	public ResponseEntity<Employee> employeeGetById(String id) {
		Employee employee = new Employee();
		Address address = new Address();
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			String sql = "SELECT * FROM Employee e\n" + "JOIN Address a ON e.id = a.employeeId\n" + "WHERE e.id = "
					+ id;
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				address.setCity(rs.getString("city"));
				address.setCountry(rs.getString("country"));
				address.setLine1(rs.getString("line1"));
				address.setLine2(rs.getString("line2"));
				address.setState(rs.getString("state"));
				address.setZipCode(rs.getString("zip_code"));

				employee.setAddress(address);
				employee.setDateOfBirth(rs.getString("date_of_birth"));
				employee.setFirstName(rs.getString("first_name"));
				employee.setLastName(rs.getString("last_name"));
			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Employee> createEmployee(Employee employee) {
		int employeeId = 0;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			String sql = "INSERT INTO Employee (first_name, last_name, date_of_birth) VALUES ('";
			sql += employee.getFirstName() + "','" + employee.getLastName() + "','" + employee.getLastName() + "'";
			sql += ")";

			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				employeeId = rs.getInt(1);

				sql = "INSERT INTO Address (line1, line2, city, state, country, zip_code, employeeId) VALUES ('";
				sql += employee.getAddress().getLine1() + "','" + employee.getAddress().getLine2() + "','"
						+ employee.getAddress().getCity() + "','";
				sql += employee.getAddress().getState() + "','" + employee.getAddress().getCountry() + "','"
						+ employee.getAddress().getZipCode() + "',";
				sql += employeeId + ")";
				stmt.executeUpdate(sql);
			}
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
			return new ResponseEntity<>(employee, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(employee, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				return new ResponseEntity<>(employee, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
				return new ResponseEntity<>(employee, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}
}
