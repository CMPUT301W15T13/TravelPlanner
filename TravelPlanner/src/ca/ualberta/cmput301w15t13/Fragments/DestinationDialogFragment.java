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

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Models.TravelItinerary;
import exceptions.DuplicateException;
import exceptions.EmptyFieldException;

/**
 * This is a custom dialog fragment for 
 * adding a destination-reason pair to a
 * claim. It provides two edit text fields
 * and a submit button. No cancel button is
 * available because clicking outside the
 * dialog will automatically close it.
 * @author mfritze
 *
 */

public class DestinationDialogFragment extends DialogFragment {
	// Based on http://developer.android.com/guide/topics/ui/dialogs.html March 06 2015
    
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(inflater.inflate(R.layout.destination_dialog_layout, null))
	    // Add action buttons
	           .setPositiveButton(R.string.finish, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                   EditText destinationView = (EditText) ((AlertDialog) dialog).findViewById(R.id.editTextDestinationName);
	                   EditText reasonView = (EditText)  ((AlertDialog) dialog).findViewById(R.id.editTextReasonName);
	                   String destination, reason;
	                   
	                   destination = destinationView.getText().toString().trim();
	                   reason = reasonView.getText().toString().trim();
	                   
	                   
	                   
	                   
	                   try {
	                	   
	                	   TravelItinerary item = new TravelItinerary(destination, reason);
	                	   // Add the destination fields and update the textview
	                	   FragmentManager fm = getFragmentManager();
	                	   ClaimManagerFragment fragment = (ClaimManagerFragment) fm.findFragmentByTag("ClaimManager");
	                	   fragment.addTravelItenerarItem(item);
					} catch (EmptyFieldException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	               }
	           });
     
	    return builder.create();
	}
	
}
