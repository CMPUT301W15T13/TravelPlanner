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
import android.content.Intent;
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
import ca.ualberta.cmput301w15t13.Activities.TestActivity;
import ca.ualberta.cmput301w15t13.Controllers.ClaimAdapter;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.Listener;
import ca.ualberta.cmput301w15t13.Models.Claim;

/**
 * This fragment is used to view claims and 
 * surface level details, including name, cost, 
 * status and dates. These fields are prone to change
 * or grow. 
 *
 */

public class ClaimViewerFragment extends Fragment {
	private ClaimAdapter claimAdapter;
	private ArrayList<Claim> claims;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		/*
		 *  DO NOT do any layout work in here. The layout
		 *  is only initialized by onStart()
		 */
		super.onCreate(savedInstanceState);
		claims = ClaimListSingleton.getClaimList().getClaimArrayList();
		this.claimAdapter = new ClaimAdapter(getActivity(), R.layout.claim_adapter_layout, this.claims);
		
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
		View v = inflater.inflate(R.layout.claim_viewer_layout, container, false);
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
				claims = ClaimListSingleton.getClaimList().getClaimArrayList();
				claimAdapter.notifyDataSetChanged();
			}
			
		});
	}
	
	private void initializeAdapter(){
		final ListView claimListView = (ListView) getView().findViewById(R.id.listViewClaim);
		claimListView.setAdapter(claimAdapter);
		
		claimListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				if(((ClaimActivity) getActivity()).isClaimant()){
					//Toast.makeText(getActivity(), "Open expense edit", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getActivity(), TestActivity.class);
					startActivity(intent);
					
				}else{
					Toast.makeText(getActivity(), "View Claim details", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		claimListView.setOnItemLongClickListener(new OnItemLongClickListener() {

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
