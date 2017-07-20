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

import com.jayway.jsonpath.JsonPath;

import entity.Instrument0386;

public class Instrument0386Controller {
	
	
	public static void sell(String price, String qty){
		OrderController.postMarketOrder("0386", "sell", price, qty);
	}
	
	public static void sellLimit(String price, String qty){
		OrderController.postLimitOrder("0386", "sell", price, qty);
	}
	
	//returns actual price that I bought
	public static void buyLimit(String price, String qty){
		OrderController.postLimitOrder("0386", "buy", price, qty);
	}
	
	public static HashMap<String, Object> buy(String price, String qty){
		/*String outputJSON = "";
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
				ArrayList<String> test = JsonPath.read(outputJSON, "$.buy[*]."+price);
				System.out.println(test.get(0));

			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		//return outputJSON;
		System.out.println(outputJSON);*/
	
	
		HashMap<String, Object> resultMap = OrderController.postMarketOrder("0386", "buy", price, qty);
		return resultMap;
	}
}
