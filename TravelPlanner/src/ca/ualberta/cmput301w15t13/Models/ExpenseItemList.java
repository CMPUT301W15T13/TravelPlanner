/*
 * Copyright 2015 James Devito
 * Copyright 2015 Matthew Fritze
 * Copyright 2015 Ben Hunter
 * Copyright 2015 Ji Hwan Kim
 * Copyright 2015 Edwin Rodriguez
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.ualberta.cmput301w15t13.Models;

import java.util.ArrayList;

/**
 * This class is a container for individual expense
 * items. It should be used by a claim object to 
 * manage it's constituent expense items.
 * 
 * Classes it works with:
 * ExpenseItem,Claim
 */
public class ExpenseItemList {
	
	ArrayList<ExpenseItem> expenseItemList = null;
	String claimID = null;
	
	public ExpenseItemList() {
		expenseItemList = new ArrayList<ExpenseItem>();
	}
	
	public ExpenseItemList(String ClaimID) {
		claimID =ClaimID; 
		expenseItemList = new ArrayList<ExpenseItem>();
	}
	
	/**
	 *  No need to check for duplicate as there could be multiple 
	 *  identical expenses in a claim
	 */
	public void add(ExpenseItem Expense) {
		expenseItemList.add(Expense);
	}
	
	public void delete(ExpenseItem Expense) {
		if (expenseItemList.contains(Expense)==true) {
			expenseItemList.remove(Expense);
		}	
	}
	
	public int size() {
		return this.expenseItemList.size();
	}
	
	public boolean isempty() {
		return this.expenseItemList.size() == 0;
	}
	
	public ArrayList<ExpenseItem> getExpenseList() {
		return this.expenseItemList;
	}
	
	/**
	 *  check if the index is valid, and if the 
	 *  list is empty. If that passes retrieve
	 *  ExpenseItem
	 */
	public ExpenseItem findExpenseItem(int index) {
		
		if (this.expenseItemList.size() > index && index >= 0) {
			return(this.expenseItemList.get(index));
		} 
		//Wrong index or empty list
		return null;
	}
}
