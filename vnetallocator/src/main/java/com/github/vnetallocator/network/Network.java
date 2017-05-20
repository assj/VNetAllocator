package com.github.vnetallocator.network;

import java.util.List;

import com.github.vnetallocator.network.link.Link;

public class Network
{
    private List<Link> links;
    
    public Network(List<Link> links)
    {
	this.links = links;
    }
    
    public List<Link> getLinks()
    {
	return links;
    }
}
