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

public class ClaimantExpenseDialogFragment extends DialogFragment{

    final OnClickListener editExpenseItem = new OnClickListener() {
        @Override
		public void onClick(final View v) {
     	   FragmentManager fm = getFragmentManager();
     	   ExpenseListViewerFragment fragment = (ExpenseListViewerFragment) fm.findFragmentByTag("ExpenseViewer");
     	   fragment.editExpenseItem();
     	   Dialog d = getDialog();
     	   d.dismiss();
        }
    };
    
    final OnClickListener deleteExpenseItem = new OnClickListener() {
        @Override
		public void onClick(final View v) {
      	   FragmentManager fm = getFragmentManager();
       	   ExpenseListViewerFragment fragment = (ExpenseListViewerFragment) fm.findFragmentByTag("ExpenseViewer");
     	   fragment.deleteExpenseItem();
     	   Dialog d = getDialog();
     	   d.dismiss();
        }
    };
    final OnClickListener viewExpenseItem = new OnClickListener() {
        @Override
		public void onClick(final View v) {
      	   FragmentManager fm = getFragmentManager();
       	   ExpenseListViewerFragment fragment = (ExpenseListViewerFragment) fm.findFragmentByTag("ExpenseViewer");
     	   fragment.viewExpenseItem();
     	   Dialog d = getDialog();
     	   d.dismiss();
        }
    };
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    View view = inflater.inflate(R.layout.claimant_expense_dialog, null);

	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(view);
	    
	    Button edit = (Button) view.findViewById(R.id.buttonEditExpense);
	    Button delete = (Button) view.findViewById(R.id.buttonDeleteExpense);
	    Button viewButton = (Button) view.findViewById(R.id.buttonViewExpense);
	    
	    edit.setOnClickListener(editExpenseItem);
	    delete.setOnClickListener(deleteExpenseItem);
	    viewButton.setOnClickListener(viewExpenseItem);
	    
	    return builder.create();
	}
}
