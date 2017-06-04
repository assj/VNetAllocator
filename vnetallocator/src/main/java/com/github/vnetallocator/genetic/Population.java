package com.github.vnetallocator.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;

import com.github.vnetallocator.network.PhysicalNetwork;
import com.github.vnetallocator.network.VirtualNetwork;

public class Population
{
    private List<Individual> individuals;
    private int size;
    
    public Population(List<Individual> individuals)
    {
	this.individuals = individuals;
	this.size = individuals.size();
    }
    
    public Population(int size, PhysicalNetwork physicalNetwork, VirtualNetwork virtualNetwork)
    {
	this.individuals = new ArrayList<Individual>(size);
	this.size = size;
	
	for(int i = 0; i < size; i++)
	{
	    PhysicalNetwork currentPhysicalNetwork = SerializationUtils.clone(physicalNetwork);
	    currentPhysicalNetwork.randomlyAllocateVirtualNetwork(virtualNetwork);
	    Individual individual = new Individual(currentPhysicalNetwork, virtualNetwork);
	    this.individuals.add(individual);
	}
    }

    public List<Individual> getFittestIndividuals(double reproductionSelectionRate)
    {
	List<Individual> fittestIndividuals = null;
	int numberOfSelectedIndividuals = (int) (this.individuals.size() * reproductionSelectionRate);
	
	Collections.sort(this.individuals);
	
	fittestIndividuals = this.individuals.subList(0, numberOfSelectedIndividuals);
	
	return fittestIndividuals;
	
    }
    
    public List<Individual> getIndividuals()
    {
	return individuals;
    }
    
    public int size()
    {
	return this.size;
    }
}
