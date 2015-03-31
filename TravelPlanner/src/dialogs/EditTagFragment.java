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

import java.util.ArrayList;

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
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Fragments.ClaimManagerFragment;
import ca.ualberta.cmput301w15t13.Models.Tag;

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

public class EditTagFragment extends DialogFragment {
	
	private String tagName;
	private int tagIndex;
	private int claimIndex;
	private int arraySize;
	
	public void setClaimIndex(int index) {
		this.claimIndex = index;
	}
	public void setTagIndex(int tagIndex) {
		this.tagIndex = tagIndex;
	}
	
	public int getTagIndex() {
		return this.tagIndex;
	}
	
	public void setSize(int s) {
		this.arraySize = s; 
	}
	
	public int getSize() {
		return this.arraySize;
	}
	// Based on http://developer.android.com/guide/topics/ui/dialogs.html March 06 2015
    
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    
	    ArrayList<Tag> tags = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex).getTags();
	    setSize(tags.size());
	    Tag t = tags.get(this.tagIndex);
	    this.tagName = t.getTagName();
	    

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(inflater.inflate(R.layout.edit_tag_layout, null))
	    // Add action buttons
	           .setNegativeButton(R.string.update, new DialogInterface.OnClickListener() {
	        	   
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                   EditText tView = (EditText) ((AlertDialog) dialog).findViewById(R.id.updatedTagText);
	                   String tagText;
	                   tagText = tView.getText().toString().trim();
					   // Add the tag field and update the textview
	                   
					   FragmentManager fm = getFragmentManager();
					   ClaimManagerFragment fragment = (ClaimManagerFragment) fm.findFragmentByTag("ClaimManager");
					   
					   fragment.editTagItem(tagText, getTagIndex());
	               }
	           });
	    
	    builder.setView(inflater.inflate(R.layout.edit_tag_layout, null))
	    // Add action buttons
	           .setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
	        	   
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   
	            	   FragmentManager fm = getFragmentManager();
					   ClaimManagerFragment fragment = (ClaimManagerFragment) fm.findFragmentByTag("ClaimManager");
					   
					   if ((getTagIndex()+1) < getSize()) {
						   //Toast.makeText(getActivity(), getTagIndex(), Toast.LENGTH_SHORT);
						   fragment.openEditTagDialog(getTagIndex()+1); 
					   } 
					   
	               }
	                 
	           });
	    
	    builder.setView(inflater.inflate(R.layout.edit_tag_layout, null))
	    // Add action buttons
	           .setNeutralButton(R.string.delete, new DialogInterface.OnClickListener() {
	        	   
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   
	            	   FragmentManager fm = getFragmentManager();
					   ClaimManagerFragment fragment = (ClaimManagerFragment) fm.findFragmentByTag("ClaimManager");
					   
					   fragment.removeTagItem(getTagIndex());
	            	
	               }
	                 
	           });
	    
	    builder.setView(inflater.inflate(R.layout.edit_tag_layout, null))
	    // Add action buttons
	           .setTitle(this.tagName);
     
	    return builder.create();
	}
	
}
