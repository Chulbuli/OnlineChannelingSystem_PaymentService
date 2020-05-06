<%@page import="model.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    
      
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Service</title>
<script src="Components/jquery-3.5.0.min.js"></script>
<link href="Views/css/bootstrap.min.css" rel="stylesheet">
<link href="Views/css/style.css" rel="stylesheet">
<script src="Components/payment.js"></script>

</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Payment Details</h1>
				<h4>Enter Payment Details</h4>
				<form id="paymentForm" name="paymentForm">
					
					Payment Amount: <input id="amount" name="Pay_amount" type="text"
						class="form-control form-control-sm"> <br> 
						
					Payment Date:<input id="date" name="Pay_date" type="date"
						class="form-control form-control-sm"> <br>
					  
					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary"> 
					<br><br>
					<input type="hidden" id="hidPaymentIDSave" name="hidPaymentIDSave" value=""> 
						
				</form>
				
				<div id="alertSuccess" class="alert alert-success"></div>
				
				<div id="alertError" class="alert alert-danger"></div>
			<br>
				<div id="divPaymentGrid">
					 <%
					 Payment PaymentObj = new Payment();
					 out.print(PaymentObj.ViewAllPaymentDetails());
					
 					  %>
					</div>


			</div>
		</div>
	</div>

</body>
</html>