package ca.ualberta.cmput301w15t13.Activities;

import java.util.ArrayList;

import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.R.layout;
import ca.ualberta.cmput301w15t13.R.menu;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Controllers.Listener;
import ca.ualberta.cmput301w15t13.Fragments.ClaimManagerFragment;
import ca.ualberta.cmput301w15t13.Models.Claim;
import ca.ualberta.cmput301w15t13.Models.ClaimList;
import ca.ualberta.cmput301w15t13.Models.Tag;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewDebug.IntToString;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class EditTag extends Activity {
	
	
//	ListView listView = (ListView)findViewById(R.id.claimListView);
//	Collection<Claim> claim = ClaimController.getClaimList().getClaims();
//	final ArrayList<Claim> list = new ArrayList<Claim>(claim);
//	Collections.sort(list);
//	//set list view
//	final ArrayAdapter<Claim> claimAdapter = new ArrayAdapter<Claim>(this, android.R.layout.simple_list_item_1, list);
//	listView.setAdapter(claimAdapter);
//	//Listener allows in-time updates
//	ClaimController.getClaimList().addListener(new Listener() {
//	@Override
//	public void update()
//	{
//	list.clear();
//	Collection<Claim> claim = ClaimController.getClaimList().getClaims();
//	list.addAll(claim);
//	claimAdapter.notifyDataSetChanged();
//	}
//	});
//	//dialog set-up
//	listView.setOnItemClickListener(new OnItemClickListener() {
//	@Override
//	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//	//Toast.makeText(MainActivity.this, "Delete " + list.get(position).toString() + "?",Toast.LENGTH_LONG).show();
//	final int pos = position;
//	AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
//	adb.setMessage(list.get(position).getName() + " Selected");
//	adb.setCancelable(true);
//	adb.setNeutralButton("View", new OnClickListener() {
//	public void onClick(DialogInterface dialog, int which)
//	{
//	Intent intent = new Intent(MainActivity.this, ViewClaim.class);
//	Bundle bundle = new Bundle();
//	bundle.putInt("index", pos);
//	intent.putExtras(bundle);
//	startActivity(intent);
//	}
//	});
//	adb.setPositiveButton("Delete", new OnClickListener() {
//	@Override
//	public void onClick(DialogInterface dialog, int which)
//	{
//	Claim claim = list.get(pos);
//	ClaimController.getClaimList().removeClaim(claim);
//	// TODO Auto-generated method stub
//	}
//	});
//	adb.setNegativeButton("Currency", new OnClickListener() {
//	@Override
//	public void onClick(DialogInterface dialog, int which)
//	{
//	Intent intent = new Intent(MainActivity.this, ViewCurrencyActivity.class);
//	Bundle bundle = new Bundle();
//	bundle.putInt("index", pos);
//	intent.putExtras(bundle);
//	startActivity(intent);
//	}
//	});
//	adb.show();
//	return;
//	}
//	});
	String id = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_tag_layout);
		
		Bundle bundle = getIntent().getExtras();
		this.id = bundle.getString("ID");		
		ListView listView = (ListView)findViewById(R.id.tagListView);
		ClaimList cl = ClaimListSingleton.getClaimList();
		Claim c = cl.getClaimByID(id);
		
		final ArrayList<Tag> claimTags = c.getTags();
		
		//set list view
		final ArrayAdapter<Tag> tagAdapter = new ArrayAdapter<Tag>(this, android.R.layout.simple_list_item_1, claimTags);
		listView.setAdapter(tagAdapter);
		//Listener allows in-time updates
		cl.addListener(new Listener() {
			
			@Override
			public void update() {
				claimTags.clear();
				ClaimList cl = ClaimListSingleton.getClaimList();
				Claim c = cl.getClaimByID(id);
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
						//todo
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
