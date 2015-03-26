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

package ca.ualberta.cmput301w15t13.Fragments;

import java.util.ArrayList;
import java.util.Date;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Activities.ClaimActivity;
import ca.ualberta.cmput301w15t13.Activities.ExpenseActivity;
import ca.ualberta.cmput301w15t13.Controllers.Approver;
import ca.ualberta.cmput301w15t13.Controllers.ClaimAdapter;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.Claimant;
import ca.ualberta.cmput301w15t13.Controllers.ExpenseAdapter;
import ca.ualberta.cmput301w15t13.Controllers.Listener;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimStatus;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;
import exceptions.InvalidUserPermissionException;

/**
 * This fragment is used to view claims and 
 * surface level details, including name, cost, 
 * status and dates. These fields are prone to change
 * or grow. 
 *
 * Outstanding Issues: Getting claimant and approver specific
 * claims should be moved to a controller
 */

public class ExpenseItemViewFragment extends Fragment {
	private TextView expenseNameView;
	private String expenseName;
	private TextView categoryView;
	private String categoryString;
	private TextView dateView;
	private Date Date;
	private String dateText;
	private double amount;
	private TextView amountView;
	private TextView currencyView;
	private String currencyString;
	private String description; 
	private TextView descriptionView;
	private int claimIndex;
	private String claimID;
	private int expenseIndex;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		claimIndex = getArguments().getInt("claimIndex");
		claimID = getArguments().getString("claimID");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.expense_view_layout, container, false);
		return v;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		expenseNameView = (TextView) getView().findViewById(R.id.textViewExpenseName);
		descriptionView = (TextView) getView().findViewById(R.id.textViewDescription);
		dateView = (TextView) getView().findViewById(R.id.textViewPurchaseDate);
		amountView = (TextView) getView().findViewById(R.id.textViewAmountView);
		categoryView = (TextView) getView().findViewById(R.id.textViewCategory);
		currencyView = (TextView) getView().findViewById(R.id.TextViewCurrencyString);

		setFields();
	}
	
	private void setFields() {
		Claim editClaim = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex);
		ExpenseItem editExpense = editClaim.getExpenseItems().get(expenseIndex);
			
		this.expenseNameView.setText(editExpense.getExpenseName());
		this.description = editExpense.getExpenseDescription();
		dateView.setText(editExpense.getPurchseDateAsString());
		this.descriptionView.setText(editExpense.getExpenseDescription());
		this.amountView.setText(String.valueOf(editExpense.getAmount()));
		this.categoryView.setText(editExpense.getExpenseCategory());
		this.currencyView.setText(editExpense.getCurrency());
		
	}
	public void setExpenseIndex(int index) {
		expenseIndex = index;
	}
}