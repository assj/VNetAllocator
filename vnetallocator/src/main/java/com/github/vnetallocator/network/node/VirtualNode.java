package com.github.vnetallocator.network.node;

import com.github.vnetallocator.model.NodeModel;

public class VirtualNode extends Node
{
    private static final long serialVersionUID = 9123403486195902371L;
    
    private int physicalNodeIndex;

    public VirtualNode(NodeModel virtualNodeModel)
    {
	super(virtualNodeModel.getId(), virtualNodeModel.getProcessingCapacity(), virtualNodeModel.getAmountOfMemory(),
		virtualNodeModel.getAmountOfDisk());
	this.physicalNodeIndex = -1;

    }

    public int getId()
    {
	return id;
    }

    public int getPhysicalNodeIndex()
    {
	return physicalNodeIndex;
    }

    public void setPhysicalNodeIndex(int physicalNodeIndex)
    {
	this.physicalNodeIndex = physicalNodeIndex;
    }
}
