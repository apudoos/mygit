package com.first.listbox;

public class ListData {

	public enum Status {
		NOTDONE, DONE
	};
	
	public final static String TITLE = "title";
	private String mTitle = new String();
	
	private Status mStatus = Status.NOTDONE;
	
	
	ListData(String title, Status status) {
		this.mTitle = title;
		this.mStatus = status;
	}
	
	public String getTitle() {
		return TITLE + mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}
	
	public Status getStatus() {
		return mStatus;
	}

	public void setStatus(Status status) {
		mStatus = status;
	}
	
	
}
