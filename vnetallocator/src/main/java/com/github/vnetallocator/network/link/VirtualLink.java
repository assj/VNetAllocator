package com.github.vnetallocator.network.link;

import java.util.ArrayList;
import java.util.List;

import com.github.vnetallocator.network.node.VirtualNode;

public class VirtualLink extends Link
{
    private static final long serialVersionUID = -6297445859590987191L;
    
    private List<Integer> physicalLinksIndices;
    
    public VirtualLink(int id, VirtualNode node1, VirtualNode node2, double delay, double speed)
    {
	super(id, node1, node2, delay, speed);
	
	this.physicalLinksIndices = new ArrayList<Integer>();
    }
    
    public List<Integer> getPhysicalLinksIndices()
    {
	return physicalLinksIndices;
    }

}
