package ca.ualberta.cmput301w15t13.test;

import java.util.Date;

import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.LoginActivity;

public class ExpenseRepeiptTest extends ActivityInstrumentationTestCase2<LoginActivity> {

	public ExpenseRepeiptTest(String name) {
		super(LoginActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Test that you can add a bitmap to a claim
	 * US06.01.01
	 */
	
	public void testAddBitmap(){
		Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888 );
		Claim claim = new Claim("Name", new Date(1), new Date(2));
		
		claim.addReceipt(bitmap);
		Bitmap returnedBitmap = claim.getReceipt();
		
		assertTrue("Bitmap is null", returnedBitmap != null);
		assertEquals("Bitmap has been changed", bitmap, returnedBitmap);
		
	}	
	
	
	/**
	 * Test that you can delete a bitmap from a claim
	 * US06.03.01
	 */
	
	public void testRemoveBitmap(){
		Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888 );
		Bitmap returnedBitmap;
		Claim claim = new Claim("Name", new Date(1), new Date(2));
		
		claim.addReceipt(bitmap);
		returnedBitmap = claim.getReceipt();
		assertEquals("Bitmap has been changed", bitmap ,returnedBitmap);

		claim.removeReceipt(bitmap);
		returnedBitmap = claim.getReceipt();
		assertEquals("Bitmap was not removed", null, returnedBitmap);
		
	}
	
	/**
	 * Test that you bitmaps are compressed before they're stored
	 * US06.04.01
	 */
	
	public void testLargeBitmap(){
		Bitmap bitmapLarge = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888 );
		
		Claim claim = new Claim("Name", new Date(1), new Date(2));
		
		claim.addReceipt(bitmapLarge);
		Bitmap returnedBitmap = claim.getReceipt();
		if(returnedBitmap.getByteCount() > 65536){
			fail("Addded a bitmap too large");
		}
		assertFalse("Bitmap wasn't modified", bitmapLarge == returnedBitmap);
	}

}
