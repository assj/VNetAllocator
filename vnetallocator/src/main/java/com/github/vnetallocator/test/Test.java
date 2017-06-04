package com.github.vnetallocator.test;

import com.github.vnetallocator.network.VirtualNetwork;
import com.github.vnetallocator.parser.JsonParser;

public class Test
{

    public static void main(String[] args)
    {
	String allocationRequestString = "{\"nodes\":[{\"id\":1,\"processingCapacity\":11,\"amountOfMemory\":12,\"amountOfDisk\":4},{\"id\":2,\"processingCapacity\":4,\"amountOfMemory\":4,\"amountOfDisk\":1},{\"id\":3,\"processingCapacity\":10,\"amountOfMemory\":17,\"amountOfDisk\":5}],\"links\": [{\"node1\":1,\"node2\":2,\"delay\":762,\"speed\":30},{\"node1\":1,\"node2\":3,\"delay\":190,\"speed\":26},{\"node1\":2,\"node2\":3,\"delay\":482,\"speed\":28}]}";
	VirtualNetwork virtualNetwork = JsonParser.parseVirtualNetwork(allocationRequestString);
	System.out.println();

    }

}
