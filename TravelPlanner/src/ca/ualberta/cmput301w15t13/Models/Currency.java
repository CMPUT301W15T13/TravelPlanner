package ca.ualberta.cmput301w15t13.Models;

public class Currency{

	
	private enum CurrencyEnum {
			
			CAD("Canadian Dollar","CAD", 0),
			USD("American Dollar","USD", 0), 
			EUR("Euro","EUR", 0), 
			GBP("British Pound","GBP", 0),  
			CHF("Swiss Frank","CHF", 0), 
			JPY("Japanese Yen","JPY", 0), 
			CNY("Chinese Yuan","CNY", 0);
		
		
		private String currencyName;
		private String currencyAbbr;
		private double totalAmount;
		
		private CurrencyEnum(String currencyName, String currencyAbbr, double totalAmount){
			this.currencyName = currencyName;
			this.currencyAbbr = currencyAbbr;
			this.totalAmount = totalAmount;
			
		}
		
		public double getTotalAmount() {
			
			return totalAmount;
		}
		
		public void setTotalAmount(double totalAmount) {
			if (totalAmount >= 0)
				this.totalAmount = totalAmount;
		
			
		}
		
		public String getCurrencyName() {
			return currencyName;
		}
		
		public String getCurrencyAbbr() {
			return currencyAbbr;
		}
	
	}


	protected CurrencyEnum currencyEnum;
	
	
	
	
}
