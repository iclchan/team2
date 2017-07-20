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
	//static HashMap<String, Object> i3988status = new HashMap<String, Object>();
	
	
	static double previous3988Price = 0.0;
	static int tradeCycleCounter = 0;
	
	//keeping track of i3988 status
	static double totalSpentOnI3988 = 0.0;
	static int totalQtyBoughtI3988 = 0;
	
/*	public static void initialiseData(){
		i3988status.put("3988", null);
	}*/
	public static void getMarketData(){
		//initialiseData();
		System.out.println("Output from Server .... \n");
		boolean brk = false;
		int sellCounter = 0;
		while(brk == false){
			String outputJSON = "";
			tradeCycleCounter++;
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
					
					
					//testing algo1
					//algo1(ask);
					
					//testing algo2
					if(ask != 0.0){
						algo2(ask);
					}
					
					
					
					/*if(i3988status.get("3988")== null){
						//market buy
						HashMap<String, Object> i3988Map = Instrument3988Controller.buy(Double.toString(askList.get(3)), "10");
						i3988status.put("3988", i3988Map.get("3988"));
					} else {
						Instrument3988 i3988 = (Instrument3988) i3988status.get("3988");
						// Bid >= 2% of what I bought
						double myPrice = Double.parseDouble(i3988.getPrice());
						if(bid >= 1.01 * Double.parseDouble(i3988.getPrice()) ){
							Instrument3988Controller.sell("1", "10"); //market sell
							i3988status.put("3988", null);
							sell3988Counter = 0;
						}
						if(sell3988Counter == 6){
							sell3988Counter = 0;
							Instrument3988Controller.sell("1", "10"); //market sell
							i3988status.put("3988", null);
						}
					}*/
					
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
	
	public static void algo1(double askPrice){
		boolean bought = false;
		HashMap<String, Object> i3988Map = null;
		Instrument3988  i3988 = null;
		try {
			i3988Map = Instrument3988Controller.buy("1", "100");
			bought = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Nobody selling 3988 instrument at this moment");
		}
		if(bought == true){
			//double priceToSell = askPrice * 1.01;
			i3988 = (Instrument3988) i3988Map.get("3988");
			double priceToSell = Double.parseDouble(i3988.getPrice()) * 1.01;
			try {
				Instrument3988Controller.sellLimit(Double.toString(priceToSell), "100");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Error happening while selling limit order");
			}
		}
		
	}
	// Skips first cycle, compare current price with previous price, if lower, limit buy @ 0.998 *,
	// Straight away post limit sell @ 1.05 * of price that I bought
	// Does every 10 seconds
	//Qty still hardcoded
	public static void algo2(double askPrice){
		
		boolean bought = false;
		
		// if very first cycle of trade do not buy/sell
		if(tradeCycleCounter == 1){
			previous3988Price = askPrice;
		} else {
			
			//
			if(askPrice < (0.998 * previous3988Price)){
				
				//limit buy
				try {
					String toBuyPriceInStr = Double.toString(askPrice);
					Instrument3988Controller.buyLimit(toBuyPriceInStr, "10"); //10 is hardcoded
					totalSpentOnI3988 += askPrice;
					totalQtyBoughtI3988 += 10; //still hardcoded 10
					double testTOTALSPENT = totalSpentOnI3988;
					double testTOTALBOUGHT = totalQtyBoughtI3988;
					previous3988Price = askPrice;
					bought = true;
				} catch (Exception e) {
					System.out.println("Nobody selling 3988 instrument at this moment");
				}
				
				//limit sell
				if(bought = true){
					double priceToSell =askPrice * 1.05;
					try {
						Instrument3988Controller.sellLimit(Double.toString(priceToSell), "10"); //10 is hardcoded
						totalSpentOnI3988 = 0;
						totalQtyBoughtI3988 = 0;
					} catch (Exception e) {
						System.out.println("Error happening while selling limit order");
					}
				}
			}
			previous3988Price = askPrice;
		}
	}
}
