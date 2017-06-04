package com.github.vnetallocator.genetic;

import java.io.Serializable;

import com.github.vnetallocator.network.PhysicalNetwork;
import com.github.vnetallocator.network.VirtualNetwork;

public class Individual implements Comparable<Individual>, Serializable
{
    private static final long serialVersionUID = -8469931308144459123L;
    
    private PhysicalNetwork physicalNetwork;
    private VirtualNetwork virtualNetwork;
    private double fitness;
    
    public Individual(PhysicalNetwork physicalNetwork, VirtualNetwork virtualNetwork)
    {
	this.physicalNetwork = physicalNetwork;
	this.virtualNetwork = virtualNetwork;
	this.fitness = Evaluator.evaluateIndividual(this);
    }
    
    @Override
    public int compareTo(Individual individual)
    {
	return new Double(this.fitness).compareTo(individual.getFitness());
    }
    
    public PhysicalNetwork getPhysicalNetwork()
    {
	return physicalNetwork;
    }
    
    public VirtualNetwork getVirtualNetwork()
    {
	return virtualNetwork;
    }
    
    public double getFitness()
    {
	this.fitness = Evaluator.evaluateIndividual(this);
	return fitness;
    }
}
