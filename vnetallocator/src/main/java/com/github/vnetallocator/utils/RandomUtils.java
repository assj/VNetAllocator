package com.github.vnetallocator.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils
{
    public static Integer getRandomElement(List<Integer> list)
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
}
