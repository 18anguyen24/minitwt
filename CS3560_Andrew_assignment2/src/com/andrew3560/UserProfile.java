package com.andrew3560;

import javax.swing.DefaultListModel;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JFrame;



public class UserProfile extends JFrame implements ActionListener
{

	private User user;

	private UserTweets twitFeed;

	private DefaultListModel tweets;
	private JScrollPane tweetsMini;
	
	private JList listTweets;
	private JTextPane tweetTextPane;

	private JList followList;
	private JScrollPane followsMini;
	private DefaultListModel followers;

	private int numAdmin;
	
	public UserProfile(User user)
	{
		this.user = user;

		tweets = new DefaultListModel();
		tweetsMini = new JScrollPane();
		listTweets = new JList();
		tweetTextPane = new JTextPane();
		twitFeed = UserTweets.getInstance();

		followers = new DefaultListModel();
		followsMini = new JScrollPane();
		followList = new JList();

		numAdmin = 0;
		setTitle("@" + user.getName());
		setSize(400, 600);
		setResizable(false);
		addPanel();
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(2, 0));
		setVisible(true);

	}

	private void addPanel()
	{
		this.add(topPanel());
		this.add(bottomPanel());
	}

	private JPanel topPanel()
	{
		JPanel userPanel = new JPanel();
		JButton button;
		userPanel.setLayout(new GridBagLayout());
		GridBagConstraints gridConstraintLayout = new GridBagConstraints();


		JTextPane textPane = new JTextPane();
		textPane.setText("User ID handle: @" + user.getId());
		textPane.setEditable(false);
		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		gridConstraintLayout.fill = GridBagConstraints.HORIZONTAL;
		gridConstraintLayout.ipadx = 125;
		gridConstraintLayout.gridx = 0;
		gridConstraintLayout.gridy = 0;
		userPanel.add(textPane, gridConstraintLayout);

		button = new JButton("Follow User");
		button.addActionListener(this);
		gridConstraintLayout.fill = GridBagConstraints.HORIZONTAL;
		gridConstraintLayout.weightx = 0.5;
		gridConstraintLayout.gridx = 1;
		gridConstraintLayout.gridy = 0;
		userPanel.add(button, gridConstraintLayout);


		followers.addElement("[FOLLOWING]");

		for(int i=0 ; i < AdminUnity.getInstance().getUserList().size() ; ++i)
		{
			TreePanel tree = new TreePanel();
			if(user.getName() != AdminUnity.getInstance().getUserList().get(i).getName()) {
				followers.addElement(AdminUnity.getInstance().getUserList().get(i).getName());
			}
		}

		followList = new JList(followers);
		followsMini.setViewportView(followList);
		followList.setLayoutOrientation(JList.VERTICAL);
		gridConstraintLayout.fill = GridBagConstraints.BOTH;
		gridConstraintLayout.ipady = 160;
		gridConstraintLayout.weightx =1;
		gridConstraintLayout.weighty = 5;
		gridConstraintLayout.gridwidth = 3;
		gridConstraintLayout.gridx = 0;
		gridConstraintLayout.gridy = 1;
		userPanel.add(followsMini, gridConstraintLayout);

		return userPanel;

	}

	private JPanel bottomPanel()
	{
		JPanel userPanel = new JPanel();
		JButton button;
		userPanel.setLayout(new GridBagLayout());
		GridBagConstraints gridConstraintLayout = new GridBagConstraints();

		tweetTextPane.setText("Tweet Message...");
		gridConstraintLayout.fill = GridBagConstraints.HORIZONTAL;
		gridConstraintLayout.weightx =50;
		gridConstraintLayout.weighty = 50;
		gridConstraintLayout.ipadx = 200;
		gridConstraintLayout.ipady = 200;
		gridConstraintLayout.gridx = 0;
		gridConstraintLayout.gridy = 0;
		userPanel.add(tweetTextPane, gridConstraintLayout);

		button = new JButton("Post Tweet");
		button.addActionListener(this);
		gridConstraintLayout.fill = GridBagConstraints.HORIZONTAL;
		gridConstraintLayout.weightx = 0.5;
		gridConstraintLayout.ipadx = 9;
		gridConstraintLayout.ipady = 35;

		gridConstraintLayout.gridx = 1;
		gridConstraintLayout.gridy = 0;
		userPanel.add(button, gridConstraintLayout);

		tweets.addElement("Twitter Feed:");
		for(int i = 0; i < twitFeed.getTweetList().size() - 1; i++)
		{
			tweets.addElement(twitFeed.getTweetList().get(i));
		}
		listTweets = new JList(tweets);
		tweetsMini.setViewportView(listTweets);
		listTweets.setLayoutOrientation(JList.VERTICAL);
		gridConstraintLayout.fill = GridBagConstraints.BOTH;
		gridConstraintLayout.ipady = 160;
		gridConstraintLayout.weightx =1;
		gridConstraintLayout.weighty = 5;
		gridConstraintLayout.gridwidth = 3;
		gridConstraintLayout.gridx = 0;
		gridConstraintLayout.gridy = 1;
		userPanel.add(tweetsMini, gridConstraintLayout);

		return userPanel;
	}



	@Override
	public void actionPerformed(ActionEvent e)
	{
		String action = e.getActionCommand();

		if(action=="Post Tweet")
		{

			followers.clear();
			followers.addElement("[FOLLOWING]");

			for(int i=0 ; i<AdminUnity.getInstance().getUserList().size() ; ++i)
			{
				TreePanel tree = new TreePanel();
				if(user.getName() != AdminUnity.getInstance().getUserList().get(i).getName())
				{
					followers.addElement(AdminUnity.getInstance().getUserList().get(i).getName());
				}
			}

			tweets.removeAllElements();
			twitFeed.addTweet(user.getName() + ": " + tweetTextPane.getText());
			Message.getInstance().tweetRead(tweetTextPane.getText());

			for(int i = 0 ; i < twitFeed.getTweetList().size() ; ++i)
			{
				tweets.addElement(twitFeed.getTweetList().get(i));
			}

			this.repaint();
			tweetTextPane.setText("");
		}
		else if(action == "Follow User")
		{
			if(numAdmin==0)
			{
				JOptionPane.showMessageDialog(new JFrame(), "You have followed "+ user.getName());
				followers.addElement("Admin");
				numAdmin++;
			}
			else
			{
				JOptionPane.showMessageDialog(new JFrame(), "You have already followed "+ user.getName(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}




	}


}