package ca.ualberta.cmput301w15t13.Models;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

/**
 * This class is represent 
 * the model containing a user-taken
 * photograph, ideally of a receipt.
 * It should be used to represent the
 * receipt to an expenseItem of a claim
 */
public class Receipt {
	Uri receiptUri;
	Bitmap receiptBitmap;

	public Receipt() {}
	
	/**
	 * This method handles setting the bitmap and will
	 * call a method to resize it
	 * @param newBitmap
	 */
	public void setBitmap(Bitmap newBitmap) {
		newBitmap = resize(newBitmap);
		receiptBitmap = newBitmap;
		return;
	}
	/**
	 * Simple getter
	 * @return
	 */
	public Bitmap getBitmap() {
		return receiptBitmap;
	}
	/**
	 * This method will convert an imageUri into a bitmap object
	 * @return
	 */
	public Bitmap toBitMap() {
		Drawable pic = Drawable.createFromPath(receiptUri.getPath());
		receiptBitmap =  ((BitmapDrawable)pic).getBitmap();
		receiptBitmap = Bitmap.createScaledBitmap(receiptBitmap, 100, 150, false);
		return receiptBitmap;
	}
	/**
	 * Simple Setter. Will call a method to set the Uri to a bitmap
	 * @param newUri
	 */
	  public void setReceiptUri(Uri newUri) {
		  receiptUri = newUri;
		  receiptBitmap = toBitMap();
	  }
	  /**
	   * Simple Getter
	   * @return
	   */
	  public Uri getReceiptUri() {
		  return receiptUri;
	  }
	/**
	 * This method will take a bitmap object and will resize it.
	 * this method is called automatically by some setters 
	 * @param image
	 * @return
	 */
	protected Bitmap resize(Bitmap image) {
		Bitmap bitmapResized = Bitmap.createScaledBitmap(image, 100, 150, false);
		return bitmapResized;
	}
	/**
	 * Simple getter returns an object that can be set to imageviews
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Drawable getDrawable(){
		return new BitmapDrawable(receiptBitmap);
	}
}
