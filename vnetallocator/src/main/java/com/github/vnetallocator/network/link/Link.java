package com.github.vnetallocator.network.link;

import com.github.vnetallocator.network.node.Node;

public class Link
{
    protected Node node1;
    protected Node node2;
    protected double delay; // ms

    public Link(Node node1, Node node2, double delay)
    {
	this.node1 = node1;
	this.node2 = node2;
	this.delay = delay;
    }
    
    public Node getNode1()
    {
	return node1;
    }
    
    public Node getNode2()
    {
	return node2;
    }

}
