package com.github.vnetallocator.network.link;

import java.io.Serializable;

import com.github.vnetallocator.network.node.Node;

public class Link implements Serializable
{
    private static final long serialVersionUID = -2220488306163840203L;
    
    protected int id;
    protected Node node1;
    protected Node node2;
    protected double delay; // ms
    protected double speed; // bps

    public Link(int id, Node node1, Node node2, double delay, double speed)
    {
	this.id = id;
	this.node1 = node1;
	this.node2 = node2;
	this.delay = delay;
	this.speed = speed;
    }
    
    public boolean containsNode(Node node)
    {
	boolean contains = false;
	
	if(this.node1.equals(node) || this.node2.equals(node))
	{
	    contains = true;
	}
	
	return contains;
    }
    
    public Node getAdjacentNode(Node node)
    {
	Node adjacentNode = null;
	
	if(containsNode(node))
	{
	    if(this.node1.equals(node))
	    {
		adjacentNode = this.node2;
	    }
	    else
	    {
		adjacentNode = this.node1;
	    }
	}
	
	return adjacentNode;
    }
    
    public int getId()
    {
	return id;
    }
    
    public Node getNode1()
    {
	return node1;
    }
    
    public Node getNode2()
    {
	return node2;
    }
    
    public double getDelay()
    {
	return delay;
    }
    
    public double getSpeed()
    {
	return speed;
    }

}
