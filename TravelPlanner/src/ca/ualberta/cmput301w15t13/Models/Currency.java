package ca.ualberta.cmput301w15t13.Models;

public class Currency{

	/**
	 * This is an enum which contains all of the allowed currencies
	 * @author eorod_000
	 *
	 */
	public enum CurrencyEnum {
			CAD("Canadian Dollar","CAD"),
			USD("American Dollar","USD"), 
			EUR("Euro","EUR"), 
			GBP("British Pound","GBP"),  
			CHF("Swiss Frank","CHF"), 
			JPY("Japanese Yen","JPY"), 
			CNY("Chinese Yuan","CNY");
		
		private String currencyName;
		private String currencyAbbr;
		
		/**
		 * A currency enuminator needs:
		 * @param currencyName: Currency full name
		 * @param currencyAbbr: Currency Abbriviation
		 */
		private CurrencyEnum(String currencyName, String currencyAbbr){
			this.currencyName = currencyName;
			this.currencyAbbr = currencyAbbr;
		}
		
		/**
		 * 
		 * @return
		 */
		public String getCurrencyName() {
			return currencyName;
		}
		
		/**
		 * 
		 * @return
		 */
		public String getCurrencyAbbr() {
			return currencyAbbr;
		}
	
	}
	
	protected CurrencyEnum currencyType;	//Currency Type
	protected double amount;				//Currency amount
	
	public Currency(CurrencyEnum type){
		currencyType = type;
		amount = 0;
	}
	
	
	public CurrencyEnum getcurrencyType(){
		return currencyType;
	}
	
	public void setCurrencyType(CurrencyEnum type){
		currencyType = type;
	}
	
	public double getCurrencyAmount(){
		return amount;
	}
	
	public void setCurrencyAmount(double newAmount){
		this.amount = newAmount;
	}



	
	
	
}
