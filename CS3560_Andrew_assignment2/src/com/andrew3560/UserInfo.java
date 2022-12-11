package com.andrew3560;
import java.text.DateFormat;   
import java.text.SimpleDateFormat;   
import java.util.Date;   

public abstract class UserInfo {
	
	private String id;
	private String name;
	private int total;

	private long creationTime;
	private long lastUpdated;

	public String getName()
	{
		return name;
	}

	public void setName(String text)
	{
		this.name = text;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}



	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}


	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public long getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(long lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}

