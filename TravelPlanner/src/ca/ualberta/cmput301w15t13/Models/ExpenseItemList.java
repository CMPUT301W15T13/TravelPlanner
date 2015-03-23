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

import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;

/**
 * This class is a container for individual expense
 * items. It is used by a claim to manage it's
 * constituent expense items.
 */
public class ExpenseItemList {
	/**
	 *  First assign the arrayList expenseItemList to be null
	 */
	ArrayList<ExpenseItem> expenseItemList = null;
	String claimID = null;
	
	/** 
	 * Initializing the ExpenseItemList
	 */
	public ExpenseItemList() {
		expenseItemList = new ArrayList<ExpenseItem>();
	}
	
	/**
	 *  Initializing the ExpenseItemList
	 * @param ClaimID
	 */
	public ExpenseItemList(String ClaimID) {
		claimID =ClaimID; 
		expenseItemList = new ArrayList<ExpenseItem>();
	}
	
	/**
	 *  some methods used to control ExpenseItemList. i.e. add, delete, update, etc
	 * some methods used to control ExpenseItemList. i.e. add, delete, update, etc
	 * @param Expense
	 */
	public void add(ExpenseItem Expense) {
		/**
		 *  No need to check for duplicate as there could be multiple identical expenses in a claim
		 */
		expenseItemList.add(Expense);
	}
	
	/**
	 * this method deletes an expense item from the expense item list
	 * @param Expense
	 */
	public void delete(ExpenseItem Expense) {
		if (expenseItemList.contains(Expense)==true) {
			/**
			 *  there is an Expense in a list we can delete, then delete
			 */
			expenseItemList.remove(Expense);
		}	
	}
	
	/**
	 * this method returns the size of an expense item list
	 * @return
	 */
	public int size() {
		return this.expenseItemList.size();
	}
	
	public boolean isempty() {
		/**
		 *  this will check if the list is empty or not, return true iff empty, else false
		 */
		if (this.expenseItemList.size() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method returns an expense item list
	 * @return
	 */
	public ArrayList<ExpenseItem> getExpenseList() {
		return this.expenseItemList;
	}
	
	public ExpenseItem findExpenseItem(int index) {
		/**
		 *  this method might be trimmed down a bit, little help will be nice if want to modify this
		 *  check index is valid, this automatically check if list is empty too
		 */
		if (this.expenseItemList.size() > index && index >= 0) {
			return(this.expenseItemList.get(index));
		} 
		/**
		 *  input index is wrong or the list is empty
		 */
		return null;
	}

	/**
	 * This method is to be implemented for project 5
	 */
	public void notifyListeners() {
		/**
		 *  TODO Auto-generated method stub	
		 */
	}
	

}
