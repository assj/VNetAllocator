package com.github.vnetallocator.network.node;

public class Node
{
    protected double processingCapacity;//GHz
    protected double amountOfMemory;	//MB
    protected double amountOfDisk;	//MB

    public Node(double processingCapacity, double amountOfMemory, double amountOfDisk)
    {
	this.processingCapacity = processingCapacity;
	this.amountOfMemory = amountOfMemory;
	this.amountOfDisk = amountOfDisk;
    }

}
