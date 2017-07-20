package controller;

import entity.Instrument0005;

public class Instrument0005Controller {
	
	private Instrument0005 i0005;
	
	public static void sell(){
		
	}
	
	
	public static void buy(String price, String qty){
		OrderController.postMarketOrder("0005", "buy", price, qty);
	}
}
