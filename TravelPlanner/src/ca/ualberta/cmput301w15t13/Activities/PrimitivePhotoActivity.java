package ca.ualberta.cmput301w15t13.Activities;

import java.io.File;

import ca.ualberta.cmput301w15t13.R;
import ca.ualberta.cmput301w15t13.R.layout;
import ca.ualberta.cmput301w15t13.R.menu;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * This activity is a copy of bogopic from the CMPUT 301 lab
 * Currently its main purpose is demonstration
 */
public class PrimitivePhotoActivity extends Activity {

	Uri imageFileUri;
	
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int PHOTO_WIDTH = 400;
	private static final int PHOTO_HEIGHT = 400;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.take_a_photo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}
	
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
		
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			
			if (resultCode == RESULT_OK) {
				TextView tv = (TextView) findViewById(R.id.textView3);
				tv.setText("Photo OK");
				ImageButton ib = (ImageButton) findViewById(R.id.expenseManagerPictureButton);
			
				Drawable pic = Drawable.createFromPath(imageFileUri.getPath());
				pic = resize(pic);
				ib.setImageDrawable(pic);
				
				
			}else if (resultCode == RESULT_CANCELED) {
				TextView tv = (TextView) findViewById(R.id.textView3);
				tv.setText("Photograph Cancelled");
				
				
			}else {
				
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
}
