package com.github.vnetallocator.network.link;

import java.util.List;

public class PathInfo
{
    private double aggregatedLinksDelay;
    private List<PhysicalLink> physicalLinksInPath;
    
    public PathInfo(double aggregatedLinksDelay, List<PhysicalLink> physicalLinksInPath)
    {
	this.aggregatedLinksDelay = aggregatedLinksDelay;
	this.physicalLinksInPath = physicalLinksInPath;
    }
    
    public double getAggregatedLinksDelay()
    {
	return aggregatedLinksDelay;
    }
    
    public List<PhysicalLink> getPhysicalLinksInPath()
    {
	return physicalLinksInPath;
    }
}
