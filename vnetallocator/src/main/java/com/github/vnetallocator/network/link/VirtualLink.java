package com.github.vnetallocator.network.link;

import com.github.vnetallocator.network.node.VirtualNode;

public class VirtualLink extends Link
{
    public VirtualLink(VirtualNode node1, VirtualNode node2, double delay)
    {
	super(node1, node2, delay);
    }

}
