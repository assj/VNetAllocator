package com.github.vnetallocator.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.SerializationUtils;

import com.github.vnetallocator.network.PhysicalNetwork;
import com.github.vnetallocator.network.VirtualNetwork;
import com.github.vnetallocator.network.node.VirtualNode;

public class GeneticAlgorithm
{
    private Population population;
    private int populationSize;
    private double mutationRate;
    private double selectionRate;

    public GeneticAlgorithm(int populationSize, PhysicalNetwork physicalNetwork, VirtualNetwork virtualNetwork, double mutationRate, double selectionRate)
    {
	this.population = new Population(populationSize, physicalNetwork, virtualNetwork);
	this.populationSize = populationSize;
	this.mutationRate = mutationRate;
	this.selectionRate = selectionRate;
    }

    public PhysicalNetwork evolve()
    {
	PhysicalNetwork bestPhysicalNetwork = null;
	
	int numOfNoImprovementCicles = this.populationSize / 2;
	int cont = 0;
	Individual bestIndividual = null;
	
	while(true)
	{
	    List<Individual> newIndividuals = null;

	    // Seleção
	    List<Individual> fittestIndividuals = this.population.getFittestIndividuals(this.selectionRate);

	    // Crossing-Over
	    // Não será implementado para essa versão

	    // Mutação
	    newIndividuals = doPopulationMutation(fittestIndividuals);
	    this.population = new Population(newIndividuals);

	    if(bestIndividual != null)
	    {
		if(bestIndividual.getFitness() > fittestIndividuals.get(0).getFitness())
		{
		    bestIndividual = fittestIndividuals.get(0);
		    cont = 0;
		}
		else
		{
		    cont++;
		}
	    }
	    else
	    {
		bestIndividual = fittestIndividuals.get(0);
	    }
	    
	    if(cont >= numOfNoImprovementCicles)
	    {
		break;
	    }
	}
	
	bestPhysicalNetwork = bestIndividual.getPhysicalNetwork();
	
	return bestPhysicalNetwork;
    }

    private List<Individual> doPopulationMutation(List<Individual> fittestIndividuals)
    {
	List<Individual> newIndividuals = new ArrayList<Individual>(this.populationSize);
	newIndividuals.addAll(fittestIndividuals);

	int numOfFittestIndividuals = fittestIndividuals.size();
	int numOfIndividualsToBeGenerated = numOfFittestIndividuals - this.populationSize;
	int cont = 0;

	extern_while: while(true)
	{
	    for (Individual currentIndividual : fittestIndividuals)
	    {
		Individual newIndividual = SerializationUtils.clone(currentIndividual);
		doIndividualMutation(newIndividual);
		newIndividuals.add(newIndividual);
		cont++;
		
		if(cont >= numOfIndividualsToBeGenerated)
		{
		    break extern_while;
		}
	    }
	}
	
	return newIndividuals;
    }

    private void doIndividualMutation(Individual individual)
    {
	PhysicalNetwork physicalNetwork = individual.getPhysicalNetwork();
	List<VirtualNode> virtualNodes = individual.getVirtualNetwork().getVirtualNodes();
	int numOfNodes = virtualNodes.size();
	int numOfNodesToBeMutated = (int) (numOfNodes * this.mutationRate);
	List<Integer> nodesToBeMutatedIndices = new ArrayList<Integer>(numOfNodesToBeMutated);

	for (int i = 0; i < numOfNodesToBeMutated; i++)
	{
	    int chosenIndex = -1;

	    if (nodesToBeMutatedIndices.isEmpty())
	    {
		chosenIndex = ThreadLocalRandom.current().nextInt(0, numOfNodes);
		nodesToBeMutatedIndices.add(chosenIndex);
	    }
	    else
	    {
		do
		{
		    chosenIndex = ThreadLocalRandom.current().nextInt(0, numOfNodes);
		} while (nodesToBeMutatedIndices.contains(chosenIndex));

		nodesToBeMutatedIndices.add(chosenIndex);
	    }
	}

	for (int nodesIndex : nodesToBeMutatedIndices)
	{
	    VirtualNode currentVirtualNode = virtualNodes.get(nodesIndex);
	    physicalNetwork.randomlyAllocateVirtualNode(currentVirtualNode, individual.getVirtualNetwork());
	}
    }

    public Population getPopulation()
    {
	return population;
    }

    public double getMutationRate()
    {
	return mutationRate;
    }

    public double getReproductionSelectionRate()
    {
	return selectionRate;
    }
}
