package ca.ualberta.cmput301w15t13.test;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.LoginActivity;

public class ClaimTagsTest extends ActivityInstrumentationTestCase2<LoginActivity> {

	public ClaimTagsTest() {
		super(LoginActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Tests that you're able to add a tag to a claim
	 * and that the tag MUST be alphanumeric
	 * US03.01.01
	 */
	
	public void testAddTag(){
		Claim claim = new Claim("Name", new Date(1), new Date(2));
		String[] validTags = {"valid","VALID", "a1", "HOr3to"},
				invalidTags = {"",  " ", "aoeu ", "a-", "-*&", "1.23"};
		
		for(String vtag: validTags){
			claim.addTag(vtag);
			assertTrue("Valid tag wasn't added", claim.getTags().contains(vtag));
		}
		String[] tags = claim.getTaigs();
		assertTrue("Tags is null", tags != null);
		assertTrue("Not all tags added", tags.length == validTags.length);
		
		claim = new Claim("Name", new Date(1), new Date(2));
		for(String itag: invalidTags){
			claim.addTag(itag);
			assertFalse("inValid tag was added", claim.getTags().contains(itag));
		}

	}
	
	/**
	 * Test that you can remove a tag
	 * US03.01.02
	 */
	public void testRemoveTag(){
		Claim claim = new Claim("Name", new Date(1), new Date(2));
		String tag = "test";
		
		claim.addTag(tag);
		assertTrue("Claim doesnt contain tag", claim.getTags().contains(tag));
		
		claim.removeTag(tag);
		assertFalse("Tag was not removed", claim.getTags().contains(tag));

	}
	
	/**
	 * Test that you cannot add duplicate tags
	 * US03.01.03
	 */
	
	public void testNoDuplicates(){
		Claim claim = new Claim("Name", new Date(1), new Date(2));
		String[] validTags = {"valid","VALID", "a1", "HOr3to"},
		
		for(String vtag: validTags){
			claim.addTag(vtag);
			try{
				claim.addTag(vtag);
				fail("Tag added twice");
			}	catch (DuplicateException e){
				
			}
		}

	}

}
