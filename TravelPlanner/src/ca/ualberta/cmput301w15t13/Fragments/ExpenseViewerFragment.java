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
import ca.ualberta.cmput301w15t13.Activities.ClaimActivity;
import ca.ualberta.cmput301w15t13.Activities.ExpenseActivity;
import ca.ualberta.cmput301w15t13.Controllers.ClaimAdapter;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.Listener;
import ca.ualberta.cmput301w15t13.Models.Claim;

/**
 * This fragment is used to view expenses and 
 * surface level details, including name, cost, 
 * status and dates. These fields are prone to change
 * or grow. 
 *
 */

public class ExpenseViewerFragment extends Fragment {
	private ClaimAdapter ExpenseAdapter;
	private ArrayList<Claim> expenses;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		/*
		 *  DO NOT do any layout work in here. The layout
		 *  is only initialized by onStart()
		 */
		super.onCreate(savedInstanceState);
		expenses = ClaimListSingleton.getClaimList().getClaimArrayList();
		this.ExpenseAdapter = new ClaimAdapter(getActivity(), R.layout.expense_adapter_layout, this.expenses);
		//expenses = ClaimListSingleton.getExpenseItemList();
		//this.ExpenseAdapter = new ExpenseAdapter(getActivity(), R.layout.expense_adapter_layout, this.expenses);
		
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
		View v = inflater.inflate(R.layout.expense_item_viewer, container, false);
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
//				claims.clear();
				expenses = ClaimListSingleton.getClaimList().getClaimArrayList();
				//expenses = ClaimListSingleton.getExpenseItemList();
				ExpenseAdapter.notifyDataSetChanged();
			}
			
		});
	}
	
	private void initializeAdapter(){
		final ListView ExpenseListView = (ListView) getView().findViewById(R.id.listViewExpenseItems);
		ExpenseListView.setAdapter(ExpenseAdapter);
		
		ExpenseListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				if(((ExpenseActivity) getActivity()).isClaimant()){
					//Toast.makeText(getActivity(), "Open expense edit", Toast.LENGTH_SHORT).show();
					//Intent intent = new Intent(getActivity(), ExpenseActivity.class);
					//startActivity(intent);
					
				}else{
					Toast.makeText(getActivity(), "View Expense details", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		ExpenseListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getActivity(), "Long Click", Toast.LENGTH_SHORT).show();
				//TODO make a popup open, and have edit as an option. Later
				((ClaimActivity) getActivity()).editClaim(position);
				return true;
			}
		});
	}

}