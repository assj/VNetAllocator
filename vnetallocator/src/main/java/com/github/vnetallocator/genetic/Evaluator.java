package com.github.vnetallocator.genetic;

import java.util.List;

import com.github.vnetallocator.network.link.PhysicalLink;
import com.github.vnetallocator.network.node.PhysicalNode;
import com.github.vnetallocator.utils.MathUtils;

public class Evaluator
{
    public static double evaluateIndividual(Individual individual)
    {
	double individualFitness = 0;
	
	double physicalNodesFitness = evaluateNodesAllocation(individual.getPhysicalNetwork().getPhysicalNodes());
	double physicalLinksFitness = evaluateLinksAllocation(individual.getPhysicalNetwork().getPhysicalLinks());
	
	individualFitness = physicalNodesFitness + physicalLinksFitness;
	
	return individualFitness;
    }

    private static double evaluateNodesAllocation(List<PhysicalNode> physicalNodes)
    {
	double physicalNodesFitness = 0;
	
	int physicalNodesSize = physicalNodes.size();
	double[] remainingResourcesArray = new double[physicalNodesSize];
	
	for(int i = 0; i < physicalNodesSize; i++)
	{
	    PhysicalNode currentPhysicalNode = physicalNodes.get(i);
	    remainingResourcesArray[i] = currentPhysicalNode.getNormalizedRemainingResources();
	}
	
	physicalNodesFitness = MathUtils.variance(remainingResourcesArray);
	
	return physicalNodesFitness;
    }
    
    private static double evaluateLinksAllocation(List<PhysicalLink> physicalLinks)
    {
	double physicalLinksFitness = 0;
	
	int physicalLinksSize = physicalLinks.size();
	double[] remainingCapacityArray = new double[physicalLinksSize];
	
	for(int i = 0; i < physicalLinksSize; i++)
	{
	    PhysicalLink currentPhysicalLink = physicalLinks.get(i);
	    remainingCapacityArray[i] = currentPhysicalLink.getNormalizedRemainingSpeed();
	}
	
	physicalLinksFitness = MathUtils.variance(remainingCapacityArray);
	
	return physicalLinksFitness;
    }
}
