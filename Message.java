package com.andrew3560;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 

public class Message
{
	
	private int positiveWords;
	private static Message instance;
		
	private Message()
	{
		positiveWords = 0;
	}
	
	public static Message getInstance()
	{
		if(instance==null)
		{
			instance = new Message();
		}
		return instance;
	}

	public void findNumPositive(String word)
	{
		File file = new File("/com/andrew3560/positive-words.txt");
		try
		{
			Scanner scan = new Scanner(file);

			while (scan.hasNextLine())
			{
				String temp = word.toLowerCase();
				if(temp.equals(scan.nextLine()))
				{
					positiveWords++;
				}
			}

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	public int positiveNumWords()
	{
		return positiveWords;
	}
	
	public void tweetRead(String tweet) {
		String[] word = tweet.split(" ");
		for (String t : word) {
			findNumPositive(t);
		}
	}
	
}
