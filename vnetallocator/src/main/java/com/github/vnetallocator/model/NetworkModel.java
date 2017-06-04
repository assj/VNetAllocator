package com.github.vnetallocator.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.vnetallocator.network.PhysicalNetwork;
import com.github.vnetallocator.network.VirtualNetwork;
import com.github.vnetallocator.network.link.PhysicalLink;
import com.github.vnetallocator.network.link.VirtualLink;
import com.github.vnetallocator.network.node.PhysicalNode;
import com.github.vnetallocator.network.node.VirtualNode;

public class NetworkModel implements Serializable
{
    private static final long serialVersionUID = 3969698868001449682L;

    private List<NodeModel> nodes;
    private List<LinkModel> links;

    public NetworkModel(List<NodeModel> nodes, List<LinkModel> links)
    {
	this.nodes = nodes;
	this.links = links;
    }

    public VirtualNetwork persistVirtualNetwork()
    {
	VirtualNetwork virtualNetwork = null;

	Map<Integer, NodeModel> virtualNodeModelMap = new HashMap<Integer, NodeModel>();

	for (NodeModel nodeModel : this.nodes)
	{
	    virtualNodeModelMap.put(nodeModel.getId(), nodeModel);
	}

	List<VirtualLink> virtualLinks = new ArrayList<VirtualLink>(this.links.size());
	List<VirtualNode> virtualNodes = new ArrayList<VirtualNode>(this.nodes.size());

	for (LinkModel linkModel : this.links)
	{
	    VirtualNode node1 = new VirtualNode(virtualNodeModelMap.get(linkModel.getNode1()));
	    VirtualNode node2 = new VirtualNode(virtualNodeModelMap.get(linkModel.getNode2()));
	    int id = linkModel.getId();
	    double delay = linkModel.getDelay();
	    double speed = linkModel.getSpeed();

	    if (!virtualNodes.contains(node1))
	    {
		virtualNodes.add(node1);
	    }

	    if (!virtualNodes.contains(node2))
	    {
		virtualNodes.add(node2);
	    }

	    VirtualLink virtualLink = new VirtualLink(id, node1, node2, delay, speed);
	    virtualLinks.add(virtualLink);
	}

	virtualNetwork = new VirtualNetwork(virtualNodes, virtualLinks);

	return virtualNetwork;
    }

    public PhysicalNetwork persistPhysicalNetwork()
    {
	PhysicalNetwork physicalNetwork = null;

	Map<Integer, NodeModel> virtualNodeModelMap = new HashMap<Integer, NodeModel>();

	for (NodeModel nodeModel : this.nodes)
	{
	    virtualNodeModelMap.put(nodeModel.getId(), nodeModel);
	}

	List<PhysicalLink> physicalLinks = new ArrayList<PhysicalLink>(this.links.size());
	List<PhysicalNode> physicalNodes = new ArrayList<PhysicalNode>(this.nodes.size());

	for (LinkModel linkModel : this.links)
	{
	    PhysicalNode node1 = new PhysicalNode(virtualNodeModelMap.get(linkModel.getNode1()));
	    PhysicalNode node2 = new PhysicalNode(virtualNodeModelMap.get(linkModel.getNode2()));
	    int id = linkModel.getId();
	    double delay = linkModel.getDelay();
	    double speed = linkModel.getSpeed();

	    if (!physicalNodes.contains(node1))
	    {
		physicalNodes.add(node1);
	    }

	    if (!physicalNodes.contains(node2))
	    {
		physicalNodes.add(node2);
	    }

	    PhysicalLink virtualLink = new PhysicalLink(id, node1, node2, delay, speed);
	    physicalLinks.add(virtualLink);
	}

	physicalNetwork = new PhysicalNetwork(physicalNodes, physicalLinks);

	return physicalNetwork;
    }

    public List<NodeModel> getNodes()
    {
	return nodes;
    }

    public void setNodes(List<NodeModel> nodes)
    {
	this.nodes = nodes;
    }

    public List<LinkModel> getLinks()
    {
	return links;
    }

    public void setLinks(List<LinkModel> links)
    {
	this.links = links;
    }

}
