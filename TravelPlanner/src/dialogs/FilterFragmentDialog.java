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
import java.util.HashMap;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.TagManager;
import ca.ualberta.cmput301w15t13.Fragments.ClaimManagerFragment;
import ca.ualberta.cmput301w15t13.Fragments.ClaimViewerFragment;
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

public class FilterFragmentDialog extends DialogFragment {
	// based on http://www.101apps.co.za/articles/making-a-list-coding-multiple-choice-list-dialogs.html on April 2nd, 2015
    
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		boolean[] isSelected = {true,false,true,false};
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    String[] test = {"tag1", "tag2", "tag3", "tag4"};
	    final ArrayList<Integer> indexList = new ArrayList<Integer>();
	    
	    builder.setTitle(R.string.filterMenu)
	    
	    	.setMultiChoiceItems(test,isSelected, new DialogInterface.OnMultiChoiceClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					// TODO Auto-generated method stub
					if (isChecked) {
						indexList.add(which);
					} else if (indexList.contains(which)) {
						indexList.remove(Integer.valueOf(which));
					}
					
				}
			})
			
			.setPositiveButton(R.string.filter, new DialogInterface.OnClickListener() {
				
				@SuppressLint("ShowToast")
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					FragmentManager fm = getFragmentManager();
					ClaimViewerFragment fragment = (ClaimViewerFragment) fm.findFragmentByTag("ClaimViewer");
					
					fragment.filterByTag(indexList);
				}

			});
	    
	    	
	    return builder.create();
	}
}
	    
//	    LayoutInflater inflater = getActivity().getLayoutInflater();
//	    
//	    final ArrayAdapter<String> tagAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_multichoice);
//	    
//	    ClaimList cl = ClaimListSingleton.getClaimList();
//	    TagManager tm = cl.getTagMan();
//	    
//	    try {
//	    	HashMap<Tag, ArrayList<String>> map = tm.getManager();
//	    	for (Entry<Tag, ArrayList<String>> entry : map.entrySet()) {
//	    		tagAdapter.add(entry.getKey().getTagName());
//	    	}
//		} catch (NullPointerException e) {
//			
//		}
//	    
//	    tagAdapter.add("All Tags");
//	    
//	    
//	    builder.setAdapter(tagAdapter,new DialogInterface.OnClickListener() {
//
//          @Override
//          public void onClick(DialogInterface dialog, int which) {
//        	  String strName = tagAdapter.getItem(which);
//          
//          }
//      });
//	    
//		
//
//	    // Inflate and set the layout for the dialog
//	    // Pass null as the parent view because its going in the dialog layout
//	    builder.setPositiveButton(R.string.filter, new DialogInterface.OnClickListener() {
//	        	   
//	               @Override
//	               public void onClick(DialogInterface dialog, int id) {}
//	           });
//     
//	    return builder.create();
//	}
//	
//}

//AlertDialog.Builder builderSingle = new AlertDialog.Builder(
//        DialogActivity.this);
//builderSingle.setTitle("Select One Name:-");


//final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//        DialogActivity.this,
//        android.R.layout.select_dialog_singlechoice);

//builderSingle.setNegativeButton("cancel",
//        new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//




