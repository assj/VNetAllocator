package com.github.vnetallocator.network.node;

import java.io.Serializable;

public class Node implements Serializable
{
    private static final long serialVersionUID = 8957466252910422874L;
    
    protected int id;
    protected String label;
    protected double processingCapacity;//GHz
    protected double amountOfMemory;	//GB
    protected double amountOfDisk;	//GB

    public Node(int id, String label, double processingCapacity, double amountOfMemory, double amountOfDisk)
    {
	this.id = id;
	this.label = label;
	this.processingCapacity = processingCapacity;
	this.amountOfMemory = amountOfMemory;
	this.amountOfDisk = amountOfDisk;
    }
    
    public int getId()
    {
	return id;
    }
    
    public String getLabel()
    {
	return label;
    }
    
    public double getProcessingCapacity()
    {
	return processingCapacity;
    }
    
    public double getAmountOfMemory()
    {
	return amountOfMemory;
    }
    
    public double getAmountOfDisk()
    {
	return amountOfDisk;
    }
    
}
