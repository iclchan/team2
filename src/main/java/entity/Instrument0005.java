package entity;

public class Instrument0005 {
	private String id;
	private String symbol;
	private String side;
	private String qty;
	private String order_type;
	private String price;
	
	public Instrument0005(String id, String symbol, String side, String qty, String order_type, String price) {this.id = id;
		this.symbol = symbol;
		this.side = side;
		this.qty = qty;
		this.order_type = order_type;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	
	
	

	
}
