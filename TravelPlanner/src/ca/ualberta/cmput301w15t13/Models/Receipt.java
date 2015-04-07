package ca.ualberta.cmput301w15t13.Models;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

/**
 * This defines the basic structure and calls for a receipt
 */
public class Receipt {
	Uri receiptUri;
	Bitmap receiptBitmap;

	public Receipt() {
		// TODO for project 
	}
	public void setBitmap(Bitmap newBitmap) {
		newBitmap = resize(newBitmap);
		receiptBitmap = newBitmap;
		return;
	}
	public Bitmap getBitmap() {
		return receiptBitmap;
	}

	public Bitmap toBitMap() {
		Drawable pic = Drawable.createFromPath(receiptUri.getPath());
		receiptBitmap =  ((BitmapDrawable)pic).getBitmap();
		receiptBitmap = Bitmap.createScaledBitmap(receiptBitmap, 100, 150, false);
		return receiptBitmap;
	}
	  public void setReceiptUri(Uri newUri) {
		  receiptUri = newUri;
		  receiptBitmap = toBitMap();
	  }
	  public Uri getReceiptUri() {
		  return receiptUri;
	  }
	protected Bitmap resize(Bitmap image) {
		Bitmap bitmapResized = Bitmap.createScaledBitmap(image, 100, 150, false);
		return bitmapResized;
	}
	@SuppressWarnings("deprecation")
	public Drawable getDrawable(){
		return new BitmapDrawable(receiptBitmap);
	}
}
