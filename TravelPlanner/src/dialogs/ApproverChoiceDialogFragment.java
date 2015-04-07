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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Fragments.ClaimViewerFragment;


/**
 * Custom Alert Dialog for approvers functionality when
 * he long-clicks a submitted claim, including the ability to
 * approve and return.
 * 
 * Outstanding Issues: Should be able to view claims from this list,
 * rather than on short click.
 *
 */
public class ApproverChoiceDialogFragment extends DialogFragment{
	int claimIndex;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    claimIndex = getArguments().getInt("index");
	}
	
	/**
	 * Upon returning, Comment is mandatory; Thus, approver must comment before returning
	 */
    final OnClickListener returnWithComment = new OnClickListener() {
        @Override
        /**
         * This will call ApproverComment dialog fragment
         */
        public void onClick(final View v) {
        	
        	/**
        	 * Ji Hwan Kim
        	 * Need to verify if the claim has been first returned
        	 * or it has been returned and it has been re-submitted
        	 * If latter is true then we check if the approver name is same as the returned approver name 
        	 */        	
        		
        	FragmentManager fm = getFragmentManager();
        	ClaimViewerFragment fragment = (ClaimViewerFragment) fm.findFragmentByTag("ClaimViewer");
        	fragment.approverComment(claimIndex);
        	Dialog d = getDialog();
        	d.dismiss();
        	
        }
    };
    
    /**
     * for Approving a claim, an approver who has not returned a claim cannot approve same claim that has been re-submitted
     * It will warn the approver by showing the error message
     */
    final OnClickListener approveClaim = new OnClickListener() {
        @Override
		public void onClick(final View v) {
        	FragmentManager fm = getFragmentManager();
        	ClaimViewerFragment fragment = (ClaimViewerFragment) fm.findFragmentByTag("ClaimViewer");
        	fragment.approverApproveComment(claimIndex);
        	Dialog d = getDialog();
        	d.dismiss();
        }
    };
    
    final OnClickListener viewClaim = new OnClickListener() {
        @Override
		public void onClick(final View v) {
      	   FragmentManager fm = getFragmentManager();
      	   ClaimViewerFragment fragment = (ClaimViewerFragment) fm.findFragmentByTag("ClaimViewer");
      	   fragment.viewClaim(claimIndex);
      	   Dialog d = getDialog();
      	   d.dismiss();
        }
    };
    
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    View view = inflater.inflate(R.layout.approver_adapter_dialog, null);

	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(view);
	    
	    Button approve = (Button) view.findViewById(R.id.buttonApproveClaim);
	    Button returnButton = (Button) view.findViewById(R.id.buttonReturnClaim);
	    Button viewButton = (Button) view.findViewById(R.id.buttonViewClaim);
	    
	    approve.setOnClickListener(approveClaim);
	    returnButton.setOnClickListener(returnWithComment);
	    viewButton.setOnClickListener(viewClaim);

	    
	    return builder.create();
	}
}

