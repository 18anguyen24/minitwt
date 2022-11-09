package com.andrew3560;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JFrame;


public class AdminUnity implements ActionListener, TreeSelectionListener {
	
	private static String AppName = "Shady Mini Twitter";
	private static AdminUnity instance;
	private static JFrame frame;
	
	private JPanel layout;
	private JTextPane userTextBox;
	private JTextPane groupTextBox;
	
	private LinkedList<User> listUsers;
	private LinkedList<Group> listGroups;
	private HashMap<String, String> mapUsers;
	private HashMap<String, String> mapGroups;

	private TreePanel treeView;
	
	private AdminUnity(String title)
	{
		frame = new JFrame();
		frame.setTitle(title);
		frame.setSize(800, 450);
		frame.setLayout(new GridLayout(0, 2));
		frame.setResizable(false);

		treeView = new TreePanel();
		layout = new JPanel();
		listUsers = new LinkedList<>();
		listGroups = new LinkedList<>();
		mapUsers = new HashMap<>();
		mapGroups = new HashMap<>();

		//frame.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		//ayout.setForeGround(Color.BLUE)

		userTextBox = new JTextPane();
		groupTextBox = new JTextPane();
	}
	
	
	public static AdminUnity getInstance()
	{
		if(instance == null)
		{
			instance = new AdminUnity(AppName);
		}

		return instance;
	}

	public void reloadFrame(String name)
	{
		if(mapUsers.containsKey(name))
		{
			userTextBox.setText("ID: " + name);
			groupTextBox.setText("Group ID: ");
		}

		else if(mapGroups.containsKey(name))
		{
			groupTextBox.setText("Group ID: " + name);
			userTextBox.setText("ID: ");
		}

		frame.invalidate();
		frame.validate();
		frame.repaint();
	}
	
	private TreePanel treeStruc()
	{
		TreePanel gridPanel = new TreePanel();
		return gridPanel;
	}

	private JPanel controlPanel()
	{
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(3,0));
		
		gridPanel.add(topControlPanel());
		gridPanel.add(centerControlPanel());
		gridPanel.add(bottomControlPanel());
		
		return gridPanel;
	}
	
	private JPanel topControlPanel() {
		JButton button;
		
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(2,2));
		
		userTextBox = new JTextPane();
		userTextBox.setText("User ID");
		userTextBox.setEditable(false);
		gridPanel.add(userTextBox);		
		
		button = new JButton("Add User"); 
		button.addActionListener(this);
		gridPanel.add(button);
		
		groupTextBox = new JTextPane();
		groupTextBox.setText("Group ID");
		groupTextBox.setEditable(false);
		gridPanel.add(groupTextBox);
		
		button = new JButton("Add Group");
		button.addActionListener(this);
		gridPanel.add(button);
		return gridPanel;
	}

	private JPanel centerControlPanel() {
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(3,0));
		
		JButton button = new JButton("Open User View");
		button.addActionListener(this);
		gridPanel.add(button);
		
		return gridPanel;
	}
	
	private JPanel bottomControlPanel() {
		JButton button;
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(2,2));
		
		button = new JButton("Show User Total");
		button.addActionListener(this);
		gridPanel.add(button);
		
		button = new JButton("Show Group Total");
		button.addActionListener(this);
		gridPanel.add(button);
		
		button = new JButton("Show Messages Total");
		button.addActionListener(this);
		gridPanel.add(button);
		
		button = new JButton("Show Positive Percentage");
		button.addActionListener(this);
		gridPanel.add(button);
		
		return gridPanel;
	}

	
	public LinkedList<User> getUserList()
	{
		return listUsers;
	}
	
	private void addUser() 
	{
		String name = treeView.getPath().toString();
		if (mapUsers.containsKey(name))
		{
			JOptionPane.showMessageDialog(frame, "Cannot make group from user!");
		}
		else
		{
			User use = getUser("User");
			if (checkUserExists(use.getName()))
			{
				JOptionPane.showMessageDialog(frame, "User already exists! Try another name.");
			} else {
				listUsers.add(use);
				mapUsers.put(use.getName(), use.getId());
			}
		}
	}

	private User getUser(String type) {
		String input = JOptionPane.showInputDialog("Enter "+ type + " Name");

		DefaultMutableTreeNode node = treeView.getPath();

		if(type == "User")
		{
			if(checkUserExists(input))
			{
				System.out.println("User already exists! Try again.");
			}
			else
			{
				treeView.addNode(node, input);
			}
		}

		User user = new User();
		user.setName(input);
		user.setId(input);

		if (input == null)
		{
			System.out.println("User closed window with no input");
		}

		return user;
	}

	private Boolean checkUserExists(String input)
	{
		for(int i=0 ; i<listUsers.size() ; ++i)
		{
			if(input.equals(listUsers.get(i).getName()))
			{
				return true;
			}
		}
		return false;
	}
	
	private void addGroup() 
	{
		if(treeView.getPath()!= null)
		{
			String name = treeView.getPath().toString();
			if (mapUsers.containsKey(name))
			{
				JOptionPane.showMessageDialog(frame, "Cannot make group from user!");
			}
			else
			{
				Group newGroup = getGroup("Group");
				listGroups.add(newGroup);
				mapGroups.put(newGroup.getName(), newGroup.getId());
			}
		}
		
	}

	private Group getGroup(String type) {
		String input = JOptionPane.showInputDialog("Enter "+ type + " Group Name");

		DefaultMutableTreeNode node = treeView.getPath();

		if(type == "Group")
		{
			treeView.addNode(node, input);
		}

		Group newGroup = new Group();
		newGroup.setName(input);
		newGroup.setId(input);

		if (input == null)
		{
			System.out.println("User closed window with no input");
		}

		return newGroup;
	}
	
	private void openProfile() 
	{
		if(treeView.getPath()!= null)
		{
			String name = treeView.getPath().toString();

			if (mapUsers.containsKey(name))
			{
				User use = new User();

				for (int i = 0; i < listUsers.size(); ++i) {
					if (name.equals(listUsers.get(i).getName())) {
						use = listUsers.get(i);
					}
				}

				UserProfile profile = new UserProfile(use);
			}
			else
			{
				JOptionPane.showMessageDialog(frame, "Cannot view profile of a Group!");
			}

			

		}
		else
		{
			System.out.println("Not Selected");

		}
		
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch (action) {
		case "Add User":	
			addUser();
			break;
		case "Add Group":
			addGroup();
			break;
		case "Open User View":
			openProfile();
			break;
		case "Show User Total":
			JOptionPane.showMessageDialog(new JFrame(), "Total Users: " + listUsers.size());
			break;
		case "Show Group Total":
			JOptionPane.showMessageDialog(new JFrame(), "Total Groups: " + listGroups.size());
			break;
		case "Show Messages Total":
			JOptionPane.showMessageDialog(new JFrame(), "Total Messages: " + (UserTweets.getInstance().getTweetList().size()-1));
			break;
		case "Show Positive Percentage":
			JOptionPane.showMessageDialog(new JFrame(), "Positive Percentage: " + Message.getInstance().positiveNumWords());
			break;
		default:
			System.out.println("Error: Unknown Button Selected");
		}		
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) 
	{

	}

	public void openGate() {
		treeView = treeStruc();
		layout = controlPanel();
		
		frame.add(treeView);
		frame.add(layout);

		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
