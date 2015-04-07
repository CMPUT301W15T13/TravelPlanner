package ca.ualberta.cmput301w15t13.Models;

import java.text.DecimalFormat;

import ca.ualberta.cmput301w15t13.Models.Currency.CurrencyEnum;

/**
 * This class holds a total of all the currencies in a claim
 * It is used to keep track of the currency totals
 * for each claim such that it can be displayed
 * from a high level point of view
 *
 * It should be used as a container class
 * To keep track of totals in predefined
 * categories, and attached to a Claim object
 * @author eorod_000
 *
 */
public class ClaimCurrencies {
	
	protected Currency currencyCAD;
	protected Currency currencyUSD;
	protected Currency currencyEUR;
	protected Currency currencyGBP;
	protected Currency currencyCHF;
	protected Currency currencyJPY;
	protected Currency currencyCNY;
	
	public ClaimCurrencies(){
		this.currencyCAD = new Currency(CurrencyEnum.CAD);
		this.currencyUSD = new Currency(CurrencyEnum.USD);
		this.currencyEUR = new Currency(CurrencyEnum.EUR);
		this.currencyGBP = new Currency(CurrencyEnum.GBP);
		this.currencyCHF = new Currency(CurrencyEnum.CHF);
		this.currencyJPY = new Currency(CurrencyEnum.JPY);
		this.currencyCNY = new Currency(CurrencyEnum.CNY);
	}
	
	public void addCurrency(Currency currencyToAdd){
		double amountToAdd = 0;
		
		if (currencyToAdd.currencyType == CurrencyEnum.CAD){
			amountToAdd = this.currencyCAD.getCurrencyAmount() + currencyToAdd.getCurrencyAmount();
			this.currencyCAD.setCurrencyAmount(amountToAdd);
		}else if (currencyToAdd.currencyType == CurrencyEnum.USD){
			amountToAdd = this.currencyUSD.getCurrencyAmount() + currencyToAdd.getCurrencyAmount();
			this.currencyUSD.setCurrencyAmount(amountToAdd);
		}else if (currencyToAdd.currencyType == CurrencyEnum.EUR){
			amountToAdd = this.currencyEUR.getCurrencyAmount() + currencyToAdd.getCurrencyAmount();
			this.currencyEUR.setCurrencyAmount(amountToAdd);
		}else if (currencyToAdd.currencyType == CurrencyEnum.GBP){
			amountToAdd = this.currencyGBP.getCurrencyAmount() + currencyToAdd.getCurrencyAmount();
			this.currencyGBP.setCurrencyAmount(amountToAdd);
		}else if (currencyToAdd.currencyType == CurrencyEnum.CHF){
			amountToAdd = this.currencyCHF.getCurrencyAmount() + currencyToAdd.getCurrencyAmount();
			this.currencyCHF.setCurrencyAmount(amountToAdd);
		}else if (currencyToAdd.currencyType == CurrencyEnum.JPY){
			amountToAdd = this.currencyJPY.getCurrencyAmount() + currencyToAdd.getCurrencyAmount();
			this.currencyJPY.setCurrencyAmount(amountToAdd);
		}else if (currencyToAdd.currencyType == CurrencyEnum.CNY){
			amountToAdd = this.currencyCNY.getCurrencyAmount() + currencyToAdd.getCurrencyAmount();
			this.currencyCNY.setCurrencyAmount(amountToAdd);
		}
	}
	
	
	public String getCurrenciesAsString(){
		String currencies = "";
		
		if (this.currencyCAD.getCurrencyAmount() > 0){
			currencies = currencies + String.format("%.02f", this.currencyCAD.getCurrencyAmount()) + " " +  this.currencyCAD.getcurrencyTypeAsString();
			currencies = currencies + "\n";
		}
		if (this.currencyUSD.getCurrencyAmount() > 0){
			currencies = currencies + String.format("%.02f", this.currencyUSD.getCurrencyAmount()) + " " + this.currencyUSD.getcurrencyTypeAsString();
			currencies = currencies + "\n";
		}
		if (this.currencyEUR.getCurrencyAmount() > 0){
			currencies = currencies + String.format("%.02f", this.currencyEUR.getCurrencyAmount()) + " " + this.currencyEUR.getcurrencyTypeAsString();
			currencies = currencies + "\n";
		}
		if (this.currencyGBP.getCurrencyAmount() > 0){
			currencies = currencies + String.format("%.02f", this.currencyGBP.getCurrencyAmount()) + " " + this.currencyGBP.getcurrencyTypeAsString();
			currencies = currencies + "\n";
		}
		if (this.currencyCHF.getCurrencyAmount() > 0){
			currencies = currencies + String.format("%.02f", this.currencyCHF.getCurrencyAmount()) + " " + this.currencyCHF.getcurrencyTypeAsString();
			currencies = currencies + "\n";
		}
		if (this.currencyJPY.getCurrencyAmount() > 0){
			currencies = currencies + String.format("%.02f", this.currencyJPY.getCurrencyAmount()) + " " + this.currencyJPY.getcurrencyTypeAsString();
			currencies = currencies + "\n";
		}
		if (this.currencyCNY.getCurrencyAmount() > 0){
			currencies = currencies + String.format("%.02f", this.currencyCNY.getCurrencyAmount()) + " " + this.currencyCNY.getcurrencyTypeAsString();
			currencies = currencies + "\n";
		}
		
		if (currencies.equals("")){
			return "0.00 : USD";
		}else{
			return currencies;
		}

		
	}


}
