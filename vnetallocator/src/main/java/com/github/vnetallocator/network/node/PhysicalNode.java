package com.github.vnetallocator.network.node;

import java.util.ArrayList;
import java.util.List;

import com.github.vnetallocator.model.NodeModel;

public class PhysicalNode extends Node
{
    private static final long serialVersionUID = 2922968498344292414L;
    
    private double remainingProcessingCapacity;
    private double remainingAmoutOfMemory;
    private double remainingAmountOfDisk;
    private List<VirtualNode> allocatedVirtualNodes;

    public PhysicalNode(NodeModel nodeModel)
    {
	super(nodeModel.getId(), nodeModel.getProcessingCapacity(), nodeModel.getAmountOfMemory(), nodeModel.getAmountOfDisk());
	this.remainingProcessingCapacity = nodeModel.getProcessingCapacity();
	this.remainingAmoutOfMemory = nodeModel.getAmountOfMemory();
	this.remainingAmountOfDisk = nodeModel.getAmountOfDisk();
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
    
    public void deallocateVirtualNode(VirtualNode virtualNode)
    {
	this.allocatedVirtualNodes.remove(virtualNode);
	this.remainingProcessingCapacity += virtualNode.getProcessingCapacity();
	this.remainingAmoutOfMemory += virtualNode.getAmountOfMemory();
	this.remainingAmountOfDisk += virtualNode.getAmountOfDisk();
	virtualNode.setPhysicalNodeIndex(-1);
	
    }
    
    public void allocateVirtualNode(VirtualNode virtualNode)
    {
	this.allocatedVirtualNodes.add(virtualNode);
	this.remainingProcessingCapacity -= virtualNode.getProcessingCapacity();
	this.remainingAmoutOfMemory -= virtualNode.getAmountOfMemory();
	this.remainingAmountOfDisk -= virtualNode.getAmountOfDisk();
    }
    
    public boolean canAllocateVirtualNode(VirtualNode virtualNode)
    {
	boolean canAllocateVirtualNode = false;
	
	if(this.remainingProcessingCapacity >= virtualNode.getProcessingCapacity() &&
	   this.remainingAmoutOfMemory >= virtualNode.getAmountOfMemory() &&
	   this.remainingAmountOfDisk >= virtualNode.getAmountOfDisk())
	{
	    canAllocateVirtualNode = true;
	}
	
	return canAllocateVirtualNode;
    }
    
    public int getId()
    {
	return id;
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
