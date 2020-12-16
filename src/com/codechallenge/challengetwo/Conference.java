package com.codechallenge.challengetwo;

/**
 * POJO class for Conference.
 * 
 * @author Santhoshkumar.DS
 *
 */
public class Conference {
	private int conferenceId;
	private String duration;
	private String title;
	
	public Conference() {} 
	
	public Conference(int conferenceId, String duration, String title) {
		this.conferenceId = conferenceId;
		this.duration = duration;
		this.title = title;
	}
	public int getConferenceId() {
		return conferenceId;
	}
	public String getDuration() {
		return duration;
	}
	public String getTitle() {
		return title;
	}
	@Override
	public String toString() {
		return "Conference Id: " + this.conferenceId + ", Duration: " + this.duration + ", Title: " + this.title;
	}
}
