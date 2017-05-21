package com.github.vnetallocator.network;

import java.util.List;

import com.github.vnetallocator.network.link.PhysicalLink;

public class PhysicalNetwork
{
    private List<PhysicalLink> physicalLinks;

    public PhysicalNetwork(List<PhysicalLink> physicalLinks)
    {
	this.physicalLinks = physicalLinks;
    }

    public boolean allocateVirtualNetwork(VirtualNetwork virtualnetwork)
    {
	boolean wasAllocated = false;

	// TODO Alocar a rede virtual seguindo as regras de restrição

	return wasAllocated;
    }

    public List<PhysicalLink> getPhysicalLinks()
    {
	return physicalLinks;
    }
}
