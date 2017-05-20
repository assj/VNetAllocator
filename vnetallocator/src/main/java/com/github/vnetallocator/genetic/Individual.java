package com.github.vnetallocator.genetic;

import com.github.vnetallocator.network.Network;

public class Individual
{
    private Network network;
    
    public Individual(Network network)
    {
	this.network = network;
    }
    
    public Network getNetwork()
    {
	return network;
    }
}
