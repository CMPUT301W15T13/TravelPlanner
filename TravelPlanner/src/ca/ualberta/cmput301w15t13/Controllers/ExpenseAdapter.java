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
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;

public class ExpenseAdapter extends ArrayAdapter{
	/* 
	 * The custom array adapter used to 
	 * display the necessary info for an expense
	 */
	
	//based on http://stackoverflow.com/questions/8166497/custom-adapter-for-list-view Jan 24th 2015
	// and http://www.ezzylearning.com/tutorial/customizing-android-listview-items-with-custom-arrayadapter Jan 25 2015
	ArrayList<ExpenseItem> expenses;
	
	@SuppressWarnings("unchecked")
	public ExpenseAdapter(Context context, int textViewResourceId, List<ExpenseItem> expenses) {
		super(context, textViewResourceId,expenses);
		this.expenses = (ArrayList<ExpenseItem>) expenses;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		View view = convertView;
		
		if(view == null){
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
			//Will need this for properly handling images
			//ImageView statusView = (ImageView) view.findViewById(R.id.imageViewAdapterStatus);
			titleView.setText(expense.getExpenseName());
			dateView.setText(expense.getPurchaseDate().toString());
			costView.setText(String.valueOf(expense.getAmount()));
			currView.setText(expense.getCurrency());

		}
		
		return view;
	}
}