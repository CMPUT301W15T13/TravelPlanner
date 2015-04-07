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
import java.util.HashMap;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.TagManager;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
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

public class ManageTagsFragment extends DialogFragment {
	// inspired by http://stackoverflow.com/questions/15762905/how-to-display-list-view-in-alert-dialog-in-android on April 2, 2015
    
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    
	    final ArrayAdapter<String> tagAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_item);
	    
	    final ArrayList<Tag> relatedTags = new ArrayList<Tag>();
	    ClaimList cl = ClaimListSingleton.getClaimList();
	    TagManager tm = cl.getTagMan();
	    
	    try {
	    	HashMap<Tag, ArrayList<String>> map = tm.getManager();
	    	for (Entry<Tag, ArrayList<String>> entry : map.entrySet()) {
	    		tagAdapter.add(entry.getKey().getTagName());
	    		relatedTags.add(entry.getKey());
	    	}
		} catch (NullPointerException e) {
			
		}
	    
	    builder.setAdapter(tagAdapter,new DialogInterface.OnClickListener() {

          @Override
          public void onClick(DialogInterface dialog, int which) {
        	  Tag tag = relatedTags.get(which);
        	  FragmentManager fm = getFragmentManager();
			  ClaimManagerFragment fragment = (ClaimManagerFragment) fm.findFragmentByTag("ClaimManager");
			   
			  fragment.associateTag(tag);
          }
      });
		

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
	        	   
	               @Override
	               public void onClick(DialogInterface dialog, int id) {}
	           });
     
	    return builder.create();
	}
	
}
