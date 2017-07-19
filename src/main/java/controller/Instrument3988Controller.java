package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.jayway.jsonpath.JsonPath;

import entity.Instrument3988;

public class Instrument3988Controller {
	
	
	public static void sell(String price, String qty){
		HashMap<String, Object> resultMap = null;
		if(checkSellTradeEmpty() != null){
			OrderController.postMarketOrder("3988", "sell", price, qty);
		}
	}
	
	
	public static HashMap<String, Object> buy(String price, String qty){
		HashMap<String, Object> resultMap = null;
		
		if(checkBuyTradeEmpty() != null){
			resultMap = OrderController.postMarketOrder("3988", "buy", price, qty);
			
		}
		return resultMap;
	}
	
	public static HashMap<String, Double> checkBuyTradeEmpty(){
		boolean result = false;
		HashMap<String, Double> resultMap = null;
		String outputJSON = "";
		try {
			
			URL url = new URL("https://cis2017-exchange.herokuapp.com/api/market_data/3988");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;

			while ((output = br.readLine()) != null) {
				outputJSON += output;
				resultMap = JsonPath.read(outputJSON, "$.buy");
				//System.out.println(test.get(0));
				//System.out.println(test);
				/*Iterator it = resultMap.entrySet().iterator();
			    while (it.hasNext()) {
			        HashMap.Entry pair = (HashMap.Entry)it.next();
			        //System.out.println(pair.getKey() + " = " + pair.getValue());
			        
			    }*/

			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		//return outputJSON;
		//System.out.println(outputJSON);
		
		
		return resultMap;
	}
	
	public static HashMap<String, Double> checkSellTradeEmpty(){
		boolean result = false;
		HashMap<String, Double> resultMap = null;
		String outputJSON = "";
		try {
			
			URL url = new URL("https://cis2017-exchange.herokuapp.com/api/market_data/3988");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;

			while ((output = br.readLine()) != null) {
				outputJSON += output;
				resultMap = JsonPath.read(outputJSON, "$.sell");
				//System.out.println(test.get(0));
				//System.out.println(test);
				/*Iterator it = resultMap.entrySet().iterator();
			    while (it.hasNext()) {
			        HashMap.Entry pair = (HashMap.Entry)it.next();
			        //System.out.println(pair.getKey() + " = " + pair.getValue());
			        
			    }*/

			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		//return outputJSON;
		//System.out.println(outputJSON);
		
		
		return resultMap;
	}
}
