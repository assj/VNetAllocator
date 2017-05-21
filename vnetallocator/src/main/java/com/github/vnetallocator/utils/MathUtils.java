package com.github.vnetallocator.utils;

public class MathUtils
{
    public static double variance(double[] array)
    {
	double variance = 0;
	
	double mean = 0;
	
	for(int i = 0; i < array.length; i++)
	{
	    mean += array[i];
	}
	
	mean /= array.length;
	
	for(int i = 0; i < array.length; i++)
	{
	    variance += Math.pow((array[i] - mean), 2);
	}
	
	variance /= array.length;
	
	return variance;
    }
    
    public static double standardDeviation(double[] array)
    {
	double standardDeviation = 0;
	
	standardDeviation = Math.sqrt(MathUtils.variance(array));
	
	return standardDeviation;
    }
}
