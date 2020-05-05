package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {

	private Connection connect() {
		Connection con = null;

		try

		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/helthcare?serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	// Add Payment

	public String AddPayment(String Pay_amount, Date Pay_date) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			String query = " insert into payment (`amount`,`paymentDate`) values ( ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

			preparedStmt.setDouble(1, Double.parseDouble(Pay_amount));
			preparedStmt.setDate(2, Pay_date);

			preparedStmt.execute();
			
			int InsertedPayId = 0;
			ResultSet rs = preparedStmt.getGeneratedKeys();
			if(rs.next())
				InsertedPayId = rs.getInt(1);
			
			con.close();

			output = "Inserted successfully"+" Pament ID = "+InsertedPayId;
		} catch (Exception e) {
			output = "Error while inserting the payment details.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	// View Payment Details

	public String ViewPaymentDetails(String fromdate, String todate) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			output = "<table border=\"1\"> <tr> "
					+ "<th>Payment ID</th> <th>Payment Amount</th> <th>Payment Date </th> </tr>";

			String query = "select * from payment  WHERE `paymentDate` BETWEEN '"+fromdate+"' AND '"+todate+"' AND Status = 'Done'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String Pay_Id = Integer.toString(rs.getInt("paymentId"));
				String Pay_amount = Double.toString(rs.getDouble("amount"));
				String Pay_date = String.valueOf(rs.getDate("paymentDate"));

				// add to HTML table
				output += "<tr><td>" + Pay_Id + "</td>";
				output += "<td>" + Pay_amount + "</td>";
				output += "<td>" + Pay_date + "</td> </tr>";

			}

			// Complete the HTML table
			output += " </table>";
			con.close();
		} catch (Exception e) {
			output = "Error while reading the payment details.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	
	public String GetTotalPayment(String fromdate, String todate) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			output = "<table border=\"1\"> <tr> "
					+ "<th> From date </th> <th> To date </th> <th> Total </th> </tr>";

			String query = "select SUM(`amount`) as total from payment  WHERE `paymentDate` BETWEEN '"+fromdate+"' AND '"+todate+"' AND Status = 'Done'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String total = Integer.toString(rs.getInt("total"));

				// add to HTML table
				output += "<tr><td>" + fromdate + "</td>";
				output += "<td>" + todate + "</td>";
				output += "<td>" + total + "</td>  </tr>";

			}

			// Complete the HTML table
			
			output += " </table>";
			con.close();
		} catch (Exception e) {
			output = "Error while reading the payment details.";
			System.err.println(e.getMessage());
		}

		return output;
	}
	// update payment

	public String UpdatePayment(String Pay_Id, String Pay_amount) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			String query = "UPDATE payment SET amount=? WHERE paymentId=? AND `Status`=  'Done' ";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setDouble(1, Double.parseDouble(Pay_amount));
			preparedStmt.setInt(2, Integer.parseInt(Pay_Id));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the payment details.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	// Delete payment details

	public String RemovePayment(String Pay_Id) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for removing.";
			}

			String query = "UPDATE `payment` SET `Status`= ? WHERE `paymentId`= ?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, "Cancelled");
			preparedStmt.setInt(2, Integer.parseInt(Pay_Id));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the payment details.";
			System.err.println(e.getMessage());
		}

		return output;

	}

}