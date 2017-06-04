package com.github.vnetallocator.network.link;

import java.util.ArrayList;
import java.util.List;

import com.github.vnetallocator.network.node.PhysicalNode;

public class PhysicalLink extends Link
{
    private static final long serialVersionUID = 3901466275968362556L;
    
    private List<VirtualLink> allocatedVirtialLinks;
    private double remainingSpeed;
    
    public PhysicalLink(int id, PhysicalNode node1, PhysicalNode node2, double delay, double speed)
    {
	super(id, node1, node2, delay, speed);
	this.allocatedVirtialLinks = new ArrayList<VirtualLink>();
	this.remainingSpeed = speed;
    }

    public double getNormalizedRemainingSpeed()
    {
	double normalizedRemainingSpeed = 0;
	
	normalizedRemainingSpeed = this.remainingSpeed / this.speed;
	
	return normalizedRemainingSpeed;
    }
    
    public void deallocateVirtualLink(VirtualLink virtualLink)
    {
	this.allocatedVirtialLinks.remove(virtualLink);
	this.remainingSpeed += virtualLink.getSpeed();
	virtualLink.getPhysicalLinksIndices().clear();
	
    }
    
    public void allocateVirtualLink(VirtualLink virtualLink)
    {
	this.allocatedVirtialLinks.add(virtualLink);
	this.remainingSpeed -= virtualLink.getSpeed();
    }
    
    public List<VirtualLink> getAllocatedVirtialLinks()
    {
	return allocatedVirtialLinks;
    }
    
    public double getRemainingSpeed()
    {
	return remainingSpeed;
    }

}
