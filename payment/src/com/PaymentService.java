package com;

import model.Payment;

import java.sql.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.*;
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;



@Path("/Payments")
public class PaymentService {
	Payment PaymentObj = new Payment();


	
	@POST
	@Path("/PaymentDetails")
	@Produces(MediaType.TEXT_PLAIN)
	public String  ViewPaymentDetails(@FormParam("fromdate") String fromdate,@FormParam("todate") String todate) {
		return PaymentObj.ViewPaymentDetails(fromdate , todate);
	}
	
	@POST
	@Path("/TotalDetails")
	@Produces(MediaType.TEXT_PLAIN)
	public String  ViewTotalPaymentDetails(@FormParam("fromdate") String fromdate,@FormParam("todate") String todate) {
		return PaymentObj.GetTotalPayment(fromdate , todate);
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String AddPayments(@FormParam("amount") String Pay_amount, @FormParam("paymentDate") Date Pay_date){
		String output = PaymentObj.AddPayment(Pay_amount, Pay_date);
		return output;
	}

	@PUT 
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) public String UpdatePayment(String PayData) 
	
	{  
		//Convert the input string to a JSON object  
		JsonObject PayObject = new JsonParser().parse(PayData).getAsJsonObject(); 
		 
		 //Read the values from the JSON object  
		String Pay_Id = PayObject.get("paymentId").getAsString();  
		String Pay_amount = PayObject.get("amount").getAsString();   
		 
		String output = PaymentObj.UpdatePayment(Pay_Id, Pay_amount); 
		 System.out.println(output);
		 return output; 
		 
	}
	
	@DELETE 
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String RemovePayment(String PayData) 
	
	{  
		//Convert the input string to an XML document  
		Document doc = Jsoup.parse(PayData, "", Parser.xmlParser());     
		
		//Read the value from the element  
		String Pay_Id = doc.select("paymentId").text(); 
	
		 
		 String output = PaymentObj.RemovePayment(Pay_Id);
		 
		 return output;
	} 


	
	
}
