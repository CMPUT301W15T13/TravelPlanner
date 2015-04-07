package ca.ualberta.cmput301w15t13.Activities;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.Controllers.ClaimListSingleton;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;

/**
 * This activity is a copy of bogopic from the CMPUT 301 lab
 * Currently its main purpose is demonstration...
 * TODO: Finish
 * 
 * Classes it works with:
 * TODO: Finish
 * 
 * Sample Use:
 * Intent intent = new Intent(this, PrimitivePhotoActivity.class);
 * startActivity(intent);
 */
public class ReceiptPhotoActivity extends Activity {

	Uri imageFileUri;
	protected int claimIndex;
	protected int expenseIndex;
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	/**
	 * This will call an available android camera
	 * Copied from the bogopic lab
	 * @param v
	 */
	public void takeAPhoto(View v) {
		
		String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TravelPlannerPhotos";
		File folderF = new File(folder);
		
		if (!folderF.exists()) {
			folderF.mkdir();
		}

		// Create an URI for the picture file
		String imageFilePath = folder + "/" + String.valueOf(System.currentTimeMillis()) + ".jpg";
		File imageFile = new File(imageFilePath);
		imageFileUri = Uri.fromFile(imageFile);
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
		
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		
	}
	/**
	 * This will handle the camera results. 
	 * if successful it will set the image to the expenseItem receipt 
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			
			if (resultCode == RESULT_OK) {
				TextView tv = (TextView) findViewById(R.id.textView3);
				tv.setText("Photo OK");
				ImageButton ib = (ImageButton) findViewById(R.id.expenseManagerPictureButton);
			
				Drawable pic = Drawable.createFromPath(imageFileUri.getPath());
				pic = resize(pic);
				ib.setImageDrawable(pic);
				ExpenseItem expense = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex).getExpenseItems().get(expenseIndex);
				if (expense.getReceipt() == null) {
					expense.addReceipt();
				}
				expense.getReceipt().setReceiptUri(imageFileUri);
			} else if (resultCode == RESULT_CANCELED) {

				ClaimListSingleton.getClaimList().notifyListeners();
				TextView tv = (TextView) findViewById(R.id.textView3);
				tv.setText("Photograph Cancelled");
			} else {
				TextView tv = (TextView) findViewById(R.id.textView3);
				tv.setText("Error");
			}			
		}
	}
	
	/**
	 * taken from http://stackoverflow.com/questions/7021578/resize-drawable-in-android on March 14 2015
	 * @param image
	 * @return
	 */
	protected Drawable resize(Drawable image) {
	    Bitmap b = ((BitmapDrawable)image).getBitmap();
	    Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 200, 300, false);
	    return new BitmapDrawable(getResources(), bitmapResized);
	}
	
	/* Below this is android stuff*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.take_a_photo);
		Bundle bundle = getIntent().getExtras();
		claimIndex = bundle.getInt("claimIndex");
		expenseIndex = bundle.getInt("expenseIndex");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}
}
