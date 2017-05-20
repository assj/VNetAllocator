package com.github.vnetallocator.network.link;

import com.github.vnetallocator.network.node.PhysicalNode;

public class PhysicalLink extends Link
{
    private VirtualLink[] allocatedVirtialLinks;
    private int capacity;
    private int remainingCapacity;
    
    public PhysicalLink(PhysicalNode node1, PhysicalNode node2, double delay, int capacity)
    {
	super(node1, node2, delay);
	this.allocatedVirtialLinks = new VirtualLink[capacity];
	this.capacity = capacity;
	this.remainingCapacity = capacity;
    }
    
    public boolean allocateVirtualLink(VirtualLink virtualLink)
    {
	boolean canAllocateVirtualLink = canAllocateVirtualLink();
	
	if(canAllocateVirtualLink)
	{
	    this.allocatedVirtialLinks[this.capacity - this.remainingCapacity] = virtualLink;
	    this.remainingCapacity--;
	}
	
	return canAllocateVirtualLink;
    }
    
    private boolean canAllocateVirtualLink()
    {
	boolean canAllocateVirtualLink = false;
	
	if(this.remainingCapacity > 0)
	{
	    canAllocateVirtualLink = true;
	}
	
	return canAllocateVirtualLink;
    }
    
    public VirtualLink[] getAllocatedVirtialLinks()
    {
	return allocatedVirtialLinks;
    }
    
    public int getCapacity()
    {
	return capacity;
    }
    
    public int getRemainingCapacity()
    {
	return remainingCapacity;
    }

}
