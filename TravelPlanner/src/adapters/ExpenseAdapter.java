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

package adapters;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;

/**
 * This is a Custom array adapter used to 
 * display the necessary info for an expense Item.
 * It should be used when you have to display
 * a list of expenseItem objects. Use as you 
 * would use an ordinary arrayAdapter.
 * 
 * Classes it works with: ExpenseItem 
 * 
 * Sample use:
 * This.ExpenseAdapter = new ExpenseAdapter(getActivity(), R.layout.expense_adapter_layout, this.expenses);
 *
 */

public class ExpenseAdapter extends ArrayAdapter<ExpenseItem>{
	
	//based on http://stackoverflow.com/questions/8166497/custom-adapter-for-list-view Jan 24th 2015
	// and http://www.ezzylearning.com/tutorial/customizing-android-listview-items-with-custom-arrayadapter Jan 25 2015
	ArrayList<ExpenseItem> expenses;
	
	public ExpenseAdapter(Context context, int textViewResourceId, List<ExpenseItem> expenses) {
		super(context, textViewResourceId,expenses);
		this.expenses = (ArrayList<ExpenseItem>) expenses;
	}
	
	/**
	 * Sets up the view for the custom array item 
	 * including Expense Item details.
	 */
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater;
			inflater = LayoutInflater.from(getContext());
			view = inflater.inflate(R.layout.expense_adapter_layout, null);
		}
		
		ExpenseItem expense = expenses.get(position);
		if(expense != null){
			TextView titleView = (TextView) view.findViewById(R.id.textViewAdapterExpenseTitle);
			TextView dateView = (TextView) view.findViewById(R.id.textViewAdapterExpenseDate);
			TextView costView = (TextView) view.findViewById(R.id.textViewAdapterExpenseAmount);
			TextView currView = (TextView) view.findViewById(R.id.textViewAdapterCurrency);
			TextView catView = (TextView) view.findViewById(R.id.TextViewExpenseCategory);
			TextView incompleteView = (TextView) view.findViewById(R.id.incompletenessIndicator);
			
			// Will need this for properly handling images
			// ImageView statusView = (ImageView) view.findViewById(R.id.imageViewAdapterStatus);
			titleView.setText(expense.getExpenseName());
			dateView.setText(expense.getPurchseDateAsString());
			DecimalFormat decim = new DecimalFormat("0.00");
			costView.setText(String.valueOf(decim.format(expense.getAmount())));
			currView.setText(expense.getCurrency());
			catView.setText(expense.getExpenseCategory());
			
			// Keep the if-else, since the button will be a toggle.
			if (expense.isComplete()) {
				incompleteView.setText(" ");
			} else {
				incompleteView.setText("!");
			}
		}
		return view;
	}
}