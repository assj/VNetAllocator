package com.github.vnetallocator.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.vnetallocator.network.PhysicalNetwork;
import com.github.vnetallocator.network.link.PhysicalLink;
import com.github.vnetallocator.network.node.PhysicalNode;

public class Individual
{
    private PhysicalNetwork network;
    private double fitness;
    
    public Individual(PhysicalNetwork network)
    {
	this.network = network;
	this.fitness = Evaluator.evaluateIndividual(this);
    }
    
    public List<PhysicalNode> getPhysicalNodes()
    {
	List<PhysicalNode> physicalNodes = new ArrayList<PhysicalNode>();
	
	List<PhysicalLink> physicalLinks = this.network.getPhysicalLinks();
	int physicalLinksSize = physicalLinks.size();
	
	for(int i = 0; i < physicalLinksSize; i++)
	{
	    PhysicalLink currentLink = physicalLinks.get(i);
	    physicalNodes.add((PhysicalNode) currentLink.getNode1());
	    physicalNodes.add((PhysicalNode) currentLink.getNode2());
	}
	
	Collections.sort(physicalNodes);
	
	return physicalNodes;
    }
    
    public PhysicalNetwork getNetwork()
    {
	return network;
    }
    
    public double getFitness()
    {
	return fitness;
    }
}
