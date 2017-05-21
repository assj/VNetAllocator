package com.github.vnetallocator.network.node;

public class Node implements Comparable<Node>
{
    protected int id;
    protected double processingCapacity;//GHz
    protected double amountOfMemory;	//MB
    protected double amountOfDisk;	//MB

    public Node(double processingCapacity, double amountOfMemory, double amountOfDisk)
    {
	this.processingCapacity = processingCapacity;
	this.amountOfMemory = amountOfMemory;
	this.amountOfDisk = amountOfDisk;
    }

    @Override
    public int compareTo(Node node)
    {
	int comparisonResult = 0;
	
	if(this.id > node.id)
	{
	    comparisonResult = 1;
	}
	else if(this.id < node.id)
	{
	    comparisonResult = -1;
	}
	
	return comparisonResult;
    }

}
