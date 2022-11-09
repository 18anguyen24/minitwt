package com.andrew3560;

import java.util.HashMap;
import java.util.LinkedList;

public class UserTweets {

	private static UserTweets instance;
	private LinkedList<String> twitterFeed;
	private HashMap<String, LinkedList<String>> userTweets;

	private UserTweets()
	{
		twitterFeed = new LinkedList<>();
	}

	public static UserTweets getInstance()
	{
		if(instance != null)
		{
			System.out.println("Instance Exists.");
		}
		else
		{
			instance = new UserTweets();
		}
		return instance;
	}

	public void addTweet(String tweet)
	{
		twitterFeed.add(tweet);
	}

	public LinkedList<String> getTweetList()
	{
		return twitterFeed;
	}

}