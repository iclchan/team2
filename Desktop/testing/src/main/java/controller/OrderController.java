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

import entity.Instrument3988;

public class OrderController extends Thread{

	public static HashMap<String, Object> postMarketOrder(String symbol, String side, String price, String qty) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {

			URL url = new URL("https://cis2017-exchange.herokuapp.com/api/orders");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{\"team_uid\": \"iMqa_BJ0HpsgVfJKxOJDGQ\", \"symbol\": \"" + symbol +"\", \"side\": \"" + side +"\", \"price\": \"" + price +"\", \"order_type\": \"market\", \"qty\": \"" + qty+"\"}";
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			String outputJSON ="";
			System.out.println(side + "ing...");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				outputJSON += output;
				String idList = JsonPath.read(outputJSON, "$.id");
				String symbolList = JsonPath.read(outputJSON, "$.symbol");
				String sideList = JsonPath.read(outputJSON, "$.side");
				String qtyList = JsonPath.read(outputJSON, "$.qty").toString();
				String orderTypeList = JsonPath.read(outputJSON, "$.order_type");
				ArrayList<Double> priceList = JsonPath.read(outputJSON, "$.fills[*].price");
				System.out.println(idList + " " + symbolList + " " + sideList);
				if(symbolList.equals("3988")){
					Instrument3988 i3988 = new Instrument3988(idList,symbolList,qtyList,qty,orderTypeList,Double.toString(priceList.get(0)));
					resultMap.put("3988", i3988);
				}
				
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return resultMap;
	}

	public static void postLimitOrder(String symbol, String side, String price, String qty) {
		try {

			URL url = new URL("https://cis2017-exchange.herokuapp.com/api/orders");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{\"team_uid\": \"iMqa_BJ0HpsgVfJKxOJDGQ\", \"symbol\": \"" + symbol +"\", \"side\": \"" + side +"\", \"price\": \"" + price +"\", \"order_type\": \"limit\", \"qty\": \"" + qty+"\"}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			String outputJSON ="";
			System.out.println(side+"ing Limit Order!");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				outputJSON = output;
				
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public static void postCancelLimit(String orderID) {
		try {

			URL url = new URL("https://cis2017-exchange.herokuapp.com/api/orders/fce07a12-5e14-4f03-811f-dc948705798c");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("DELETE");
			conn.setRequestProperty("Content-Type", "application/json");

			//String input = "{\"team_uid\": \"iMqa_BJ0HpsgVfJKxOJDGQ\", \"symbol\": \"3988\", \"side\": \"sell\", \"price\": \"5.15\", \"order_type\": \"limit\", \"qty\": \"50\"}";

			//OutputStream os = conn.getOutputStream();
			//os.write(input.getBytes());
			//os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public static void getInstrumentDetails(String instrument) {
		boolean result = false;
		//System.out.println("Output from Server .... \n");
		boolean brk = false;
		int counter = 0;
		while(brk == false){
			counter++;
			
			String outputJSON = "";
			
			try {

				URL url = new URL("https://cis2017-exchange.herokuapp.com/api/market_data/"+instrument);
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
					ArrayList<String> test = JsonPath.read(outputJSON, "$[*].symbol");
				

				}

				conn.disconnect();

			} catch (MalformedURLException e) {

				e.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();

			}

			//return outputJSON;
			System.out.println(outputJSON);
			if(counter == 3){
				result = true;
				break;
			}
		}
		
	}

}
