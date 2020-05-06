$(document).ready(function() 
{  
	//Page refresher
	if ($("#alertSuccess").text().trim() == "")  
	{   
		$("#alertSuccess").hide();  
	}  
		$("#alertError").hide(); 
});

// SAVE ============================================
//Request Algorithm 

$(document).on("click", "#btnSave", function(event) {

	// Clear alerts---------------------
	
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validatePaymentForm();
	if (status != true) 
	{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	// If valid------------------------   

	var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";

	$.ajax(
	{
		url : "PaymentAPI",
		type : type,
		data : $("#paymentForm").serialize(),
		dataType : "text",
		complete : function(response, status) 
		{
			onPaymentSaveComplete(response.responseText, status);
		}
	});
});

//Response Algorithm

function onPaymentSaveComplete(response, status) 
{
	if (status == "success") 
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success") 
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			
			$("#divPaymentGrid").html(resultSet.data);
			
		} else if (resultSet.status.trim() == "error") 
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
		
	} else if (status == "error") 
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else 
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	
	$("#hidPaymentIDSave").val("");
	$("#paymentForm")[0].reset();
}



// CLIENT-MODEL================================================================

function validatePaymentForm() {
	// Payment Amount
	if ($("#amount").val().trim() == "") {
		return "Insert Payment Amount";
	}
	
	// is numerical value  
	var tmpAmount = $("#amount").val().trim();  
	if (!$.isNumeric(tmpAmount))  
	{   
		return "Insert a numerical value for Payment Amount.";  
	}

	// Payment Date
	if ($("#date").val().trim() == "") {
		return "Insert Payment Date";
	}
	return true;
}

// UPDATE==========================================
	$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidPaymentIDSave").val($(this).closest("tr").find('#hidPaymentIDUpdate').val());
	$("#amount").val($(this).closest("tr").find('td:eq(1)').text());
	$("#date").val($(this).closest("tr").find('td:eq(2)').text());
				
});
	
//REMOVE===========================================
	$(document).on("click", ".btnRemove", function(event)
			{
		
		$.ajax({
			url : "PaymentAPI",
			type : "DELETE",
			data : "Pay_Id=" + $(this).data("paymentid"),
			dataType : "text",
			 complete : function(response, status)
			 {
			 onPaymentDeleteComplete(response.responseText, status);
			 } 
		
		});
	});

	
		function onPaymentDeleteComplete(response, status)
		{
			if (status == "success")
		{
				var resultSet = JSON.parse(response);
				if (resultSet.status.trim() == "success")
		{
				$("#alertSuccess").text("Successfully deleted.");
				$("#alertSuccess").show();
				
				$("#divPaymentGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
				$("#alertError").text(resultSet.data);
				$("#alertError").show();
		}
		}
		else if (status == "error")
		{
			$("#alertError").text("Error while deleting.");
			$("#alertError").show();
		} 
		else
		{
			$("#alertError").text("Unknown error while deleting.");
			$("#alertError").show();
		}
}