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
	static double previous0386Price = 0.0;
	static double previous0005Price = 0.0;
	static double previous0001Price = 0.0;
	static double previous0388Price = 0.0;
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
				
				String symbol3988 = "";
				double bid3988 = 0.0;
				double ask3988 = 0.0;
				
				String symbol0386 = "";
				double bid0386 = 0.0;
				double ask0386 = 0.0;
				
				String symbol0005 = "";
				double bid0005 = 0.0;
				double ask0005 = 0.0;
				
				String symbol0388 = "";
				double bid0388 = 0.0;
				double ask0388 = 0.0;
				
				String symbol0001 = "";
				double bid0001 = 0.0;
				double ask0001 = 0.0;
				
				while ((output = br.readLine()) != null) {
					outputJSON += output;
					ArrayList<String> symbolList = JsonPath.read(outputJSON, "$[*].symbol");
					ArrayList<Double> bidList = JsonPath.read(outputJSON, "$[*].bid");
					ArrayList<Double> askList = JsonPath.read(outputJSON, "$[*].ask");
					
					//Get 3 because 3988 is at pos 3
					symbol3988 = symbolList.get(3);
					bid3988 = bidList.get(3);
					ask3988 = askList.get(3);
					//System.out.println("symbol: " + symbol + " BID: " + bid  + " ASK: " + ask);
					
					symbol0386 = symbolList.get(1);
					bid0386 = bidList.get(1);
					ask0386= askList.get(1);
					//System.out.println("symbol: " + symbol0386 + " BID: " + bid0386  + " ASK: " + ask0386);
					
					symbol0005 = symbolList.get(0);
					bid0005 = bidList.get(0);
					ask0005= askList.get(0);
					//System.out.println("symbol: " + symbol0005 + " BID: " + bid0005  + " ASK: " + ask0005);
					

					symbol0001 = symbolList.get(4);
					bid0001 = bidList.get(4);
					ask0001= askList.get(4);
					System.out.println("symbol: " + symbol0001 + " BID: " + bid0001  + " ASK: " + ask0001);
					
					//testing algo2
					algo2(ask3988, ask0386, ask0005, ask0388, ask0001);
					
					
					
					
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
	public static void algo2(double ask3988Price, double ask0386Price, 
			double ask0005Price, double ask0388Price, double ask0001Price){
		
		boolean bought = false;
		
		
		// if very first cycle of trade do not buy/sell
		
		if(tradeCycleCounter == 1){
			previous3988Price = ask3988Price;
			previous0386Price = ask0386Price;
			previous0005Price = ask0005Price;
			previous0388Price = ask0388Price;
			previous0001Price = ask0001Price;
		} else {
			/*
			 * 
			 * 3988 Trades
			 * 
			 
			//start of 3988 transaction
			if(ask3988Price < (0.97 * previous3988Price) && ask3988Price != 0.0){
				
				//3988 limit buy
				try {
					String toBuyPriceInStr = Double.toString(ask3988Price);
					Instrument3988Controller.buyLimit(toBuyPriceInStr, "100"); //10 is hardcoded
					totalSpentOnI3988 += ask3988Price;
					totalQtyBoughtI3988 += 100; //still hardcoded 10
					double testTOTALSPENT = totalSpentOnI3988;
					double testTOTALBOUGHT = totalQtyBoughtI3988;
					previous3988Price = ask3988Price;
					bought = true;
				} catch (Exception e) {
					System.out.println("Nobody selling 3988 instrument at this moment");
				}
				
				//3988 limit sell
				if(bought = true){
					double priceToSell = ask3988Price * 1.01;
					try {
						Instrument3988Controller.sellLimit(Double.toString(priceToSell), "100"); //10 is hardcoded
						totalSpentOnI3988 = 0;
						totalQtyBoughtI3988 = 0;
					} catch (Exception e) {
						System.out.println("Error happening while selling limit order");
					}
				}
			}
			if(ask3988Price != 0){
				previous3988Price = ask3988Price;
			}
			//end of 3988 transaction
			*/
			
			 /* 
			 * 0386 Trades
			 * 
			 
			//start of 0386 transaction
			if(ask0386Price < (0.97 * previous0386Price) && ask0386Price != 0.0){
				
				//0386 limit buy
				try {
					String toBuyPriceInStr = Double.toString(ask0386Price);
					//Instrument0386Controller.buyLimit(toBuyPriceInStr, "1"); //10 is hardcoded
					//test market buy
					Instrument0386Controller.buy(toBuyPriceInStr, "100"); //10 is hardcoded
					//totalSpentOnI0386 += ask0386Price;
					//totalQtyBoughtI3988 += 100; //still hardcoded 10
					//double testTOTALSPENT = totalSpentOnI3988;
					//double testTOTALBOUGHT = totalQtyBoughtI3988;
					previous0386Price = ask0386Price;
					bought = true;
				} catch (Exception e) {
					System.out.println("Nobody selling 0386 instrument at this moment");
				}
				
				//0386 limit sell
				if(bought = true){
					double priceToSell = ask0386Price * 1.01;
					try {
						//test market sell
						//Instrument0386Controller.sell(Double.toString(priceToSell), "1"); //10 is hardcoded
						Instrument0386Controller.sellLimit(Double.toString(priceToSell), "100"); //10 is hardcoded
						//totalSpentOnI386 = 0;
						//totalQtyBoughtI396 = 0;
					} catch (Exception e) {
						System.out.println("Error happening while selling limit 0386 order");
					}
				}
			}
			if(ask0386Price != 0){
				previous0386Price = ask0386Price;
			}
			//end of 0386 transaction
*/			
			/*
			 * 
			 * 0005 Trades
			 * 
			 */
			//start of 0005 transaction
			/*
			if(ask0005Price < (0.97 * previous0005Price) && ask0005Price != 0.0){
				
				//0005 limit buy
				try {
					String toBuyPriceInStr = Double.toString(ask0005Price);
					Instrument0005Controller.buy(toBuyPriceInStr, "1"); //10 is hardcoded
					//Instrument0005Controller.buyLimit(toBuyPriceInStr, "100"); //10 is hardcoded
					//totalSpentOnI3988 += ask3988Price;
					totalQtyBoughtI3988 += 100; //still hardcoded 10
					double testTOTALSPENT = totalSpentOnI3988;
					//double testTOTALBOUGHT = totalQtyBoughtI3988;
					previous0005Price = ask0005Price;
					bought = true;
				} catch (Exception e) {
					System.out.println("Nobody selling 0005 instrument at this moment");
				}
				
				//0005 limit sell
				if(bought = true){
					double priceToSell = ask0005Price * 1.01;
					try {
						Instrument0005Controller.sellLimit(Double.toString(priceToSell), "1"); //10 is hardcoded
						//totalSpentOnI3988 = 0;
						//totalQtyBoughtI3988 = 0;
					} catch (Exception e) {
						System.out.println("Error happening while selling limit order");
					}
				}
			}
			if(ask0005Price != 0){
				previous0005Price = ask0005Price;
			}
			//end of 0005 transaction
			*/
			//0388 Trades
			
			/*
			//start of 0388 transaction
			if(ask0388Price < (0.97 * previous0388Price) && ask0388Price != 0.0){
				
				//0388 limit buy
				try {
					String toBuyPriceInStr = Double.toString(ask0388Price);
					Instrument0388Controller.buy(toBuyPriceInStr, "1"); //10 is hardcoded
					//Instrument0005Controller.buyLimit(toBuyPriceInStr, "100"); //10 is hardcoded
					previous0388Price = ask0388Price;
					bought = true;
				} catch (Exception e) {
					System.out.println("Nobody selling 0388 instrument at this moment");
				}
				
				//0388 limit sell
				if(bought = true){
					double priceToSell = ask0388Price * 1.01;
					try {
						Instrument0388Controller.sellLimit(Double.toString(priceToSell), "1"); //10 is hardcoded
					} catch (Exception e) {
						System.out.println("Error happening while selling limit order");
					}
				}
			}
			if(ask0388Price != 0){
				previous0388Price = ask0388Price;
			}
			//end of 0388 transaction
			*/
			
			
			//0001 Trades
			//start of 0001 transaction
			if(ask0001Price < (0.97 * previous0001Price) && ask0001Price != 0.0){
				
				//0001 limit buy
				try {
					String toBuyPriceInStr = Double.toString(ask0001Price);
					Instrument0001Controller.buy(toBuyPriceInStr, "1"); //10 is hardcoded
					//Instrument0001Controller.buyLimit(toBuyPriceInStr, "100"); //10 is hardcoded
					previous0001Price = ask0001Price;
					bought = true;
				} catch (Exception e) {
					System.out.println("Nobody selling 0388 instrument at this moment");
				}
				
				//0001 limit sell
				if(bought = true){
					double priceToSell = ask0001Price * 1.01;
					try {
						Instrument0001Controller.sellLimit(Double.toString(priceToSell), "1"); //10 is hardcoded
					} catch (Exception e) {
						System.out.println("Error happening while selling limit order");
					}
				}
			}
			if(ask0001Price != 0){
				previous0001Price = ask0001Price;
			}
			//end of 0001 transaction
		}
	}
}
