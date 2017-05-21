package com.github.vnetallocator.network.node;

import java.util.ArrayList;
import java.util.List;

public class PhysicalNode extends Node
{
    private double remainingProcessingCapacity;
    private double remainingAmoutOfMemory;
    private double remainingAmountOfDisk;
    private List<VirtualNode> allocatedVirtualNodes;

    public PhysicalNode(double processingCapacity, double amountOfMemory, double amountOfDisk)
    {
	super(processingCapacity, amountOfMemory, amountOfDisk);
	this.remainingProcessingCapacity = processingCapacity;
	this.remainingAmoutOfMemory = amountOfMemory;
	this.remainingAmountOfDisk = amountOfDisk;
	this.allocatedVirtualNodes = new ArrayList<VirtualNode>();
    }

    public double getNormalizedRemainingResources()
    {
	double normalizedRemainingResources = 0;

	double normalizedRemainingProcessingCapacity = this.remainingProcessingCapacity / this.processingCapacity;
	double normalizedRemainingMemoryAmount = this.remainingAmoutOfMemory / this.amountOfMemory;
	double normalizedRemainingDiskAmount = this.remainingAmountOfDisk / this.amountOfDisk;

	normalizedRemainingResources = (normalizedRemainingProcessingCapacity + normalizedRemainingMemoryAmount
		+ normalizedRemainingDiskAmount) / 3;

	return normalizedRemainingResources;
    }

    public double getRemainingProcessingCapacity()
    {
	return remainingProcessingCapacity;
    }

    public double getRemainingAmoutOfMemory()
    {
	return remainingAmoutOfMemory;
    }

    public double getRemainingAmountOfDisk()
    {
	return remainingAmountOfDisk;
    }
    
    public List<VirtualNode> getAllocatedVirtualNodes()
    {
	return allocatedVirtualNodes;
    }
}
