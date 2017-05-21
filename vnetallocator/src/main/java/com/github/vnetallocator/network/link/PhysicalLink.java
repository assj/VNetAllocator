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
    
    public double getNormalizedRemainingCapacity()
    {
	double normalizedRemainingCapacity = 0;
	
	normalizedRemainingCapacity = this.remainingCapacity / this.capacity;
	
	return normalizedRemainingCapacity;
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
