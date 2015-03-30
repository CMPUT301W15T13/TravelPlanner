package ca.ualberta.cmput301w15t13.Models;


/**
 * This defines the basic functionality of a tag,
 * currently only including setters and getters
 * for the tag string.
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
		this.tagName = name;
	}
	
	@Override
	public String toString() {
		return (tagName);
	}
}
