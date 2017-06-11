import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.shutterfly.lta.dao.LTADao;
import com.shutterfly.lta.model.CustomerBean;

public class CalculateLTA {

		private static Connection conn1 = null;

		public static void main(String[] args) throws SQLException {
		
			try{
				
				// Create database connection with derby client
			    String dbURL1 = "jdbc:derby://localhost:1527/LTA;create=true;user=root;password=root";
	            final Connection conn = DriverManager.getConnection(dbURL1);
	            conn1 = conn;	

	            Float customerRevenue;
				
	            // noofLTA is no of records which we want to display
				Integer noofLTA = Integer.parseInt(args[0]); 
				
				// Hashmap to store customer revenue
				Map<String, Float> perCustomerRevenue = new HashMap<String, Float>();
				
				// read json data from input file
				final JSONObject obj = JSONUtils.getJSONObjectFromFile("/inputfile.json");
				
				//Read json array from input data
				final JSONArray inputdata = obj.getJSONArray("input");
				
				final int n = inputdata.length();
				
				//Call to Ingest method
				for (int i = 0; i < n; ++i) {
					   JSONObject currentdata = inputdata.getJSONObject(i);
					   ingest(currentdata.getString("type"),currentdata);
				   }
				
				// loop to calculate and store customer revenue
				for (int i = 0; i < n; ++i)
				{
					JSONObject currentdata = inputdata.getJSONObject(i);
					
					if(currentdata.getString("type").equals("CUSTOMER") && currentdata.getString("verb").equals("NEW"))
				      {
				    	  customerRevenue= CommonUtils.calculateLTA(currentdata.getString("key"), conn1);
				    	  perCustomerRevenue.put(currentdata.getString("key"), customerRevenue);
				      }
					else
						continue;
				}
				
				// Call to calculate top x records
				TopXSimpleLTVCustomers(noofLTA,perCustomerRevenue);
				conn1.close();
				System.out.println("Program completed successfully");
				return;
				
			}
			catch(Exception er)
			{
				er.printStackTrace();
				conn1.close();
			}		

	}



	private static void TopXSimpleLTVCustomers(Integer noofLTA,Map<String, Float> perCustomerRevenue) {
		/*
		 input parameter 1. no of records to display
		 				 2.	Customer revenue
		 */
		int total_records;
		StringBuilder outputStr = new StringBuilder(); 
		// Logic to sort Hashmap
		
		Set<Entry<String,Float>> set = perCustomerRevenue.entrySet();
		
		List<Entry<String,Float>> list = new ArrayList<Entry<String,Float>>(set);
		
		Collections.sort(list,new Comparator<Entry<String,Float>>()
				{

					public int compare(Entry<String, Float> o1, Entry<String, Float> o2) {
						return o2.getValue().compareTo(o1.getValue());
					}
			
				}
		
				);
		
		
		// logic to display top x records
		if(noofLTA>list.size())
			total_records =  list.size();
		else
			total_records =  noofLTA;	
		
		System.out.println("Top 10 Customer and their revenue is as follows :: ");
		
		for(int i=0;i<total_records;i++)
				{
					
					System.out.println(list.get(i));
					outputStr.append(" \n "+list.get(i));
				}	
		// logic to write result in output file
		WriteOutputToFile(outputStr.toString());
		
		
	}



	private static void WriteOutputToFile(String outputStr) {
		/*
		 * input parameter : 1. String to be write in output folder
		 */
		FileOutputStream revenueoutput = null;
		File file;
		
		// location of output file
		file = new File("d:/outputfile.txt");
		
		try {
			revenueoutput = new FileOutputStream(file);
			
			// logic to create output file
			if (!file.exists()) {
				try {
					file.createNewFile();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			// logic to write output in output file
			byte[] contentInBytes = outputStr.getBytes();
				revenueoutput.write(contentInBytes);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	private static CustomerBean getCustomerFromJsonObject(JSONObject object){
		CustomerBean customer = new CustomerBean();
		customer.setType(object.getString("type"));
		customer.setVerb(object.getString("verb"));
		customer.setKey(object.getString("key"));
		customer.setEventTime(object.getString("event_time"));
		customer.setLastName(object.getString("last_name"));
		customer.setAdrCity(object.getString("adr_city"));
		customer.setAdrState(object.getString("adr_state"));
		return customer;
	}

	private static void ingest(String input_type, JSONObject object2) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		/*
		 input parameter 1. Event
		                 2. JSON object to insert 
		 */
		Statement stmt = conn1.createStatement();
		LTADao dao = LTADao.getInstance();
		
		// logic to insert update records in Derby database
		
		if(input_type.equals("CUSTOMER"))
		{
			CustomerBean customer = getCustomerFromJsonObject(object2);
			if(object2.getString("verb").equals("NEW")){
				dao.insertCustomerRecord(customer);
			}else{
				
			}
			if(object2.getString("verb").equals("NEW"))
            stmt.execute("insert into customer values ('" +
           	object2.getString("type") + "','" + object2.getString("verb") + "','" + object2.getString("key")+"','" + object2.getString("event_time")+"','" + object2.getString("last_name")+"','" + object2.getString("adr_city")+"','" + object2.getString("adr_state") +"')");
			else
				stmt.execute("UPDATE customer SET type = '"+object2.getString("type")+"', verb = '"+object2.getString("verb")+"', event_time = '"+object2.getString("event_time")+"' , last_name = '"+object2.getString("last_name")+"', adr_city = '"+object2.getString("adr_city")+"', adr_state = '"+object2.getString("adr_state")+"' WHERE customer_id = '"+object2.getString("key")+"'");
				
		}
		else if(input_type.equals("SITE_VISIT"))
		{
			stmt.execute("insert into site_visit values ('" +
       	object2.getString("type") + "','" + object2.getString("verb") + "','" + object2.getString("key")+"','" + object2.getString("event_time")+"','" + object2.getString("customer_id")+"')");
		}
		else if(input_type.equals("IMAGE"))
		{
			stmt.execute("insert into image values ('" +
           	object2.getString("type") + "','" + object2.getString("verb") + "','" + object2.getString("key")+"','" + object2.getString("event_time")+"','" + object2.getString("camera_make")+"','" + object2.getString("camera_model") +"','" + object2.getString("customer_id")+"')");
		}
		else if(input_type.equals("ORDER"))
		{
			stmt.execute("insert into customerorder values ('" +
           	object2.getString("type") + "','" + object2.getString("verb") + "','" + object2.getString("key")+"','" + object2.getString("event_time")+"'," + object2.getString("total_amount").replace("USD","")+"'" + object2.getString("customer_id")+"')");
		}
		else
		{
			System.out.println("Incorrect type");
		}	
		stmt.close();
		
	}


}
