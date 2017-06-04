package com.github.vnetallocator.model;

import java.io.Serializable;

public class NodeModel implements Serializable
{
    private static final long serialVersionUID = 5833593924596648861L;

    private int id;
    private String label;
    private double processingCapacity;
    private double amountOfMemory;
    private double amountOfDisk;

    public NodeModel(int id, String label, double processingCapacity, double amountOfMemory, double amountOfDisk)
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
