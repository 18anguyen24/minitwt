package com.andrew3560;

import java.awt.GridLayout;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



public class TreePanel extends JPanel implements TreeSelectionListener
{
    private JTree tree;
    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode path;
    
	public TreePanel()
    {
        path = null;
        this.setLayout(new GridLayout(1,0));
        
        rootNode = new DefaultMutableTreeNode("Root");
        treeModel = new DefaultTreeModel(rootNode);
        tree = new JTree(treeModel);
       
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);
        tree.setShowsRootHandles(true);
        tree.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
	}

    private void setPath(DefaultMutableTreeNode node)
    {
        path = node;
    }

    public DefaultMutableTreeNode getPath()
    {
        return path;
    }
	
	public DefaultMutableTreeNode addNode(DefaultMutableTreeNode parent, String name)
    {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(name);
        if(parent != null)
        {
            treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
        }
        else
        {
            parent = rootNode;
        }
        return childNode;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e)
    {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		String selected = "" + tree.getLastSelectedPathComponent();

		if(node == null)
        {
			AdminUnity.getInstance().reloadFrame("User ID");
		}
        else
        {
			setPath(node);
			AdminUnity.getInstance().reloadFrame(selected);
		}
	}
	

}
