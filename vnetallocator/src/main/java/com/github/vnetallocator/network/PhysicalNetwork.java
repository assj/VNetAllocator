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

    public boolean randomlyAllocateVirtualNetwork(VirtualNetwork virtualnetwork)
    {
	boolean wasAllocated = true;

	for (int i = 0; i < virtualnetwork.getVirtualNodes().size(); i++)
	{
	    VirtualNode virtualNode = virtualnetwork.getVirtualNodes().get(i);
	    wasAllocated = randomlyAllocateVirtualNode(virtualNode, virtualnetwork);

	    if (!wasAllocated)
	    {
		break;
	    }
	}

	return wasAllocated;
    }

    public boolean randomlyAllocateVirtualNode(VirtualNode virtualNode, VirtualNetwork virtualNetwork)
    {
	int numOfPhysicalNodes = this.physicalNodes.size();
	List<Integer> physicalNodesCandidatesIndices = IntStream.range(0, numOfPhysicalNodes).boxed()
		.collect(Collectors.toList());

	for (int i = 0; i < numOfPhysicalNodes; i++)
	{
	    PhysicalNode currentPhysicalNode = this.physicalNodes.get(i);

	    if (currentPhysicalNode.getAllocatedVirtualNodes().contains(virtualNode))
	    {
		physicalNodesCandidatesIndices.remove(Integer.valueOf(i));
		break;
	    }
	}

	List<VirtualNode> virtualNodes = virtualNetwork.getVirtualNodes();
	List<VirtualLink> virtualNodeLinks = virtualNetwork.getVirtualNodeLinks(virtualNode);
	int numOfVirtualNodes = virtualNodes.size();

	for (int i = 0; i < numOfVirtualNodes; i++)
	{
	    VirtualNode currentVirtualNode = virtualNodes.get(i);
	    int currentPhysicalNodeIndex = currentVirtualNode.getPhysicalNodeIndex();

	    if (currentPhysicalNodeIndex > -1)
	    {
		physicalNodesCandidatesIndices.remove(Integer.valueOf(currentPhysicalNodeIndex));
	    }
	}

	boolean wasAllocated = false;

	while (!wasAllocated && !physicalNodesCandidatesIndices.isEmpty())
	{
	    int physicalNodesCandidateIndex = RandomUtils.getRandomIntegerElement(physicalNodesCandidatesIndices);
	    wasAllocated = allocateAtIndex(virtualNode, physicalNodesCandidateIndex, virtualNodeLinks);

	    if (!wasAllocated)
	    {
		physicalNodesCandidatesIndices.remove(Integer.valueOf(physicalNodesCandidateIndex));
	    }
	}

	return wasAllocated;
    }

    private boolean allocateAtIndex(VirtualNode virtualNode, int index, List<VirtualLink> virtualLinks)
    {
	boolean wasRealocated = true;
	boolean canAllocate = true;
	PhysicalNode newPhysicalNode = this.physicalNodes.get(index);
	Map<PhysicalNode, VirtualLink> adjacentPhysicalNodeToVirtualLink = new HashMap<PhysicalNode, VirtualLink>();
	List<PathInfo> possiblePaths = null;

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

		possiblePaths = possiblePaths(newPhysicalNode, adjacentPhysicalNode, currentDelay,
			currentRequestedSpeed);

		if (possiblePaths.isEmpty())
		{
		    if (adjacentPhysicalNode != null)
		    {
			canAllocate = false;
			break;
		    }
		}
		else
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
	    List<Integer> physicalLinksIndices = new ArrayList<Integer>(virtualLink.getPhysicalLinksIndices());

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
	    VirtualLink currentVirtualLink = adjacentPhysicalNodeToVirtualLink.get(adjacentPhysicalNode);

	    List<PathInfo> possiblePaths = possiblePaths(physicalNode, adjacentPhysicalNode,
		    currentVirtualLink.getDelay(), currentVirtualLink.getSpeed());

	    if (!possiblePaths.isEmpty())
	    {
		PathInfo pathInfo = RandomUtils.getRandomPathInfoElement(possiblePaths);

		for (PhysicalLink currentPhysicalLink : pathInfo.getPhysicalLinksInPath())
		{
		    int currentPhysicalLinkIndex = this.physicalLinks.indexOf(currentPhysicalLink);
		    currentPhysicalLink.allocateVirtualLink(currentVirtualLink);
		    currentVirtualLink.getPhysicalLinksIndices().add(currentPhysicalLinkIndex);
		}
	    }
	}
    }

    private List<PathInfo> possiblePaths(PhysicalNode physicalNode1, PhysicalNode physicalNode2, double limitDelay,
	    double requestedSpeed)
    {
	List<PathInfo> possiblePaths = new ArrayList<PathInfo>();

	if (physicalNode2 != null)
	{
	    List<PathInfo> pathInfoList = getShortestPathInfo(physicalNode1, physicalNode2);
	    int canLinkCont = 0;

	    for (PathInfo pathInfo : pathInfoList)
	    {
		if (pathInfo.getAggregatedLinksDelay() <= limitDelay)
		{
		    List<PhysicalLink> physicalLinksInPath = pathInfo.getPhysicalLinksInPath();

		    for (PhysicalLink physicalLinkInPath : physicalLinksInPath)
		    {
			if (physicalLinkInPath.getRemainingSpeed() > requestedSpeed)
			{
			    canLinkCont++;
			}
		    }

		    if (canLinkCont == physicalLinksInPath.size())
		    {
			possiblePaths.add(pathInfo);
		    }
		}
		
		canLinkCont = 0;
	    }
	}

	return possiblePaths;
    }

    private List<PathInfo> getShortestPathInfo(PhysicalNode physicalNode1, PhysicalNode physicalNode2)
    {
	List<PathInfo> pathInfoList = new ArrayList<PathInfo>();

	int phisicalNode1Id = physicalNode1.getId();
	int phisicalNode2Id = physicalNode2.getId();
	File topologyFile = new File(getClass().getClassLoader().getResource("RNP.json").getFile());
	String topologyFilePath = topologyFile.getAbsolutePath();
	File bestRouteFinderFile = new File(getClass().getClassLoader().getResource("best_route_finder.py").getFile());
	String bestRouteFinderFilePath = bestRouteFinderFile.getAbsolutePath();

	String[] runtimeStringArray = new String[] { "python", bestRouteFinderFilePath, topologyFilePath,
		String.valueOf(phisicalNode1Id), String.valueOf(phisicalNode2Id) };

	Process process;

	try
	{
	    process = Runtime.getRuntime().exec(runtimeStringArray);
	    BufferedReader bfReaderReturn = new BufferedReader(new InputStreamReader(process.getInputStream()));

	    List<String> returns = new ArrayList<String>();

	    bfReaderReturn.readLine();

	    while (bfReaderReturn.ready())
	    {
		String returnedString = bfReaderReturn.readLine();
		returnedString = returnedString.substring(1);
		returnedString = returnedString.substring(0, returnedString.length() - 1);

		returns.add(returnedString);
	    }

	    bfReaderReturn.close();

	    for (String returnedString : returns)
	    {
		String[] splittedReturnedString = returnedString.split(",");
		double aggregatedLinksDelay = Double.parseDouble(splittedReturnedString[0].trim());
		List<PhysicalLink> physicalLinks = new ArrayList<PhysicalLink>();

		for (int i = 1; i < splittedReturnedString.length; i++)
		{
		    int currentLinkId = Integer.parseInt(splittedReturnedString[i].trim());
		    physicalLinks.add(getLinkWithId(currentLinkId));
		}

		PathInfo pathInfo = new PathInfo(aggregatedLinksDelay, physicalLinks);
		pathInfoList.add(pathInfo);
	    }
	}
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return pathInfoList;
    }

    private PhysicalLink getLinkWithId(int linkId)
    {
	PhysicalLink physicalLink = null;

	for (PhysicalLink currentPhysicalLink : this.physicalLinks)
	{
	    if (currentPhysicalLink.getId() == linkId)
	    {
		physicalLink = currentPhysicalLink;
	    }
	}

	return physicalLink;
    }

    @Override
    public String toString()
    {
	StringBuilder stringBuilder = new StringBuilder();

	for (PhysicalNode physicalNode : this.physicalNodes)
	{
	    stringBuilder.append("No " + "\"" + physicalNode.getLabel() + "\"" + ": ");
	    stringBuilder.append(100 * physicalNode.getNormalizedRemainingResources() + "%\n");
	}

	stringBuilder.append("\n");

	for (PhysicalLink physicalLink : this.physicalLinks)
	{
	    stringBuilder.append("Link " + physicalLink.getId() + ": ");
	    stringBuilder.append(100 * physicalLink.getNormalizedRemainingSpeed() + "%\n");
	}

	return stringBuilder.toString();
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
