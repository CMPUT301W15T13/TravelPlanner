package ca.ualberta.cmput301w15t13.Models;


/**
 * Currency class is used to
 * enumerate over all given
 * currencies (as in the description).
 * This is preferred over using a hard
 * coded arrayList
 *
 * It should be used to define what
 * currencies are possible for a
 * claim object and children, to have
 *
 */

public class Currency {

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
		
	protected CurrencyEnum currencyType;  
	protected double amount;
	
	public Currency(String currencyType, double newAmount){
		CurrencyEnum type = this.getEnumFromString(currencyType);
		this.currencyType = type;
		amount = newAmount;
	}
	
	public Currency(CurrencyEnum type){
		currencyType = type;
		amount = 0;
	}
	
	
	private CurrencyEnum getEnumFromString(String currencyType){
		if (currencyType.equals("CAD")){
			return CurrencyEnum.CAD;
		}else if (currencyType.equals("USD")){
			return CurrencyEnum.USD;
		}else if (currencyType.equals("EUR")){
			return CurrencyEnum.EUR;
		}else if (currencyType.equals("GBP")){
			return CurrencyEnum.GBP;
		}else if (currencyType.equals("CHF")){
			return CurrencyEnum.CHF;
		}else if (currencyType.equals("JPY")){
			return CurrencyEnum.JPY;
		}else{
			return CurrencyEnum.CNY;
		}
		
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

	public String getcurrencyTypeAsString() {
		return this.currencyType.getCurrencyAbbr();
	}	
}
