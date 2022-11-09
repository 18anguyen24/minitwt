package com.andrew3560;

public abstract class UserInfo {
	
	private String id;
	private String name;
	private int total;


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
}
