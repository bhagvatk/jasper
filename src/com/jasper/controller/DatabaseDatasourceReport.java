package com.jasper.controller;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.jasper.helper.JasperHelper;

import net.sf.dynamicreports.examples.Templates;
import net.sf.dynamicreports.report.exception.DRException;

/**
 * @author 
 */
public class DatabaseDatasourceReport {
	private Connection connection;

	public DatabaseDatasourceReport() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jasperspring","root","root");
			//createTable();
			build();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void build() {
		try {
			report()
			  .setTemplate(JasperHelper.reportTemplate)
			  .columns(
			  	col.column("Item",       "item",      type.stringType()),
			  	col.column("Quantity",   "quantity",  type.integerType()),
			  	col.column("Unit price", "unitprice", type.bigDecimalType()))
			  .title(JasperHelper.createTitleComponent("DatabaseDatasource"))
			  .pageFooter(JasperHelper.footerComponent)
			  .setDataSource("SELECT * FROM sales", connection)
			  .show();
		} catch (DRException e) {
			e.printStackTrace();
		}
	}

	private void createTable() throws SQLException {
		Statement st = connection.createStatement();
		//st.execute("CREATE TABLE sales (item VARCHAR(50), quantity INTEGER, unitprice DECIMAL)");
		st.execute("INSERT INTO sales VALUES ('Book1', 5, 100)");
		st.execute("INSERT INTO sales VALUES ('Book2', 5, 100)");
		st.execute("INSERT INTO sales VALUES ('Book3', 5, 100)");
		st.execute("INSERT INTO sales VALUES ('Book4', 5, 100)");
		st.execute("INSERT INTO sales VALUES ('Book5', 5, 100)");
		st.execute("INSERT INTO sales VALUES ('Book6', 5, 100)");
	}

	public static void main(String[] args) {
		new DatabaseDatasourceReport();
	}
}