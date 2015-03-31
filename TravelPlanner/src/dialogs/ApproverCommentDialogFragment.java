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
import android.widget.EditText;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Fragments.ClaimViewerFragment;

public class ApproverCommentDialogFragment extends DialogFragment {
	private String comment;
	private EditText commentField;
	
	/*
	public ApproverCommentDialogFragment(Claim editClaim) {
		// TODO Auto-generated constructor stub
		
	}
	*/
	
	int claimIndex;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    claimIndex = getArguments().getInt("index");
	}

	final OnClickListener commitComment = new OnClickListener() {
		@Override
		public void onClick(final View v) {
			comment = commentField.getText().toString().trim();
			
			Toast.makeText(getActivity(), "Commit was clicked ", Toast.LENGTH_SHORT).show();
						
	     	/**
	     	 * Here I think the return claim shoudn't be called maybe
	     	 * THIS IS WHERE THE APPROVER COMMENT TRANSACTION WILL BE MADE
	     	 */
			
	     	if (comment.matches("") || comment==null) {
		        Toast.makeText(getActivity(), "You did not enter comments", Toast.LENGTH_SHORT).show();
		    } else {
		    	FragmentManager fm = getFragmentManager();
		     	ClaimViewerFragment fragment = (ClaimViewerFragment) fm.findFragmentByTag("ClaimViewer");    	
		     	fragment.returnClaim(claimIndex);
				Dialog d = getDialog();
				d.dismiss();
		    }
	     	/*
			Toast.makeText(getActivity(), "Commit was clicked", Toast.LENGTH_SHORT).show();
			FragmentManager fm = getFragmentManager();
	     	ClaimViewerFragment fragment = (ClaimViewerFragment) fm.findFragmentByTag("ClaimViewer");
	     	fragment.returnClaim(claimIndex);
			Dialog d = getDialog();
			d.dismiss();
			*/
		}
	};

	
	/** Im thinking I need a listener so that it can check if the comment was
	 * empty or not, also it can pass the comment once it is written
	 */
	
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    View view = inflater.inflate(R.layout.approver_comment_dialog, null);

	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(view);
	    commentField = (EditText) view.findViewById(R.id.ApproverCommentEditText);
	    
	    /**
	     * Can't let the approver commit without writing anything on the edit text
	     * Thus, I have to make sure that the user has input something then proceed to commitComment
	     */
	    
	    Button commit = (Button) view.findViewById(R.id.ButtonCommitApproverComment);
	    
	    comment = commentField.getText().toString();   
	    	    
	    commit.setOnClickListener(commitComment);
	    
	    return builder.create();
	}

}
