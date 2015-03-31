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
package dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Fragments.ClaimManagerFragment;

/**
 * This is a custom dialog fragment for 
 * Editing tags. It uses the same layout as the
 * tag dialog layout, but adds an "edit existing"
 * button.
 */

public class TagChoiceFragment extends DialogFragment {
	// Based on http://developer.android.com/guide/topics/ui/dialogs.html March 06 2015
    
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    // Also add buttons
	    builder.setView(inflater.inflate(R.layout.tag_dialog_layout, null))
	           .setNegativeButton(R.string.add, new DialogInterface.OnClickListener() {
	        	   
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                   EditText tView = (EditText) ((AlertDialog) dialog).findViewById(R.id.editTagText);
	                   String tagText;
	                   tagText = tView.getText().toString().trim();
					   // Add the tag field and update the textview
	                   
					   FragmentManager fm = getFragmentManager();
					   ClaimManagerFragment fragment = (ClaimManagerFragment) fm.findFragmentByTag("ClaimManager");
					   
					   fragment.addTagItem(tagText);}
	           });
	    
	    builder.setView(inflater.inflate(R.layout.tag_dialog_layout, null))
	           .setPositiveButton(R.string.editExisting, new DialogInterface.OnClickListener() {
	        	   
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   
	            	   FragmentManager fm = getFragmentManager();
					   ClaimManagerFragment fragment = (ClaimManagerFragment) fm.findFragmentByTag("ClaimManager");
					   
					   fragment.openEditTagDialog(0);
	               }
	           });
     
	    return builder.create();
	}
	
}
