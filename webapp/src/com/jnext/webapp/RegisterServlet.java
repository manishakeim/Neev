package com.jnext.webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class RegisterServlet extends HttpServlet{

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
						throws ServletException, IOException{
			System.out.println("\nConnected to SERVER!\n");
			 final String DB_URL = "jdbc:mysql://localhost/neev";//company";
			   final String USER = "root";
			   final String PASS = "root";
			   Connection conn = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   PreparedStatement stmt = null;
			
		 String k=	req.getParameter("method");
		 System.out.println("Method is : "+k);
		 
		 
		 /*
		  * 1. Updating customer info missing - will be same as in Supplier info update
		  * 2. PlaceOrder is empty
		  *
		  * */
		 if(k.equals("pdf"))
		 {
			 res.setContentType("application/json");    
			   JSONArray jarr=new JSONArray();
			   JSONObject job=new JSONObject();
			   ResultSet rs=null;
			   String sql;
			   String supplier[]=new String[4];
			   try{   
			    	sql = "Select * from supplier";
			    	  stmt = conn.prepareStatement(sql);
			    	  rs = stmt.executeQuery(sql);
			    	  while(rs.next())
			    	  {
			    		  job=new JSONObject();
			    		  supplier[0]= ""+rs.getInt(1);
			    		  supplier[1]= rs.getString(2);
			    		  supplier[2]=""+rs.getInt(3);
			    		  supplier[3]= rs.getString(4);
			    		  job.put("0",supplier[0]);
			    		  job.put("1",supplier[1]);
			    		  job.put("2",supplier[2]);
			    		  job.put("3",supplier[3]);
			    		  System.out.println(supplier[0]+" "+supplier[1]+" "+supplier[2]+" "+supplier[3]+" ");
			    		
			    		  jarr.put(job);
			    	  }
			    	  
			    	  rs.close();
					  System.out.println("After adding the object to jsonarray");
				      PrintWriter w=res.getWriter();
				      w.write(jarr.toString());
				      w.flush();
				      w.close();
				
				      	  stmt.close();
				    	    
				    	  conn.close();
				      
				}catch(SQLException e)
			      {
			    	  System.out.println("SERVER : SQL EXCEPTION1 : "+e);
			      }
			      catch(JSONException e)
			      {
			    	  System.out.println("SERVER : JSON EXCEPTION : "+e);
			    }
		 }
		 
		 if(k.equals("supplier_update"))
		 {
			 System.out.println("SupplierDisplay code on Server Side");
			 int records=0;
			 String old_no =req.getParameter("Supplier_pno");
				String address=req.getParameter("add");//should be Driver id instead
				String update_phno =req.getParameter("up_no");
             if(!(address.equals("")))
             {
				
				String sql ="update supplier set Address=? where Phone=?";
				try{
				  stmt = conn.prepareStatement(sql);
				  stmt.setString(1,address);
				  stmt.setString(2,old_no);
				  records=stmt.executeUpdate();
				  if(records==0)
					  res.setStatus(409);
				  		
				}
				catch(Exception e)
				{
					res.setStatus(409);
					System.out.println("Exception in Supplier updation  details"+e);
					
				}}
             if(!(update_phno.equals("")))
             {
            	 records=0;
            	 
            	 String sql ="update supplier set Phone=? where Phone=?";
             	try{
   				  stmt = conn.prepareStatement(sql);
   				  stmt.setString(1,update_phno);
   				stmt.setString(2,old_no);
   				  records=stmt.executeUpdate();
   				  if(records==0)
					  res.setStatus(409);
				  	
   				}
   				catch(Exception e)
   				{
   					res.setStatus(409);
   					System.out.println("Exception in Supplier updation details"+e);
   				}
             }
		 }	
		 
		 if(k.equals("customerinfo"))
		 {
			 
			 int records=0;
			 String old_no =req.getParameter("c_ph");
				String address=req.getParameter("c_add");//should be Driver id instead
				String update_phno =req.getParameter("u_ph");
             if(!(address.equals("")))
             {
				
				String sql ="update customer set Address=? where Customer_Phone=?";
				try{
				  stmt = conn.prepareStatement(sql);
				  stmt.setString(1,address);
				  stmt.setString(2,old_no);
				  records=stmt.executeUpdate();
				  if(records==0)
					  res.setStatus(409);
				  		
				}
				catch(Exception e)
				{
					res.setStatus(409);
					System.out.println("Exception in Customer updation  details"+e);
					
				}}
             if(!(update_phno.equals("")))
             {
            	 records=0;
            	 
            	 String sql ="update customer set Customer_Phone=? where Customer_Phone=?";
             	try{
   				  stmt = conn.prepareStatement(sql);
   				  stmt.setString(1,update_phno);
   				stmt.setString(2,old_no);
   				  records=stmt.executeUpdate();
   				  if(records==0)
					  res.setStatus(409);
				  	
   				}
   				catch(Exception e)
   				{
   					res.setStatus(409);
   					System.out.println("Exception in Customer updation details"+e);
   				}
             }
		 }	
		 
		 
		 else if(k.equals("myKPICount"))
		 {
			 res.setContentType("application/json");    
			   JSONArray jarr=new JSONArray();
			   //JSONObject obj1=new JSONObject();
			   //JSONObject obj2=new JSONObject();
			   double  inv=0,sales=0,in_transit=0,in_progress=0,returned=0;
			   ResultSet rs1=null,rs2=null,rs3=null,rs4=null,rs5=null;
			   	String sql;
			       String n="0";
			       double count=0.00;//"count";
			       //double price=0.00;//"price";
			   
			    try{   
			    	sql = "Select SUM(Quantity) from raw_materials";
			    	  stmt = conn.prepareStatement(sql);
			    	  rs1 = stmt.executeQuery(sql);
			    	  if(rs1.next())
			    	  inv = rs1.getDouble("SUM(Quantity)");
			    	  System.out.println("KPICount 1 : "+inv);
			    	  rs1.close();
			    	  
			    	  jarr.put(new JSONObject().put("kpiCount",inv));
			    }catch(SQLException e)
			      {
			    	  System.out.println("SERVER : SQL EXCEPTION1");
			      }
			      catch(JSONException e)
			      {
			    	  System.out.println("SERVER : JSON EXCEPTION");
			    }
			     
			    
			    sql="select SUM(Delivered_Quantity) from order_products where Delivery_Status='Delivered' AND Delivered_On=(Select curdate())";
			    //sql2="select count(*) as Products from furnished_products";
			      try{
			    	//  ResultSet rs=null;
			    	
			      stmt = conn.prepareStatement(sql);
			      rs2 = stmt.executeQuery(sql);
			     if(rs2.next()) 
			       count  = rs2.getInt("SUM(Delivered_Quantity)");
			       
		    	  System.out.println("KPICount 2 : "+count);
			     rs2.close();
			     
			     jarr.put(new JSONObject().put("kpiCount",count));
			
			      }catch(SQLException e)
			      {
			    	  System.out.println("SERVER : SQL EXCEPTION2");
			      }
			      catch(JSONException e)
			      {
			    	  System.out.println("SERVER : JSON EXCEPTION");
			    }    
			    
			    sql ="Select SUM(Expected_Quantity) from order_products where Delivery_Status ='In_Transit' and Delivered_On = (select CURDATE())";
		    	
			    try{
			    stmt = conn.prepareStatement(sql);
		    	 // stmt.setString(1,"In_Transit");
		    	  rs3 = stmt.executeQuery(sql);
		    	  if(rs3.next())
		    	  in_transit = rs3.getInt("SUM(Expected_Quantity)");
		    	  
		    	  rs3.close();
		    	  System.out.println("KPICount 3 : "+in_transit);
		    	  jarr.put(new JSONObject().put("kpiCount",in_transit));
			    }catch(SQLException e)
			      {
			    	  System.out.println("SERVER : SQL EXCEPTION3 : "+e);
			      }
			      catch(JSONException e)
			      {
			    	  System.out.println("SERVER : JSON EXCEPTION");
			    }
			      
			       
			      sql ="Select SUM(Expected_Quantity) from order_products where Delivery_Status ='In_Progress' and Delivered_On = (select CURDATE())";
		    	  
			      try{
			      stmt = conn.prepareStatement(sql);
		    	  //stmt.setString(1,"In_Progress");
		    	  rs4 = stmt.executeQuery(sql);
		    	 if(rs4.next())
		    	  in_progress = rs4.getInt("SUM(Expected_Quantity)");
		    	 
		    	 rs4.close();
		    	 
		    	  System.out.println("KPICount 4 : "+in_progress);
		    	 jarr.put(new JSONObject().put("kpiCount",in_progress));
			      }
					catch(SQLException e)
					{
						  System.out.println("SERVER : SQL EXCEPTION4 : "+e);
					}
					catch(JSONException e)
					{
						  System.out.println("SERVER : JSON EXCEPTION");
					}
					
			      
			      
			      //sql ="Select SUM(f.Product_Price*(Expected_Quantity-Delivered_Quantity)) from order_products o, furnished_products f where Delivery_Status ='Delivered' AND Delivered_On = (select CURDATE()) AND f.Product_ID=o.Product_ID";
			      sql ="Select (SUM(Expected_Quantity) - SUM(Delivered_Quantity)) from order_products where Delivery_Status ='Delivered' AND Delivered_On = (select CURDATE())";
		    	  
			      try{			      
			      stmt = conn.prepareStatement(sql);
		    	  //stmt.setString(1,"Delivered");
		    	  rs5 = stmt.executeQuery(sql);
		    	  if(rs5.next())
		    	  returned = rs5.getInt(1);
		    	  rs5.close();
		    	  
		    	  jarr.put(new JSONObject().put("kpiCount",returned));
		    	  
		    	  System.out.println("KPICount 5 : "+returned);
			      }catch(SQLException e)
					{
					  System.out.println("SERVER : SQL EXCEPTION5 : "+e);
				}
				catch(JSONException e)
				{
					  System.out.println("SERVER : JSON EXCEPTION");
				}
			       
		 
			       try{
			       
			       
			            System.out.println("Item Names Successfully inserted in Array");
			      
			     
			     
	    		 
	    		 System.out.println("After adding the object to jsonarray");
			     PrintWriter w=res.getWriter();
			      w.write(jarr.toString());
			      w.flush();
			      w.close();
			
			      	  stmt.close();
			    	    
			    	  conn.close();
		 
		 }catch(Exception e)
			       {
			 		System.out.println("Error in closing connections"+e);
			       }
		 }
		 
		 /*sql1="select SUM(o.Delivered_Quantity*f.Product_Price) from order_products o, furnished_products f where Delivery_Status ='Delivered' AND Delivered_On=(Select curdate()) AND o.Product_ID=f.Product_ID";*/

		 else if(k.equals("myKPIPrice"))
		 {
			 res.setContentType("application/json");    
			   JSONArray jarr=new JSONArray();
			   //JSONObject obj1=new JSONObject();
			   //JSONObject obj2=new JSONObject();
			   double  inv=0,sales=0,in_transit=0,in_progress=0,returned=0;
			   ResultSet rs1=null,rs2=null,rs3=null,rs4=null,rs5=null;
			   	String sql;
			       String n="0";
			       //double count=0.00;//"count";
			       double price=0.00;//"price";
			       double sum=0.00;
			    try{   
			    	sql = "Select (m.Quantity * AVG(r.Unit_Price)) AS Single from raw_materials r,material_track m where r.Item_ID = m.Item_ID group by Item_Name";
			    	  stmt = conn.prepareStatement(sql);
			    	  rs1 = stmt.executeQuery(sql);
			    	  if(rs1.next())
			    	  {
			    		 sum=sum+ rs1.getInt(1);
			    				 System.out.println("Inv Sum is : " +sum);
			    	  }
			    	  inv =sum;//rs1.getDouble("SUM(Quantity)");
			    	
			    	  System.out.println("KPIPrice 1 : "+inv);
			    	  rs1.close();
			    	  
			    	  jarr.put(new JSONObject().put("kpiPrice",inv));
			    }catch(SQLException e)
			      {
			    	  System.out.println("SERVER : SQL EXCEPTION1");
			      }
			      catch(JSONException e)
			      {
			    	  System.out.println("SERVER : JSON EXCEPTION");
			    }
			     
			    
			    sql="select SUM(o.Delivered_Quantity*f.Product_Price) from order_products o, furnished_products f where Delivery_Status ='Delivered' AND Delivered_On=(Select curdate()) AND o.Product_ID=f.Product_ID";
			    //sql2="select count(*) as Products from furnished_products";
			      try{
			    	//  ResultSet rs=null;
			    	
			      stmt = conn.prepareStatement(sql);
			      rs2 = stmt.executeQuery(sql);
			     if(rs2.next()) 
			       price= rs2.getInt(1);
			       
			     rs2.close();
			     System.out.println("KPIPrice 2 : "+price);
			     jarr.put(new JSONObject().put("kpiPrice",price));
			
			      }catch(SQLException e)
			      {
			    	  System.out.println("SERVER : SQL EXCEPTION2");
			      }
			      catch(JSONException e)
			      {
			    	  System.out.println("SERVER : JSON EXCEPTION");
			    }    
			    
			    sql ="select SUM(o.Delivered_Quantity*f.Product_Price) from order_products o, furnished_products f where Delivery_Status ='In_Transit' AND Delivered_On=(Select curdate()) AND o.Product_ID=f.Product_ID";
		    	
			    try{
			    stmt = conn.prepareStatement(sql);
		    	 // stmt.setString(1,"In_Transit");
		    	  rs3 = stmt.executeQuery(sql);
		    	  if(rs3.next())
		    		  in_transit = rs3.getInt(1);
		    	  
		    	  rs3.close();
		    	  System.out.println("KPIPrice 3 : "+in_transit);
		    	  jarr.put(new JSONObject().put("kpiPrice",in_transit));
			    }catch(SQLException e)
			      {
			    	  System.out.println("SERVER : SQL EXCEPTION3 : "+e);
			      }
			      catch(JSONException e)
			      {
			    	  System.out.println("SERVER : JSON EXCEPTION");
			    }
			      
			       
			      sql ="select SUM(o.Delivered_Quantity*f.Product_Price) from order_products o, furnished_products f where Delivery_Status ='In_Transit' AND Delivered_On=(Select curdate()) AND o.Product_ID=f.Product_ID";
		    	  
			      try{
			      stmt = conn.prepareStatement(sql);
		    	  //stmt.setString(1,"In_Progress");
		    	  rs4 = stmt.executeQuery(sql);
		    	 if(rs4.next())
		    	  in_progress = rs4.getInt(1);
		    	 
		    	 rs4.close();
		    	 System.out.println("KPIPrice 4 : "+in_progress);
		    	 
		    	 jarr.put(new JSONObject().put("kpiPrice",in_progress));
			      }
					catch(SQLException e)
					{
						  System.out.println("SERVER : SQL EXCEPTION4 : "+e);
					}
					catch(JSONException e)
					{
						  System.out.println("SERVER : JSON EXCEPTION");
					}
					
			      
			      
			     // sql ="Select (SUM(Expected_Quantity) - SUM(Delivered_Quantity)) from order_products where Delivery_Status ='Delivered' AND Delivered_On = (select CURDATE())";
			      sql ="Select SUM(f.Product_Price*(Expected_Quantity-Delivered_Quantity)) from order_products o, furnished_products f where Delivery_Status ='Delivered' AND Delivered_On = (select CURDATE()) AND f.Product_ID=o.Product_ID";
			      
			      try{			      
			      stmt = conn.prepareStatement(sql);
		    	  //stmt.setString(1,"Delivered");
		    	  rs5 = stmt.executeQuery(sql);
		    	  if(rs5.next())
		    	  returned = rs5.getInt(1);
		    	  rs5.close();
		    	  
		    	  
		    	  System.out.println("KPIPrice 5 : "+returned);
		    	  jarr.put(new JSONObject().put("kpiPrice",returned));
			      }catch(SQLException e)
					{
					  System.out.println("SERVER : SQL EXCEPTION5 : "+e);
				}
				catch(JSONException e)
				{
					  System.out.println("SERVER : JSON EXCEPTION");
				}
			       
		 
			       try{
			       
			       
			            System.out.println("Item Names Successfully inserted in Array");
			      
			     
			     
	    		 
	    		 System.out.println("After adding the object to jsonarray");
			     PrintWriter w=res.getWriter();
			      w.write(jarr.toString());
			      w.flush();
			      w.close();
			
			      	  stmt.close();
			    	    
			    	  conn.close();
		 
		 }catch(Exception e)
			       {
			 		System.out.println("Error in closing connections"+e);
			       }
		 
		 }
	
	
		 /***********CUSTOM KPI*********/
		 else if(k.equals("customKPICount"))
		 {
			 res.setContentType("application/json");    
			   JSONArray jarr=new JSONArray();
			   //JSONObject obj1=new JSONObject();
			   //JSONObject obj2=new JSONObject();
			   double  inv=0,sales=0,in_transit=0,in_progress=0,returned=0;
			   ResultSet rs1=null,rs2=null,rs3=null,rs4=null,rs5=null;
			   	String sql;
			       String n="0";
			       double count=0.00;//"count";
			       //double price=0.00;//"price";
			   
			    try{   
			    	sql = "Select SUM(Quantity) from raw_materials";
			    	  stmt = conn.prepareStatement(sql);
			    	  rs1 = stmt.executeQuery(sql);
			    	  if(rs1.next())
			    	  inv = rs1.getDouble("SUM(Quantity)");
			    	  System.out.println("KPICount 1 : "+inv);
			    	  rs1.close();
			    	  
			    	  jarr.put(new JSONObject().put("kpiCount",inv));
			    }catch(SQLException e)
			      {
			    	  System.out.println("SERVER : SQL EXCEPTION1");
			      }
			      catch(JSONException e)
			      {
			    	  System.out.println("SERVER : JSON EXCEPTION");
			    }
			     
			    
			    sql="select SUM(Delivered_Quantity) from order_products where Delivery_Status='Delivered' AND Delivered_On=(Select curdate())";
			    //sql2="select count(*) as Products from furnished_products";
			      try{
			    	//  ResultSet rs=null;
			    	
			      stmt = conn.prepareStatement(sql);
			      rs2 = stmt.executeQuery(sql);
			     if(rs2.next()) 
			       count  = rs2.getInt("SUM(Delivered_Quantity)");
			       
		    	  System.out.println("KPICount 2 : "+count);
			     rs2.close();
			     
			     jarr.put(new JSONObject().put("kpiCount",count));
			
			      }catch(SQLException e)
			      {
			    	  System.out.println("SERVER : SQL EXCEPTION2");
			      }
			      catch(JSONException e)
			      {
			    	  System.out.println("SERVER : JSON EXCEPTION");
			    }    
			    
			    sql ="Select SUM(Expected_Quantity) from order_products where Delivery_Status ='In_Transit' and Delivered_On = (select CURDATE())";
		    	
			    try{
			    stmt = conn.prepareStatement(sql);
		    	 // stmt.setString(1,"In_Transit");
		    	  rs3 = stmt.executeQuery(sql);
		    	  if(rs3.next())
		    	  in_transit = rs3.getInt("SUM(Expected_Quantity)");
		    	  
		    	  rs3.close();
		    	  System.out.println("KPICount 3 : "+in_transit);
		    	  jarr.put(new JSONObject().put("kpiCount",in_transit));
			    }catch(SQLException e)
			      {
			    	  System.out.println("SERVER : SQL EXCEPTION3 : "+e);
			      }
			      catch(JSONException e)
			      {
			    	  System.out.println("SERVER : JSON EXCEPTION");
			    }
			      
			       
			      sql ="Select SUM(Expected_Quantity) from order_products where Delivery_Status ='In_Progress' and Delivered_On = (select CURDATE())";
		    	  
			      try{
			      stmt = conn.prepareStatement(sql);
		    	  //stmt.setString(1,"In_Progress");
		    	  rs4 = stmt.executeQuery(sql);
		    	 if(rs4.next())
		    	  in_progress = rs4.getInt("SUM(Expected_Quantity)");
		    	 
		    	 rs4.close();
		    	 
		    	  System.out.println("KPICount 4 : "+in_progress);
		    	 jarr.put(new JSONObject().put("kpiCount",in_progress));
			      }
					catch(SQLException e)
					{
						  System.out.println("SERVER : SQL EXCEPTION4 : "+e);
					}
					catch(JSONException e)
					{
						  System.out.println("SERVER : JSON EXCEPTION");
					}
					
			      
			      
			      //sql ="Select SUM(f.Product_Price*(Expected_Quantity-Delivered_Quantity)) from order_products o, furnished_products f where Delivery_Status ='Delivered' AND Delivered_On = (select CURDATE()) AND f.Product_ID=o.Product_ID";
			      sql ="Select (SUM(Expected_Quantity) - SUM(Delivered_Quantity)) from order_products where Delivery_Status ='Delivered' AND Delivered_On = (select CURDATE())";
		    	  
			      try{			      
			      stmt = conn.prepareStatement(sql);
		    	  //stmt.setString(1,"Delivered");
		    	  rs5 = stmt.executeQuery(sql);
		    	  if(rs5.next())
		    	  returned = rs5.getInt(1);
		    	  rs5.close();
		    	  
		    	  jarr.put(new JSONObject().put("kpiCount",returned));
		    	  
		    	  System.out.println("KPICount 5 : "+returned);
			      }catch(SQLException e)
					{
					  System.out.println("SERVER : SQL EXCEPTION5 : "+e);
				}
				catch(JSONException e)
				{
					  System.out.println("SERVER : JSON EXCEPTION");
				}
			       
		 
			       try{
			       
			       
			            System.out.println("Item Names Successfully inserted in Array");
			      
			     
			     
	    		 
	    		 System.out.println("After adding the object to jsonarray");
			     PrintWriter w=res.getWriter();
			      w.write(jarr.toString());
			      w.flush();
			      w.close();
			
			      	  stmt.close();
			    	    
			    	  conn.close();
		 
		 }catch(Exception e)
			       {
			 		System.out.println("Error in closing connections"+e);
			       }
		 }
		 
		 /*sql1="select SUM(o.Delivered_Quantity*f.Product_Price) from order_products o, furnished_products f where Delivery_Status ='Delivered' AND Delivered_On=(Select curdate()) AND o.Product_ID=f.Product_ID";*/

		 else if(k.equals("customKPIPrice"))
		 {
			 res.setContentType("application/json");    
			   JSONArray jarr=new JSONArray();
			   //JSONObject obj1=new JSONObject();
			   //JSONObject obj2=new JSONObject();
			   double  inv=0,sales=0,in_transit=0,in_progress=0,returned=0;
			   ResultSet rs1=null,rs2=null,rs3=null,rs4=null,rs5=null;
			   	String sql;
			       String n="0";
			       //double count=0.00;//"count";
			       double price=0.00;//"price";
			       double sum=0.00;
			    try{   
			    	sql = "Select (m.Quantity * AVG(r.Unit_Price)) AS Single from raw_materials r,material_track m where r.Item_ID = m.Item_ID group by Item_Name";
			    	  stmt = conn.prepareStatement(sql);
			    	  rs1 = stmt.executeQuery(sql);
			    	  if(rs1.next())
			    	  {
			    		 sum=sum+ rs1.getInt(1);
			    				 System.out.println("Inv Sum is : " +sum);
			    	  }
			    	  inv =sum;//rs1.getDouble("SUM(Quantity)");
			    	
			    	  System.out.println("KPIPrice 1 : "+inv);
			    	  rs1.close();
			    	  
			    	  jarr.put(new JSONObject().put("kpiPrice",inv));
			    }catch(SQLException e)
			      {
			    	  System.out.println("SERVER : SQL EXCEPTION1");
			      }
			      catch(JSONException e)
			      {
			    	  System.out.println("SERVER : JSON EXCEPTION");
			    }
			     
			    
			    sql="select SUM(o.Delivered_Quantity*f.Product_Price) from order_products o, furnished_products f where Delivery_Status ='Delivered' AND Delivered_On=(Select curdate()) AND o.Product_ID=f.Product_ID";
			    //sql2="select count(*) as Products from furnished_products";
			      try{
			    	//  ResultSet rs=null;
			    	
			      stmt = conn.prepareStatement(sql);
			      rs2 = stmt.executeQuery(sql);
			     if(rs2.next()) 
			       price= rs2.getInt(1);
			       
			     rs2.close();
			     System.out.println("KPIPrice 2 : "+price);
			     jarr.put(new JSONObject().put("kpiPrice",price));
			
			      }catch(SQLException e)
			      {
			    	  System.out.println("SERVER : SQL EXCEPTION2");
			      }
			      catch(JSONException e)
			      {
			    	  System.out.println("SERVER : JSON EXCEPTION");
			    }    
			    
			    sql ="select SUM(o.Delivered_Quantity*f.Product_Price) from order_products o, furnished_products f where Delivery_Status ='In_Transit' AND Delivered_On=(Select curdate()) AND o.Product_ID=f.Product_ID";
		    	
			    try{
			    stmt = conn.prepareStatement(sql);
		    	 // stmt.setString(1,"In_Transit");
		    	  rs3 = stmt.executeQuery(sql);
		    	  if(rs3.next())
		    		  in_transit = rs3.getInt(1);
		    	  
		    	  rs3.close();
		    	  System.out.println("KPIPrice 3 : "+in_transit);
		    	  jarr.put(new JSONObject().put("kpiPrice",in_transit));
			    }catch(SQLException e)
			      {
			    	  System.out.println("SERVER : SQL EXCEPTION3 : "+e);
			      }
			      catch(JSONException e)
			      {
			    	  System.out.println("SERVER : JSON EXCEPTION");
			    }
			      
			       
			      sql ="select SUM(o.Delivered_Quantity*f.Product_Price) from order_products o, furnished_products f where Delivery_Status ='In_Transit' AND Delivered_On=(Select curdate()) AND o.Product_ID=f.Product_ID";
		    	  
			      try{
			      stmt = conn.prepareStatement(sql);
		    	  //stmt.setString(1,"In_Progress");
		    	  rs4 = stmt.executeQuery(sql);
		    	 if(rs4.next())
		    	  in_progress = rs4.getInt(1);
		    	 
		    	 rs4.close();
		    	 System.out.println("KPIPrice 4 : "+in_progress);
		    	 
		    	 jarr.put(new JSONObject().put("kpiPrice",in_progress));
			      }
					catch(SQLException e)
					{
						  System.out.println("SERVER : SQL EXCEPTION4 : "+e);
					}
					catch(JSONException e)
					{
						  System.out.println("SERVER : JSON EXCEPTION");
					}
					
			      
			      
			     // sql ="Select (SUM(Expected_Quantity) - SUM(Delivered_Quantity)) from order_products where Delivery_Status ='Delivered' AND Delivered_On = (select CURDATE())";
			      sql ="Select SUM(f.Product_Price*(Expected_Quantity-Delivered_Quantity)) from order_products o, furnished_products f where Delivery_Status ='Delivered' AND Delivered_On = (select CURDATE()) AND f.Product_ID=o.Product_ID";
			      
			      try{			      
			      stmt = conn.prepareStatement(sql);
		    	  //stmt.setString(1,"Delivered");
		    	  rs5 = stmt.executeQuery(sql);
		    	  if(rs5.next())
		    	  returned = rs5.getInt(1);
		    	  rs5.close();
		    	  
		    	  
		    	  System.out.println("KPIPrice 5 : "+returned);
		    	  jarr.put(new JSONObject().put("kpiPrice",returned));
			      }catch(SQLException e)
					{
					  System.out.println("SERVER : SQL EXCEPTION5 : "+e);
				}
				catch(JSONException e)
				{
					  System.out.println("SERVER : JSON EXCEPTION");
				}
			       
		 
			       try{
			       
			       
			            System.out.println("Item Names Successfully inserted in Array");
			      
			     
			     
	    		 
	    		 System.out.println("After adding the object to jsonarray");
			     PrintWriter w=res.getWriter();
			      w.write(jarr.toString());
			      w.flush();
			      w.close();
			
			      	  stmt.close();
			    	    
			    	  conn.close();
		 
		 }catch(Exception e)
			       {
			 		System.out.println("Error in closing connections"+e);
			       }
		 
		 }
	
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 else if(k.equals("FetchRM"))
		 {
			 System.out.println("Register Servlet FetchItem Code started");
			 res.setContentType("application/json");    
			   JSONArray jarr=new JSONArray();
			   JSONObject obj1=new JSONObject();
			       String sql1,sql2;
			       String n="0";
			      sql1="SELECT Item_Name from raw_materials";
			    //sql2="select count(*) as Products from furnished_products";
			      try{
			    	  ResultSet rs=null;
			    	
			      stmt = conn.prepareStatement(sql1);
			    rs = stmt.executeQuery(sql1);
					String pname="No item";
				  while(rs.next()){
			       pname  = rs.getString(1);
			    	jarr.put(new JSONObject().put("itemName",pname));
			    	/*one option is add itemId along with Item Name in the JSONObject*/
			    	
				  }
				  System.out.println("Item Names Successfully inserted in Array");
			     rs.close();
			     
			     
 	    		 
 	    		 System.out.println("After adding the object to jsonarray");
			      PrintWriter w=res.getWriter();
			      w.write(jarr.toString());
			      w.flush();
			      w.close();
			
			      	  stmt.close();
			    	    
			    	  conn.close();
			      }
			      catch(SQLException e)
			      {
			    	  System.out.println("SERVER : SQL EXCEPTION");
			      }
			      catch(JSONException e)
			      {
			    	  System.out.println("SERVER : JSON EXCEPTION");
			    }
			      System.out.println("FetchItem Quesry Successful");
		 }

		 

		 else if(k.equals("pieChart"))
		 {
			 int  inv,sales,in_transit,in_progress,returned;
			 String iname="";
			 double iquan=0.00;
			 double iprice=0.00;
			 String sql="";
			 int var=Integer.parseInt(req.getParameter("var"));
			 System.out.println("Check1");
			 JSONArray jarr=new JSONArray();
			 JSONObject job=new JSONObject();
			 try{
		    	  ResultSet rs=null;		    	
		    	  	
		    	  switch(var)
		    	  {
		    	   case 0 : sql="Select DISTINCT(Item_Name), m.Quantity, AVG(r.Unit_Price) from raw_materials r,material_track m where r.Item_ID = m.Item_ID group by Item_Name";
		    		   break;
		    	   
		    	   case 1 : sql="Select f.Product_Name, SUM(o.Delivered_Quantity), f.Product_Price from furnished_products f, order_products o where o.Product_ID=f.Product_ID and Delivery_Status='Delivered' and Delivered_On=CURDATE() group by f.Product_ID";
		    		   break;
		    		   
		    	   case 2 : sql="Select f.Product_Name, SUM(o.Delivered_Quantity), f.Product_Price from furnished_products f, order_products o where o.Product_ID=f.Product_ID and Delivery_Status='In_Transit' and Delivered_On=CURDATE() group by f.Product_ID";
		    		   break;
		    		   
		    	   case 3 : sql="Select f.Product_Name, SUM(o.Delivered_Quantity), f.Product_Price from furnished_products f, order_products o where o.Product_ID=f.Product_ID and Delivery_Status='In_Progress' and Delivered_On=CURDATE() group by f.Product_ID";
		    		   break;
		    		   
		    	   case 4 : sql="Select f.Product_Name, SUM( o.Expected_Quantity - o.Delivered_Quantity), f.Product_Price from furnished_products f, order_products o where o.Product_ID=f.Product_ID and Delivery_Status='Delivered' and Delivered_On=CURDATE() group by f.Product_ID";
		    		   break;		    	   
		    	  }
		    	  
		    	  stmt = conn.prepareStatement(sql);
		    	  rs = stmt.executeQuery(sql);
		    	  
		    	  while(rs.next()){
				  iname =rs.getString(1);
				  iquan=rs.getDouble(2);
				  iprice=rs.getDouble(3);
				  job=new JSONObject();
				  job.put("itemName",iname);
				  job.put("iQuantity",iquan);
				  job.put("iPrice",iprice);
				  jarr.put(job);
				  
				  System.out.println("INAME : "+iname+"   Quantity : "+iquan+"   Price : "+iprice);
				  
		    	
			  }
			  System.out.println("Item Names Successfully inserted in Array");
			  
			  					  		    	 
			  rs.close();
			  System.out.println("After adding the object to jsonarray");
		      PrintWriter w=res.getWriter();
		      w.write(jarr.toString());
		      w.flush();
		      w.close();
		
		      	  stmt.close();
		    	    
		    	  conn.close();
		      
		
			 }catch(Exception e)
			 {
				 System.out.println("Exception in server"+e);
				 
			 }
			 
			 System.out.println("BYE BYE");
		     
		 }
		
		 else if(k.equals("customPieChart"))
		 {
			 int  inv,sales,in_transit,in_progress,returned;
			 String iname="";
			 double iquan=0.00;
			 double iprice=0.00;
			 String sql="";
			 int var=Integer.parseInt(req.getParameter("var"));
			 System.out.println("Check1");
			 JSONArray jarr=new JSONArray();
			 JSONObject job=new JSONObject();
			 try{
		    	  ResultSet rs=null;		    	
		    	  	
		    	  switch(var)
		    	  {
		    	   case 0 : sql="Select DISTINCT(Item_Name), m.Quantity, AVG(r.Unit_Price) from raw_materials r,material_track m where r.Item_ID = m.Item_ID group by Item_Name";
		    		   break;
		    	   
		    	   case 1 : sql="Select f.Product_Name, SUM(o.Delivered_Quantity), f.Product_Price from furnished_products f, order_products o where o.Product_ID=f.Product_ID and Delivery_Status='Delivered' and Delivered_On=CURDATE() group by f.Product_ID";
		    		   break;
		    		   
		    	   case 2 : sql="Select f.Product_Name, SUM(o.Delivered_Quantity), f.Product_Price from furnished_products f, order_products o where o.Product_ID=f.Product_ID and Delivery_Status='In_Transit' and Delivered_On=CURDATE() group by f.Product_ID";
		    		   break;
		    		   
		    	   case 3 : sql="Select f.Product_Name, SUM(o.Delivered_Quantity), f.Product_Price from furnished_products f, order_products o where o.Product_ID=f.Product_ID and Delivery_Status='In_Progress' and Delivered_On=CURDATE() group by f.Product_ID";
		    		   break;
		    		   
		    	   case 4 : sql="Select f.Product_Name, SUM( o.Expected_Quantity - o.Delivered_Quantity), f.Product_Price from furnished_products f, order_products o where o.Product_ID=f.Product_ID and Delivery_Status='Delivered' and Delivered_On=CURDATE() group by f.Product_ID";
		    		   break;		    	   
		    	  }
		    	  
		    	  stmt = conn.prepareStatement(sql);
		    	  rs = stmt.executeQuery(sql);
		    	  
		    	  while(rs.next()){
				  iname =rs.getString(1);
				  iquan=rs.getDouble(2);
				  iprice=rs.getDouble(3);
				  job=new JSONObject();
				  job.put("itemName",iname);
				  job.put("iQuantity",iquan);
				  job.put("iPrice",iprice);
				  jarr.put(job);
				  
				  System.out.println("INAME : "+iname+"   Quantity : "+iquan+"   Price : "+iprice);
				  
		    	
			  }
			  System.out.println("Item Names Successfully inserted in Array");
			  
			  					  		    	 
			  rs.close();
			  System.out.println("After adding the object to jsonarray");
		      PrintWriter w=res.getWriter();
		      w.write(jarr.toString());
		      w.flush();
		      w.close();
		
		      	  stmt.close();
		    	    
		    	  conn.close();
		      
		
			 }catch(Exception e)
			 {
				 System.out.println("Exception in server"+e);
				 
			 }
			 
			 System.out.println("BYE BYE");
		     
		 }
		
		 
		 
		 else if(k.equals("delivery_data"))
			{
				int records=0;
			 	
		        String oid=req.getParameter("oid");
				String d_quan=req.getParameter("quantity");//should be Driver id instead
				String d_date=req.getParameter("date");
				
				String sql ="Update order_products set Delivery_Status='Delivered', Delivered_Quantity=? , Delivered_On=? where Order_ID=?";//"Insert into in_transit(Order_ID,Delivered_Quantity,Delivered_On) values(?,?,?)";
				
				try{
				
				  stmt = conn.prepareStatement(sql);
				  stmt.setInt(3,Integer.parseInt(oid));
				  stmt.setDouble(1,Double.parseDouble(d_quan));
				  stmt.setString(2,d_date);
				
				  records=stmt.executeUpdate();
				
				  if(records==0)
					  res.setStatus(409);
								}
				catch(Exception e)
				{
					System.out.println("Exception in delivery_data server code"+e);
					res.setStatus(409);
					
				}
			}

		 else if(k.equals("Place_Order"))
		 {
			 
                    int c_id =0,p_id = 0;
                    
			        String oid=req.getParameter("oid");
			        String ph=req.getParameter("cph");
					String Date=req.getParameter("date");
					String iname=req.getParameter("i_name");
					String sql ="Select Customer_ID from customer where Customer_Phone =?";
					
					try{
					 stmt = conn.prepareStatement(sql);
					 stmt.setString(1, ph);
					 ResultSet rs =  stmt.executeQuery();
					 if(rs.next())
					 c_id= rs.getInt("Customer_ID");
					 else
						 res.setStatus(409);
					}
					catch(Exception e)
					{
						System.out.println("Exception in customer Insertion1 : "+e);
						
					}
             String sql2 ="Select Product_ID from furnished_products where Product_Name =?";
					
					try{
					 stmt = conn.prepareStatement(sql2);
					 stmt.setString(1, iname);
					 ResultSet rs =  stmt.executeQuery();
					 if(rs.next())
					 p_id= rs.getInt("Product_ID");
					 else
						 res.setStatus(409);
					}
					catch(Exception e)
					{
						System.out.println("Exception in customer Insertion1 : "+e);
						
					}
					String sql1 ="Insert into order_products(Order_ID,Customer_ID,Expected_Delivery_Date,Product_ID) values(?,?,?,?)";
					try{
					  stmt = conn.prepareStatement(sql1);
					  stmt.setInt(1,Integer.parseInt(oid));
					  stmt.setInt(2,c_id);
					  stmt.setString(3,Date);
					  stmt.setInt(4,p_id);
					  stmt.executeUpdate();
					}
					catch(Exception e)
					{
						System.out.println("Exception in customer Insertion2 : "+e);
						
					}
				 
			 }

		 
		 else if(k.equals("FDelete"))
		 {
			 /*Delete the furnished Product*/
			 int records=0;
			 	String iname=req.getParameter("iname");
				System.out.println("ItemNAme is  :"+iname);
				String sql ="Delete from furnished_products where Product_Name=?";
				try{
				  stmt = conn.prepareStatement(sql);
				  stmt.setString(1,iname);
				  records=stmt.executeUpdate();
				  if(records==0)
					  res.setStatus(409);
				  /*else
				  {
					  sql="Delete from product_inputs where Product_ID=(Select Product_ID from furnished_product where Product_Name=?)";
					  stmt = conn.prepareStatement(sql);
					  stmt.setString(1,iname);
					  records=stmt.executeUpdate();
					  if(records==0)
						  res.setStatus(409);
				  }*/
				}catch(SQLException e)
				{
					res.setStatus(409);
					System.out.println("SQL Exception : "+e);				
				}
				
		 }
		 
		 else if(k.equals("FUpdate"))
		 {
			 System.out.println("FUpdate code on Server Side");
			 int records=0;
			 String item_name=req.getParameter("iname");
				String price=req.getParameter("iprice");//should be Driver id instead
				String quan=req.getParameter("iqty");
             if(!(price.equals("")))
             {
				
				String sql ="update furnished_products set Product_Price=? where Product_Name=?";
				try{
				  stmt = conn.prepareStatement(sql);
				  stmt.setString(2,item_name);
				  stmt.setDouble(1,Double.parseDouble(price));
				  records=stmt.executeUpdate();
				  if(records==0)
					  {
					  res.setStatus(409);
				  	 /*how to exit out from here??*/	
					  }
				}
				catch(Exception e)
				{
					res.setStatus(409);
					System.out.println("Exception in FUpdate "+e);
					
				}}
             if(!(quan.equals("")))
             {
            	 String status="";
            	 ResultSet rs=null;
            	 records=0;
            	String sql="update furnished_products set Available_Quantity=? where Product_Name=?";
              	try{
    				  stmt = conn.prepareStatement(sql);
    				  stmt.setString(2,item_name);
    				  stmt.setInt(1,Integer.parseInt(quan));
    				  records=stmt.executeUpdate();
    				 
    				  if(records!=0)
    				  {
    					  sql="Select Status from furnished_products where Product_Name=?";
    					  stmt=conn.prepareStatement(sql);
    					  stmt.setString(1,item_name);
    					  rs=stmt.executeQuery();
    					  rs.next();
    					  status=rs.getString(1);
    					  if(status=="Out of Stock")
    		            	 {
    		            		 sql="update furnished_products set Status='Available' where Product_Name=?";
    		            		 try{
    		            			 stmt = conn.prepareStatement(sql);
    		            			 stmt.setString(1,item_name);
    		            			 stmt.executeUpdate(sql);
    		            		 }catch(Exception e)
    		            		 {
    		            			res.setStatus(409); 
    		            		 }
    		            	 }
    					  }
    				  else if(records==0)
    				  {
    					  res.setStatus(409);
    				  }
    				}
    				catch(Exception e)
    				{
    					res.setStatus(409);
    					System.out.println("Exception in in_transit details2"+e);
    				}
            	  
             }
			
			 
		 }
		 
		 else if(k.equals("FetchItem"))
		 {
			 System.out.println("Register Servlet FetchItem Code started");
			 res.setContentType("application/json");    
			   JSONArray jarr=new JSONArray();
			   JSONObject obj1=new JSONObject();
			       String sql1,sql2;
			       String n="0";
			      sql1="SELECT Product_Name from furnished_products";
			    //sql2="select count(*) as Products from furnished_products";
			      try{
			    	  ResultSet rs=null;
			    	
			      stmt = conn.prepareStatement(sql1);
			    rs = stmt.executeQuery(sql1);
					String pname="No item";
				  while(rs.next()){
			       pname  = rs.getString("Product_Name");
			    	jarr.put(new JSONObject().put("itemName",pname));
			    	/*one option is add itemId along with Item Name in the JSONObject*/
			    	
				  }
				  System.out.println("Item Names Successfully inserted in Array");
			     rs.close();
			     
			     
 	    		 
 	    		 System.out.println("After adding the object to jsonarray");
			      PrintWriter w=res.getWriter();
			      w.write(jarr.toString());
			      w.flush();
			      w.close();
			
			      	  stmt.close();
			    	    
			    	  conn.close();
			      }
			      catch(SQLException e)
			      {
			    	  System.out.println("SERVER : SQL EXCEPTION");
			      }
			      catch(JSONException e)
			      {
			    	  System.out.println("SERVER : JSON EXCEPTION");
			    }
			      System.out.println("FetchItem Quesry Successful");
		 }

		 
		 else if(k.equals("FNew"))
		 {
			int records=0;
			String pname=req.getParameter("pname");
			String pprice=req.getParameter("pprice");
			String pqty=req.getParameter("pqty");
			
			String sql ="Insert into furnished_products(Product_Name,Product_Price,Available_Quantity)values(?,?,?)";
			try{
			  stmt = conn.prepareStatement(sql);
			  stmt.setString(1,pname);
			  stmt.setString(2,pprice);
			  stmt.setString(3,pqty);
			  records=stmt.executeUpdate();
			  if(records==0)
				  res.setStatus(409);
			  	
			}catch(SQLException e)
			{
				res.setStatus(409);
				System.out.println("SQL Exception : "+e);				
			}
			
		 }
		 else if(k.equals("Login"))
		 {
			 	String uname=req.getParameter("uname");
			 	String upass=req.getParameter("upass");
			 	String status="fail";
			 	res.setContentType("application/json");    
			   JSONArray jarr=new JSONArray();
			   JSONObject obj1=new JSONObject();
			       String sql;
			      sql="SELECT name, password from employee where name='user'";
			    
			      try{
			    	  stmt = conn.prepareStatement(sql);
			      
				  ResultSet rs=null;
			    	rs = stmt.executeQuery(sql);
				  String ename="";
			      String epass="";
			       while(rs.next()){
			       ename  = rs.getString("name");
			       epass = rs.getString("password");
				      }

			     rs.close();
			     /*because ename will be empty if no user match is found*/
			     if(uname.equals(ename) && upass.equals(epass))
			     {
			    	 status="success";
			     }
			   	obj1.put("status",status);
			
				  jarr.put(obj1);
			      System.out.println("After adding the object to jsonarray");
			      PrintWriter w=res.getWriter();
			      w.write(jarr.toString());
			      w.flush();
			      w.close();
			
			      	  stmt.close();
			    	    
			    	  conn.close();
			      }
			      catch(SQLException e)
			      {
			    	  res.setStatus(409);
			    	  System.out.println("SERVER : SQL EXCEPTION");
			      }
			      catch(JSONException e)
			      {
			    	  System.out.println("SERVER : SQL EXCEPTION");
			    }
			      System.out.println("Login Quesry Successful");
		 }
		 /***WHERE ARE WE USING THIS SECTION OF CODE??*****/
		/* else if(k.equals("insert")) 
			{
				System.out.println("INSERTIONNNNN");
				 String name=req.getParameter("nm");
					String mail=req.getParameter("pass");
					System.out.println("\nname is : "+name);
				    System.out.println("\nemail is : "+mail);

				//res.setContentType("application/json"); 
				
			  String sql ="Insert into employee values(?,?)";
			try{
			  stmt = conn.prepareStatement(sql);
			  stmt.setString(1,name);
			  stmt.setString(2,mail);
			  stmt.executeUpdate();
			}catch(SQLException e)
			{
				System.out.println("SQL Exception : "+e);				
			}
			}
		 
		 */
		 
		 /*COMPLETE EDITING*/
//####### inserting supplier info ######################				
			else if(k.equals("supplierInsert"))
			{
				
		        String nm=req.getParameter("nm");
				String ph=req.getParameter("ph");
				String ad=req.getParameter("ad");
			//Supplier insert code'
				String sql ="Insert into supplier(Supplier_Name,Phone,Address) values(?,?,?)";
				try{
				  stmt = conn.prepareStatement(sql);
			//	  stmt.setString(1,sid);
				  stmt.setString(1,nm);
				  stmt.setString(2,ph);
				  stmt.setString(3,ad);
				  stmt.executeUpdate();
				}
				catch(Exception e)
				{
					res.setStatus(409);
					System.out.println("Exception in Supplier Insertion"+e);
					
				}
			}
			
// ###############	inserting customer details ####################		
			
			else if(k.equals("AddCustomer"))
			{
					       
		        String name=req.getParameter("cname");
				String ph=req.getParameter("cph");
				String ad=req.getParameter("cadd");

				String sql ="Insert into customer(Customer_Name, Address, Customer_Phone) values(?,?,?)";
				try{
				  stmt = conn.prepareStatement(sql);
				  stmt.setString(1,name);
				  stmt.setString(3,ph);
				  stmt.setString(2,ad);
				  stmt.executeUpdate();
				}
				catch(Exception e)
				{
					res.setStatus(409);
					System.out.println("Exception in customer Insertion"+e);
					
				}
			}
		 
 // ########   inserting rawmaterial info ##################
			else if(k.equals("updaterawmaterial"))
			{
				
		        String name=req.getParameter("nm");
				String quantity=req.getParameter("quan");
				String date=req.getParameter("date");
				String unit_p=req.getParameter("unit_price");
				String sql ="Insert into raw_materials values(?,?,?,?)";
				try{
				  stmt = conn.prepareStatement(sql);
				  stmt.setString(1,name);
				  stmt.setString(2,quantity);
				  stmt.setString(3,date);
				  stmt.setString(4,unit_p);
				  stmt.executeUpdate();
				}
				catch(Exception e)
				{
					System.out.println("Exception in raw material Insertion"+e);
					
				}
			}
			else if(k.equals("Extract_D_Name"))
			{
				String dname ="",d_phn="";
				res.setContentType("application/json");    
				  JSONArray jarr=new JSONArray();
				 JSONObject obj1=new JSONObject();
			     d_phn=req.getParameter("dph");
			     
		        String sql1 ="Select Driver_Name from driver where Driver_Phone = ?";
		        try{
					  stmt = conn.prepareStatement(sql1);
					  stmt.setString(1,d_phn);
					  ResultSet rs = stmt.executeQuery();
					  rs.next();
					  
						 // d_id = rs.getString("Driver_ID");
					  dname =rs.getString("Driver_name");
					  
					  obj1.put("D_Name",dname);
				       jarr.put(obj1);
					   System.out.println("After adding the object to jsonarray");
					   PrintWriter w=res.getWriter();
					    w.write(jarr.toString());
					    w.flush();
					    w.close();
					    stmt.close();
					    conn.close();
					      
		        }
		       
			      catch(JSONException e)
			      {
			    	  System.out.println("SERVER : SQL EXCEPTION");
			    }
		        catch(Exception e)
				{
					System.out.println("Exception in getting driver id"+e);
					
				}
				
			}
		        else if(k.equals("Transit") )
		        {
		        int records=0;
		        String d_id ="",d_phn="";
		        String oid=req.getParameter("oid");
		        d_phn=req.getParameter("dph");
		        String sql1 ="Select Driver_Name from driver where Driver_Phone = ?";
		        try{
					  stmt = conn.prepareStatement(sql1);
					  stmt.setString(1,d_phn);
					  ResultSet rs = stmt.executeQuery();
					  rs.next();
					  d_id = rs.getString("Driver_ID");
		        
				   String sql ="Insert into in_transit values(?,?)";
				  stmt = conn.prepareStatement(sql);
				  stmt.setString(1,oid);
				  stmt.setString(2,d_id);
				  //stmt.setString(3,t_date);
				 stmt.executeUpdate();
				 if(records==0)
				 {
					 res.setStatus(409);
				 }
				}
					 
			    catch(Exception e)
				{
					System.out.println("Exception in in_transit details"+e);
					
				}
				System.out.println("Transit Query Successful");
			}

			// ######## updating raw material #########
			else if(k.equals("Update_raw_material"))
			{
				String nm="";
				int Sid=0;
		        String item_name=req.getParameter("iname");
				//String new_name=req.getParameter("iname");
				String quan=req.getParameter("rm_quantity");
				String price=req.getParameter("unit_price");
				String date=req.getParameter("date");
				String supplier_no = req.getParameter("supplier_phno");
				System.out.println("Sno is : "+supplier_no);
				String sql ="select SID from supplier where Phone =?";
				try{
					  stmt = conn.prepareStatement(sql);
					  stmt.setString(1,supplier_no);
					 ResultSet rs = stmt.executeQuery();
					 rs.next();
					 Sid = rs.getInt("SID");
				}
				catch(Exception e)
				{
					res.setStatus(409);
					System.out.println("Exception in updating raw material details : 1 : Sid is : "+Sid +" Exception : "+e);
					System.out.println("iname : "+item_name);
					System.out.println("rm_quantity : "+quan);
					System.out.println("unit_price : "+price);
					System.out.println("date : "+date);
					System.out.println("supplier_no : "+supplier_no);
				}
				
				
			//	if(item_name.equals(""))
				//	nm = new_name;
				//else
			//	nm = item_name;
               
				String sql2 ="Insert into raw_materials(SID,Item_Name,Quantity,Unit_Price,Date) values(?,?,?,?,?)";
				try{
				  stmt = conn.prepareStatement(sql2);
				  stmt.setInt(1,Sid);
				  stmt.setString(2,item_name);
				  stmt.setString(3,quan);
				  stmt.setDouble(4,Double.parseDouble(price));
				  stmt.setString(5,date);
				  stmt.executeUpdate();
				}
				catch(Exception e)
				{
					res.setStatus(409);
					System.out.println("Exception in updating raw material details : 2 : "+e);
					
				}
			} 
		 
		 
		 
//########## deducting quantity from raw materials
			else if(k.equals("deduct_raw_material"))
			{
				
		        String deduct_quan=req.getParameter("deduct_quan");
				String Item_name=req.getParameter("iname");
				int iid=0;
				System.out.println("iname  : "+Item_name);
				System.out.println("Quantity  : "+deduct_quan);
				String sql ="Select Item_ID from raw_materials where Item_Name=?";
				try{
				  stmt = conn.prepareStatement(sql);
				  stmt.setString(1,Item_name);
				  ResultSet rs = stmt.executeQuery();
				  rs.next();
				  iid = rs.getInt("Item_ID");
				  
				}
				catch(Exception e)
				{
					System.out.println("Exception in deduction of raw material"+e);
					
				}
		
		try{	
			 
			int records=0;
			String sql2="Update material_track set Quantity= Quantity - ? where Item_ID=?";
				  stmt = conn.prepareStatement(sql2);
				  stmt.setDouble(1,Double.parseDouble(deduct_quan));
				  stmt.setInt(2,iid);
			
				  records=stmt.executeUpdate();
				  if(records==0)
					  res.setStatus(409);
			}
				catch(Exception e)
				{
					res.setStatus(409);
					System.out.println("Exception in  deduction of raw material"+e);
				}
			}
		 	 		 		 
	
		 	 
			/*if(k.equals("extract"))
			{
				System.out.println("EXTRACTTTT");
				res.setContentType("application/json");    
			    JSONObject jsonObject= new JSONObject();
			    JSONArray jarr=new JSONArray();
			   JSONObject obj1=new JSONObject();
			    System.out.println("Creating statement...");
			      
			      String sql;
			      sql="SELECT name, email from employee where name = 'nidhipal'";
			      
			      //"SELECT Dname, Dnumber, Mgr_ssn FROM department";
					try {
						stmt = conn.prepareStatement(sql);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    
					// TODO Auto-generated catch block
				      
			      
			      
			      ResultSet rs=null;
				try {
					
					rs = stmt.executeQuery(sql);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
				}
			      
			 
			      String ename="";
			      String email="";
			      
			    
			     try{ 
			    	 while(rs.next()){
			         //Retrieve by column name
			    	
			         ename  = rs.getString("name");
			        email = rs.getString("email");

					      	
			      }

			     rs.close();
			     }catch(SQLException e)
			     {
			    	 System.out.println("SQL Exception 4");
			    	 
			     }
			  
			      try {
					obj1.put("name",name);
					obj1.put("email",email);
				
			      } catch (JSONException e) {
					// TODO Auto-generated catch block
			    	  System.out.println("JSONException1");
			    	  e.printStackTrace();
				}
			     
			      jarr.put(obj1);
			      System.out.println("After adding the object to jsonarray");
			      PrintWriter w=res.getWriter();
			      w.write(jarr.toString());
			      w.flush();
			      w.close();
			
			      try {
		
			    	  stmt.close();
			    	    
			    	  conn.close();
		
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("SQL EXCeption6");
					e.printStackTrace();
				}catch(NullPointerException e)
			      {
					System.out.println("Null pointer exception on server code");
					System.out.println(e);
					e.printStackTrace();
					
			      }
			}		      
			   
			   
			   /*(SQLException se){
			      //Handle errors for JDBC
			      	System.out.println("\nHandle errors for JDBC\n");
				se.printStackTrace();
			   }catch(Exception e){
			      //Handle errors for Class.forName
				System.out.println("\nHandle errors for Class.forName\n");
			      e.printStackTrace();
			   }finally{
			      //finally block used to close resources
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			      }// nothing we can do
			      try{
			         if(conn!=null)
			            conn.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
			   }//end try*/
		
			   
		
}
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		doPost(req, res);
	}
	
	
		private HttpServletResponse setContentType(String string) {
			// TODO Auto-generated method stub
			return null;
		}
}

