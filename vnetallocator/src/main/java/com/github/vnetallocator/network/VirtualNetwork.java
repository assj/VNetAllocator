package com.github.vnetallocator.network;

import java.util.List;

import com.github.vnetallocator.network.link.VirtualLink;

public class VirtualNetwork
{
    private List<VirtualLink> virtualLink;

    public VirtualNetwork(List<VirtualLink> virtualLink)
    {
	this.virtualLink = virtualLink;
    }

    public List<VirtualLink> getVirtualLinks()
    {
	return virtualLink;
    }
}
