package com.github.vnetallocator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

import com.github.vnetallocator.genetic.GeneticAlgorithm;
import com.github.vnetallocator.network.PhysicalNetwork;
import com.github.vnetallocator.network.VirtualNetwork;
import com.github.vnetallocator.parser.JsonParser;

public class Allocator
{
    /**
     * @param args [0] - Tamanho da população
     * @param args [1] - Taxa de mutação (0 - 1)
     * @param args [2] - Taxa de seleção (0 - 1)
     * @param args [3] - Quantidade de requisições
     */
    public static void main(String[] args)
    {
	// TODO Adicionar logs no sistema.

	if (args.length == 4)
	{
	    int populationSize = Integer.parseInt(args[0]);
	    double mutationRate = Double.parseDouble(args[1]);
	    double selectionRate = Double.parseDouble(args[2]);
	    int numOfRequisitions = Integer.parseInt(args[3]);

	    File jsonTopologyFile = new File(Allocator.class.getClassLoader().getResource("RNP.json").getFile());

	    if (jsonTopologyFile.exists() && jsonTopologyFile.isFile())
	    {
		try
		{
		    String jsonTopologyFileContent = IOUtils
			    .toString(new FileReader(jsonTopologyFile.getAbsolutePath()));
		    PhysicalNetwork physicalNetwork = JsonParser.parsePhysicalNetworkModel(jsonTopologyFileContent);

		    long startTime = System.currentTimeMillis();

		    System.out.println("--------------------------------------------------");
		    System.out.println("INICIO");
		    System.out.println("--------------------------------------------------\n");

		    for (int i = 0; i < numOfRequisitions; i++)
		    {
			long requisitionStartTime = System.currentTimeMillis();
			String jsonRequisitionContent = runRequisitionGenerator();
			VirtualNetwork virtualNetwork = JsonParser.parseVirtualNetwork(jsonRequisitionContent);

			int numOfNodes = virtualNetwork.getVirtualNodes().size();
			int numOfLinks = virtualNetwork.getVirtualLinks().size();

			System.out.println("##################################################");
			System.out
				.println("Requisicao " + (i + 1) + " - N:" + numOfNodes + " | L:" + numOfLinks + "\n");

			GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(populationSize, physicalNetwork,
				virtualNetwork, mutationRate, selectionRate);
			physicalNetwork = geneticAlgorithm.evolve();

			long requisitionEndTime = System.currentTimeMillis();
			
			float elapsedRequisitionTime = (requisitionEndTime - requisitionStartTime) / 1000;
			
			System.out.printf("\nTempo decorrido: %.1f segundos\n", elapsedRequisitionTime);
			System.out.println("##################################################\n");
		    }

		    long endTime = System.currentTimeMillis();

		    float elapsedTime = (endTime - startTime) / 1000;
		    
		    System.out.println("Rede final: ");
		    System.out.println(physicalNetwork.toString());
		    System.out.println("\n--------------------------------------------------");
		    System.out.println("FIM");
		    System.out.println("--------------------------------------------------");
		    System.out.printf("\nTempo decorrido: %.1f segundos\n", elapsedTime);
		}
		catch (IOException e)
		{
		    e.printStackTrace();
		}
	    }
	    else
	    {
		// TODO Log de erro - arquivo inválido
	    }
	}
	else
	{
	    // TODO Log de erro - quantidade de parâmetros inválida
	}
    }

    private static String runRequisitionGenerator()
    {
	String jsonRequisitionContent = null;

	File requestGeneratorFile = new File(
		Allocator.class.getClassLoader().getResource("request_generator.py").getFile());
	String runtimeString = "python " + requestGeneratorFile.getAbsolutePath();

	Process process;

	try
	{
	    process = Runtime.getRuntime().exec(runtimeString);
	    BufferedReader bfReaderReturned = new BufferedReader(new InputStreamReader(process.getInputStream()));
	    jsonRequisitionContent = bfReaderReturned.readLine();
	    bfReaderReturned.close();
	}
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return jsonRequisitionContent;
    }
}
