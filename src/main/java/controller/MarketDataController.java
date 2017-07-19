package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jayway.jsonpath.JsonPath;

import entity.Instrument3988;

public class MarketDataController extends Thread{
	static HashMap<String, Object> i3988status = new HashMap<String, Object>();
	
	public static void initialiseData(){
		i3988status.put("3988", null);
	}
	public static void getMarketData(){
		initialiseData();
		System.out.println("Output from Server .... \n");
		boolean brk = false;
		int sell3988Counter = -1;
		while(brk == false){
			String outputJSON = "";
			
			try {

				URL url = new URL("https://cis2017-exchange.herokuapp.com/api/market_data");
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
				String symbol = "";
				double bid = 0.0;
				double ask = 0.0;
				while ((output = br.readLine()) != null) {
					outputJSON += output;
					ArrayList<String> symbolList = JsonPath.read(outputJSON, "$[*].symbol");
					ArrayList<Double> bidList = JsonPath.read(outputJSON, "$[*].bid");
					ArrayList<Double> askList = JsonPath.read(outputJSON, "$[*].ask");
					symbol = symbolList.get(3);
					bid = bidList.get(3);
					ask = askList.get(3);
					System.out.println("symbol: " + symbol + " BID: " + bid  + " ASK: " + ask);
					if(i3988status.get("3988")== null){
						//market buy
						HashMap<String, Object> i3988Map = null;
						try {
							i3988Map = Instrument3988Controller.buy(Double.toString(askList.get(3)), "100");
							i3988status.put("3988", i3988Map.get("3988"));
						} catch (Exception e) {
							
						}
						
					} else {
						Instrument3988 i3988 = (Instrument3988) i3988status.get("3988");
						// Bid >= 2% of what I bought
						double myPrice = Double.parseDouble(i3988.getPrice());
						if(bid >= 1.015 * Double.parseDouble(i3988.getPrice()) ){
							try {
								Instrument3988Controller.sell("1", "100"); //market sell
								i3988status.put("3988", null);
							} catch (Exception e) {
								
							}
							sell3988Counter = 0;
						}
						if(bid <= 0.99 * Double.parseDouble(i3988.getPrice()) && bid >= 0.989 * Double.parseDouble(i3988.getPrice()) ){
							sell3988Counter = 0;
							try {
								Instrument3988Controller.sell("1", "100"); //market sell
								i3988status.put("3988", null);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
							}
						}
					}
					
				}
				try {
					Thread.sleep(6600);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				conn.disconnect();

			} catch (MalformedURLException e) {

				e.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();

			}

			//return outputJSON;
			//System.out.println(outputJSON);
			//OrderController.getInstrumentDetails("0005");
			
			//Implement action
			sell3988Counter++;
			System.out.println("SELL 3988 Counter : " + sell3988Counter);
		}
	}

	/*public static void getMarketData() {
		Thread thread = new Thread(new Runnable()
		{
			public void run()
			{
				// do some work here
				System.out.println("Output from Server .... \n");
				boolean brk = false;

				while(brk == false){
					String outputJSON = "";

					try {

						URL url = new URL("https://cis2017-exchange.herokuapp.com/api/market_data");
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


				}
			}
		});
		//thread.start();



		//Below code to test out multi-threading

		//OrderController.getInstrumentDetails("0001");
		//OrderController t1 = new OrderController();
		//t1.getInstrumentDetails("0005");
		try {
			thread.join();
			t1.join();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}







	}*/
}
