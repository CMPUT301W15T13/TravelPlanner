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

import adapters.ExpenseAdapter;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Activities.ExpenseActivity;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.Listener;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;
import dialogs.ClaimantExpenseDialogFragment;

/**
 * This fragment is used to view expenses and 
 * surface level details, including name, cost, 
 * status and dates. These fields are prone to change
 * or grow. 
 *
 */

public class ExpenseListViewerFragment extends Fragment {
	private ExpenseAdapter ExpenseAdapter;
	private ArrayList<ExpenseItem> expenses;
	private int claimIndex;
	private String claimID;
	private int expenseIndex;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		/*
		 *  DO NOT do any layout work in here. The layout
		 *  is only initialized by onStart()
		 */
		super.onCreate(savedInstanceState);
		claimIndex = getArguments().getInt("claimIndex");
		claimID = getArguments().getString("claimID");
		Claim claim = ClaimListSingleton.getClaimByID(claimID);
		expenses = claim.getExpenseItems();
		//expenses = ClaimListSingleton.getClaimList().getExpenseList(claimIndex);
		this.ExpenseAdapter = new ExpenseAdapter(getActivity(), R.layout.expense_adapter_layout, this.expenses);		
	}
	
	@Override
	public void onStart() {
		super.onStart();
		addListeners();
		initializeAdapter();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.expense_list_viewer, container, false);
		return v;
	}

	/**
	 * Adds the Listeners for the ArrayAdapter that 
	 * holds all of the claims. Other standard listeners
	 * can be added here.
	 */
	private void addListeners(){
		ClaimListSingleton.getClaimList().addListener(new Listener(){

			@Override
			public void update() {
				ExpenseAdapter.notifyDataSetChanged();
			}
			
		});
	}
	
	/**
	 * This will initialize the adapter for the expenses
	 */
	private void initializeAdapter(){	
		final ListView ExpenseListView = (ListView) getView().findViewById(R.id.listViewExpenseItems);
		ExpenseListView.setAdapter(ExpenseAdapter);
		ExpenseListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				if(((ExpenseActivity) getActivity()).isClaimant()){
					((ExpenseActivity) getActivity()).viewExpense(position);
				}
			}
		});
		/**
		 * Similarly for the long click
		 *
		 */	
		ExpenseListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(((ExpenseActivity) getActivity()).isClaimant()){
					ClaimantExpenseDialogFragment dialog = new ClaimantExpenseDialogFragment();
				    Bundle args = new Bundle();
				    args.putInt("expenseIndex", position);
				    args.putInt("claimIndex", claimIndex);
				    dialog.setArguments(args);
				    dialog.show(getFragmentManager(), "Long Click Pop-Up");
				    
				    //redundancy needed for long click listener to work
				    expenseIndex = position;
				}else{
					//TEMP THIS IS FOR THE APPROVER
					//new ApproverExpenseDialogFragment().show(getFragmentManager(), "Long Click Pop-Up");
				}
				return true;
			}
		});
	}

	public void editExpenseItem() {
		if(((ExpenseActivity) getActivity()).isClaimant()){
			Claim ourClaim = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex);
			if(ourClaim.isEditable()){
				((ExpenseActivity) getActivity()).editExpense(expenseIndex);
			} else{
				Toast.makeText(getActivity(), "Cannot edit this expense.", Toast.LENGTH_SHORT).show();
			}
			
		} else {
			Toast.makeText(getActivity(), "View Expense details", Toast.LENGTH_SHORT).show();
		}
		
	}

	public void deleteExpenseItem() {
		ExpenseItem expense = expenses.get(expenseIndex);
		expenses.remove(expenseIndex);
	//	Claim claim = ClaimListSingleton.getClaimByID(claimID).removeExpenseItem(ex);
		ClaimListSingleton.getClaimByID(claimID).removeExpenseItem(expense);
		ExpenseAdapter.notifyDataSetChanged();		
	}

	public void viewExpenseItem() {		
		((ExpenseActivity) getActivity()).viewExpense(expenseIndex);
	}

}