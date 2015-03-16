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

package ca.ualberta.cmput301w15t13.Controllers;

import java.util.ArrayList;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;
import ca.ualberta.cmput301w15t13.Models.ExpenseItemList;

/**
 * Singleton pattern for the ExpenseList class.
 */
public class ExpenseListSingleton {
	
	private static ExpenseItemList ExpenseItemList; 
	
	static public ExpenseItemList getExpenseItemList() {
		if (ExpenseItemList == null) {
			ExpenseItemList = new ExpenseItemList();
		}
		return ExpenseItemList;
	}
	
	static public void setExpenseItemList(ArrayList<ExpenseItem> expenses) {
		if (ExpenseItemList == null) { 
			ExpenseItemList = new ExpenseItemList();
		}
	}
	
	static public void addExpenseItem(ExpenseItem expense) {
		if (ExpenseItemList == null) {
			ExpenseItemList = new ExpenseItemList();
		}
		ExpenseItemList.add(expense);
	}
	
	public static boolean isEmpty(){
		if (ExpenseItemList == null) {
			ExpenseItemList = new ExpenseItemList();
		}
	
		if (ExpenseItemList.size() <0) {
			return true;
		} 
		return false;
	}
}