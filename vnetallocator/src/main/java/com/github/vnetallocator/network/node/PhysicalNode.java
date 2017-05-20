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

    public boolean allocateVirtualNode(VirtualNode virtualNode)
    {
	boolean canAllocateVirtualNode = canAllocateVirtualNode(virtualNode);
	
	if (canAllocateVirtualNode)
	{
	    this.allocatedVirtualNodes.add(virtualNode);
	    this.remainingProcessingCapacity -= virtualNode.processingCapacity;
	    this.remainingAmoutOfMemory -= virtualNode.amountOfMemory;
	    this.remainingAmountOfDisk -= virtualNode.amountOfDisk;
	}
	
	return canAllocateVirtualNode;
    }

    private boolean canAllocateVirtualNode(VirtualNode virtualNode)
    {
	boolean canAllocateVirtualNode = false;

	if (virtualNode != null)
	{
	    double possibleRemainingProcessingCapacity = this.remainingProcessingCapacity
		    - virtualNode.processingCapacity;
	    double possibleRemainingAmountOfMemory = this.remainingAmoutOfMemory - virtualNode.amountOfMemory;
	    double possibleRemainingAmountOfDisk = this.remainingAmountOfDisk - virtualNode.amountOfDisk;

	    if (possibleRemainingProcessingCapacity >= 0 && possibleRemainingAmountOfMemory >= 0
		    && possibleRemainingAmountOfDisk >= 0)
	    {
		canAllocateVirtualNode = true;
	    }
	}

	return canAllocateVirtualNode;

    }

    public double getNormalizedRemainingCapacity()
    {
	double normalizedRemainingCapacity = 0;

	double normalizedRemainingProcessingCapacity = this.remainingProcessingCapacity / this.processingCapacity;
	double normalizedRemainingMemoryAmount = this.remainingAmoutOfMemory / this.amountOfMemory;
	double normalizedRemainingDiskAmount = this.remainingAmountOfDisk / this.amountOfDisk;

	normalizedRemainingCapacity = (normalizedRemainingProcessingCapacity + normalizedRemainingMemoryAmount
		+ normalizedRemainingDiskAmount) / 3;

	return normalizedRemainingCapacity;
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
}
