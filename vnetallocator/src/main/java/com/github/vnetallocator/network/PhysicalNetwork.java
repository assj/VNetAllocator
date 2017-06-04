package com.github.vnetallocator.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.github.vnetallocator.network.link.PathInfo;
import com.github.vnetallocator.network.link.PhysicalLink;
import com.github.vnetallocator.network.link.VirtualLink;
import com.github.vnetallocator.network.node.PhysicalNode;
import com.github.vnetallocator.network.node.VirtualNode;
import com.github.vnetallocator.utils.RandomUtils;

public class PhysicalNetwork implements Serializable
{
    private static final long serialVersionUID = 7043705504884611544L;

    private List<PhysicalNode> physicalNodes;
    private List<PhysicalLink> physicalLinks;

    public PhysicalNetwork(List<PhysicalNode> physicalNodes, List<PhysicalLink> physicalLinks)
    {
	this.physicalNodes = physicalNodes;
	this.physicalLinks = physicalLinks;
    }

    public void randomlyAllocateVirtualNetwork(VirtualNetwork virtualnetwork)
    {
	for (VirtualNode virtualNode : virtualnetwork.getVirtualNodes())
	{
	    randomlyAllocateVirtualNode(virtualNode, virtualnetwork);
	}
    }

    public void randomlyAllocateVirtualNode(VirtualNode virtualNode, VirtualNetwork virtualNetwork)
    {
	int numOfPhysicalNodes = this.physicalNodes.size();
	List<Integer> physicalNodesCandidatesIndices = IntStream.range(0, numOfPhysicalNodes).boxed()
		.collect(Collectors.toList());

	for (int i = 0; i < numOfPhysicalNodes; i++)
	{
	    PhysicalNode currentPhysicalNode = this.physicalNodes.get(i);

	    if (currentPhysicalNode.getAllocatedVirtualNodes().contains(virtualNode))
	    {
		physicalNodesCandidatesIndices.remove(i);
		break;
	    }
	}

	List<VirtualNode> adjacentVirtualNodes = virtualNetwork.getAdjacentVirtualNodes(virtualNode);
	List<VirtualLink> virtualNodeLinks = virtualNetwork.getVirtualNodeLinks(virtualNode);
	int numOfAdjacentVirtualNodes = adjacentVirtualNodes.size();

	for (int i = 0; i < numOfAdjacentVirtualNodes; i++)
	{
	    VirtualNode curentAdjacentVirtualNode = adjacentVirtualNodes.get(i);
	    int currentPhysicalNodeIndex = curentAdjacentVirtualNode.getPhysicalNodeIndex();

	    if (currentPhysicalNodeIndex > -1)
	    {
		physicalNodesCandidatesIndices.remove(currentPhysicalNodeIndex);
	    }
	}

	boolean wasAllocated = false;
	int cont = 0;

	while (!wasAllocated && cont < physicalNodesCandidatesIndices.size())
	{
	    int physicalNodesCandidateIndex = RandomUtils.getRandomElement(physicalNodesCandidatesIndices);
	    wasAllocated = allocateAtIndex(virtualNode, physicalNodesCandidateIndex, virtualNodeLinks);

	    if (!wasAllocated)
	    {
		physicalNodesCandidatesIndices.remove(physicalNodesCandidateIndex);
	    }

	    cont++;
	}
    }

    private boolean allocateAtIndex(VirtualNode virtualNode, int index, List<VirtualLink> virtualLinks)
    {
	boolean wasRealocated = true;
	boolean canAllocate = true;
	PhysicalNode newPhysicalNode = this.physicalNodes.get(index);
	Map<PhysicalNode, VirtualLink> adjacentPhysicalNodeToVirtualLink = new HashMap<PhysicalNode, VirtualLink>();

	if (!newPhysicalNode.canAllocateVirtualNode(virtualNode))
	{
	    canAllocate = false;
	}
	else
	{
	    for (VirtualLink currentVirtualLink : virtualLinks)
	    {
		VirtualNode adjacentVirtualNode = (VirtualNode) currentVirtualLink.getAdjacentNode(virtualNode);

		PhysicalNode adjacentPhysicalNode = null;
		int adjacentVirtualNodePhysicalNodeIndex = adjacentVirtualNode.getPhysicalNodeIndex();

		if (adjacentVirtualNodePhysicalNodeIndex > -1)
		{
		    adjacentPhysicalNode = this.physicalNodes.get(adjacentVirtualNodePhysicalNodeIndex);
		}

		double currentDelay = currentVirtualLink.getDelay();
		double currentRequestedSpeed = currentVirtualLink.getSpeed();

		boolean canLinkNodes = canLinkNodes(newPhysicalNode, adjacentPhysicalNode, currentDelay,
			currentRequestedSpeed);

		if (!canLinkNodes)
		{
		    canAllocate = false;
		    break;
		}
		else if (adjacentPhysicalNode != null)
		{
		    adjacentPhysicalNodeToVirtualLink.put(adjacentPhysicalNode, currentVirtualLink);
		}
	    }
	}

	if (canAllocate)
	{
	    int oldPhysicalNodeIndex = virtualNode.getPhysicalNodeIndex();

	    // Remover o nó virtual de onde estava
	    if (oldPhysicalNodeIndex > -1)
	    {
		this.physicalNodes.get(oldPhysicalNodeIndex).deallocateVirtualNode(virtualNode);
	    }

	    // Remover os links virtuais antigos
	    deallocateVirtualLinks(virtualLinks);

	    // Alocar o nó virtual no novo lugar
	    this.physicalNodes.get(index).allocateVirtualNode(virtualNode);
	    virtualNode.setPhysicalNodeIndex(index);

	    // Alocar os links virtuais nos novos lugares
	    allocateVirtualLinks(newPhysicalNode, adjacentPhysicalNodeToVirtualLink);
	}

	wasRealocated = canAllocate;

	return wasRealocated;
    }

    private void deallocateVirtualLinks(List<VirtualLink> virtualLinks)
    {
	for (VirtualLink virtualLink : virtualLinks)
	{
	    List<Integer> physicalLinksIndices = virtualLink.getPhysicalLinksIndices();

	    for (int physicalLinkIndex : physicalLinksIndices)
	    {
		if (physicalLinkIndex > -1)
		{
		    this.physicalLinks.get(physicalLinkIndex).deallocateVirtualLink(virtualLink);
		}
	    }
	}

    }

    private void allocateVirtualLinks(PhysicalNode physicalNode,
	    Map<PhysicalNode, VirtualLink> adjacentPhysicalNodeToVirtualLink)
    {
	for (PhysicalNode adjacentPhysicalNode : adjacentPhysicalNodeToVirtualLink.keySet())
	{
	    PathInfo pathInfo = getShortestPathInfo(physicalNode, adjacentPhysicalNode);
	    VirtualLink currentVirtualLink = adjacentPhysicalNodeToVirtualLink.get(adjacentPhysicalNode);

	    for (PhysicalLink currentPhysicalLink : pathInfo.getPhysicalLinksInPath())
	    {
		int currentPhysicalLinkIndex = this.physicalLinks.indexOf(currentPhysicalLink);
		currentPhysicalLink.deallocateVirtualLink(currentVirtualLink);
		currentVirtualLink.getPhysicalLinksIndices().add(currentPhysicalLinkIndex);
	    }
	}

    }

    private boolean canLinkNodes(PhysicalNode physicalNode1, PhysicalNode physicalNode2, double limitDelay,
	    double requestedSpeed)
    {
	boolean canLinkNodes = true;

	if (physicalNode2 != null)
	{
	    PathInfo pathInfo = getShortestPathInfo(physicalNode1, physicalNode2);

	    if (pathInfo.getAggregatedLinksDelay() <= limitDelay)
	    {
		List<PhysicalLink> physicalLinksInPath = pathInfo.getPhysicalLinksInPath();

		for (PhysicalLink physicalLinkInPath : physicalLinksInPath)
		{
		    if (physicalLinkInPath.getRemainingSpeed() < requestedSpeed)
		    {
			canLinkNodes = false;
			break;
		    }
		}
	    }
	    else
	    {
		canLinkNodes = false;
	    }
	}

	return canLinkNodes;
    }

    private PathInfo getShortestPathInfo(PhysicalNode physicalNode1, PhysicalNode physicalNode2)
    {
	PathInfo pathInfo = null;

	int phisicalNode1Id = physicalNode1.getId();
	int phisicalNode2Id = physicalNode2.getId();
	File topologyFile = new File(getClass().getClassLoader().getResource("RNP.json").getFile());
	String topologyFilePath = topologyFile.getAbsolutePath();
	File routerFile = new File(getClass().getClassLoader().getResource("roteador2.py").getFile());
	String routerFilePath = routerFile.getAbsolutePath();

	String runtimeString = "python " + routerFilePath + " " + topologyFilePath + " " + phisicalNode1Id + " "
		+ phisicalNode2Id;

	Process process;

	try
	{
	    process = Runtime.getRuntime().exec(runtimeString);
	    BufferedReader bfReaderReturned = new BufferedReader(new InputStreamReader(process.getInputStream()));
	    String returnedString = bfReaderReturned.readLine();

	    returnedString.replaceAll("[", "");
	    returnedString.replaceAll("]", "");
	    returnedString.replaceAll(" ", "");

	    String[] splittedReturnedString = returnedString.split(",");

	    double aggregatedLinksDelay = Double.parseDouble(splittedReturnedString[0]);
	    List<PhysicalLink> physicalLinks = new ArrayList<PhysicalLink>();

	    for (int i = 1; i < splittedReturnedString.length; i++)
	    {
		int currentLinkIndex = Integer.parseInt(splittedReturnedString[i]);
		physicalLinks.add(this.physicalLinks.get(currentLinkIndex));
	    }

	    pathInfo = new PathInfo(aggregatedLinksDelay, physicalLinks);
	}
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return pathInfo;
    }

    public List<PhysicalNode> getPhysicalNodes()
    {
	return physicalNodes;
    }

    public List<PhysicalLink> getPhysicalLinks()
    {
	return physicalLinks;
    }
}
