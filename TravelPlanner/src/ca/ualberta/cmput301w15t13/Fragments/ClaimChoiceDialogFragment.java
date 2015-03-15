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

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import ca.ualberta.cmput301w15t13.R;
/**
 * 
 * TODO
 */

public class ClaimChoiceDialogFragment extends DialogFragment{
	// Based on http://martin.cubeactive.com/android-onclicklitener-tutorial/ March 15 2015
    final OnClickListener editClaim = new OnClickListener() {
        @Override
		public void onClick(final View v) {
     	   FragmentManager fm = getFragmentManager();
     	   ClaimViewerFragment fragment = (ClaimViewerFragment) fm.findFragmentByTag("ClaimViewer");
     	   fragment.editClaim();
     	   Dialog d = getDialog();
     	   d.dismiss();
        }
    };
    
    final OnClickListener submitClaim = new OnClickListener() {
        @Override
		public void onClick(final View v) {
      	   FragmentManager fm = getFragmentManager();
      	   ClaimViewerFragment fragment = (ClaimViewerFragment) fm.findFragmentByTag("ClaimViewer");
      	   fragment.submitClaim();
      	   Dialog d = getDialog();
      	   d.dismiss();
        }
    };
    
    final OnClickListener deleteClaim = new OnClickListener() {
        @Override
		public void onClick(final View v) {
      	   FragmentManager fm = getFragmentManager();
      	   ClaimViewerFragment fragment = (ClaimViewerFragment) fm.findFragmentByTag("ClaimViewer");
      	   fragment.deleteClaim();
      	   Dialog d = getDialog();
      	   d.dismiss();
        }
    };
    
    final OnClickListener viewClaim = new OnClickListener() {
        @Override
		public void onClick(final View v) {
      	   FragmentManager fm = getFragmentManager();
      	   ClaimViewerFragment fragment = (ClaimViewerFragment) fm.findFragmentByTag("ClaimViewer");
      	   fragment.viewClaim();
      	   Dialog d = getDialog();
      	   d.dismiss();
        }
    };
	
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    View view = inflater.inflate(R.layout.claimant_adapter_dialog, null);

	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(view);
	    
	    Button edit = (Button) view.findViewById(R.id.buttonEditClaim);
	    Button submit = (Button) view.findViewById(R.id.buttonSubmitClaim);
	    Button delete = (Button) view.findViewById(R.id.buttonDeleteClaim);
	    Button viewButton = (Button) view.findViewById(R.id.buttonViewClaim);
	    
	    edit.setOnClickListener(editClaim);
	    submit.setOnClickListener(submitClaim);
	    delete.setOnClickListener(deleteClaim);
	    viewButton.setOnClickListener(viewClaim);
	    
	    return builder.create();
	}
}
