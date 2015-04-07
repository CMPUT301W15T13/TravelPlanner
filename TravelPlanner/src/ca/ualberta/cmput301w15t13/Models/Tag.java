package ca.ualberta.cmput301w15t13.Models;


/**
 * This class models a Tag
 * object. Using the Tag model 
 * over an arrayList of Strings
 * is favorable as we can define Tag
 * operations more easily and 
 * map the same tag to a new claim.
 * 
 *  This should be used as a way
 *  of indicating or marking something
 *  about a paticular claim object
 *  
 *  Classes it works with:
 *  Claim, TagManager
 */
public class Tag {
	String tagName;

	public Tag(String tagName){
		this.tagName = tagName;
	}
	
	public String getTagName() {
		return this.tagName;
	}
	
	public void setTagName(String name) {
		if (name == "") {
			this.tagName = "Unnamed";
		} else {
			this.tagName = name;
		}
	}
	//Defines how a tag is displayed 
	//if using an ArrayAdapter<tag>
	@Override
	public String toString() {
		return (tagName);
	}
}
