package com.amazonaws.lambda.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<String,List<RequestDetails>> {
	RequestDetails req=new RequestDetails();
	List<RequestDetails> li=new ArrayList<>();
    @Override
    public List<RequestDetails> handleRequest(String id,Context context) {
    	List<RequestDetails> list=new ArrayList<>();
    	try {
    		list=getDetails();
    		return list;
		} catch (SQLException sqlException) {
			
			return null;
		}
	
}

private List<RequestDetails> getDetails() throws SQLException
{
	Connection connection = getConnection();
	Statement statement = connection.createStatement();
	String query = getquery();
	ResultSet rs = statement.executeQuery(query);
	while(rs.next()){
        //Retrieve by column name
        req.setId(rs.getString("id"));
        req.setUsername(rs.getString("username"));
        li.add(req);
     }
	return li;
//
//
////if(1 == responseCode)
////{
////	return null;
////}
//
}

    private String getquery() {
    	String query = "SELECT * FROM MNCDemo.users";
    	//String query = "SELECT * FROM MNCDemo.users WHERE id=";
//    	if (id != null) {
//    		query = query.concat("'" + id +  "')");
//    	}
    	System.out.println("the query is "+query);
    	return query;
    	}

private Connection getConnection() throws SQLException {
// TODO Auto-generated method stub
		String url = "jdbc:mysql://mnc.crqfuqauylyw.us-east-1.rds.amazonaws.com:3306";
		String username = "chaya";
		String password = "iworkformodicum";
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;


}
        

    }


