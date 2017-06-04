package com.github.vnetallocator.model;

import java.io.Serializable;

public class LinkModel implements Serializable
{
    private static final long serialVersionUID = -8746195677548591711L;
    
    private int id;
    private int node1;
    private int node2;
    private double delay;
    private double speed;

    public LinkModel(int id, int node1, int node2, double delay, double speed)
    {
	this.id = id;
	this.node1 = node1;
	this.node2 = node2;
	this.delay = delay;
	this.speed = speed;
    }

    public int getId()
    {
	return id;
    }
    
    public int getNode1()
    {
	return node1;
    }

    public int getNode2()
    {
	return node2;
    }

    public double getDelay()
    {
	return delay;
    }

    public double getSpeed()
    {
	return speed;
    }

}
