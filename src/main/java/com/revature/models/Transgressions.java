package com.revature.models;

public class Transgressions{
	
	private String note;
	private int mispellings;
	private String username;
    
    public Transgressions(String note, int mispellings, String username) {
    	this.note = note;
    	this.mispellings = mispellings;
    	this.username = username;
    }
    
    public String getUsername() {
    	return username;
    }
    
    public String getNote() {
    	return note;
    }
    
    public int getMispellings() {
    	return mispellings;
    }

}
