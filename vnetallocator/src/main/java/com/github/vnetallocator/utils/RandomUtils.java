package com.github.vnetallocator.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.github.vnetallocator.network.link.PathInfo;

public class RandomUtils
{
    public static Integer getRandomIntegerElement(List<Integer> list)
    {
	Integer selectedElement = null;
	
	if(list != null && !list.isEmpty())
	{
	    int listSize = list.size();
	    int randomIndex = ThreadLocalRandom.current().nextInt(0, listSize);
	    selectedElement = list.get(randomIndex);
	}
	
	return selectedElement;
    }
    
    public static PathInfo getRandomPathInfoElement(List<PathInfo> list)
    {
	PathInfo selectedElement = null;
	
	if(list != null && !list.isEmpty())
	{
	    int listSize = list.size();
	    int randomIndex = ThreadLocalRandom.current().nextInt(0, listSize);
	    selectedElement = list.get(randomIndex);
	}
	
	return selectedElement;
    }
}
