package com.github.vnetallocator.genetic;

public class GeneticAlgorithm
{
    private Population population;
    private double crossingOverRate;
    private double mutationRate;
    private double reproductionSelectionRate;

    public GeneticAlgorithm(Population population, double crossingOverRate, double mutationRate,
	    double reproductionSelectionRate)
    {
	this.population = population;
	this.crossingOverRate = crossingOverRate;
	this.mutationRate = mutationRate;
	this.reproductionSelectionRate = reproductionSelectionRate;
    }

    public void evolve()
    {
	boolean stopEvolution = false;

	do
	{
	    // TODO Implementar a evolução da população
	    // TODO Implmentear uma função para calcular o critério de parada
	} while (!stopEvolution);
    }

    public Population getPopulation()
    {
	return population;
    }

    public double getCrossingOverRate()
    {
	return crossingOverRate;
    }

    public double getMutationRate()
    {
	return mutationRate;
    }

    public double getReproductionSelectionRate()
    {
	return reproductionSelectionRate;
    }
}
