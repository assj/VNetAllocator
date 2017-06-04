package com.github.vnetallocator.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.github.vnetallocator.network.link.VirtualLink;
import com.github.vnetallocator.network.node.VirtualNode;

public class VirtualNetwork implements Serializable
{
    private static final long serialVersionUID = 6577505743478667817L;
    
    private List<VirtualNode> virtualNodes;
    private List<VirtualLink> virtualLinks;

    public VirtualNetwork(List<VirtualNode> virtualNodes, List<VirtualLink> virtualLinks)
    {
	this.virtualNodes = virtualNodes;
	this.virtualLinks = virtualLinks;
    }

    public List<VirtualNode> getAdjacentVirtualNodes(VirtualNode virtualNode)
    {
	List<VirtualNode> adjacentVirtualNodes = new ArrayList<VirtualNode>();
	
	int numOfVirtualLinks = this.virtualLinks.size();
	
	for(int i = 0 ; i < numOfVirtualLinks; i++)
	{
	    VirtualLink currentVirtualLink = this.virtualLinks.get(i);
	    
	    if(currentVirtualLink.containsNode(virtualNode))
	    {
		adjacentVirtualNodes.add((VirtualNode) currentVirtualLink.getAdjacentNode(virtualNode));
	    }
	}
	
	return adjacentVirtualNodes;
    }
    
    public List<VirtualLink> getVirtualNodeLinks(VirtualNode virtualNode)
    {
	List<VirtualLink> virtualLinks = new ArrayList<VirtualLink>();
	
	for(VirtualLink virtualLink: this.virtualLinks)
	{
	    if(virtualLink.containsNode(virtualNode))
	    {
		virtualLinks.add(virtualLink);
	    }
	}
	
	return virtualLinks;
    }
    
    public List<VirtualNode> getVirtualNodes()
    {
	return virtualNodes;
    }
    
    public List<VirtualLink> getVirtualLinks()
    {
	return virtualLinks;
    }
}
