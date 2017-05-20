package com.github.vnetallocator.genetic;

import java.util.List;

public class Population
{
    private List<Individual> individuals;
    
    public Population(List<Individual> individuals)
    {
	this.individuals = individuals;
    }
    
    public List<Individual> getIndividuals()
    {
	return individuals;
    }
}
