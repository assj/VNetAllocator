package com.github.vnetallocator.parser;

import com.github.vnetallocator.model.NetworkModel;
import com.github.vnetallocator.network.PhysicalNetwork;
import com.github.vnetallocator.network.VirtualNetwork;
import com.google.gson.Gson;

public class JsonParser
{
    public static VirtualNetwork parseVirtualNetwork(String virtualNetworktString)
    {
	VirtualNetwork virtualNetwork = null;

	if (virtualNetworktString != null && !virtualNetworktString.isEmpty())
	{
	    Gson gson = new Gson();
	    NetworkModel networkModel = gson.fromJson(virtualNetworktString, NetworkModel.class);
	    virtualNetwork = networkModel.persistVirtualNetwork();
	}

	return virtualNetwork;
    }

    public static PhysicalNetwork parsePhysicalNetworkModel(String physicalNetworktString)
    {
	PhysicalNetwork physicalNetwork = null;

	if (physicalNetworktString != null && !physicalNetworktString.isEmpty())
	{
	    Gson gson = new Gson();
	    NetworkModel networkModel = gson.fromJson(physicalNetworktString, NetworkModel.class);
	    physicalNetwork = networkModel.persistPhysicalNetwork();
	}

	return physicalNetwork;
    }
}
