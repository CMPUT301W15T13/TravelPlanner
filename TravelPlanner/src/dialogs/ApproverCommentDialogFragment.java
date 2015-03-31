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

import android.view.View.OnClickListener;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Fragments.ClaimViewerFragment;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ca.ualberta.cmput301w15t13.Models.Claim;

public class ApproverCommentDialogFragment extends DialogFragment {

	final OnClickListener commitComment = new OnClickListener() {
		/**
		 * There will be if statements to check if the comment field is empty or not
		 * If empty then Toast error message
		 * Else we submit the change and return the claim
		 */
		@Override
		public void onClick(final View v) {
			Toast.makeText(getActivity(), "Commit was clicked", Toast.LENGTH_SHORT).show();
			Dialog d = getDialog();
			d.dismiss();
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		/*
		 *  DO NOT do any layout work in here. The layout
		 *  is only initialized by onStart()
		 */
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
		/**
		 * Upon clicking commit by approver, the fragment will call returnClaim
		 * and comments will be saved
		 */
		// first, assume the comment is not complete
		//complete = false;
		Button Commit = (Button) getView().findViewById(R.id.ButtonCommitApproverComment);
		
		/*
		// we call fillComment, which will force the approver to fill the comment
		fillComment();
		// after clicking commit, we will check if the comment field was filled or not
		Commit.setOnClickListener(commitComment);
		*/
		
		Commit.setOnClickListener(commitComment);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.approver_comment_dialog, container, false);
		return v;
	}

}
