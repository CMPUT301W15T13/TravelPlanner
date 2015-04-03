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

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;

import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Fragments.ClaimViewerFragment;
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
	private String[] tags = null;
	private boolean[] isSelected = null;


	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    
	    final ArrayList<Integer> indexList = new ArrayList<Integer>();
	    
	    builder.setTitle(R.string.filterMenu)
	    
	    	.setMultiChoiceItems(tags,isSelected, new DialogInterface.OnMultiChoiceClickListener() {
				
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
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					FragmentManager fm = getFragmentManager();
					ClaimViewerFragment fragment = (ClaimViewerFragment) fm.findFragmentByTag("ClaimViewer");
					
					fragment.filterByTag(indexList);
				}

			});
	    
	    	
	    return builder.create();
	}
	
	
	public String[] getTags() {
		return tags;
	}



	public void setTags(String[] tags) {
		this.tags = tags;
	}



	public boolean[] getIsSelected() {
		return isSelected;
	}



	public void setIsSelected(boolean[] isSelected) {
		this.isSelected = isSelected;
	}
}
	   



