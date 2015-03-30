package ca.ualberta.cmput301w15t13.Activities;

import java.util.ArrayList;

import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.Listener;

import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
import ca.ualberta.cmput301w15t13.Models.Tag;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;


public class EditTag extends Activity {
	
	

	String ID = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_tag_layout);
		
		Bundle bundle = getIntent().getExtras();
		this.ID = bundle.getString("ID");	
		
		ListView listView = (ListView)findViewById(R.id.tagListView);
		
		ClaimList cl = ClaimListSingleton.getClaimList();
		Claim c = cl.getClaimByID(ID);
		
		final ArrayList<Tag> claimTags = c.getTags();
		
		//set list view
		final ArrayAdapter<Tag> tagAdapter = new ArrayAdapter<Tag>(this, android.R.layout.simple_list_item_1, claimTags);
		
		listView.setAdapter(tagAdapter);
		//Listener allows in-time updates
		ClaimListSingleton.getClaimList().addListener(new Listener() {
			
			@Override
			public void update() {
				//claimTags.clear();
				ClaimList cl = ClaimListSingleton.getClaimList();
				Claim c = cl.getClaimByID(ID);
				ArrayList<Tag> claimTags = c.getTags();
				tagAdapter.notifyDataSetChanged();
			}
		});
		
		//dialog set-up
		listView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				
				//Toast.makeText(MainActivity.this, "Delete " + list.get(position).toString() + "?",Toast.LENGTH_LONG).show();
				
				final int pos = position;
				
				AlertDialog.Builder adb = new AlertDialog.Builder(EditTag.this);
				
				adb.setMessage(claimTags.get(position).getTagName());
				adb.setCancelable(true);
				
				adb.setPositiveButton("Edit", new OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						//todo
					}
					
				});
				
				adb.setNegativeButton("Remove", new OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						
						Tag tag = claimTags.get(pos);
						ClaimListSingleton.getClaimList().getClaimByID(ID).removeTag(tag);
						
//						Claim c = cl.getClaimByID(ID);
//						ArrayList<Tag> claimTags = c.getTags();
//						claimTags.remove(tag);
//						c.tags = claimTags;
						//tm.remove(tag, c.getclaimID());
						
						
					}
					
				});
				
				adb.show();
				
				return;
				
			}
		});	
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_tag, menu);
		return true;
	}

}
