package ca.ualberta.cmput301w15t13.Models;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * This defines the basic structure and calls for a receipt
 */
public class Receipt {
	Uri receiptUri;

	public Receipt() {
		// TODO for project 
	}

	public Bitmap toBitMap() {
		// TODO for project 5
		// for now it is returning null
		return null;
	}
	  public void setReceiptUri(Uri newUri) {
		  receiptUri = newUri;
	  }
	  public Uri getReceiptUri() {
		  return receiptUri;
	  }

}
