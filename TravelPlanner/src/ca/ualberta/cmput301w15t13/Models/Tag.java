package ca.ualberta.cmput301w15t13.Models;

import java.util.Random;
import java.util.SortedSet;
import java.util.UUID;

/*
 * This defines the basic functionality of a tag
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
}
